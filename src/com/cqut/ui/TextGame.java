package com.cqut.ui;

import com.cqut.domain.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TextGame {
    public void start(ArrayList<User> list,User user) {
        String username = user.getUsername();
        System.out.println("============================");
        System.out.println("     "+username+"欢迎来到文字格斗游戏     ");
        System.out.println("============================");

        Hero player = null;
        Floor head = null;
        int count;
        if (user.getHero() == null) {
            player = creatCharacter(username);
            user.setHero(player);
            head = new Floor(1);
            player.headFloor = head;
            player.currentFloor = head;
            player.currentFloorNum = 1;
            System.out.println("角色创建成功");
            count = 1;
            
            player.addItem(new Item(13, "金币", Item.ItemType.POTION, "游戏货币", 9999, 0, 0, 0, 0), 100);
            System.out.println("获得初始资金：100G");
        } else {
            player = user.getHero();
            head = player.headFloor;
            System.out.println("角色加载成功");
            count = player.currentFloorNum;
        }

        System.out.println("角色属性："+player.showStatus());
        System.out.println("拥有的技能： "+player.showSkill());

        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy("初级士兵",80,15,10,"力拔山兮"));
        enemies.add(new Enemy("敏捷刺客",60,20,5,"闪身连刺"));
        enemies.add(new Enemy("重装坦克",120,10,20,"举盾防御"));
        enemies.add(new Enemy("神秘法师",70,25,8,"咒术——火"));

        Scanner sc = new Scanner(System.in);
        Floor current = head;

        while (true){

            current = head;
            for (int i = 1; i < count; i++) {
                if (current.getNextFloor() == null) {
                    current.setNextFloor(new Floor(i + 1));
                }
                current = current.getNextFloor();
            }
            player.currentFloor = current;
            player.currentFloorNum = count;

            int roomCount = current.getSpareRoomCount();
            while (true){
                System.out.println("请选择操作：1.开始探索本层");
                System.out.println("          2.选择楼层");
                System.out.println("          3.查看背包");
                System.out.println("          4.查看装备");
                System.out.println("          5.退出并存档");
                
                int input = getValidInput(sc, 1, 5);
                
                switch (input){
                    case 1:
                        player.currentFloor=current;
                        System.out.println("当前层数："+ count);
                        while(true){
                            current.showRoomStatus();

                            boolean allExplored = true;
                            for (int i = 0; i < current.getRooms().length; i++){
                                if (current.getRooms()[i].getTypeNum() != 5 && !current.getRooms()[i].isFinished()){
                                    allExplored = false;
                                    break;
                                }
                            }

                            if (allExplored){
                                System.out.println("本层所有房间已探索完成，自动进入下一层！");
                                count++;
                                break;
                            }

                            if (current.isClear()){
                                System.out.println("BOSS已击败！你可以：");
                                System.out.println("1.进入下一层");
                                System.out.println("2.继续探索本层");
                                int choice = getValidInput(sc, 1, 2);
                                if (choice == 1){
                                    count++;
                                    break;
                                }
                            }

                            System.out.println("请选择房间(-1退出本层探索)：");
                            int roomChoice = sc.nextInt();
                            if (roomChoice == -1){
                                System.out.println("退出本层探索");
                                break;
                            }

                            if (roomChoice < 1 || roomChoice > roomCount){
                                System.out.println("无效房间号，请输入 1-" + roomCount + " 之间的数字：");
                                roomChoice = getValidInput(sc, 1, roomCount);
                            }

                            current.getRooms()[roomChoice - 1].Trigger(current, player, enemies);
                        }
                        break;

                    case 2:
                        System.out.println("请选择楼层(当前可选择层数1-" + count + ") ：");
                        int floorChoice = sc.nextInt();

                        if (floorChoice < 1 || floorChoice > count){
                            System.out.println("无效楼层号，请输入 1-" + count + " 之间的数字：");
                            floorChoice = getValidInput(sc, 1, count);
                        }
                        count = floorChoice;
                        break;
                        
                    case 3:
                        player.showBag();
                        System.out.println("是否使用物品？(输入物品ID，或输入0取消)");
                        int useItemChoice = sc.nextInt();
                        if (useItemChoice > 0) {
                            player.usePotion(useItemChoice);
                        }
                        break;
                        
                    case 4:
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
                            int itemId = sc.nextInt();
                            if (player.hasItem(itemId)) {
                                player.equipItem(itemId);
                            } else {
                                System.out.println("没有该物品！");
                            }
                        }
                        break;

                    case 5:
                        System.out.println("游戏结束，已保存进度");
                        FileManager.saveUser(list,"D:/code/IDEA_PRE/First_Project/userdata.json");
                        return;
                    default:
                        System.out.println("无效输入");
                        break;
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


    
    private void handleLoot(Hero player, Enemy enemy, int wins) {
        Random random = new Random();
        
        int goldReward = random.nextInt(20) + 10 + wins * 5;
        player.addItem(new Item(13, "金币", Item.ItemType.POTION, "游戏货币", 9999, 0, 0, 0, 0), goldReward);
        System.out.println("获得金币：" + goldReward + "G");
        
        int enemyLevel = wins + 1;
        ArrayList<Item> possibleLoot = ItemFactory.getLootItems(enemyLevel);
        
        if (!possibleLoot.isEmpty() && random.nextInt(100) < 40) {
            Item lootItem = possibleLoot.get(random.nextInt(possibleLoot.size()));
            int quantity = 1;
            if (lootItem.type == Item.ItemType.POTION) {
                quantity = random.nextInt(2) + 1;
            }
            player.addItem(lootItem, quantity);
        }
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
                sc.next(); // 清除非法输入
            }
        }
    }
}