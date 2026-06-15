package com.cqut.domain;

public class BagItem {
    public Item item;
    public int count;

    public BagItem(){
        item = new Item();
        count = 0;
    }

    public BagItem(Item item,int num){
        this.item = item;
        count = num;
    }

    public void AddNum(int num){
        if (count == item.maxCount){
            System.out.println("物品已满");
        }else if (count + num >= item.maxCount){
            count = item.maxCount;
            System.out.println("物品添加成功");
        }else {
            count += num;
            System.out.println("物品添加成功");
        }
    }

    public boolean RemoveNum(int num){
        if (count - num < 0){
            count = 0;
            System.out.println("物品已清空");
            return false ;
        }else {
            count -= num;
            System.out.println("物品已删除");
            return true;
        }
    }

    public static class Item {
        public int id;
        public String name;
        public String description;
        public int maxCount;
        public ItemType type;
        public int value;

        public Item() {
            this.type = ItemType.CONSUMABLE;
            this.value = 0;
        }

        public Item(int id, String name, String description, ItemType type, int value, int maxCount) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.type = type;
            this.value = value;
            this.maxCount = maxCount;
        }

        public String showInfo() {
            return String.format("[%s] %s - %s (数量: %d)",
                type.getDescription(), name, description, maxCount);
        }
    }

    public enum ItemType {
        WEAPON("武器"),
        CONSUMABLE("消耗品"),
        ARMOR("防具"),
        ACCESSORY("饰品");

        private final String description;

        ItemType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
