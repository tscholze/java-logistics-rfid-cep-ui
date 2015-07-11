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
public class RuleContainer implements Serializable {

    private String rule;
    private String uniqueName;
    private String path;
    private String className;
    private String description;
    private boolean addEvent;

    public RuleContainer(String uniqueName, String rule, String path, String className, String description, boolean addEvent) {
        this.uniqueName = uniqueName;
        this.rule = rule;
        this.description = description;
        this.addEvent = addEvent;
        this.path = path;
        this.className = className;
    }

    public String getRule() {
        return rule;
    }

    public String getPath() {
        return path;
    }

    public String getClassName() {
        return className;
    }

    public boolean isAddEvent() {
        return addEvent;
    }

    public String getUniqueName() {
        return uniqueName;

    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "RuleContainer{" +
                "rule='" + rule + '\'' +
                ", uniqueName='" + uniqueName + '\'' +
                ", path='" + path + '\'' +
                ", className='" + className + '\'' +
                ", description='" + description + '\'' +
                ", addEvent=" + addEvent +
                '}';
    }

    public RuleContainer(String uniqueName, String rule, String path, String className) {
        this(uniqueName, rule, path, className, "(no description)", true);
    }
}
