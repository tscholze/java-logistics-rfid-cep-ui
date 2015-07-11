package de.hsa.CmdUI;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * @author : Falk Alexander
 *         Date: 05.06.12
 *         Time: 20:44
 */
public class LSPrinter implements Observer {

    private SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> LSMessage = (Map) arg;
        System.out.println(df.format(LSMessage.get("timeStamp")) + ": " + LSMessage.get("sensorName") + " has status " +
                LSMessage.get("status"));
    }
}
