package com.fisherevans.lrk.rpg.items.enchantments;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/27/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnholyMight extends Enchantment
{
    private float _percentage;

    public UnholyMight(float percentage)
    {
        super("Unholy Might", String.format("Increases your damage output by %2d%%", (int)(percentage*100)));
        _percentage = percentage;
    }

    public float getPercentage()
    {
        return _percentage;
    }

    public void setPercentage(float percentage)
    {
        _percentage = percentage;
    }
}
