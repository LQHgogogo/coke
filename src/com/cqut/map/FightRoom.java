package com.cqut.map;

import com.cqut.domain.Enemy;
import com.cqut.enemy.Enemy_Normal_Mage;

public class FightRoom extends Room
{
    private int enemyNumber;

    public FightRoom(int enemyNumber, String name)
    {
        this.enemyNumber = enemyNumber;
        this.name = name;
    }

    public void setEnemyNumber(int number)
    {
        this.enemyNumber =  number;
    }

    public void  setName(String name)
    {
        this.name = name;
    }

    public void fight(int lv)
    {

    }
}
