package com.fisherevans.lrk.notifications;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.notifications.types.Notification;
import com.fisherevans.lrk.states.GFX;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/15/13
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Notifications
{
    public final int margin = 30;
    public final int padding = 20;
    public final Color background = new Color(1f, 0, 0, 0.6f);
    public final Color foreground = new Color(1f, 1f, 1f);

    private ArrayList<Notification> _notfications;

    private UnicodeFont font;

    public Notifications()
    {
        _notfications = new ArrayList<>();
    }

    public void init()
    {
        font = Resources.getFont(2);
    }

    public void update(float delta)
    {
        if(_notfications == null)
            return;

        Notification notification;
        for(int notificationId = 0;notificationId < _notfications.size();notificationId++)
        {

            notification = _notfications.get(notificationId);
            notification.update(delta);

            if(notification.isExpired())
            {
                _notfications.remove(notificationId);
                notificationId--;
            }
        }
    }

    public void render(Graphics gfx)
    {
        if(_notfications == null)
            return;

        int totalHeight = padding*2 + margin + font.getHeight("|");

        Notification notification;
        Color fore, back;
        float aScale;
        for(int notificationId = 0;notificationId < _notfications.size();notificationId++)
        {
            notification = _notfications.get(notificationId);

            aScale = (float) (Math.pow(1f-notification.getInterpolation(),0.5));
            fore = new Color(foreground.r, foreground.g, foreground.b, foreground.a*aScale);
            back = new Color(background.r, background.g, background.b, background.a*aScale);

            gfx.setColor(back);
            gfx.fillRect(margin, totalHeight*notificationId + margin, padding*2 + font.getWidth(notification.getMessage()), padding*2 + font.getLineHeight());
            GFX.drawTextAbsolute(margin + padding, totalHeight*notificationId + margin + padding, font, fore, notification.getMessage());
        }
    }

    public void addNotification(Notification notification)
    {
        _notfications.add(notification);
    }
}
