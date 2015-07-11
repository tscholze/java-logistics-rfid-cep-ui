#!/bin/bash -e

function start() {
    # run hornetq
    cd ~/hornetq-2.2.14.Final/bin
    ./run.sh &

    # hornetq needs to warm up
    sleep 20

    # run cep server
    xterm -e "cd ~/ss12rfid/trunk/cep_server/target; java -jar cep_server-1.0-jar-with-dependencies.jar" &

    # run db
    xterm -e "cd ~/ss12rfid/trunk/db_module; mvn exec:java -Dexec.mainClass='org.hsqldb.Server' -Dexec.args='-database.0 file:target/data/db_module'" &
    sleep 9
    # if not main class from pom then cp
    xterm -e "cd ~/ss12rfid/trunk/db_module/target; java -cp db_module-1.0-jar-with-dependencies.jar de.hsa.TestLauncher" &
    sleep 9
    xterm -e "cd ~/ss12rfid/trunk/db_module/target; java -jar db_module-1.0-jar-with-dependencies.jar de.hsa.RunListener" &

    # run web module
    xterm -e "cd ~/ss12rfid/trunk/web_module; mvn jetty:run" &

    # run hw encapsulator
    xterm -e "cd ~/ss12rfid/trunk/hw_encapsulater/target; java -cp hw_encapsulater-1.0-jar-with-dependencies.jar hw_encapsulater.usecases.DeliverHanutaUsecase" &

    # run gui
    xterm -e "cd ~/ss12rfid/branches/GraphicalUserInterface/target; java -jar nice_gui-0.0.1-SNAPSHOT-jar-with-dependencies.jar" &
}

function initialise() {
    killall java || true
    cd ~
    if [[ ! -e ~/hornetq-2.2.14.Final.zip ]]; then
	    wget http://downloads.jboss.org/hornetq/hornetq-2.2.14.Final.zip
        unzip hornetq-2.2.14.Final.zip
	cp ~/ss12rfid/examples/hornetq_config_example/hornetq-jms.xml ~/hornetq-2.2.14.Final/config/stand-alone/non-clustered
    fi

    # build rfid utils
    if [[ -d ~/.m2/repository/AlienRFID ]]; then
        cd ~/ss12rfid/trunk/rfid_utils
        mvn install
    else
        cd ~/ss12rfid/branches/standalone_prototype/resources
        mvn install:install-file -Dfile=AlienRFID.jar -DgroupId=AlienRFID \
		-DartifactId=AlienRFID -Dversion=0.0.0 -Dpackaging=jar
    fi

    # build cep server
    cd ~/ss12rfid/trunk/cep_server
    mvn package

    # build db module
    cd ~/ss12rfid/trunk/db_module
    mvn clean
    mvn install

    # build web module
    cd ~/ss12rfid/trunk/web_module
    mvn install

    # build hw encapsulator
    cd ~/ss12rfid/trunk/hw_encapsulater
    mvn package

    # build java gui
    cd ~/ss12rfid/branches/GraphicalUserInterface/
    mvn package

    start

}

case $1 in
    restart)
        killall java || true
        start
        ;;
    init)
        killall java || true
        initialise
        ;;
    *)
        echo $"Usage: $0 {restart|init}"
        exit 1
esac
