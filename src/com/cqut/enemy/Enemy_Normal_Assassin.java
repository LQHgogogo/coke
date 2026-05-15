package com.cqut.enemy;

import com.cqut.domain.Enemy;

public class Enemy_Normal_Assassin extends Enemy
{
    public Enemy_Normal_Assassin(int lv)
    {
        Enemy one = new Enemy("敏捷刺客",60 + (lv - 1) * 5,20 + (lv - 1) * 4,5 + (lv - 1),"闪身连刺");
        setLv(lv);
    }
}
