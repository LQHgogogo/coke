package com.cqut.domain;

import java.util.HashMap;
import java.util.Map;

public class ItemSystem {
    private static Map<Integer, BagItem.Item> itemDatabase;

    static {
        itemDatabase = new HashMap<>();
        initializeItems();
    }

    private static void initializeItems() {
        // 武器类 (ID: 1000-1999)
        registerItem(new BagItem.Item(1001, "铁剑", "攻击+10", BagItem.ItemType.WEAPON, 10, 1));
        registerItem(new BagItem.Item(1002, "钢剑", "攻击+20", BagItem.ItemType.WEAPON, 20, 1));
        registerItem(new BagItem.Item(1003, "勇者之剑", "攻击+35", BagItem.ItemType.WEAPON, 35, 1));
        registerItem(new BagItem.Item(1004, "传说之刃", "攻击+50", BagItem.ItemType.WEAPON, 50, 1));
        registerItem(new BagItem.Item(1005, "木杖", "攻击+8", BagItem.ItemType.WEAPON, 8, 1));
        registerItem(new BagItem.Item(1006, "法师权杖", "攻击+30", BagItem.ItemType.WEAPON, 30, 1));

        // 药水类 (ID: 2000-2999)
        registerItem(new BagItem.Item(2001, "小型生命药水", "恢复30点HP", BagItem.ItemType.CONSUMABLE, 30, 10));
        registerItem(new BagItem.Item(2002, "中型生命药水", "恢复60点HP", BagItem.ItemType.CONSUMABLE, 60, 10));
        registerItem(new BagItem.Item(2003, "大型生命药水", "恢复100点HP", BagItem.ItemType.CONSUMABLE, 100, 10));
        registerItem(new BagItem.Item(2004, "超级生命药水", "恢复200点HP", BagItem.ItemType.CONSUMABLE, 200, 10));
        registerItem(new BagItem.Item(2005, "攻击药水", "临时攻击+10", BagItem.ItemType.CONSUMABLE, 10, 5));
        registerItem(new BagItem.Item(2006, "防御药水", "临时防御+10", BagItem.ItemType.CONSUMABLE, 10, 5));
    }

    private static void registerItem(BagItem.Item item) {
        itemDatabase.put(item.id, item);
    }

    public static BagItem.Item getItemById(int id) {
        BagItem.Item template = itemDatabase.get(id);
        if (template != null) {
            return new BagItem.Item(
                template.id,
                template.name,
                template.description,
                template.type,
                template.value,
                template.maxCount
            );
        }
        return null;
    }

    public static BagItem createBagItem(int itemId, int count) {
        BagItem.Item itemTemplate = getItemById(itemId);
        if (itemTemplate != null) {
            return new BagItem(itemTemplate, count);
        }
        return null;
    }

    public static void showAllItems() {
        System.out.println("========== 物品图鉴 ==========");
        for (BagItem.Item item : itemDatabase.values()) {
            System.out.println(item.showInfo());
        }
        System.out.println("============================");
    }

    public static boolean useItem(Hero hero, int bagIndex) {
        if (bagIndex < 0 || bagIndex >= hero.bag.size()) {
            System.out.println("无效的背包位置！");
            return false;
        }

        BagItem bagItem = hero.bag.get(bagIndex);
        if (bagItem == null || bagItem.count <= 0) {
            System.out.println("该位置没有物品！");
            return false;
        }

        BagItem.Item item = bagItem.item;

        switch (item.type) {
            case WEAPON:
                return equipWeapon(hero, bagItem);
            case CONSUMABLE:
                return useConsumable(hero, bagItem);
            default:
                System.out.println("该物品暂时无法使用！");
                return false;
        }
    }

    private static boolean equipWeapon(Hero hero, BagItem weaponBagItem) {
        if (hero.equippedWeapon != null) {
            System.out.println("卸下已装备的武器：" + hero.equippedWeapon.item.name);
            hero.attack -= hero.equippedWeapon.item.value;
        }

        System.out.println("装备了武器：" + weaponBagItem.item.name);
        System.out.println("攻击力 +" + weaponBagItem.item.value);
        hero.attack += weaponBagItem.item.value;
        hero.equippedWeapon = weaponBagItem;

        weaponBagItem.RemoveNum(1);
        if (weaponBagItem.count <= 0) {
            hero.bag.remove(weaponBagItem);
        }

        return true;
    }

    private static boolean useConsumable(Hero hero, BagItem consumableBagItem) {
        BagItem.Item item = consumableBagItem.item;

        switch (item.id) {
            case 2001:
            case 2002:
            case 2003:
            case 2004:
                int healAmount = item.value;
                hero.heal(healAmount);
                System.out.println("使用了 " + item.name + "，恢复了 " + healAmount + " 点生命值！");
                System.out.println("当前生命值：" + hero.HP + "/" + hero.maxHP);
                break;

            case 2005:
                hero.attack += item.value;
                System.out.println("使用了 " + item.name + "，攻击力临时提升 " + item.value + " 点！");
                System.out.println("当前攻击力：" + hero.attack);
                break;

            case 2006:
                hero.defense += item.value;
                System.out.println("使用了 " + item.name + "，防御力临时提升 " + item.value + " 点！");
                System.out.println("当前防御力：" + hero.defense);
                break;

            default:
                System.out.println("未知的消耗品类型！");
                return false;
        }

        consumableBagItem.RemoveNum(1);
        if (consumableBagItem.count <= 0) {
            hero.bag.remove(consumableBagItem);
        }

        return true;
    }

    public static void showInventory(Hero hero) {
        System.out.println("\n========== 背包 ==========");
        if (hero.bag.isEmpty()) {
            System.out.println("背包是空的");
        } else {
            for (int i = 0; i < hero.bag.size(); i++) {
                BagItem bagItem = hero.bag.get(i);
                System.out.println((i + 1) + ". " + bagItem.item.showInfo());
            }
        }
        System.out.println("=========================\n");
    }

    public static void addItemToBag(Hero hero, int itemId, int count) {
        BagItem newItem = createBagItem(itemId, count);
        if (newItem != null) {
            hero.bag.add(newItem);
            System.out.println("获得物品：" + newItem.item.name + " x" + count);
        } else {
            System.out.println("物品ID不存在：" + itemId);
        }
    }
}