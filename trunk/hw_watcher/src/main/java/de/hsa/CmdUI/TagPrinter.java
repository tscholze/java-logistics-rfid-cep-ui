package de.hsa.CmdUI;

import de.hsa.RfidTag;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * @author : Falk Alexander
 *         Date: 05.06.12
 *         Time: 20:44
 */
public class TagPrinter implements Observer {

    private SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );

    @Override
    public void update(Observable o, Object arg) {
        Map<String, Object> TagMessage = (Map) arg;
        System.out.println(df.format(TagMessage.get("timeStamp")) + ": ");
        RfidTag[] tags = (RfidTag[]) TagMessage.get("content");
        for (RfidTag tag : tags) {
            System.out.println(tag.toLongString());
        }
    }
}
