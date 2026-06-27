package com.cqut.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemFactory {
    
    private static final HashMap<Integer, Item> itemDatabase;
    
    static {
        itemDatabase = new HashMap<>();
        
        itemDatabase.put(1, Item.createWeapon(1, "铁剑", 5, 50));
        itemDatabase.put(2, Item.createWeapon(2, "钢剑", 10, 100));
        itemDatabase.put(3, Item.createWeapon(3, "金剑", 20, 250));
        itemDatabase.put(4, Item.createWeapon(4, "大地之剑", 35, 500));
        
        itemDatabase.put(5, Item.createArmor(5, "皮甲", 3, 40));
        itemDatabase.put(6, Item.createArmor(6, "铁甲", 8, 90));
        itemDatabase.put(7, Item.createArmor(7, "钢甲", 15, 200));
        itemDatabase.put(8, Item.createArmor(8, "圣骑士铠甲", 25, 450));
        
        itemDatabase.put(9, Item.createPotion(9, "小型生命药水", 30, 5, 20));
        itemDatabase.put(10, Item.createPotion(10, "中型生命药水", 60, 5, 40));
        itemDatabase.put(11, Item.createPotion(11, "大型生命药水", 100, 3, 70));
        itemDatabase.put(12, Item.createPotion(12, "超级生命药水", 200, 2, 150));
        
        itemDatabase.put(13, new Item(13, "金币", Item.ItemType.GOLD, "游戏货币", 9999, 0, 0, 0, 0));

        itemDatabase.put(14, new Item(14, "旋风斩技能书", Item.ItemType.SKILL_BOOK, "习得技能：旋风斩", 1, 0, 0, 0, 0));
        itemDatabase.put(15, new Item(15, "雷霆一击技能书", Item.ItemType.SKILL_BOOK, "习得技能：雷霆一击", 1, 0, 0, 0, 0));
        itemDatabase.put(16, new Item(16, "圣光普照技能书", Item.ItemType.SKILL_BOOK, "习得技能：圣光普照", 1, 0, 0, 0, 0));
    }

    public static ArrayList<Item> getAllItems() {
        return new ArrayList<>(itemDatabase.values());
    }

    public static Item getItemById(int id) {
        Item item = itemDatabase.get(id);
        if (item != null) {
            return item;
        }
        if (id > 1000) {
            int baseId = id / 1000;
            int tier = id % 1000;
            Item baseItem = itemDatabase.get(baseId);
            if (baseItem != null && baseItem.type == Item.ItemType.WEAPON) {
                return Item.createTieredWeapon(baseItem, tier);
            }
        }
        return null;
    }

    public static ArrayList<Item> getLootItems(int enemyLevel) {
        ArrayList<Item> possibleLoot = new ArrayList<>();
        
        possibleLoot.add(getItemById(9));
        
        if (enemyLevel >= 2) {
            possibleLoot.add(getItemById(10));
            possibleLoot.add(getItemById(1));
            possibleLoot.add(getItemById(5));
        }
        
        if (enemyLevel >= 4) {
            possibleLoot.add(getItemById(2));
            possibleLoot.add(getItemById(6));
            possibleLoot.add(getItemById(11));
        }
        
        if (enemyLevel >= 6) {
            possibleLoot.add(getItemById(3));
            possibleLoot.add(getItemById(7));
        }
        
        if (enemyLevel >= 8) {
            possibleLoot.add(getItemById(4));
            possibleLoot.add(getItemById(8));
            possibleLoot.add(getItemById(12));
        }
        
        return possibleLoot;
    }
}