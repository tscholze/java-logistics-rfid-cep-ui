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

import com.alien.enterpriseRFID.tags.Tag;

import java.io.Serializable;

/**
 * @author : Falk Alexander
 *         Date: 18.05.12
 *         Time: 20:59
 *
 * This class copies all attributes from the AlienTag. AlienTags can not be
 * send through the network because they are not serializeable. So this class
 * will represent the AlienTags on other components.
 * For the meaning of all of the attributes look at the Alien Dokumentation.
 */
public class RfidTag implements Serializable {
    int antenna;
    long CRC;
    int direction;
    long discoverTime;
    String[] G2Data;
    long hostDiscoverTime;
    long hostRenewTime;
    long persistTime;
    int protocol;
    String protocolString;
    int receiveAntenna;
    int renewCount;
    long renewTime;
    double RSSI;
    double smoothPosition;
    double smoothSpeed;
    double speed;
    String tagID;
    long timeToLive;
    int transmitAntenna;

    public RfidTag(Tag tag) {
        this.antenna = tag.getAntenna();
        this.CRC = tag.getCRC();
        this.direction = tag.getDirection();
        this.discoverTime = tag.getDiscoverTime();
        this.G2Data = tag.getG2Data();
        this.hostDiscoverTime = tag.getHostDiscoverTime();
        this.hostRenewTime = tag.getHostRenewTime();
        this.persistTime = tag.getPersistTime();
        this.protocol = tag.getProtocol();
        this.protocolString = tag.getProtocolString();
        this.receiveAntenna = tag.getReceiveAntenna();
        this.renewCount = tag.getRenewCount();
        this.renewTime = tag.getRenewTime();
        this.RSSI = tag.getRSSI();
        this.smoothPosition = tag.getSmoothPosition();
        this.smoothSpeed = tag.getSmoothSpeed();
        this.speed = tag.getSpeed();
        this.tagID = tag.getTagID();
        this.timeToLive = tag.getTimeToLive();
        this.transmitAntenna = tag.getTransmitAntenna();
    }

    @Override
    public String toString() {
        return "RfidTag{" +
                "tagID='" + tagID + '\'' +
                '}';
    }

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return true;
		return ((RfidTag) o).getTagID().equals(tagID);
	}

    public String toLongString() {
        return "RfidTag{" +
                "tagID='" + tagID + '\'' +
                ", discoverTime=" + discoverTime +
                ", hostRenewTime=" + hostRenewTime +
                ", antenna=" + antenna +
                ", renewCount=" + renewCount +
                '}';
    }

    public int getAntenna() {
        return antenna;
    }

    public long getCRC() {
        return CRC;
    }

    public int getDirection() {
        return direction;
    }

    public long getDiscoverTime() {
        return discoverTime;
    }

    public String[] getG2Data() {
        return G2Data;
    }

    public long getHostDiscoverTime() {
        return hostDiscoverTime;
    }

    public long getHostRenewTime() {
        return hostRenewTime;
    }

    public long getPersistTime() {
        return persistTime;
    }

    public int getProtocol() {
        return protocol;
    }

    public String getProtocolString() {
        return protocolString;
    }

    public int getReceiveAntenna() {
        return receiveAntenna;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public long getRenewTime() {
        return renewTime;
    }

    public double getRSSI() {
        return RSSI;
    }

    public double getSmoothPosition() {
        return smoothPosition;
    }

    public double getSmoothSpeed() {
        return smoothSpeed;
    }

    public double getSpeed() {
        return speed;
    }

    public String getTagID() {
        return tagID;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public int getTransmitAntenna() {
        return transmitAntenna;
    }
}
