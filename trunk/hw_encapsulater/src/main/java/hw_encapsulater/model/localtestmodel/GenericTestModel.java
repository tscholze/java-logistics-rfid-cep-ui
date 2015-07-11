package hw_encapsulater.model.localtestmodel;

import com.alien.enterpriseRFID.notify.Message;
import com.alien.enterpriseRFID.tags.Tag;
import hw_encapsulater.model.GenericModel;

import javax.jms.ObjectMessage;

/**
 * To make local testing easier a custom test can be derived from 
 * GenericTestModel. By implementing the abstract methods the behavior of
 * TagList can be influenced. This class sends a notify-event to all observing
 * classes. The frequency of this events is given by the notifyFrequency method
 * in microseconds.<br \>
 * The method initializeTagList must be called one time to create a tag-list.
 * The modifyTags method changes the tag-list as you wish. It is called when
 * notifyFrequency expires.
 * 
 * @author Falk Alexander
 *
 */
public abstract class GenericTestModel extends GenericModel {

    protected boolean isRunning = false;
    protected Tag[] tagList = new Tag[0];

    public GenericTestModel() {
        this.initializeTagList();
    }

    @Override
    public void run() {
        while (isRunning) {
            this.modifyTags();
            this.observeData();
            try {
                Thread.sleep(notifyFrequency());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void startStopReader(boolean running) {
        this.isRunning = running;
        if (running) {
            this.start();
        } else {
            this.stop();
        }
    }

    /**
     * Returns the time to wait until the next event is sent. A calue of 2000
     * means a time of 2 seconds.
     * 
     * @return time to wait in microseconds
     */
    public abstract long notifyFrequency();

    /**
     * Changes the tagList when notifyFrequency expires.
     */
    public abstract void modifyTags();
    
    /**
     * Notifies the Observers that data has changed and sends the new Tag[].
     */
    public void observeData() {
        setChanged();
        notifyObservers(this.tagList);
    }

    @Override
    public void start() {
        this.isRunning = true;
        new Thread(this).start();
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    /**
     * This method is called by the constructor and creats the tag-list for 
     * the fist use. After this the method is not called anymore.
     */
    public abstract void initializeTagList();

    @Override
    public Tag[] getTagList() {
        return this.tagList;
    }

    @Override
    public void setStdOut(boolean stdOut) {
        notImplemented(this.getClass().getEnclosingMethod().toString());
        
    }

    @Override
    public void setSimpleLog(boolean simpleLog) {
        notImplemented(this.getClass().getEnclosingMethod().toString());
        
    }

    @Override
    public void messageReceived(Message arg0) {
        notImplemented(this.getClass().getEnclosingMethod().toString());
        
    }

    @Override
    public void onMessage(javax.jms.Message arg0) {
        notImplemented(this.getClass().getEnclosingMethod().toString());

    }

    @Override
    protected void setDefaultSettings() {
        notImplemented(this.getClass().getEnclosingMethod().toString());

    }

    @Override
    protected void applysettings(ObjectMessage message) {
        notImplemented(this.getClass().getEnclosingMethod().toString());

    }

    @Override
    protected void shutdown() {
        this.stop();
    }

    @Override
    protected void exit() {
        this.stop();

    }

    @Override
    protected void setup() {
        notImplemented(this.getClass().getEnclosingMethod().toString());
    }

    private void notImplemented(String m) {
        System.out.println("forced to call a not implemented method :( " + m);
    }
}
