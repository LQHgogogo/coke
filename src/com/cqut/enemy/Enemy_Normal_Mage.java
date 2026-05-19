package com.cqut.enemy;

import com.cqut.domain.Enemy;

public class Enemy_Normal_Mage extends Enemy
{
    public Enemy_Normal_Mage(int lv)
    {
        super("神秘法师", 70 + (lv -1) * 10, 25 + (lv - 1) * 5, 8 + (lv - 1), "咒术——火");
        setLv(lv);
    }
}
