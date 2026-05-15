package com.cqut.enemy;

import com.cqut.domain.Enemy;

public class Enemy_Normal_Soldier extends Enemy
{
    public Enemy_Normal_Soldier(int lv)
    {
        Enemy one = new Enemy("初级士兵",80 + (lv - 1) * 10,15 + (lv - 1) * 3,10 + (lv - 1) * 2,"力拔山兮");
        setLv(lv);
    }
}
