package com.cqut.map;

import com.cqut.domain.Enemy;
import com.cqut.domain.Hero;
import com.cqut.enemy.Enemy_Normal_Assassin;
import com.cqut.enemy.Enemy_Normal_Mage;
import com.cqut.enemy.Enemy_Normal_Soldier;
import com.cqut.enemy.Enemy_Normal_Tank;
import com.cqut.thing.Drug_normalMedicine;

public class WarehouseRoom extends Room
{
    private int enemyNumber = 0;
    private boolean fight =  false;

    public void finishFight()
    {
        fight = true;
    }

    public boolean isFight()
    {
        return fight;
    }

    public void setEnemyNumber(int number)
    {
        this.enemyNumber =  number;
    }

    public int getEnemyNumber()
    {
        return enemyNumber;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void seek(Hero player, int level)
    {
        System.out.println("发现物品：");                                 //简单的物品搜查逻辑
        if (level <= 3)
        {
            System.out.println("1.急救包");
            player.addBagItem(new Drug_normalMedicine(), 1);
        }
        else if (level <= 7)
        {
            System.out.println("1.急救包*2");
            player.addBagItem(new Drug_normalMedicine(), 2);
        }
        else
        {
            System.out.println("1.急救包*3");
            player.addBagItem(new Drug_normalMedicine(), 3);
        }
        this.finishRoom();
    }

    public void fight(Hero player)
    {
        if (!isFight())
        {
            for (int i = 0; i < this.enemyNumber; i++)
            {
                if (i == 0)
                {
                    Enemy enemy = new Enemy_Normal_Soldier(1 + player.getLv() / 2);
                    FightSpace fightSpace = new FightSpace(player, enemy);
                    fightSpace.fight(player, enemy);
                }
                else
                {
                    int kind = (int) (Math.random() * 4);
                    Enemy enemy = null;
                    switch (kind)
                    {
                        case 0:
                            enemy = new Enemy_Normal_Mage(1 + player.getLv() / 2);
                            break;
                        case 1:
                            enemy = new Enemy_Normal_Assassin(1 + player.getLv() / 2);
                            break;
                        case 2:
                            enemy = new Enemy_Normal_Tank(1 + player.getLv() / 2);
                            break;
                        case 3:
                            enemy = new Enemy_Normal_Soldier(1 + player.getLv() / 2);
                            break;
                    }
                    FightSpace fightSpace = new FightSpace(player, enemy);
                    fightSpace.fight(player, enemy);
                }
            }
            if (player.isAlive())
            {
                System.out.println("你已击败场内所有敌人！");
                this.finishFight();
            }
        }
    }
}
