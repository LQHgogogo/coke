package com.cqut.thing;

public class Weapon extends BagItem                        //所有攻击武器继承此类
{


    public Weapon()
    {
        super();
    }

    public Weapon(int id, String name, String description, int maxCount, int attackBuff)
    {
        super(id, name, description, maxCount);
        this.attackBuff = attackBuff;
    }

    public void setAttackBuff(int num)
    {
        this.attackBuff = num;
    }
}
