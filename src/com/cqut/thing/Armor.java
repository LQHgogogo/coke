package com.cqut.thing;

public class Armor extends BagItem                   //所有护具继承此类
{


    public Armor()
    {
        super();
    }

    public Armor(int id, String name, String description, int maxCount)
    {
        super(id, name, description, maxCount);
    }

    public Armor(int id, String name, String description, int maxCount, int attackBuff)
    {
        super(id, name, description, maxCount);
        this.defenseBuff = attackBuff;
    }

    public void setAttackBuff(int num)
    {
        this.defenseBuff = num;
    }
}
