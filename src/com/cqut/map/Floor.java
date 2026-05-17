package com.cqut.map;

import com.cqut.domain.Hero;

import java.util.Scanner;

public class Floor
{
    private int level;
    private WarehouseRoom[] warehouseRoom;
    private FightRoom[] fightRoom;
    private StoryRoom storyRoom;
    private boolean finished = false;               //引入finish机制，以此判断能否进入下一层
    Scanner input = new Scanner(System.in);

    public boolean isFinished()
    {
        return finished;
    }

    public void finishLevel()
    {
        this.finished = true;
    }

    public Floor(int level)                         //根据层级创建房间
    {
        storyRoom = new StoryRoom(level);

        warehouseRoom = new WarehouseRoom[1 + level / 5];
        for (int i = 0; i < warehouseRoom.length; i++)
        {
            warehouseRoom[i] = new WarehouseRoom();
            warehouseRoom[i].setEnemyNumber(level / 3);
            warehouseRoom[i].setName("储物间" + (i + 1));
        }

        fightRoom = new FightRoom[3 + level / 4];
        for (int i = 0; i < fightRoom.length; i++)
        {
            fightRoom[i] = new FightRoom();
            fightRoom[i].setEnemyNumber(2 + level / 3);
            fightRoom[i].setName("战场" + (i + 1));
        }
    }

    public void choose(Hero player)                     //循环选择当前操作
    {

        boolean action = true;
        while (action)
        {
            System.out.println("请选择你的操作：");
            System.out.println("1.进入一个战场");
            System.out.println("2.寻找一个储物间");
            System.out.println("3.前往故事地点");
            System.out.println("4.离开");

            int choice = input.nextInt();
            switch (choice)
            {
                case 1:
                    intoFightRoom(player);
                    break;
                case 2:
                    intowarehouseRoom(player);
                    break;
                case 3:
                    if (canRead())
                    {
                        //添加剧情功能
                        finishLevel();
                    }
                    else
                    {
                        System.out.println("该楼层完成度不高，未能发现故事地点");
                    }
                    break;
                case 4:
                    action = false;
                    break;
                default:
                    System.out.println("请正确输入");
                    break;
            }

            if (!player.isAlive())
            {
                break;
            }
        }
    }

    public void intoFightRoom(Hero player)             //转为在战场进行操作
    {
        System.out.println("选择你要进入的战场:（当前楼层共有" + fightRoom.length + "个战场）（选0返回）");
        System.out.print("未探索的战场：");
        for (int i = 0; i < fightRoom.length; i++)
        {
            if (!fightRoom[i].isInto())
            {
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();
        System.out.print("未完成的战场：");
        for (int i = 0; i < fightRoom.length; i++)
        {
            if (!fightRoom[i].isFinish())
            {
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();

        while(true)
        {
            int choice = input.nextInt();
            if (choice > 0 && choice <= fightRoom.length)
            {
                fightRoom[choice - 1].intoRoom();
                fightRoom[choice - 1].fight(player);
                if (player.isAlive())
                {
                    fightRoom[choice - 1].leaveRoom();
                }
                break;
            }
            else if (choice == 0)
            {
                break;
            }
            else
            {
                System.out.println("请正确输入");
            }
        }
    }

    public void intowarehouseRoom(Hero player)                  //转为在储物间进行操作
    {
        System.out.println("选择你要进入的储物间:（当前楼层共有" + warehouseRoom.length + "个储物间）（选0返回）");
        System.out.print("未探索的储物间：");
        for (int i = 0; i < warehouseRoom.length; i++)
        {
            if (!warehouseRoom[i].isInto())
            {
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();
        System.out.print("未完成的储物间：");
        for (int i = 0; i < warehouseRoom.length; i++)
        {
            if (!warehouseRoom[i].isFinish())
            {
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();

        while (true)
        {
            int choice = input.nextInt();
            String kong = input.nextLine();                           //用于清除输入区缓存
            if (choice > 0 && choice <= warehouseRoom.length)
            {
                warehouseRoom[choice - 1].intoRoom();
                if (warehouseRoom[choice - 1].getEnemyNumber() != 0)    //判断储物间内是否有敌人，若有，则优先进行战斗
                {
                    warehouseRoom[choice - 1].fight(player);
                }
                if (player.isAlive())
                {
                    System.out.println("要搜查房间吗？（Y/N）");
                    String choice1;
                    while (true)
                    {
                        choice1 = input.nextLine().trim().toUpperCase();
                        if (choice1.equals("Y") || choice1.equals("N"))      //可自主选择当前阶段是否搜查房间物资
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("无效输入，请输入 Y 或 N：");
                        }
                    }
                    if (choice1.equals("Y"))
                    {
                        warehouseRoom[choice - 1].seek(player);
                    }
                    warehouseRoom[choice - 1].leaveRoom();
                }
                break;
            }
            else if (choice == 0)
            {
                break;
            }
            else
            {
                System.out.println("请正确输入");
            }
        }
    }

    public boolean canRead()           //根据地图完成度判断能否解锁该层剧情
    {
        int intoFight = 0;
        for (int i = 0; i < fightRoom.length; i++)
        {
            if (fightRoom[i].isInto())
            {
                intoFight++;
            }
        }

        int intoWarehouse = 0;
        for (int i = 0; i < warehouseRoom.length; i++)
        {
            if (warehouseRoom[i].isInto())
            {
                intoWarehouse++;
            }
        }

        if (intoWarehouse == warehouseRoom.length && intoFight == fightRoom.length)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
