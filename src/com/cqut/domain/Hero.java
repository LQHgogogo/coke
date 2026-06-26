package com.cqut.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hero extends Character {
    
    // ==================== 属性字段 ====================
    
    public ArrayList<String> skillList;
    public HashMap<Integer, Integer> bag;
    public int Lv;
    public int Exp;
    
    public Floor headFloor;
    public transient Floor currentFloor;
    public int currentFloorNum;
    
    public Item equippedWeapon;
    public Item equippedArmor;
    
    // 剧情进度: 0=未触发, 1=螺旋之门, 2=莉娅的火堆, 3=被遗忘的矿洞, 4=神骸回廊, 5=维兰德斯王座, 6=神域胎心, 7=剧情完结
    public int storyProgress;
    
    private int baseAttack;
    private int baseDefense;

    // ==================== 构造方法 ====================

    public Hero() {
        super();
        initializeBasicStats();
    }

    public Hero(String name, int HP, int attack, int defense) {
        super(name, HP, attack, defense);
        initializeBasicStats();
        this.baseAttack = attack;
        this.baseDefense = defense;
    }

    private void initializeBasicStats() {
        skillList = new ArrayList<String>();
        bag = new HashMap<Integer, Integer>();
        Lv = 1;
        Exp = 0;
        equippedWeapon = null;
        equippedArmor = null;
        storyProgress = 0;
    }

    // ==================== 技能系统 ====================

    public void addSkill(String skill) {
        skillList.add(skill);
    }

    public String showSkill() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < skillList.size(); i++) {
            sb.append(skillList.get(i));
            if (i != skillList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    // ==================== 装备系统 ====================

    public void equipItem(int itemId) {
        if (!hasItem(itemId)) {
            System.out.println("没有该物品！");
            return;
        }

        Item item = ItemFactory.getItemById(itemId);

        switch (item.type) {
            case WEAPON:
                equipWeapon(item);
                break;
            case ARMOR:
                equipArmor(item);
                break;
            default:
                System.out.println("该物品无法装备！");
                break;
        }
    }

    private void equipWeapon(Item weapon) {
        if (equippedWeapon != null) {
            System.out.println("已卸下 " + equippedWeapon.name);
        }
        equippedWeapon = weapon;
        System.out.println("装备了 " + weapon.name + "！攻击力 +" + weapon.attackBonus);
        updateStats();
    }

    private void equipArmor(Item armor) {
        if (equippedArmor != null) {
            System.out.println("已卸下 " + equippedArmor.name);
        }
        equippedArmor = armor;
        System.out.println("装备了 " + armor.name + "！防御力 +" + armor.defenseBonus);
        updateStats();
    }

    public void unequipWeapon() {
        if (equippedWeapon != null) {
            System.out.println("已卸下 " + equippedWeapon.name);
            equippedWeapon = null;
            updateStats();
        }
    }

    public void unequipArmor() {
        if (equippedArmor != null) {
            System.out.println("已卸下 " + equippedArmor.name);
            equippedArmor = null;
            updateStats();
        }
    }

    private void updateStats() {
        this.attack = baseAttack;
        this.defense = baseDefense;

        if (equippedWeapon != null) {
            this.attack += equippedWeapon.attackBonus;
        }
        if (equippedArmor != null) {
            this.defense += equippedArmor.defenseBonus;
        }
    }

    public void setBaseAttack(int baseAttack) {
        this.baseAttack = baseAttack;
        updateStats();
    }

    public void setBaseDefense(int baseDefense) {
        this.baseDefense = baseDefense;
        updateStats();
    }

    // ==================== 背包系统 ====================

    public void addItem(Item item, int count) {
        if (item.type == Item.ItemType.WEAPON || item.type == Item.ItemType.ARMOR) {
            addEquipment(item);
        } else {
            addConsumable(item, count);
        }
    }

    private void addEquipment(Item equipment) {
        if (bag.containsKey(equipment.id)) {
            System.out.println("你已经拥有该装备！");
            return;
        }
        bag.put(equipment.id, 1);
        System.out.println("获得了 " + equipment.name + "！");
    }

    private void addConsumable(Item consumable, int count) {
        if (bag.containsKey(consumable.id)) {
            addExistingConsumable(consumable, count);
        } else {
            addNewConsumable(consumable, count);
        }
    }

    private void addExistingConsumable(Item item, int count) {
        int currentCount = bag.get(item.id);
        int maxCount = item.maxCount;

        if (currentCount >= maxCount) {
            System.out.println(item.name + " 数量已达上限");
            return;
        }

        int newCount = Math.min(currentCount + count, maxCount);
        int added = newCount - currentCount;
        bag.put(item.id, newCount);
        System.out.println("物品添加成功，添加了 " + added + " 个 " + item.name);
    }

    private void addNewConsumable(Item item, int count) {
        int actualCount = Math.min(count, item.maxCount);
        bag.put(item.id, actualCount);
        System.out.println("获得了 " + item.name + " x" + actualCount + "！");
    }

    public void removeItem(int itemId, int count) {
        if (!bag.containsKey(itemId)) {
            System.out.println("没有该物品！");
            return;
        }

        int currentCount = bag.get(itemId);
        Item item = ItemFactory.getItemById(itemId);

        if (currentCount < count) {
            System.out.println(item.name + " 数量不足");
            return;
        }

        int newCount = currentCount - count;
        if (newCount == 0) {
            bag.remove(itemId);
            System.out.println(item.name + " 已用完");
        } else {
            bag.put(itemId, newCount);
            System.out.println("使用了 " + count + " 个 " + item.name);
        }
    }

    public void usePotion(int itemId) {
        if (!validatePotionUse(itemId)) {
            return;
        }

        Item potion = ItemFactory.getItemById(itemId);
        this.heal(potion.healAmount);

        System.out.println("使用了 " + potion.name + "，恢复了 " + potion.healAmount + " 点生命值！");
        System.out.println("当前生命值：" + this.HP + "/" + this.maxHP);

        removeItem(itemId, 1);
    }

    private boolean validatePotionUse(int itemId) {
        if (!hasItem(itemId)) {
            System.out.println("没有该物品！");
            return false;
        }

        Item item = ItemFactory.getItemById(itemId);
        if (item.type != Item.ItemType.POTION) {
            System.out.println("这不是药品！");
            return false;
        }

        if (this.HP >= this.maxHP) {
            System.out.println("生命值已满，无需使用药品！");
            return false;
        }

        return true;
    }

    public int getItemCount(int itemId) {
        return bag.getOrDefault(itemId, 0);
    }

    public boolean hasItem(int itemId) {
        return bag.containsKey(itemId);
    }

    // ==================== 显示系统 ====================

    public void showBag() {
        System.out.println("\n========== 背包 ==========");
        if (bag.isEmpty()) {
            System.out.println("背包为空");
        } else {
            displayBagItems();
        }
        System.out.println("=========================\n");
    }

    private void displayBagItems() {
        int index = 1;
        for (Map.Entry<Integer, Integer> entry : bag.entrySet()) {
            Item item = ItemFactory.getItemById(entry.getKey());
            int count = entry.getValue();

            String info = buildBagItemInfo(item, count);
            System.out.println(index + ". [ID:" + entry.getKey() + "] " + info);
            index++;
        }
    }

    private String buildBagItemInfo(Item item, int count) {
        String info = item.showInfo();
        if (item.type != Item.ItemType.WEAPON && item.type != Item.ItemType.ARMOR) {
            info += " x" + count;
        }
        return info;
    }

    public void showEquipment() {
        System.out.println("\n========== 装备 ==========");
        System.out.println("武器：" + getWeaponDisplay());
        System.out.println("防具：" + getArmorDisplay());
        System.out.println("=========================\n");

    }

    private String getWeaponDisplay() {
        return equippedWeapon != null ? equippedWeapon.showInfo() : "无";
    }

    private String getArmorDisplay() {
        return equippedArmor != null ? equippedArmor.showInfo() : "无";
    }
}
