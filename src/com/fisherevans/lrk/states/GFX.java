package com.fisherevans.lrk.states;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Options;
import com.fisherevans.lrk.Resources;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static void drawImage(float x, float y, Image img)
    {
        img.draw(x, y, img.getWidth(), img.getHeight());
    }

    public static void drawImageCentered(float x, float y, Image img)
    {
        float dx = -img.getWidth()/2f;
        float dy = -img.getHeight()/2f;
        img.draw(x+dx, y+dy, img.getWidth(), img.getHeight());
    }

    public static void drawTextAbsolute(float x, float y, UnicodeFont font, Color c, String text)
    {
        font.drawString(x, y, text, c);
    }

    public static void drawTextCenteredH(float x, float y, float width, UnicodeFont font, Color c, String text)
    {
        drawText(x, y, width, 0, TEXT_CENTER, TEXT_TOP, font, c, text);
    }

    public static void drawTextCenteredV(float x, float y, float height, UnicodeFont font, Color c, String text)
    {
        drawText(x, y, 0, height, TEXT_LEFT, TEXT_CENTER, font, c, text);
    }

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
            while(textSplit.size() > 0 && true)
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

    public static void fill(Graphics g, Color c)
    {
        g.setColor(c);
        g.fillRect(0, 0, Options.getGameWidth(), Options.getDisplayHeight());

    }
}
