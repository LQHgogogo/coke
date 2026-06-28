package com.cqut.ui;

import com.cqut.domain.*;

import java.util.ArrayList;
import java.util.Scanner;

public class TextGame {
    public void start(ArrayList<User> list,User user) {
        String username = user.getUsername();
        System.out.println("================================");
        System.out.println("     "+username+"欢迎来到神域     ");
        System.out.println("================================");

        restartGame:
        while (true) {
        Hero player = null;
        Floor head = null;
        int count;
        if (user.getHero() == null) {
            player = creatCharacter(username);
            user.setHero(player);
            head = new Floor(1);
            head.setRooms();
            player.headFloor = head;
            player.currentFloor = head;
            player.currentFloorNum = 1;
            System.out.println("角色创建成功");
            count = 1;

            player.addGold(100);
            player.addItem(new Item(9, "药品", Item.ItemType.POTION, "回血药品", 9999, 0, 0, 100, 0), 2);
            System.out.println("获得初始资金：100G，2个小型药品");
        } else {
            player = user.getHero();
            head = player.headFloor;
            player.updateStats();
            System.out.println("角色加载成功");
            count = player.currentFloorNum;
        }

        System.out.println("角色属性："+player.showStatus());
        System.out.println("拥有的技能： "+player.showSkill());

        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy("初级士兵",100,17,12,"力拔山兮"));
        enemies.add(new Enemy("敏捷刺客",70,20,6,"闪身连刺"));
        enemies.add(new Enemy("重装坦克",130,9,18,"举盾防御"));
        enemies.add(new Enemy("神秘法师",75,23,8,"咒术——火"));

        ArrayList<Enemy> bosses = new ArrayList<Enemy>();
        bosses.add(new Enemy("幽影蛛皇",130,28,13,"暗丝缚魂","剧毒吞噬"));
        bosses.add(new Enemy("霜铠冰将",175,18,19,"极寒冰封","冰霜护甲"));
        bosses.add(new Enemy("雷械巨核",135,29,15,"雷霆奔袭","电磁脉冲"));
        bosses.add(new Enemy("枯瘴树灵",170,22,17,"腐根蚀骨","生命汲取"));
        bosses.add(new Enemy("虚空魔神",120,30,9,"湮灭次元","空间扭曲"));
        bosses.add(new Enemy("焚岩督军",160,26,16,"烈焰横斩","熔岩喷发"));
        Scanner sc = new Scanner(System.in);
        Floor current = head;

        while (true){

            current = head;
            for (int i = 1; i < count; i++) {
                if (current.getNextFloor() == null) {
                    Floor newFloor = new Floor(i + 1);
                    newFloor.setRooms();
                    current.setNextFloor(newFloor);
                }
                current = current.getNextFloor();
            }
            player.currentFloor = current;
            player.currentFloorNum = count;

            int roomCount = current.getSpareRoomCount();
            floorMenu: //楼层循环标签
            while (true){
                System.out.println("请选择操作：1.开始探索本层");
                System.out.println("          2.选择楼层");
                System.out.println("          3.查看状态");
                System.out.println("          4.查看背包");
                System.out.println("          5.查看装备");
                System.out.println("          6.退出并存档");
                
                int input = getValidInput(sc, 1, 6);
                
                switch (input){
                    case 1:
                        player.currentFloor=current;
                        System.out.println("当前层数："+ count);
                        while(true){
                            current.showRoomStatus();

                            if (current.isClear()){
                                System.out.println("剧情与BOSS已通过！你可以：");
                                System.out.println("1.进入下一层");
                                System.out.println("2.继续探索本层");
                                int choice = getValidInput(sc, 1, 2);
                                if (choice == 1){
                                    count++;
                                    break floorMenu;
                                }
                            }

                            int maxRoom = current.getStoreRoom() != null ? roomCount + 1 : roomCount;
                            System.out.println("请选择房间(1-" + maxRoom + "，-1退出本层探索)：");

                            int roomChoice = getValidInput(sc, -1, maxRoom);
                            if (roomChoice == -1){
                                System.out.println("退出本层探索");
                                break;
                            }
                            if (current.getStoreRoom() != null && roomChoice == roomCount + 1){
                                if (current.getStoreRoom().isFinished()) {
                                    System.out.println("该商店已探索");
                                    continue;
                                }
                                current.getStoreRoom().Trigger(current, player, enemies, bosses);
                            } else {
                                if (current.getRooms()[roomChoice - 1].isFinished()) {
                                    System.out.println("该房间已探索");
                                    continue;
                                }
                                current.getRooms()[roomChoice - 1].Trigger(current, player, enemies, bosses);
                            }

                            System.out.println("----------------------------------");

                            if (!player.isAlive()) {
                                user.setHero(null);
                                FileManager.saveUser(list, FileManager.USER_DATA_PATH);
                                System.out.println("\n你已死亡！");
                                System.out.println("1.重新开始");
                                System.out.println("2.退出游戏");
                                int deathChoice = getValidInput(sc, 1, 2);
                                if (deathChoice == 1) {
                                    continue restartGame;
                                } else {
                                    System.out.println("游戏结束，已保存进度");
                                    return;
                                }
                            }
                        }
                        break;

                    case 2:
                        System.out.println("请选择楼层(当前可选择层数1-" + count + ") ：");
                        int floorChoice = getValidInput(sc, 1, count);
                        count = floorChoice;
                        break floorMenu;
                        
                    case 3:
                        player.showPlayerStatus();
                        break;

                    case 4:
                        player.showBag();
                        System.out.println("是否使用物品？(输入物品ID，或输入0取消)");
                        int useItemChoice = getValidInput(sc, 0, Integer.MAX_VALUE);
                        if (useItemChoice > 0) {
                            player.usePotion(useItemChoice);
                        }
                        break;
                        
                    case 5:
                        player.showEquipment();
                        System.out.println("是否更换装备？(1.卸下武器 2.卸下防具 3.从背包装备 0.取消)");
                        int equipChoice = getValidInput(sc, 0, 3);
                        if (equipChoice == 1) {
                            player.unequipWeapon();
                        } else if (equipChoice == 2) {
                            player.unequipArmor();
                        } else if (equipChoice == 3) {
                            System.out.println("选择要装备的物品（输入ID）：");
                            player.showBag();
                            int itemId = getValidInput(sc, 0, Integer.MAX_VALUE);
                            if (player.hasItem(itemId)) {
                                player.equipItem(itemId);
                            } else {
                                System.out.println("没有该物品！");
                            }
                        }
                        break;

                    case 6:
                        System.out.println("游戏结束，已保存进度");
                        FileManager.saveUser(list, FileManager.USER_DATA_PATH);
                        return;
                    default:
                        System.out.println("无效输入");
                        break;
                }
            }
        }
        }
    }



    public Hero creatCharacter(String username) {
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
            int input = getValidInput(sc, 0, point);
            point -= input;
            values[i] = input;
        }

        Hero player=new Hero(username,100+values[0]*10,10+values[1]*2,values[2]);

        player.addSkill("普通攻击");
        player.addSkill("强力一击");
        player.addSkill("生命汲取");

        return  player;
    }

    public static int calculateDamage(int atack, int defense){
        int Demage=atack-defense;
        if (Demage<=0){
            Demage=1;
        }
        return Demage;
    }

    public static int getValidInput(Scanner sc, int min, int max) {
        while (true) {
            if (sc.hasNextInt()) {
                int input = sc.nextInt();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("无效输入，请输入 " + min + " 到 " + max + " 之间的数字：");
                }
            } else {
                System.out.println("无效输入，请输入数字：");
                sc.next();
            }
        }
    }
}