package com.cqut.thing;

public class BagItem
{
    private int count;
    protected int attackBuff;                        //将子类特有属性放在父类中，方便调用
    protected int drugEfficacy;
    protected int defenseBuff;
    protected String skill;
    protected int id;                                       //武器id用1开头，后接两位数字；同理，护具用2开头，技能书用3开头，治疗药品用4开头。
    protected String name;
    protected String description;
    protected int maxCount;


    public BagItem()
    {
        this.count = 1;
    }

    public BagItem(int num)
    {
        this.count = num;
    }

    public BagItem(int id, String name, String description, int maxCount)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maxCount = maxCount;
    }

    public void AddNum(int num)
    {
        if (this.count == this.getMaxCount())
        {
            System.out.println("物品已满");
        }
        else if (this.count + num >= this.getMaxCount())
        {
            this.count = this.getMaxCount();
            System.out.println("物品添加成功");
        }
        else
        {
            this.count += num;
            System.out.println("物品添加成功");
        }
    }

    // 删除物品未完善，根据后续需求修改
    public boolean RemoveNum(int num)
    {
        if (this.count - num <= 0)
        {
            this.count = 0;
            System.out.println("物品已清空");
            return false ;
        }
        else
        {
            this.count -= num;
            System.out.println("指定数量物品已删除");
            return true;
        }
    }

    public int getId()
    {
        return this.id;
    }

    @Override
    public String toString()
    {
        if (this.count == 1)
        {
            return this.getName() + "(" + this.getDescription() + ")";
        }
        else
        {
            return this.getName() + "*" + this.count + "(" + this.getDescription() + ")";
        }
    }

    public String getName()       //输出背包内物品名
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }

    public int getCount()       //输出该物品目前数量
    {
        return this.count;
    }

    public int getDefenseBuff()
    {
        return this.defenseBuff;
    }

    public int getDrugEfficacy()
    {
        return this.drugEfficacy;
    }

    public int getAttackBuff()
    {
        return this.attackBuff;
    }

    public String getSkill()
    {
        return this.skill;
    }

    public int  getMaxCount()
    {
        return this.maxCount;
    }
}
