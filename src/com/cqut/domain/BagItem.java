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

    // 删除物品未完善，根据后续需求修改
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
}
