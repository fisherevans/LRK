package com.fisherevans.lrk.notifications;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.notifications.types.Notification;
import com.fisherevans.lrk.states.GFX;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

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

    private AngelCodeFont font;

    private float height, textY, textHeight, slide;

    private Image _bgLeft, _bgMid, _bgRight;

    private int renderScale = 1;

    private boolean blockNotifications = true;

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
        textHeight = font.getHeight("|");
        textY = (_bgLeft.getHeight() - textHeight)/2f;

        IMG_BAG = Resources.getImage("res/test/images/notification_bag.png");
        IMG_COG = Resources.getImage("res/test/images/notification_settings.png");
        IMG_QUEST = Resources.getImage("res/test/images/notification_star.png");
        IMG_SWORD = Resources.getImage("res/test/images/notification_sword.png");
    }

    public void update(float delta)
    {
        if(blockNotifications || _notifications == null || _notifications.size() == 0)
            return;

        int updateThreshold = (int) (DisplayManager.getWindowHeight()/(height*renderScale));

        Notification notification;
        for(int notificationId = 0;notificationId < _notifications.size();notificationId++)
        {

            notification = _notifications.get(notificationId);
            if(notificationId < updateThreshold)
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
        if(blockNotifications || _notifications == null || _notifications.size() == 0)
            return;

        Notification notification;
        Color baseFore, baseBack, alphaFore, alphaBack, alphaIcon;
        Rectangle bgLeftRect, bgMidRect, bgRightRect, iconRect, textRect;
        float alphaScale, topPadding;
        Image icon;

        for(int id = 0;id < _notifications.size();id++)
        {
            topPadding = id*height + slide + margin;

            notification = _notifications.get(id);
            icon = notification.getIcon();
            baseFore = notification.getForeground();
            baseBack = notification.getBackground();

            alphaScale = (float) (1 - Math.pow(2f*(notification.getInterpolation()-0.5f),8));
            alphaFore = new Color(baseFore.r, baseFore.g, baseFore.b, baseFore.a*alphaScale);
            alphaBack = new Color(baseBack.r, baseBack.g, baseBack.b, baseBack.a*alphaScale);
            alphaIcon = new Color(1f, 1f, 1f, alphaScale);

            bgLeftRect = new Rectangle(margin, topPadding, _bgLeft.getWidth(), _bgLeft.getHeight());
            textRect = new Rectangle(margin + _bgLeft.getWidth() + padding, textY + topPadding, font.getWidth(notification.getMessage()), textHeight);
            bgMidRect = new Rectangle(margin + _bgLeft.getWidth(), topPadding, textRect.getWidth() + padding*2, _bgMid.getHeight());

            iconRect = new Rectangle(0, 0, 0, 0);
            if(icon != null)
            {
                iconRect = new Rectangle(textRect.getX(), (_bgLeft.getHeight()-icon.getHeight())/2f + topPadding, icon.getWidth(), icon.getHeight());
                float iconPush = padding + iconRect.getWidth();
                textRect.setX(textRect.getX() + iconPush);
                bgMidRect.setWidth(bgMidRect.getWidth() + iconPush);
            }

            bgRightRect = new Rectangle(bgMidRect.getX() + bgMidRect.getWidth(), topPadding, _bgRight.getWidth(), _bgRight.getHeight());

            _bgLeft.draw(bgLeftRect.getX(), bgLeftRect.getY(), alphaBack);
            _bgMid.draw(bgMidRect.getX(),bgMidRect.getY(), bgMidRect.getWidth(), bgMidRect.getHeight(), alphaBack);
            _bgRight.draw(bgRightRect.getX(), bgRightRect.getY(), alphaBack);

            if(icon != null)
                icon.draw(iconRect.getX(), iconRect.getY(), alphaIcon);

            GFX.drawTextAbsolute(textRect.getX(), textRect.getY(), font, alphaFore, notification.getMessage());
        }
    }

    public void addNotification(Notification notification)
    {
        LRK.log("Adding notification: " + notification.getMessage());
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

    public boolean isBlockNotifications()
    {
        return blockNotifications;
    }

    public void setBlockNotifications(boolean blockNotifications)
    {
        this.blockNotifications = blockNotifications;
    }
}
