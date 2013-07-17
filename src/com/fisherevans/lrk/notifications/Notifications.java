package com.fisherevans.lrk.notifications;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.notifications.types.Notification;
import com.fisherevans.lrk.states.GFX;
import org.newdawn.slick.Color;
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
    public final int margin = 10;
    public final int padding = 17;
    private final float slideSpeed = margin*padding;

    private ArrayList<Notification> _notifications;

    private UnicodeFont font;

    private float height, slide;

    public Notifications()
    {
        _notifications = new ArrayList<>();
    }

    public void init()
    {
        font = Resources.getFont(1);

        height = padding*2 + margin + font.getHeight("|");
    }

    public void update(float delta)
    {
        if(_notifications == null || _notifications.size() == 0)
            return;

        Notification notification;
        for(int notificationId = 0;notificationId < _notifications.size();notificationId++)
        {

            notification = _notifications.get(notificationId);
            notification.update(delta);

            if(notification.isExpired())
            {
                _notifications.remove(notificationId);
                notificationId--;
                slide += height;
            }
        }

        slide -= (slide+slideSpeed)*delta;
        slide = slide < 0 || _notifications.size() == 0 ? 0 : slide;
    }

    public void render(Graphics gfx)
    {
        if(_notifications == null || _notifications.size() == 0)
            return;

        Notification notification;
        Color fore, back, foreground, background;
        float aScale;
        for(int notificationId = 0;notificationId < _notifications.size();notificationId++)
        {
            notification = _notifications.get(notificationId);

            aScale = (float) (Math.pow(1f-notification.getInterpolation(),0.35));
            foreground = notification.getForeground();
            background = notification.getBackground();
            fore = new Color(foreground.r, foreground.g, foreground.b, foreground.a*aScale);
            back = new Color(background.r, background.g, background.b, background.a*aScale);

            gfx.setColor(back);
            gfx.fillRect(margin, slide + height*notificationId + margin, padding*2 + font.getWidth(notification.getMessage()), padding*2 + font.getLineHeight());
            GFX.drawTextAbsolute(margin + padding, slide + height*notificationId + margin + padding, font, fore, notification.getMessage());
        }
    }

    public void addNotification(Notification notification)
    {
        _notifications.add(notification);
    }
}
