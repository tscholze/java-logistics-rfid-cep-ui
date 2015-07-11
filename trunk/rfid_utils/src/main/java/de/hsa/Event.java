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

package de.hsa;

import java.io.Serializable;

/**
 * @author : Falk Alexander
 *         Date: 25.05.12
 *         Time: 12:43
 */
public class Event implements Serializable {

    private String id;
    private RfidTag[] tags;

    public Event(String id, RfidTag[] tags) {
        this.id = id;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public RfidTag[] getTags() {
        return tags;
    }
}
