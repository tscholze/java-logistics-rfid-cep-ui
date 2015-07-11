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

package examples.groupchat_extended.src.main.java.groupchat_extended;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * User: Falk Alexander
 * Date: 17.05.12
 * Time: 16:05
 *
 * Dummyclass created as an example that serialization is possible.
 */
public class CustomMessage implements Serializable {
    private String message;
    private  String user;
    private String timestamp;

    @Override
    public String toString() {
        return "(" + timestamp + ") " + user + ": " + message;
    }

    public String getMessage() {
        return message;
    }

    public String getUser() {
        return user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public CustomMessage (String user, String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        this.timestamp = dateFormat.format(System.currentTimeMillis());
        this.message = message;
        this.user = user;
    }
    
}
