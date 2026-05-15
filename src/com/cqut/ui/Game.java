package com.cqut.ui;

import com.cqut.domain.*;
import com.cqut.map.Floor;

import java.util.Scanner;

public class Game                                   // 编写中，暂时未使用
{
    public void  start(String username)
    {
        System.out.println("============================");
        System.out.println("     "+username+"欢迎来到文字格斗游戏     ");
        System.out.println("============================");

        Hero player = creatCharacter(username);
        System.out.println("角色创建成功");
        System.out.println("角色初始属性："+player.showStatus());
        System.out.println("拥有的技能： "+player.showSkill());

        Floor[] level = new Floor[9];
        for (int i = 0; i < level.length; i++)
        {
            level[i] = new Floor(i + 1);
        }

        int numOfLevel = 0;

        while (numOfLevel < 9)
        {
            System.out.println("你已到达第" + (numOfLevel + 1) + "层地牢");
        }


    }

    public Hero creatCharacter(String username)
    {
        System.out.println("创建你的角色：");
        System.out.println("你的角色名为："+ username);

        int point=20;
        System.out.println("请分配属性点（共20点）;");
        System.out.println("1.生命值（每点 + 10HP）：");
        System.out.println("2.攻击力（每点 + 2ATK）：");
        System.out.println("3.防御力（每点 + 1DEF）：");

        Scanner sc = new Scanner(System.in);

        String[] attributes ={"生命值","攻击力","防御力"};
        int[] values = new int[3];

        for (int i=0;i<attributes.length;i++){
            System.out.println("分配点数到"+attributes[i]+"（剩余点数："+point+"）：");
            int input = sc.nextInt();
            if (input<0){
                System.out.println("无效输入，默认分配点数0");
                input = 0;
            }

            if (input>point){
                System.out.println("属性点不足，剩余点数全部分配到"+attributes[i]);
                input = point;
            }

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
