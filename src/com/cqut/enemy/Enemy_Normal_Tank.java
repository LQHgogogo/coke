package com.cqut.enemy;

import com.cqut.domain.Enemy;

public class Enemy_Normal_Tank extends Enemy
{
    public Enemy_Normal_Tank(int lv)
    {
        setLv(lv);
        setName("重装坦克");
        setMaxHP(120 + (lv - 1) * 20);
        setAttack(10 + (lv - 1) * 2);
        setDefense(20 + (lv - 1) * 4);
        setSkill("举盾防御");
    }
}
