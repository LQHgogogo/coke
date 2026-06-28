package com.cqut.domain;

public class Item {
    public int id;
    public String name;
    public ItemType type;
    public String description;

    public int maxCount;
    public int attackBonus;
    public int defenseBonus;
    public int healAmount;
    public int price;
    public int tier;

    public enum ItemType {
        WEAPON("武器"),
        ARMOR("防具"),
        POTION("药品"),
        SKILL_BOOK("技能书");

        private final String typeName;

        ItemType(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeName() {
            return typeName;
        }
    }

    public Item() {
        this.id = 0;
        this.name = "未知物品";
        this.type = ItemType.POTION;
        this.description = "";
        this.maxCount = 1;
        this.attackBonus = 0;
        this.defenseBonus = 0;
        this.healAmount = 0;
        this.price = 0;
    }

    public Item(int id, String name, ItemType type, String description,
                int maxCount, int attackBonus, int defenseBonus, int healAmount, int price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.maxCount = maxCount;
        this.attackBonus = attackBonus;
        this.defenseBonus = defenseBonus;
        this.healAmount = healAmount;
        this.price = price;
    }

    public static Item createWeapon(int id, String name, int attackBonus, int price) {
        return new Item(id, name, ItemType.WEAPON, "攻击力 +" + attackBonus,
                       1, attackBonus, 0, 0, price);
    }

    public static Item createArmor(int id, String name, int defenseBonus, int price) {
        return new Item(id, name, ItemType.ARMOR, "防御力 +" + defenseBonus,
                       1, 0, defenseBonus, 0, price);
    }

    public static Item createPotion(int id, String name, int healPercent, int maxCount, int price) {
        return new Item(id, name, ItemType.POTION, "恢复 " + healPercent + "% HP",
                       maxCount, 0, 0, healPercent, price);
    }

    public static Item createTieredWeapon(Item baseWeapon, int newTier) {
        int newId = baseWeapon.id * 1000 + newTier;
        int scaledAttack = (int)(baseWeapon.attackBonus * (1.0 + newTier * 0.5));
        int scaledPrice = baseWeapon.price * (newTier + 1);
        String tierSuffix = newTier > 0 ? "+" + newTier : "";
        Item result = new Item(newId, baseWeapon.name + tierSuffix, ItemType.WEAPON,
                       "", 1, scaledAttack, 0, 0, scaledPrice);
        result.tier = newTier;
        return result;
    }

    public static int getBaseId(int itemId) {
        return itemId > 1000 ? itemId / 1000 : itemId;
    }

    public static int getTier(int itemId) {
        return itemId > 1000 ? itemId % 1000 : 0;
    }

    public String showInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" [").append(type.getTypeName()).append("]");

        switch (type) {
            case WEAPON:
                if (tier > 0) {
                    sb.append(" 阶").append(tier);
                }
                sb.append(" 攻击+").append(attackBonus);
                break;
            case ARMOR:
                sb.append(" 防御+").append(defenseBonus);
                break;
            case POTION:
                sb.append(" 恢复").append(healAmount).append("%HP");
                break;
            case SKILL_BOOK:
                sb.append(" ").append(description);
                break;
        }

        sb.append(" 价格:").append(price).append("G");
        if (!description.isEmpty()) {
            sb.append("\n  ").append(description);
        }
        return sb.toString();
    }
}