package com.cqut.map;

import com.cqut.domain.Hero;
import com.cqut.thing.BagItem;
import com.cqut.util.InputValidator;

import java.util.Scanner;

public class BagSpace
{
    private Hero player;

    public BagSpace(Hero player)
    {
        this.player = player;
    }

    public void intoBagSpace(Hero player)                          //显示背包与装备界面
    {
        Scanner input = new Scanner(System.in);
        int choice = -1;
        while (choice != 0)
        {
            System.out.println("\n");
            System.out.println("做出你的选择：");
            System.out.println("0.退出");
            System.out.println("1.查看装备");
            System.out.println("2.查看背包");
            choice = InputValidator.validateBagSpace(input, 2);
            switch (choice)
            {
                case 1:
                    this.showEquipment(player);
                    break;
                case 2:
                    this.bagChoice(player);
                    break;
            }
        }
    }

    public void bagChoice(Hero player)                      //背包界面的选择
    {
        Scanner input = new Scanner(System.in);
        int choice = -1;
        while(choice != 0)
        {
            System.out.println("\n");
            player.showBag();
            System.out.println("输入物品序号操作：（选0退出）");
            choice = InputValidator.validateBagSpace(input, player.getBagLength());
            System.out.println("\n");
            if (choice != 0)
            {
                System.out.println("已选中" + player.getBagItem(choice - 1).getName());
                System.out.println("选择你的操作：");
                System.out.println("0.放弃操作");
                System.out.println("1.丢弃");
                if ((player.getBagItem(choice - 1).getId() - 200) < 100)
                {
                    System.out.println("2.装备");
                }
                else if ((player.getBagItem(choice - 1).getId() - 300) > 100)
                {
                    System.out.println("2.学习");
                }
                else
                {
                    System.out.println("2.使用");
                }
                int choice2 = InputValidator.validateBagSpace(input, 2);
                if (choice2 != 0)
                {
                    System.out.println("\n");
                    switch (choice2)
                    {
                        case 1:
                            if (player.getBagItem(choice2).getId() - 300 < 100)
                            {
                                player.removeBagItem(player.getBagItem(choice - 1), 1);
                            }
                            else
                            {
                                System.out.print("选择数量：（超出数量视为全部丢弃）");
                                int num = InputValidator.validateBagSpace_non_zero(input, player.getBagItem(choice - 1).getCount());
                                player.removeBagItem(player.getBagItem(choice - 1), num);
                            }
                            break;
                        case 2:
                            if ((player.getBagItem(choice - 1).getId() - 200) < 100)
                            {
                                this.equipThing(player.getBagItem(choice - 1), player);
                                System.out.println("您已装备“" + player.getBagItem(choice - 1).getName() + "“");
                            }
                            else if ((player.getBagItem(choice - 1).getId() - 300) > 100)
                            {
                                player.addSkill(player.getBagItem(choice - 1).getSkill());
                                System.out.println("恭喜！您学会了“" + player.getBagItem(choice - 1).getSkill() + "”");
                            }
                            else
                            {
                                System.out.println("您已使用“" + player.getBagItem(choice - 1).getName() + "”");
                                this.useThing(player.getBagItem(choice - 1), player);
                            }
                            player.removeBagItem(player.getBagItem(choice - 1), 1);
                            break;
                    }
                }
            }
        }
    }

    public void useThing(BagItem thing,Hero player)                   //背包界面物品使用逻辑
    {
        if ((thing.getId() - 400) < 100)
        {
            player.heal(thing.getDrugEfficacy());
            System.out.println("当前生命值：" + player.getHP() + "/" + player.getMaxHP());
        }
    }

    public void equipThing(BagItem thing, Hero player)                       //背包界面装备逻辑
    {
        if (thing.getId() - 100 < 100)
        {
            player.setWeapon(thing);
            player.setAttack(player.getAttack() + thing.getAttackBuff());
        }
        else
        {
            player.setArmor(thing);
            player.setDefense(player.getDefense() + thing.getDefenseBuff());
        }
    }

    public void showEquipment(Hero player)                                //展示全身装备
    {
        Scanner input = new Scanner(System.in);
        int choice = -1;
        while (choice != 0)
        {
            System.out.println("\n");
            if(player.getWeapon() == null)
            {
                System.out.println("当前武器为" + player.getWeapon());
            }
            else
            {
                System.out.println("当前武器为" + player.getWeapon().getName());
            }
            if (player.getArmor() == null)
            {
                System.out.println("当前护具为" +  player.getArmor());
            }
            else
            {
                System.out.println("当前护具为" + player.getArmor().getName());
            }
            System.out.println("请操作：");
            System.out.println("0.退出");
            System.out.println("1.卸下武器");
            System.out.println("2.卸下护具");
            choice = InputValidator.validateBagSpace(input, 2);
            switch (choice)
            {
                case 1:
                    if (player.getWeapon() == null)
                    {
                        System.out.println("您未装备武器");
                    }
                    else
                    {
                        player.addBagItem(player.getWeapon(), 1);
                        System.out.println("成功卸下武器“" + player.getWeapon().getName() + "”");
                        player.setWeapon(null);
                    }
                    break;
                case 2:
                    if (player.getArmor() == null)
                    {
                        System.out.println("您未装备护具");
                    }
                    else
                    {
                        player.addBagItem(player.getArmor(), 1);
                        System.out.println("成功卸下护具“" + player.getArmor().getName() + "”");
                        player.setArmor(null);
                    }
                    break;
            }
        }
    }
}
