package com.cqut.map;

import com.cqut.domain.*;
import com.cqut.ui.login;

import java.util.Random;
import java.util.Scanner;

public class FightSpace
{
    private Hero player;
    private Enemy enemy;

    public  FightSpace(Hero player, Enemy enemy)
    {
        this.player = player;
        this.enemy = enemy;
    }

    public void fight(Hero player, Enemy enemy)        //战斗代码调用了lqh的祖传代码
    {
        int round = 1;
        while (player.isAlive() && enemy.isAlive())
        {
            System.out.println(player.getName() + "的生命值：" + player.getHP() + "/" + player.getMaxHP());
            System.out.println("敌人“" + enemy.getName() + "”的生命值：" + enemy.getHP() + "/" + enemy.getMaxHP());

            System.out.println("=====第" + round + "轮战斗=====");
            playerTurn(player, enemy);
            enemyTurn(enemy, player);

            if (player.isAlive() && !enemy.isAlive())
            {
                System.out.println("敌人“" + enemy.getName() + "”已被击溃！战斗结束");
            }
            else if (enemy.isAlive() && !player.isAlive())
            {
                System.out.println("你被" + enemy.getName() + "击溃了！");
                System.out.println("看来" + player.getName() + "的故事到此结束！");
            }
            else
            {
                System.out.println("进入下一轮战斗！");
            }
            round++;
        }

        if (player.isAlive())
        {
            int addExp = 20 + enemy.getLv() * round;
            int beforLv = player.getLv();
            System.out.println("恭喜获得经验：" + addExp + "exp");
            player.expUp(addExp);
            player.LvUp();
            int afterLv = player.getLv();
            if (afterLv > beforLv)
            {
                System.out.println("恭喜等级提升！当前等级：" + afterLv);
                System.out.println("生命值：" + player.getHP() + "/" + player.getMaxHP() + "  攻击力：" + player.getAttack() + "  防御力：" + player.getDefense());
            }
        }
    }

    public static void playerTurn(Hero player, Enemy enemy)
    {
        System.out.println("===你的回合===");
        System.out.println("请选择技能：");
        for (int i=0;i<player.getSkillList().size();i++)
        {
            System.out.println((i+1)+"."+player.getSkillList().get(i)+" ");
        }
        Scanner sc = new Scanner(System.in);
        int input = -1;
        while (true)
        {
            if (sc.hasNextInt())
            {
                input = sc.nextInt() - 1;
                if (input >= 0 && input < player.getSkillList().size())
                {
                    break;
                }
                else
                {
                    System.out.println("无效输入，请输入 1-" + player.getSkillList().size() + " 之间的数字：");
                }
            }
            else
            {
                System.out.println("无效输入，请输入数字：");
                sc.next();
            }
        }

        switch (input)
        {
            case 0:
                System.out.println("你选择了普通攻击");
                int demage1 = calculateDamage(player.getAttack(),enemy.getDefense());
                System.out.println("你使用普通攻击对"+enemy.getName()+"，造成"+demage1+"点伤害！");
                enemy.takeDamage(demage1);
                break;
            case 1:
                if (player.getHP()>=10)
                {
                    System.out.println("你选择了强力一击(那么力量的代价是什么呢——消耗10点生命)");
                    player.takeDamage(10);
                    int demage2 = calculateDamage(player.getAttack()*2,enemy.getDefense());
                    enemy.takeDamage(demage2);
                    System.out.println("你使用强力一击对"+enemy.getName()+"，造成"+demage2+"点伤害！");
                }
                else
                {
                    System.out.println("你的生命值不足，无法使用该技能，但使用普通攻击");
                    int demage3 = calculateDamage(player.getAttack(),enemy.getDefense());
                    System.out.println("你使用普通攻击对"+enemy.getName()+"，造成"+demage3+"点伤害！");
                    enemy.takeDamage(demage3);
                }
                break;
            case 2:
                if (player.getHP()>=10)
                {
                    System.out.println("你选择了生命汲取(绝望中的生机——消耗10点生命)");
                    player.takeDamage(10);
                    Random r=new Random();
                    int heal = r.nextInt(30+(int)(player.getLv()*1.5))+1;
                    player.heal(heal);
                    System.out.println("你回复了"+heal+"点生命值！");
                }
                else
                {
                    System.out.println("你的生命值不足，无法使用该技能,但还是用出了普通攻击");
                    int demage4=calculateDamage(player.getAttack(),enemy.getDefense());
                    enemy.takeDamage(demage4);
                    System.out.println("你使用普通攻击对"+enemy.getName()+"，造成"+demage4+"点伤害！");
                }
                break;
            default:
                System.out.println("无效输入,默认进行普通攻击");
                int demage5=calculateDamage(player.getAttack(),enemy.getDefense());
                System.out.println("你使用普通攻击对"+enemy.getName()+"，造成"+demage5+"点伤害！");
                enemy.takeDamage(demage5);
                break;
        }
    }

    public static void enemyTurn(Enemy enemy,Hero player)
    {
        System.out.println("===敌人回合===");

        String action="普通攻击";

        Random r=new Random();
        int randomNum = r.nextInt(2);
        if (randomNum==1)
        {
            action= enemy.getSkill();
        }

        switch (action)
        {
            case "普通攻击":
                System.out.println("敌人使用了普通攻击");
                int demage1=calculateDamage(enemy.getAttack(),player.getDefense());
                System.out.println(enemy.getName()+"使用普通攻击，对我造成了"+demage1+"点伤害！");
                player.takeDamage(demage1);
                break;
            case "力拔山兮":
                System.out.println("敌人使用了力拔山兮");
                int demage2=calculateDamage((int)(enemy.getAttack()*1.5),player.getDefense());
                System.out.println(enemy.getName()+"使用力拔山兮，对我造成了"+demage2+"点伤害！");
                player.takeDamage(demage2);
                break;
            case "闪身连刺":
                System.out.println("敌人使用了闪身连刺");
                int demage3=0;
                for (int i=0;i<2;i++){
                    demage3+=calculateDamage(enemy.getAttack()/2, player.getDefense()/3);
                }
                System.out.println(enemy.getName()+"使用闪身连刺，对我造成了"+demage3+"点伤害！");
                player.takeDamage(demage3);
                break;
            case "举盾防御":
                System.out.println("敌人使用了举盾防御");
                enemy.setDefending(true);
                System.out.println(enemy.getName()+"已进入防御状态！");
                break;
            case "咒术——火":
                System.out.println("敌人使用了咒术——火");
                int demage4=calculateDamage((int)(enemy.getAttack()*1.8),player.getDefense()/2);
                System.out.println(enemy.getName()+"使用了咒术——火，对我造成了"+demage4+"点伤害！");
                player.takeDamage(demage4);
                break;
        }
    }

    public static int calculateDamage(int atack, int defense)
    {
        int Demage=atack-defense;
        if (Demage<=0)
        {
            Demage=1;
        }
        return Demage;
    }
}
