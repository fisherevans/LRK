package com.fisherevans.lrk.notifications;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.notifications.types.Notification;
import com.fisherevans.lrk.states.GFX;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
    public static Image IMG_BAG, IMG_COG, IMG_QUEST, IMG_SWORD;

    public final int margin = 10;
    public final int padding = 12;
    private final float slideSpeed = margin*padding*1.5f;

    private ArrayList<Notification> _notifications;

    private UnicodeFont font;

    private float height, textY, textRight, slide;

    private Image _bgLeft, _bgMid, _bgRight;

    private int renderScale = 2;

    public Notifications()
    {
        _notifications = new ArrayList<>();
    }

    public void init()
    {
        font = Resources.getFont(1);

        _bgLeft = Resources.getImage("res/test/images/notification_bg_left.png");
        _bgMid = Resources.getImage("res/test/images/notification_bg_mid.png");
        _bgRight = Resources.getImage("res/test/images/notification_bg_right.png");

        height = _bgLeft.getHeight() + margin;
        textY = _bgLeft.getHeight()/2f - font.getHeight("|")/2f;
        textRight = _bgLeft.getWidth() + margin;

        IMG_BAG = Resources.getImage("res/test/images/notification_bag.png");
        IMG_COG = Resources.getImage("res/test/images/notification_settings.png");
        IMG_QUEST = Resources.getImage("res/test/images/notification_star.png");
        IMG_SWORD = Resources.getImage("res/test/images/notification_sword.png");
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
        float alpha, textX, midWidth, topPadding;
        String text;
        Image icon;

        float midX = margin + _bgLeft.getWidth();


        for(int notificationId = 0;notificationId < _notifications.size();notificationId++)
        {
            topPadding = notificationId*height + slide + margin;

            notification = _notifications.get(notificationId);
            icon = notification.getIcon();
            foreground = notification.getForeground();
            background = notification.getBackground();

            textX = margin + _bgLeft.getWidth() + padding;
            midWidth = font.getWidth(notification.getMessage()) + padding*2;

            alpha = (float) (Math.pow(1f-notification.getInterpolation(),0.3));

            if(icon != null)
            {
                float iconTotalWidth = padding + icon.getWidth();
                midWidth += iconTotalWidth;
                textX += iconTotalWidth;
            }

            fore = new Color(foreground.r, foreground.g, foreground.b, foreground.a*alpha);
            back = new Color(background.r, background.g, background.b, background.a*alpha);

            _bgLeft.draw(margin, topPadding, back);
            _bgMid.draw(margin + _bgLeft.getWidth(), topPadding, midWidth, _bgMid.getHeight(), back);
            _bgRight.draw(margin + _bgLeft.getWidth() + midWidth, topPadding, back);

            if(icon != null)
                icon.draw(margin + _bgLeft.getWidth() + padding, topPadding + (_bgLeft.getHeight()-icon.getHeight())/2f);

            GFX.drawTextAbsolute(textX, topPadding + textY, font, fore, notification.getMessage());
        }
    }

    public void addNotification(Notification notification)
    {
        _notifications.add(notification);
    }

    public int getRenderScale()
    {
        return renderScale;
    }

    public void setRenderScale(int renderScale)
    {
        this.renderScale = renderScale;
    }
}
