package com.cqut.ui;

import com.cqut.domain.Enemy;
import com.cqut.domain.Floor;
import com.cqut.domain.Hero;
import com.cqut.domain.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TextGame_backup {

    public void start(User user) {
        String username = user.getUsername();
        System.out.println("============================");
        System.out.println("     "+username+"欢迎来到文字格斗游戏     ");
        System.out.println("============================");

        Hero player = creatCharacter(username);
        System.out.println("角色创建成功");
        System.out.println("角色初始属性："+player.showStatus());
        System.out.println("拥有的技能： "+player.showSkill());

        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy("初级士兵",80,15,10,"力拔山兮"));
        enemies.add(new Enemy("敏捷刺客",60,20,5,"闪身连刺"));
        enemies.add(new Enemy("重装坦克",120,10,20,"举盾防御"));
        enemies.add(new Enemy("神秘法师",70,25,8,"咒术——火"));

        int count = 1;
        int win = 0;

        while (true){
            Scanner sc = new Scanner(System.in);

            Floor floor = new Floor(count);
            int roomCount = floor.getSpareRoomCount();
            while (true){
                System.out.println("请选择操作：1.开始探索本层");
                System.out.println("          2.退出并存档");
                
                int input = getValidInput(sc, 1, 2);
                
                switch (input){
                    case 1:
                        System.out.println("当前层数："+ count);
                        while(true){
                            floor.showRoomStatus();

                            boolean allExplored = true;
                            for (int i = 0; i < floor.getRooms().length; i++){
                                if (floor.getRooms()[i].getTypeNum() != 5 && !floor.getRooms()[i].isFinished()){
                                    allExplored = false;
                                    break;
                                }
                            }

                            if (allExplored){
                                System.out.println("本层所有房间已探索完成，自动进入下一层！");
                                count++;
                                break;
                            }

                            if (floor.isClear()){
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

                            floor.getRooms()[roomChoice - 1].Trigger(floor);
                        }
                        break;
                    case 2:
                        System.out.println("游戏结束，已保存进度");
                        return;
                    default:
                        System.out.println("无效输入");
                        break;
                }
            }
        }
    }

    public String getBlood(String  name,int HP,int maxHP){
        int BloodLength=20;
        int filled =(int)( HP * 1.0 / maxHP * BloodLength);
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("【");
        for (int i = 0; i < 20; i++) {
            if (i < filled){
                sb.append("⬛\uFE0F");
            }else {
                sb.append("⬜\uFE0F");
            }
        }
        sb.append("】").append( HP).append("/"+maxHP).append(" HP");
        return sb.toString();
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

    public static void playerTurn(Hero player, Enemy enemy,int wins){
        System.out.println("===你的回合===");
        System.out.println("请选择技能：");
        for (int i=0;i<player.skillList.size();i++){
            System.out.println((i+1)+"."+player.skillList.get(i)+" ");
        }
        Scanner sc = new Scanner(System.in);
        int input = -1;
        while (true) {
            if (sc.hasNextInt()) {
                input = sc.nextInt() - 1;
                if (input >= 0 && input < player.skillList.size()) {
                    break;
                } else {
                    System.out.println("无效输入，请输入 1-" + player.skillList.size() + " 之间的数字：");
                }
            } else {
                System.out.println("无效输入，请输入数字：");
                sc.next();
            }
        }

        switch ( input){
            case 0:
                System.out.println("你选择了普通攻击");
                int demage1 = calculateDamage(player.attack,enemy.defense);
                System.out.println("你使用普通攻击对"+enemy.name+"，造成"+demage1+"点伤害！");
                enemy.takeDamage(demage1);
                break;
            case 1:
                if (player.HP>=10){
                    System.out.println("你选择了强力一击(那么力量的代价是什么呢——消耗10点生命)");
                    player.takeDamage(10);
                    int demage2 = calculateDamage(player.attack*2,enemy.defense);
                    enemy.takeDamage(demage2);
                    System.out.println("你使用强力一击对"+enemy.name+"，造成"+demage2+"点伤害！");
                }else {
                    System.out.println("你的生命值不足，无法使用该技能，但使用普通攻击");
                    int demage3 = calculateDamage(player.attack,enemy.defense);
                    System.out.println("你使用普通攻击对"+enemy.name+"，造成"+demage3+"点伤害！");
                    enemy.takeDamage(demage3);
                }
                break;
            case 2:
                if (player.HP>=10){
                    System.out.println("你选择了生命汲取(绝望中的生机——消耗10点生命)");
                    player.takeDamage(10);
                    Random r=new Random();
                    int heal = r.nextInt(30+(int)(wins*1.5))+1;
                    player.heal(heal);
                    System.out.println("你回复了"+heal+"点生命值！");
                }else {
                    System.out.println("你的生命值不足，无法使用该技能,但还是用出了普通攻击");
                    int demage4=calculateDamage(player.attack,enemy.defense);
                    enemy.takeDamage(demage4);
                    System.out.println("你使用普通攻击对"+enemy.name+"，造成"+demage4+"点伤害！");
                }
                break;
            default:
                System.out.println("无效输入,默认进行普通攻击");
                int demage5=calculateDamage(player.attack,enemy.defense);
                System.out.println("你使用普通攻击对"+enemy.name+"，造成"+demage5+"点伤害！");
                enemy.takeDamage(demage5);
                break;
        }
    }

    public static void enemyTurn(Enemy enemy,Hero player){
        System.out.println("===敌人回合===");

        String action="普通攻击";

        Random r=new Random();
        int randomNum = r.nextInt(2);
        if (randomNum==1){
            action= enemy.skill;
        }

        switch (action){
            case "普通攻击":
                System.out.println("敌人使用了普通攻击");
                int demage1=calculateDamage(enemy.attack,player.defense);
                System.out.println(enemy.name+"使用普通攻击，对我造成了"+demage1+"点伤害！");
                player.takeDamage(demage1);
                break;
            case "力拔山兮":
                System.out.println("敌人使用了力拔山兮");
                int demage2=calculateDamage((int)(enemy.attack*1.5),player.defense);
                System.out.println(enemy.name+"使用力拔山兮，对我造成了"+demage2+"点伤害！");
                player.takeDamage(demage2);
                break;
            case "闪身连刺":
                System.out.println("敌人使用了闪身连刺");
                int demage3=0;
                for (int i=0;i<2;i++){
                    demage3+=calculateDamage(enemy.attack/2, player.defense/3);
                }
                System.out.println(enemy.name+"使用闪身连刺，对我造成了"+demage3+"点伤害！");
                player.takeDamage(demage3);
                break;
            case "举盾防御":
                System.out.println("敌人使用了举盾防御");
                enemy.defending=true;
                System.out.println(enemy.name+"已进入防御状态！");
                break;
            case "咒术——火":
                System.out.println("敌人使用了咒术——火");
                int demage4=calculateDamage((int)(enemy.attack*1.8),player.defense/2);
                System.out.println(enemy.name+"使用了咒术——火，对我造成了"+demage4+"点伤害！");
                player.takeDamage(demage4);
                break;
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



