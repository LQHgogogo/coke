package com.cqut.enemy;

import com.cqut.domain.Enemy;

public class Enemy_Normal_Tank extends Enemy
{
    public Enemy_Normal_Tank(int lv)
    {
        super("重装坦克", 120 + (lv - 1) * 20, 10 + (lv - 1) * 2, 20 + (lv - 1) * 4, "举盾防御");
        setLv(lv);
    }
}
