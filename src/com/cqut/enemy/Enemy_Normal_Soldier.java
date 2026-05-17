package com.cqut.enemy;

import com.cqut.domain.Enemy;

public class Enemy_Normal_Soldier extends Enemy
{
    public Enemy_Normal_Soldier(int lv)
    {
        setLv(lv);
        setName("初级士兵");
        setMaxHP(80 + (lv - 1) * 10);
        setHP(getMaxHP());
        setAttack(15 + (lv - 1) * 3);
        setDefense(10 + (lv - 1) * 2);
        setSkill("力拔山兮");
    }
}
