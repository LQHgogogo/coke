package com.cqut.ui;

import com.cqut.domain.*;
import com.cqut.map.Floor;
import com.cqut.util.InputValidator;

import java.util.Scanner;

public class Game                                   // 框架编写完成，已可以使用
{
    Scanner input = new Scanner(System.in);

    public void  start(String username)
    {
        System.out.println("============================");
        System.out.println("     "+username+"欢迎来到文字格斗游戏     ");
        System.out.println("============================");

        Hero player = creatCharacter(username);
        System.out.println("角色创建成功");
        System.out.println("角色初始属性："+player.showStatus());
        System.out.println("拥有的技能： "+player.showSkill());

        Floor[] level = new Floor[9];                    //创建层数
        for (int i = 0; i < level.length; i++)
        {
            level[i] = new Floor(i + 1);
        }

        int numOfLevel = 0;

        while (numOfLevel < 9)                         //开始游戏
        {
            System.out.println("你已到达第" + (numOfLevel + 1) + "层地牢");
            boolean action = true;
            while (action)
            {
                System.out.println("\n");
                System.out.println("请选择你的操作：");
                if (numOfLevel != 0)
                {
                    System.out.println("1.返回上层");
                }
                System.out.println("2.探索该层");
                if (numOfLevel != 8)
                {
                    System.out.println("3.前往下一层");
                }
                int choice = InputValidator.validateMenuChoice(input, 1, 3);
                switch (choice)
                {
                    case 1:
                        if (numOfLevel != 0)
                        {
                            numOfLevel--;
                            action = false;
                        }
                        else
                        {
                            System.out.println("该楼层不可用");
                        }
                        break;
                    case 2:
                        level[numOfLevel].choose(player);
                        break;
                    case 3:
                        if (numOfLevel != 8 && level[numOfLevel].isFinished())
                        {
                            numOfLevel++;
                            action = false;
                        }
                        else if (numOfLevel != 8 &&  !level[numOfLevel].isFinished())
                        {
                            System.out.println("下一层未解锁");
                        }
                        else
                        {
                            System.out.println("该楼层不可用");
                        }
                        break;
                    default:
                        System.out.println("输入无效");
                        break;
                }
                if (!player.isAlive())
                {
                    break;
                }
            }
            if (!player.isAlive())
            {
                break;
            }
        }


    }

    public Hero creatCharacter(String username)                          //英雄创建
    {
        System.out.println("创建你的角色：");
        System.out.println("你的角色名为："+ username);

        int point=40;
        System.out.println("请分配属性点（共40点）;");
        System.out.println("1.生命值（每点 + 10HP）：");
        System.out.println("2.攻击力（每点 + 2ATK）：");
        System.out.println("3.防御力（每点 + 1DEF）：");

        Scanner sc = new Scanner(System.in);

        String[] attributes ={"生命值","攻击力","防御力"};
        int[] values = new int[3];

        for (int i=0;i<attributes.length;i++)
        {
            System.out.println("分配点数到"+attributes[i]+"（剩余点数："+point+"）：");
            int input = InputValidator.validateAttributePoint(sc, point);
            point -= input;
            values[i] = input;
        }

        Hero player=new Hero(username,100+values[0]*10,10+values[1]*2,values[2]);

        player.addSkill("普通攻击");
        player.addSkill("强力一击");
        player.addSkill("生命汲取");

        return  player;
    }
}
