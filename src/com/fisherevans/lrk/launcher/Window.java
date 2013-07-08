package com.fisherevans.lrk.launcher;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.managers.DisplayManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * User: Fisher evans
 * Date: 6/10/13
 * Time: 9:49 PM
 */
public class Window extends JFrame implements ComponentListener, WindowListener
{
    public Window()
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void componentResized(ComponentEvent e)
    {
        Component c = (Component)e.getSource();

        if(c == this && this.getExtendedState() == this.MAXIMIZED_BOTH)
            DisplayManager.setStartMaximized(true);
        else
        {
            if(c == Game.gameCanvas)
                DisplayManager.setDimensions(c.getWidth(), c.getHeight());
            else if(c == this)
                DisplayManager.refreshDisplay();
        }
    }

    @Override
    public void componentMoved(ComponentEvent e)
    {
        Component c = (Component)e.getSource();

        if(c == this)
        {
            DisplayManager.setPositionX(this.getX());
            DisplayManager.setPositionY(this.getY());

            DisplayManager.refreshDisplay();
        }
    }

    @Override
    public void componentShown(ComponentEvent e)
    {
    }

    @Override
    public void componentHidden(ComponentEvent e)
    {
    }

    @Override
    public void windowOpened(WindowEvent e)
    {
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        Game.finalClose();
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
    }
}
