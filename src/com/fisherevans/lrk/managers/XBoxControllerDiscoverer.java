package com.fisherevans.lrk.managers;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.notifications.Notifications;
import com.fisherevans.lrk.notifications.types.Notification;
import de.hardcode.jxinput.JXInputDevice;
import de.hardcode.jxinput.JXInputManager;
import de.hardcode.jxinput.event.JXInputEventManager;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/20/13
 * Time: 7:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class XBoxControllerDiscoverer implements Runnable
{
    public static boolean run = true;
    public static final long SLEEP_TIME = 30*1000;
    public static final String CONNECTED = "XBox 360 Controller Connected!";
    public static final String DISCONNECTED = "XBox 360 Controller Disconnected!";

    public XBoxControllerDiscoverer()
    {
        JXInputEventManager.setTriggerIntervall(-1);
    }

    @Override
    public void run()
    {
        while(run)
        {
            boolean found = false;

            JXInputDevice dev;
            String name;
            JXInputManager.updateFeatures();
            for(int id = 0;id < JXInputManager.getNumberOfDevices();id++)
            {
                name = JXInputManager.getJXInputDevice(id).getName().toLowerCase();
                LRK.log(name);
                if(name.contains("xbox") && name.contains("360"))
                {
                    if(InputManager.getXboxController() == null)
                    {
                        Game.lrk.getNotifications().addNotification(new Notification(CONNECTED, Notification.GREY, Notifications.IMG_COG));
                        InputManager.setXboxController(new XBoxController(JXInputManager.getJXInputDevice(id)));
                    }
                    if(InputManager.getXboxController() != null)
                    {
                        found = true;
                        break;
                    }
                }
            }

            LRK.log("Found? " + found + " - Null? " + (InputManager.getXboxController() == null));
            if(!found)
            {
                if(InputManager.getXboxController() != null)
                    Game.lrk.getNotifications().addNotification(new Notification(DISCONNECTED, Notification.GREY, Notifications.IMG_COG));
                InputManager.setXboxController(null);
            }

            try
            {
                Thread.sleep(SLEEP_TIME);
            }
            catch (InterruptedException e)
            {
                LRK.log("Failed to sleep in XBoxControllerDiscoverer");
            }

            //LRK.log("Scanning for controller");
        }
    }
}
