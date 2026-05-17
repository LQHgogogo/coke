package com.cqut.enemy;

import com.cqut.domain.Enemy;

public class Enemy_Normal_Mage extends Enemy
{
    public Enemy_Normal_Mage(int lv)
    {
        setLv(lv);
        setName("神秘法师");
        setMaxHP(70 + (lv -1) * 10);
        setHP(getMaxHP());
        setAttack(25 + (lv - 1) * 5);
        setDefense(8 + (lv - 1));
        setSkill("咒术——火");
    }
}
