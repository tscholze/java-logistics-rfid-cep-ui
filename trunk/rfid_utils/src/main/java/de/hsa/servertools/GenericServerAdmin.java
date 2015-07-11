/*
 * Visualisierung eines RFID-Scanners
 * Copyright (C) 2012  ss12rfid-Team
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.hsa.servertools;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import static de.hsa.servertools.SerializationTools.*;

/**
 * @author : Falk Alexander
 *         Date: 19.05.12
 *         Time: 21:40
 *
 * This class dose not work. It is used but dose not more than causing overhead.
 * Do not use this unless you know what you are doing!
 */
public class GenericServerAdmin {

    @Deprecated
    public GenericServerAdmin(String[] args , Runnable serverMainClass) throws IOException, ClassNotFoundException {
        if (args.length < 0) {
            System.out.println("No command was given on the commandline. Try one of these: " + Arrays.toString(ServerCommands.values()));
        }
        ServerCommands com = ServerCommands.valueOf(args[0].toUpperCase());
        switch (com) {
            case START:
                if (checkForRemains(getDefaultFileName(serverMainClass))) {
                    System.out.println("Remaining server class found! Du you really want to start a new server? Then try FORESTART instead.");
                } else { // no remains were found
                    Thread t = new Thread(serverMainClass);
                    t.start();
                    //saveClass(serverMainClass);
                }
                break;
            case STOP:
                PrimitiveServerControler primitiveServerControler = (PrimitiveServerControler) resumeFromDisk(getDefaultFileName(serverMainClass));
                try {
                    primitiveServerControler.stopServer();
                } catch (Exception e) {
                    System.err.println("Sorry :(\nI wasn't able to clean up.");
                    //e.printStackTrace();
                }
                deleteFile(primitiveServerControler);
                break;
            case FORCESTART:
                Thread t1 = new Thread(serverMainClass);
                t1.start();
                deleteFile(serverMainClass);
                //saveClass(serverMainClass);
                break;
            default:
                break;
        }
    }

    private void saveClass(Object target) throws IOException {
        try {
            saveToDisk((Serializable) target);
        } catch (IOException e) {
            System.out.println("Your serverMainClass is not serializable!");
            throw e;
        }
    }

    private boolean checkForRemains(String file) {
        if (new File(file).isFile()) return true;
        else return false;
    }

    private void deleteFile(Object file) {
        //@TODO: this dose not work
        new File(getDefaultFileName(file)).delete();
    }
}
