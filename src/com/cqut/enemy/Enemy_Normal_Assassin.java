package com.cqut.enemy;

import com.cqut.domain.Enemy;

public class Enemy_Normal_Assassin extends Enemy
{
    public Enemy_Normal_Assassin(int lv)
    {
        setLv(lv);
        setName("敏捷刺客");
        setMaxHP(60 + (lv - 1) * 5);
        setHP(getMaxHP());
        setAttack(20 + (lv - 1) * 4);
        setDefense(5 + (lv - 1));
        setSkill("闪身连刺");
    }
}
