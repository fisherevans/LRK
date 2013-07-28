package com.fisherevans.lrk.rpg;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.rpg.entitycomponents.CombatSkill;
import com.fisherevans.lrk.rpg.entitycomponents.Health;
import com.fisherevans.lrk.rpg.entitycomponents.Level;
import com.fisherevans.lrk.rpg.items.Armor;
import com.fisherevans.lrk.rpg.items.Consumable;
import com.fisherevans.lrk.rpg.items.Equipment;
import com.fisherevans.lrk.rpg.items.Weapon;
import com.fisherevans.lrk.rpg.items.enchantments.UnholyMight;

/**
 * User: Fisher evans
 * Date: 5/31/13
 * Time: 8:03 AM
 */
public class RPGEntityGenerator
{
    public static Player getAnarok()
    {
        RPGEntity entity = new RPGEntity("Anarok", RPGEntity.Profession.Mage, 99, 999, 999, 999);

        Inventory inventory = new Inventory();
        inventory.addItem(new Weapon("Short Sword", "A simple sword", Resources.getImage("res/test/images/icon_weapon.png"), Equipment.Position.MainHand, 4, 0, 0.5f));
        inventory.addItem(new Weapon("Battle Axe", "A bloody axe used in battle", Resources.getImage("res/test/images/icon_weapon.png"), Equipment.Position.MainHand, 7, 0, 0.8f));
        inventory.addItem(new Weapon("Wooden Shield", "Won't do you much good", Resources.getImage("res/test/images/icon_weapon.png"), Equipment.Position.OffHand, 0, 5, 0.5f));

        Weapon enchanted = new Weapon("Great Sword", "A large, powerful sword", Resources.getImage("res/test/images/icon_weapon.png"), Equipment.Position.MainHand, 11, 0, 1f, true);
        enchanted.setEnchantment(new UnholyMight(0.45f));
        inventory.addItem(enchanted);

        inventory.addItem(new Armor("Iron Helm", "A modest helmet", Resources.getImage("res/test/images/icon_armor.png"), Equipment.Position.Head, 0, 6));
        inventory.addItem(new Armor("Rusty Chainmail", "Old, but useful", Resources.getImage("res/test/images/icon_armor.png"), Equipment.Position.Chest, 0, 10));
        inventory.addItem(new Armor("Leather Boots", "Light and reliable", Resources.getImage("res/test/images/icon_armor.png"), Equipment.Position.Legs, 0, 4));
        inventory.addItem(new Armor("Dragon Chestplate", "Stronger than diamond", Resources.getImage("res/test/images/icon_armor.png"), Equipment.Position.Chest, 2, 25));

        inventory.addItem(new Consumable("Health Potion", "Heals you for 20 health over 10 seconds", Resources.getImage("res/test/images/icon_consumable.png"), null, 10));
        inventory.addItem(new Consumable("Iron Flesh Potion", "Increases your armor by 10 for 3 seconds", Resources.getImage("res/test/images/icon_consumable.png"), null, 5));
        inventory.addItem(new Consumable("Fire Breath Tonic", "Your attacks set foes on fire 10 seconds", Resources.getImage("res/test/images/icon_consumable.png"), null, 5));
        inventory.addItem(new Consumable("Strong Health Potion", "Heals you for 60 health over 20 seconds", Resources.getImage("res/test/images/icon_consumable.png"), null, 10));
        inventory.addItem(new Consumable("Frenzy Brew", "You attack 33% faster for 30 seconds", Resources.getImage("res/test/images/icon_consumable.png"), null, 5));

        Player character = new Player(entity, inventory);

        return character;
    }
}
