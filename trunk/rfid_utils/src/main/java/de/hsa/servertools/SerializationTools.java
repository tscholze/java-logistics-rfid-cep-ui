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

import java.io.*;

/**
 * @author : Falk Alexander
 *         Date: 19.05.12
 *         Time: 20:27
 */
public class SerializationTools {
    public static String getDefaultFileName(Object target ) {
        return target.getClass().getName() + ".data";
    }
    public static String saveToDisk(Serializable target) throws IOException {
        return saveToDisk(target, null);
    }

    public static String saveToDisk(Serializable target, String targetFile) throws IOException {
        if (targetFile == null) {
            targetFile = getDefaultFileName(target);
        }

        // Write to disk with FileOutputStream
        FileOutputStream f_out = new FileOutputStream(targetFile);

        // Write object with ObjectOutputStream
        ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

        // Write object out to disk
        obj_out.writeObject(target);

        return targetFile;
    }
    
    public static Object resumeFromDisk(String filename) throws IOException, ClassNotFoundException {
        // Read from disk using FileInputStream
        FileInputStream f_in = new FileInputStream(filename);

        // Read object using ObjectInputStream
        ObjectInputStream obj_in = new ObjectInputStream (f_in);

        // Read an object
        return obj_in.readObject();
    }
}
