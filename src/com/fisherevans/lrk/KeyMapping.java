package com.fisherevans.lrk;

import org.newdawn.slick.Input;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/8/13
 * Time: 8:04 PM
 */
public class KeyMapping
{
    private static int
            _up1 = Input.KEY_W, _up2 = Input.KEY_UP,
            _down1 = Input.KEY_S, _down2 = Input.KEY_DOWN,
            _left1 = Input.KEY_A, _left2 = Input.KEY_LEFT,
            _right1 = Input.KEY_D, _right2 = Input.KEY_RIGHT,
            _select1 = Input.KEY_SPACE, _select2 = Input.KEY_ENTER,
            _back1 = Input.KEY_ESCAPE, _back2 = Input.KEY_BACK;

    public static boolean isUp(int key) { return key == _up1 || key == _up2; }
    public static boolean isDown(int key) { return key == _down1 || key == _down2; }
    public static boolean isLeft(int key) { return key == _left1 || key == _left2; }
    public static boolean isRight(int key) { return key == _right1 || key == _right2; }
    public static boolean isSelect(int key) { return key == _select1 || key == _select2; }
    public static boolean isBack(int key) { return key == _back1 || key == _back2; }

    public static int getUp1()
    {
        return _up1;
    }

    public static void setUp1(int up1)
    {
        _up1 = up1;
    }

    public static int getUp2()
    {
        return _up2;
    }

    public static void setUp2(int up2)
    {
        _up2 = up2;
    }

    public static int getDown1()
    {
        return _down1;
    }

    public static void setDown1(int down1)
    {
        _down1 = down1;
    }

    public static int getDown2()
    {
        return _down2;
    }

    public static void setDown2(int down2)
    {
        _down2 = down2;
    }

    public static int getLeft1()
    {
        return _left1;
    }

    public static void setLeft1(int left1)
    {
        _left1 = left1;
    }

    public static int getLeft2()
    {
        return _left2;
    }

    public static void setLeft2(int left2)
    {
        _left2 = left2;
    }

    public static int getRight1()
    {
        return _right1;
    }

    public static void setRight1(int right1)
    {
        _right1 = right1;
    }

    public static int getRight2()
    {
        return _right2;
    }

    public static void setRight2(int right2)
    {
        _right2 = right2;
    }

    public static int getSelect1()
    {
        return _select1;
    }

    public static void setSelect1(int select1)
    {
        _select1 = select1;
    }

    public static int getSelect2()
    {
        return _select2;
    }

    public static void setSelect2(int select2)
    {
        _select2 = select2;
    }

    public static int getBack1()
    {
        return _back1;
    }

    public static void setBack1(int back1)
    {
        _back1 = back1;
    }

    public static int getBack2()
    {
        return _back2;
    }

    public static void setBack2(int back2)
    {
        _back2 = back2;
    }
}
