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

package de.hsa.enums;

public enum SettingParameter {
	SETAUTOMODE(new String[]{"1"}),
	SETAUTOSTOPTIMER(new String[]{"500"}),
	SETNOTIFYMODE(new String[]{"1"}),
	SETNOTIFYTRIGGER(new String[]{"TrueFalse"}),
	SETNOTIFYFORMAT(new String[]{"XML"}),
	SETNOTIFYADDRESS(new String[]{"192.168.1.2", "4000"}),
	SETPERSISTTIME(new String[]{"1"}),
	SETALIENIP(new String[]{"192.168.1.100", "23"}),
	SETSIMPLELOG(new String[]{"false"}),
	SETSERVICEPORT(new String[]{"4000"}),
	SETANTENNASEQUENCE(new String[]{"0 1 3 2 | 2 3 1 0"})
	;
	
	private String[] defaultValue;
	
	SettingParameter(String[] defaultValue){
		this.defaultValue = defaultValue;
	}
	
	public String[] getDefault(){
		return this.defaultValue;
	}
}
