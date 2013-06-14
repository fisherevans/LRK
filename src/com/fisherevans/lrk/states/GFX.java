package com.fisherevans.lrk.states;

import com.fisherevans.lrk.managers.DisplayManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.UnicodeFont;

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

    /**
     * Draw the given text on the screen with the given font.
     * @param x the x position of the top left of the text
     * @param y the y position of the top left of the text
     * @param font the font to use
     * @param c the color to draw the text in
     * @param text the text to draw
     */
    public static void drawTextAbsolute(float x, float y, UnicodeFont font, Color c, String text)
    {
        font.drawString(x, y, text, c);
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
    public static void drawTextCenteredH(float x, float y, float width, UnicodeFont font, Color c, String text)
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
    public static void drawTextCenteredV(float x, float y, float height, UnicodeFont font, Color c, String text)
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
    public static void drawText(float x, float y, float width, float height, int horzAlign, int vertAlign, UnicodeFont font, Color c, String text)
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
            case TEXT_CENTER: dy = (height - font.getHeight(text))/2f; break;
            case TEXT_BOTTOM: dy = height - font.getHeight(text); break;
        }

        font.drawString(x + dx, y + dy, text, c);
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
    public static float drawTextWrap(float x, float y, float width, float height, int horzAlign, int vertAlign, UnicodeFont font, Color c, String baseText)
    {
        float wrapWidth = width;
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
                if(font.getWidth(lineBuffer) <= wrapWidth)
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


        float halfHeight = height/2f, yDiff = 0;
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
        g.fillRect(0, 0, DisplayManager.getRenderWidth(), DisplayManager.getRenderHeight());

    }

    /**
     * adjusts a floating point value to match a real pixel value of the current display window. prevents visual taring
     * @param original the float position
     * @return the adjust position (moved to the nearest (floored) pixel)
     */
    public static float filterDrawPosition(float original)
    {
        return original;
        /*
        float filtered = (int)(original*DisplayManager.getScale());
        filtered /= DisplayManager.getScale();
        return filtered;
        // */
    }
}
