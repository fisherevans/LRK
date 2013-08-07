package com.fisherevans.lrk.states;

import com.fisherevans.lrk.managers.DisplayManager;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/6/13
 * Time: 7:12 PM
 */
public class GFX
{
    public static final int
        TEXT_LEFT = 0,
        TEXT_CENTER = 1,
        TEXT_RIGHT = 2,
        TEXT_TOP = 3,
        TEXT_BOTTOM = 4;

    private static final float LINE_HEIGHT_SCALE = 1.5f;

    /**
     * Draw an image to the display with the top left of the image at given x and y
     * @param x x pos of top left of the image
     * @param y y pos of the top left of the image
     * @param img The image to draw
     */
    public static void drawImage(float x, float y, Image img)
    {
        img.draw(x, y, img.getWidth(), img.getHeight());
    }

    /**
     * Draw an image to the display with the top left of the image at given x and y and stretched to the given width and height
     * @param x x pos of top left of the image
     * @param y y pos of the top left of the image
     * @param width the width to draw the image
     * @param height thr height to draw the image
     * @param img The image to draw
     */
    public static void drawImage(float x, float y, float width, float height, Image img)
    {
        img.draw(x, y, width, height);
    }

    /**
     * Draw an image to the display centered on the given x and y
     * @param x the x pos to center the image on
     * @param y the y pos to center the image on
     * @param img the image to draw
     */
    public static void drawImageCentered(float x, float y, Image img)
    {
        float dx = -img.getWidth()/2f;
        float dy = -img.getHeight()/2f;
        img.draw(x+dx, y+dy, img.getWidth(), img.getHeight());
    }

    public static void drawImageCentered(float x, float y, Image img, Color c)
    {
        float dx = -img.getWidth()/2f;
        float dy = -img.getHeight()/2f;
        img.draw(x+dx, y+dy, img.getWidth(), img.getHeight(), c);
    }

    public static void drawFlashImageCentered(float x, float y, Image img, Color c)
    {
        float dx = -img.getWidth()/2f;
        float dy = -img.getHeight()/2f;
        img.drawFlash(x+dx, y+dy, img.getWidth(), img.getHeight(), c);
    }

    /**
     * Draw an image to the display centered on the given x and y and scaled
     * @param x the x pos to center the image on
     * @param y the y pos to center the image on
     * @param img the image to draw
     */
    public static void drawImageCenteredRounded(float x, float y, Image img, int scale, Color c)
    {
        float dx = -img.getWidth()/2f*scale;
        float dy = -img.getHeight()/2f*scale;
        img.draw((int)(x+dx), (int)(y+dy), img.getWidth()*scale, img.getHeight()*scale, c);
    }

    /**
     * Draw the given text on the screen with the given font.
     * @param x the x position of the top left of the text
     * @param y the y position of the top left of the text
     * @param font the font to use
     * @param c the color to draw the text in
     * @param text the text to draw
     */
    public static void drawTextAbsolute(float x, float y, AngelCodeFont font, Color c, String text)
    {
        font.drawString((int)x, (int)y, text, c);
    }

    /**
     * draw text centered within a given width at a given x and y on the screen
     * @param x the x pos of the left of the width line
     * @param y the y pos of the center line
     * @param width the width to center tne text on
     * @param font the font to use
     * @param c the color to draw the text in
     * @param text the text to draw
     */
    public static void drawTextCenteredH(float x, float y, float width, AngelCodeFont font, Color c, String text)
    {
        drawText(x, y, width, 0, TEXT_CENTER, TEXT_TOP, font, c, text);
    }


    /**
     * draw text centered within a given height at a given x and y on the screen
     * @param x the x pos of the center line
     * @param y the y pos of top of the center line
     * @param height the width to center tne text on
     * @param font the font to use
     * @param c the color to draw the text in
     * @param text the text to draw
     */
    public static void drawTextCenteredV(float x, float y, float height, AngelCodeFont font, Color c, String text)
    {
        drawText(x, y, 0, height, TEXT_LEFT, TEXT_CENTER, font, c, text);
    }

    /**
     * fully functioned text drawing method
     * @param x x pos of the top left of the text box
     * @param y y pos of the top left of the text box
     * @param width width of the text box
     * @param height height of the text box
     * @param horzAlign horizontal alignment within the text box
     * @param vertAlign vertical alignment within the text box
     * @param font the font to use
     * @param c the color to draw the text in
     * @param text the text to draw
     */
    public static void drawText(float x, float y, float width, float height, int horzAlign, int vertAlign, AngelCodeFont font, Color c, String text)
    {
        float dx = 0, dy = 0;
        switch(horzAlign)
        {
            case TEXT_LEFT: dx = 0; break;
            case TEXT_CENTER: dx = (width - font.getWidth(text))/2f; break;
            case TEXT_RIGHT: dx = width - font.getWidth(text); break;
        }
        switch(vertAlign)
        {
            case TEXT_TOP: dy = 0; break;
            case TEXT_CENTER: dy = (height - font.getHeight("0"))/2f; break;
            case TEXT_BOTTOM: dy = height - font.getHeight("0"); break;
        }

        font.drawString((int)(x + dx), (int)(y + dy), text, c);
    }

    /**
     * draw a text box on the screen at a given position and alignment
     * @param x x pos of the top left of the text box
     * @param y the y pos of the the top left of the text box
     * @param width width of the text box
     * @param height height of the text box
     * @param horzAlign horizontal alignment of the text within the text box
     * @param vertAlign vertical alignment of the text within the text box
     * @param font the font to use
     * @param c the color to use
     * @param baseText the text to print
     * @return returns the height of the text drawn
     */
    public static float drawTextWrap(float x, float y, float width, float height, int horzAlign, int vertAlign, AngelCodeFont font, Color c, String baseText)
    {
        ArrayList<String> textSplit = new ArrayList(Arrays.asList(baseText.split(" ")));
        ArrayList<String> texts = new ArrayList<String>();
        String line = "";
        while(textSplit.size() > 0)
        {
            String lineBuffer;
            line = textSplit.remove(0);
            while(textSplit.size() > 0)
            {
                lineBuffer = line + textSplit.get(0);
                if(font.getWidth(lineBuffer) <= width)
                {
                    line += " " + textSplit.remove(0);
                }
                else
                {
                    texts.add(line);
                    line = "";
                    break;
                }
            }
        }
        if(!line.equals(""))
            texts.add(line);


        float yDiff = 0;
        float lineHeight = font.getLineHeight()*LINE_HEIGHT_SCALE;
        switch (vertAlign)
        {
            case TEXT_TOP: yDiff = 0; break;
            case TEXT_CENTER: yDiff = (height/lineHeight - texts.size())*lineHeight/2f; break;
            case TEXT_BOTTOM: yDiff = (height/lineHeight - texts.size())*lineHeight; break;
        }
        for(int id = 0;id < texts.size();id++)
        {
            drawText(x, y+(id*lineHeight)+yDiff, width, 0, horzAlign, vertAlign, font, c, texts.get(id));
        }

        return lineHeight*texts.size();
    }

    /**
     * draw a rectangle of a given color over the given graphics
     * @param g graphics to draw on
     * @param c the color to draw, supports alpha
     */
    public static void fill(Graphics g, Color c)
    {
        g.setColor(c);
        g.fillRect(0, 0, DisplayManager.getWindowWidth(), DisplayManager.getWindowHeight());
    }
}
