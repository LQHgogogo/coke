package com.cqut.domain;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Floor {
    private Room[] rooms;
    private int floorNum;
    private boolean isClear;
    private boolean storyCleared;
    private boolean bossCleared;
    private Room storeRoom;
    private Floor nextFloor;

    public Floor() {}

    public Floor(int floorNum) {
        this.floorNum = floorNum;
        this.nextFloor = null;
        this.storyCleared = false;
        this.bossCleared = false;
    }
    
    public void setRooms() {
        rooms = new Room[10];
        Random random = new Random();

        int[] roomTypes = new int[10];
        

        int bossIndex = random.nextInt(10);
        roomTypes[bossIndex] = 3;

        int storyIndex;
        do {
            storyIndex = random.nextInt(10);
        } while (storyIndex == bossIndex);
        roomTypes[storyIndex] = 4;

        int battleCount = random.nextInt(3) + 1;
        int placedBattle = 0;
        while (placedBattle < battleCount) {
            int index = random.nextInt(10);
            if (roomTypes[index] == 0) { // 还未分配类型
                roomTypes[index] = 1;
                placedBattle++;
            }
        }

        int rewardCount = random.nextInt(4);
        int placedReward = 0;
        while (placedReward < rewardCount) {
            int index = random.nextInt(10);
            if (roomTypes[index] == 0) {
                roomTypes[index] = 2;
                placedReward++;
            }
        }

        for (int i = 0; i < 10; i++) {
            if (roomTypes[i] == 0) {
                roomTypes[i] = 5;
            }
        }

        for (int i = 0; i < 10; i++) {
            rooms[i] = new Room(roomTypes[i]);
        }
        
        // 7. 随机打乱房间顺序
        shuffleRooms(random);

        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].getTypeNum() == 5) {
                int lastIndex = getLastNoneSpareRoom(rooms);
                if (lastIndex > i) {
                    Room temp = rooms[i];
                    rooms[i] = rooms[lastIndex];
                    rooms[lastIndex] = temp;
                }
            }
        }

        if (floorNum%3==0){
            storeRoom=new Room(6);
        }
    }

    public static int getLastNoneSpareRoom(Room[] rooms){
        for (int i = rooms.length - 1; i >= 0; i--) {
            if (rooms[i].getTypeNum() != 5){
                return i;
            }
        }
        return -1;
    }

    private void shuffleRooms(Random random) {
        for (int i = rooms.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Room temp = rooms[i];
            rooms[i] = rooms[j];
            rooms[j] = temp;
        }
    }

    public void showRoomStatus(){
        if (rooms == null) {
            System.out.println("房间尚未初始化");
            return;
        }
        
        int count = 1;
        for (int i = 0; i < rooms.length; i++){
            if (rooms[i].getTypeNum() != 5){
                String tag = "";
                if (rooms[i].getTypeNum() == 4) {
                    tag = "【BOSS】";
                }
                if (rooms[i].isFinished() == true){
                    System.out.println("第" + floorNum + "层第" + count + "个房间" + tag + "：已探索");
                }else{
                    System.out.println("第" + floorNum + "层第" + count + "个房间" + tag + "：未探索");
                }
                count++;
            }
        }
        if (storeRoom!=null) {
            System.out.println("第" + floorNum + "层第" + count + "个房间：商店房");
        }
    }

    public int getSpareRoomCount(){
        int count=0;
        for (int i = 0; i < rooms.length; i++){
            if (rooms[i].getTypeNum() != 5){
                count++;
            }
        }
        return count;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public Floor getNextFloor() {
        return nextFloor;
    }

    public void setNextFloor(Floor nextFloor) {
        this.nextFloor = nextFloor;
    }

    public Room getStoreRoom() {
        return storeRoom;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public boolean isClear() {
        return storyCleared && bossCleared;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public boolean isStoryCleared() {
        return storyCleared;
    }

    public void setStoryCleared(boolean storyCleared) {
        this.storyCleared = storyCleared;
    }

    public boolean isBossCleared() {
        return bossCleared;
    }

    public void setBossCleared(boolean bossCleared) {
        this.bossCleared = bossCleared;
    }

    public int getFloorNum() {
        return floorNum;
    }


    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————

    public static class Room {
        private int TypeNUm;
        private boolean isFinished;
        
        public Room() {
        }
        
        public Room(int TypeNUm) {
            this.TypeNUm = TypeNUm;
            isFinished = false;
        }
        
        //触发房间探索交互功能1为战斗房间，2为奖励房间，3为剧情房间，4为楼层boss房，5为空房间,6为商店房�?
        public void Trigger(Floor floor,Hero player, ArrayList<Enemy> enemies,ArrayList<Enemy> bosses)
        {
            if (TypeNUm==1){
                System.out.println("你进入战斗房间");
                Random random = new Random();
                Enemy enemy = scaleEnemy(new Enemy(enemies.get(random.nextInt(enemies.size()))), floor.floorNum, false);
                System.out.println("你遇到了"+enemy.name);
                System.out.println(enemy.showStatus());
                while(player.isAlive() && enemy.isAlive()){
                    System.out.println(floor.getBlood(player.name, player.HP, player.maxHP));
                    System.out.println(floor.getBlood(enemy.name, enemy.HP, enemy.maxHP));
                    playerTurn(floor, player, enemy);
                    if(!enemy.isAlive()){
                        System.out.println("你击杀了"+enemy.name);
                        handleLootDrop(player, enemy, false);
                        isFinished = true;
                        return;
                    }
                    enemyTurn(enemy,player);
                    if(!player.isAlive()){
                        System.out.println("你被"+enemy.name+"杀死");
                        isFinished = true;
                    }
                }
            } else if (TypeNUm==2) {
                // 奖励房间逻辑
                System.out.println("\n========== 奖励房间 ==========");
                System.out.println("你发现了一个神秘的宝箱！");

                // 显示关闭的宝箱图案
                showChest(false);

                // 询问玩家是否打开宝箱
                Scanner sc = new Scanner(System.in);
                System.out.print("\n是否打开宝箱？（1-是/2-否）：");
                int choice = getValidInput(sc, 1, 2);

                if (choice == 1) {
                    System.out.println("\n你打开了宝箱...");

                    // 显示打开的宝箱图案
                    showChest(true);

                    Random random = new Random();

                    // 15%概率宝箱为空
                    if (random.nextInt(100) < 25) {
                        System.out.println("\n宝箱里空空如也，什么也没有...");
                        System.out.println("看来运气不太好呢！");
                    } else {
                        // 随机获得10-100经验值
                        int expGained = random.nextInt(91) + 10; // 10-100
                        player.Exp += expGained;
                        System.out.println("\n你获得了 " + expGained + " 点经验值！");
                        System.out.println("当前经验值：" + player.Exp);

                        // 随机获得10-100金币
                        int goldCount = random.nextInt(91) + 10;
                        player.addGold(goldCount);
                        System.out.println("你获得了 " + goldCount + " 个金币！");

                        Item rewardItem = getRandomRewardItem(random);
                        if (rewardItem != null) {
                            if (rewardItem.type == Item.ItemType.POTION) {
                                // 生成随机数量
                                int potionCount = random.nextInt(1) + 1;
                                player.addItem(rewardItem, potionCount);
                                System.out.println("你获得了 " + potionCount + " 个" + rewardItem.name + "！");
                            } else {
                                player.addItem(rewardItem, 1);
                                System.out.println("你获得了：" + rewardItem.name + "！");
                            }
                        }
                    }
                    // 设置房间为已探索
                    isFinished = true;

                } else {
                    System.out.println("\n你选择不打开宝箱，离开了房间。");
                }
                System.out.println("=========================\n");
            }else if (TypeNUm==3) {
                boolean triggered = StoryManager.triggerStory(player, floor);
                isFinished = true;

                Random random = new Random();
                int expGain = random.nextInt(30) + 10;
                player.addExp(expGain);
                System.out.println("剧情启迪，你获得了" + expGain + "点经验！");

                if (player.storyProgress >= StoryManager.STORY_COMPLETE) {
                    System.out.println("你低头望向自己的手，紧握拳头，感觉到了力量的洗礼");
                }
                floor.setStoryCleared(true);
            }else if (TypeNUm==4) {
                System.out.println("你进入了BOSS房间！");
                Random random = new Random();
                Enemy boss = scaleEnemy(new Enemy(bosses.get(random.nextInt(bosses.size()))), floor.floorNum, true);
                System.out.println("你遇到了BOSS：" + boss.name);
                System.out.println(boss.showStatus());

                while(player.isAlive() && boss.isAlive()){
                    System.out.println(floor.getBlood(player.name, player.HP, player.maxHP));
                    System.out.println(floor.getBlood(boss.name, boss.HP, boss.maxHP));
                    playerTurn(floor, player, boss);

                    if(!boss.isAlive()){
                        System.out.println("你击败了BOSS：" + boss.name);
                        handleLootDrop(player, boss, true);

                        int expGain = random.nextInt(50) + 50;
                        player.addExp(expGain);
                        System.out.println("你获得了" + expGain + "点经验！");

                        isFinished = true;
                        floor.setBossCleared(true);
                        System.out.println("恭喜通关第" + floor.floorNum + "层！");
                        return;
                    }

                    enemyTurn(boss, player);
                    if(!player.isAlive()){
                        System.out.println("你被BOSS " + boss.name + " 击败...");
                        isFinished = true;
                        return;
                    }

                }
            }else if (TypeNUm==5){
                isFinished = true;
            }else if (TypeNUm==6){
                // 商店房间逻辑
                List<Item> shopItems = new ArrayList<>();
                Random random = new Random();

                // 生成商店物品
                List<Item> allItems = ItemFactory.getAllItems();
                for (Item item : allItems) {
                    if (item.id == 13) {
                        continue;
                    }

                    if (item.type == Item.ItemType.POTION && random.nextInt(100) < 60) {
                        shopItems.add(item);
                    } else if ((item.type == Item.ItemType.WEAPON || item.type == Item.ItemType.ARMOR)
                               && random.nextInt(100) < 30) {
                        shopItems.add(item);
                    }
                }

                if (shopItems.isEmpty()) {
                    shopItems.add(ItemFactory.getItemById(9));
                    shopItems.add(ItemFactory.getItemById(1));
                }

                // 打开商店
                System.out.println("\n========== 商店 ==========");
                System.out.println("欢迎来到神秘商店！");
                System.out.println("当前金币: " + player.getGold() + "G\n");

                // 显示商品列表
                System.out.println("可购买商品列表：");
                int index = 1;
                for (Item item : shopItems) {
                    System.out.println(index + ". [ID:" + item.id + "] " + item.showInfo());
                    index++;
                }
                System.out.println();

                // 购买循环
                Scanner sc = new Scanner(System.in);
                while (true) {
                    System.out.print("请输入要购买的物品ID（0退出）：");
                    int choice = 0;
                    try {
                        choice = sc.nextInt();
                    } catch (Exception e) {
                        sc.next();
                        System.out.println("无效输入！");
                        continue;
                    }

                    if (choice == 0) {
                        System.out.println("感谢光临！");
                        break;
                    }

                    // 购买物品
                    Item targetItem = null;
                    for (Item item : shopItems) {
                        if (item.id == choice) {
                            targetItem = item;
                            break;
                        }
                    }

                    if (targetItem == null) {
                        System.out.println("商品不存在！");
                        continue;
                    }

                    if (player.getGold() < targetItem.price) {
                        System.out.println("金币不足！需要 " + targetItem.price + "G，当前拥有 " + player.getGold() + "G");
                        continue;
                    }

                    if ((targetItem.type == Item.ItemType.WEAPON || targetItem.type == Item.ItemType.ARMOR)
                        && player.hasItem(choice)) {
                        System.out.println("你已经拥有该装备！");
                        continue;
                    }

                    player.addGold(-targetItem.price);
                    player.addItem(targetItem, 1);
                    System.out.println("成功购买 " + targetItem.name + "！花费 " + targetItem.price + "G，剩余 " + player.getGold() + "G");
                }
                System.out.println("=========================\n");

                isFinished = true;
            }
        }
        
        public void SetTypeNUm(int TypeNUm)
        {
            this.TypeNUm = TypeNUm;
        }
        
        public int getTypeNum() {
            return TypeNUm;
        }
        
        public void setTypeNum(int typeNum) {
            this.TypeNUm = typeNum;
        }
        
        public boolean isFinished() {
            return isFinished;
        }
        
        public void setFinished(boolean finished) {
            this.isFinished = finished;
        }
    }
    public static void playerTurn(Floor floor, Hero player, Enemy enemy){
        System.out.println("===你的回合===");
        Scanner sc = new Scanner(System.in);
        System.out.println("是否使用药水？（1-是/2-否）");
        int potionChoice=getValidInput(sc,1,2);
        if (potionChoice==1){
            ArrayList<Integer> availablePotions = new ArrayList<>();
            for (int i=9;i<=12;i++){
                if (player.hasItem(i)){
                    availablePotions.add(i);
                }
            }
            if(availablePotions.isEmpty()){
                System.out.println("你没有可用的药水");
            }else{
                System.out.println("请选择药水：");
                for (int i=0;i<availablePotions.size();i++){
                    Item potion = ItemFactory.getItemById(availablePotions.get(i));
                    int count = player.getItemCount(availablePotions.get(i));
                    System.out.println((i+1)+"."+potion.name+" 数量："+count);
                }
                int selectedPotionIndex = getValidInput(sc,1,availablePotions.size())-1;
                int selectedPotionId = availablePotions.get(selectedPotionIndex);
                player.usePotion(selectedPotionId);
                System.out.println(floor.getBlood(player.name, player.HP, player.maxHP));
                System.out.println(floor.getBlood(enemy.name, enemy.HP, enemy.maxHP));
            }
        }else{
            System.out.println("你选择不使用药水");
        }
        System.out.println("请选择技能：");
        for (int i=0;i<player.skillList.size();i++){
            System.out.println((i+1)+"."+player.skillList.get(i)+" ");
        }
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

        String selectedSkill = player.skillList.get(input);
        switch (selectedSkill) {
            case "普通攻击":
                System.out.println("你选择了普通攻击");
                int demage1 = calculateDamage(player.attack,enemy.defense);
                System.out.println("你使用普通攻击对"+enemy.name+"，造成"+demage1+"点伤害！");
                enemy.takeDamage(demage1);
                break;
            case "强力一击":
                if (player.HP > player.maxHP * 0.05){
                    int cost1 = (int)(player.maxHP * 0.05);
                    System.out.println("你选择了强力一击(那么力量的代价是什么呢——消耗" + cost1 + "点生命)");
                    player.takeDamage(cost1);
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
            case "生命汲取":
                if (player.HP>=10){
                    System.out.println("你选择了生命汲取(绝望中的生机——消耗10点生命)");
                    player.takeDamage(10);
                    Random r=new Random();
                    int heal = r.nextInt(30 + (int)(player.Lv * 1.5)) + 1;
                    player.heal(heal);
                    System.out.println("你回复了"+heal+"点生命值！");
                }else {
                    System.out.println("你的生命值不足，无法使用该技能,但还是用出了普通攻击");
                    int demage4=calculateDamage(player.attack,enemy.defense);
                    enemy.takeDamage(demage4);
                    System.out.println("你使用普通攻击对"+enemy.name+"，造成"+demage4+"点伤害！");
                }
                break;
            case "旋风斩":
                System.out.println("你选择了旋风斩");
                int demage6 = calculateDamage((int)(player.attack * 1.5), enemy.defense);
                System.out.println("你使用旋风斩对" + enemy.name + "，造成" + demage6 + "点伤害！");
                enemy.takeDamage(demage6);
                break;
            case "雷霆一击":
                if (player.HP > player.maxHP * 0.1) {
                    int cost2 = (int)(player.maxHP * 0.1);
                    System.out.println("你选择了雷霆一击(天雷奔涌——消耗" + cost2 + "点生命)");
                    player.takeDamage(cost2);
                    int demage7 = calculateDamage((int)(player.attack * 2.5), enemy.defense);
                    enemy.takeDamage(demage7);
                    System.out.println("你使用雷霆一击对" + enemy.name + "，造成" + demage7 + "点伤害！");
                } else {
                    System.out.println("你的生命值不足，无法使用该技能，但使用普通攻击");
                    int demage8 = calculateDamage(player.attack, enemy.defense);
                    System.out.println("你使用普通攻击对" + enemy.name + "，造成" + demage8 + "点伤害！");
                    enemy.takeDamage(demage8);
                }
                break;
            case "圣光普照":
                if (player.HP >= 5) {
                    System.out.println("你选择了圣光普照(圣光庇护——消耗5点生命)");
                    player.takeDamage(5);
                    int heal2 = (int)(player.maxHP * 0.3);
                    player.heal(heal2);
                    System.out.println("圣光普照回复了" + heal2 + "点生命值！");
                } else {
                    System.out.println("你的生命值不足，无法使用该技能，但使用普通攻击");
                    int demage9 = calculateDamage(player.attack, enemy.defense);
                    System.out.println("你使用普通攻击对" + enemy.name + "，造成" + demage9 + "点伤害！");
                    enemy.takeDamage(demage9);
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
        int randomNum = r.nextInt(3);
        if (randomNum == 1) {
            action = enemy.skill;
        } else if (randomNum == 2 && enemy.skill2 != null) {
            action = enemy.skill2;
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
            case "暗丝缚魂":
                System.out.println("敌人使用了暗丝缚魂");
                int demage5=calculateDamage((int)(enemy.attack*1.6),player.defense);
                System.out.println(enemy.name+"使用暗丝缚魂，对我造成了"+demage5+"点伤害！");
                player.takeDamage(demage5);
                break;
            case "极寒冰封":
                System.out.println("敌人使用了极寒冰封");
                int demage6=calculateDamage((int)(enemy.attack*1.4),player.defense/2);
                System.out.println(enemy.name+"使用极寒冰封，对我造成了"+demage6+"点伤害！");
                player.takeDamage(demage6);
                break;
            case "雷霆奔袭":
                System.out.println("敌人使用了雷霆奔袭");
                int demage7=0;
                for (int i=0;i<3;i++){
                    demage7+=calculateDamage(enemy.attack/3, player.defense/2);
                }
                System.out.println(enemy.name+"使用雷霆奔袭，对我造成了"+demage7+"点伤害！");
                player.takeDamage(demage7);
                break;
            case "腐根蚀骨":
                System.out.println("敌人使用了腐根蚀骨");
                int demage8=calculateDamage((int)(enemy.attack*1.5),player.defense-5);
                System.out.println(enemy.name+"使用腐根蚀骨，对我造成了"+demage8+"点伤害！");
                player.takeDamage(demage8);
                break;
            case "湮灭次元":
                System.out.println("敌人使用了湮灭次元");
                int demage9=calculateDamage((int)(enemy.attack*2),player.defense);
                System.out.println(enemy.name+"使用湮灭次元，对我造成了"+demage9+"点伤害！");
                player.takeDamage(demage9);
                break;
            case "烈焰横斩":
                System.out.println("敌人使用了烈焰横斩");
                int demage10=calculateDamage((int)(enemy.attack*1.7),player.defense);
                System.out.println(enemy.name+"使用烈焰横斩，对我造成了"+demage10+"点伤害！");
                player.takeDamage(demage10);
                break;
            case "毁灭吐息":
                System.out.println("敌人使用了毁灭吐息");
                int demage11=calculateDamage((int)(enemy.attack*1.9),player.defense);
                System.out.println(enemy.name+"使用毁灭吐息，对我造成了"+demage11+"点伤害！");
                player.takeDamage(demage11);
                break;
            case "神圣审判":
                System.out.println("敌人使用了神圣审判");
                int demage12=calculateDamage((int)(enemy.attack*1.5),player.defense/2);
                System.out.println(enemy.name+"使用神圣审判，对我造成了"+demage12+"点伤害！");
                player.takeDamage(demage12);
                break;
            case "死亡印记":
                System.out.println("敌人使用了死亡印记");
                int demage13=calculateDamage((int)(enemy.attack*2.2),player.defense);
                System.out.println(enemy.name+"使用死亡印记，对我造成了"+demage13+"点伤害！");
                player.takeDamage(demage13);
                break;
            case "天雷轰顶":
                System.out.println("敌人使用了天雷轰顶");
                int demage14=0;
                for (int i=0;i<2;i++){
                    demage14+=calculateDamage(enemy.attack/2, player.defense/2);
                }
                System.out.println(enemy.name+"使用天雷轰顶，对我造成了"+demage14+"点伤害！");
                player.takeDamage(demage14);
                break;
            case "绝对零度":
                System.out.println("敌人使用了绝对零度");
                int demage15=calculateDamage((int)(enemy.attack*1.6),player.defense-3);
                System.out.println(enemy.name+"使用绝对零度，对我造成了"+demage15+"点伤害！");
                player.takeDamage(demage15);
                break;
            case "虚空破碎":
                System.out.println("敌人使用了虚空破碎");
                int demage16=calculateDamage((int)(enemy.attack*2.5),player.defense);
                System.out.println(enemy.name+"使用虚空破碎，对我造成了"+demage16+"点伤害！");
                player.takeDamage(demage16);
                break;
            case "剧毒吞噬":
                System.out.println("敌人使用了剧毒吞噬");
                int demage17=calculateDamage((int)(enemy.attack*1.6),player.defense/2);
                System.out.println(enemy.name+"使用剧毒吞噬，对我造成了"+demage17+"点伤害！");
                player.takeDamage(demage17);
                break;
            case "冰霜护甲":
                System.out.println("敌人使用了冰霜护甲");
                enemy.defending=true;
                int healAmount=enemy.maxHP/10;
                enemy.HP=Math.min(enemy.HP+healAmount,enemy.maxHP);
                System.out.println(enemy.name+"已进入防御状态，并恢复了"+healAmount+"点生命！");
                break;
            case "电磁脉冲":
                System.out.println("敌人使用了电磁脉冲");
                int demage18=0;
                for (int i=0;i<3;i++){
                    demage18+=calculateDamage(enemy.attack/2,player.defense/3);
                }
                System.out.println(enemy.name+"使用电磁脉冲，对我造成了"+demage18+"点伤害！");
                player.takeDamage(demage18);
                break;
            case "生命汲取":
                System.out.println("敌人使用了生命汲取");
                int demage19=calculateDamage((int)(enemy.attack*1.4),player.defense);
                int stealHP=demage19/2;
                enemy.HP=Math.min(enemy.HP+stealHP,enemy.maxHP);
                System.out.println(enemy.name+"使用生命汲取，对我造成了"+demage19+"点伤害，并恢复了"+stealHP+"点生命！");
                player.takeDamage(demage19);
                break;
            case "空间扭曲":
                System.out.println("敌人使用了空间扭曲");
                int demage20=calculateDamage((int)(enemy.attack*2.0),0);
                System.out.println(enemy.name+"使用空间扭曲，无视防御，对我造成了"+demage20+"点伤害！");
                player.takeDamage(demage20);
                break;
            case "熔岩喷发":
                System.out.println("敌人使用了熔岩喷发");
                int demage21=calculateDamage((int)(enemy.attack*2.0),player.defense/2);
                System.out.println(enemy.name+"使用熔岩喷发，对我造成了"+demage21+"点伤害！");
                player.takeDamage(demage21);
                break;
        }
    }

    public static Enemy scaleEnemy(Enemy enemy, int floorNum, boolean isBoss) {
        double hpMult = isBoss ? (1 + (floorNum - 1) * 0.12) : (1 + (floorNum - 1) * 0.25);
        double atkMult = isBoss ? (1 + (floorNum - 1) * 0.06) : (1 + (floorNum - 1) * 0.2);
        double defMult = isBoss ? (1 + (floorNum - 1) * 0.04) : (1 + (floorNum - 1) * 0.15);

        enemy.maxHP = (int)(enemy.maxHP * hpMult);
        enemy.HP = enemy.maxHP;
        enemy.attack = (int)(enemy.attack * atkMult);
        enemy.defense = (int)(enemy.defense * defMult);
        return enemy;
    }

    public static int calculateDamage(int atack, int defense){
        int Demage=atack-defense;
        if (Demage<=0){
            Demage=1;
        }
        return Demage;
    }
    public String getBlood(String  name,int HP,int maxHP){
        int BloodLength=20;
        int filled =(int)( HP * 1.0 / maxHP * BloodLength);
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("【");
        for (int i = 0; i < 20; i++) {
            if (i < filled){
                sb.append("=");
            }else {
                sb.append("-");
            }
        }
        sb.append("】").append( HP).append("/"+maxHP).append(" HP");
        return sb.toString();
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
    public static void handleLootDrop(Hero player, Enemy enemy, boolean isBoss) {
        Random random = new Random();
        int expGain = random.nextInt(50) + 1;
        player.addExp(expGain);
        System.out.println("你获得了" + expGain + "点经验！");

        int goldGain = random.nextInt(51) +(int)(20*(1+0.05*player.Lv));
        player.addGold(goldGain);
        System.out.println("你获得了" + goldGain + "个金币！");

        if (isBoss && random.nextDouble() <= 0.25) {
            int skillBookId = random.nextInt(3) + 14;
            Item skillBook = ItemFactory.getItemById(skillBookId);
            if (skillBook != null) {
                System.out.println("BOSS掉落了技能书：【" + skillBook.name + "】！");
                player.addItem(skillBook, 1);
            }
        }

        double dropRate = 0.3;
        if (random.nextDouble() > dropRate) {
            return;
        }
        int itemId = random.nextInt(12) + 1;
        Item lootItem = ItemFactory.getItemById(itemId);

        if (lootItem == null) {
            return;
        }
        if (itemId >= 9 && itemId <= 12) {
            player.addItem(lootItem, 1);
            System.out.println("怪物掉落了：" + lootItem.name + " x1");
        } else {
            player.addItem(lootItem, 1);
            System.out.println("怪物掉落了：" + lootItem.name);
        }
    }

    private static void showChest(boolean isOpen) {
        if (isOpen) {
            System.out.println("      ╔════════════╗");
            System.out.println("    ║            ║  ");
            System.out.println("    ║            ║  ");
            System.out.println("    ║     🌟     ║  ");
            System.out.println("    ║            ║  ");
            System.out.println("    ║            ║  ");
            System.out.println("    ╚════════════╝\n");
        } else {
            System.out.println("    ╔════════════╗");
            System.out.println("    ║  ╔══════╗  ║");
            System.out.println("    ║  ║      ║  ║");
            System.out.println("    ║  ║ ???? ║  ║");
            System.out.println("    ║  ║      ║  ║");
            System.out.println("    ║  ╚══════╝  ║");
            System.out.println("    ╚════════════╝\n");
        }
    }


    private static Item getRandomRewardItem(Random random) {
        List<Item> allItems = ItemFactory.getAllItems();

        if (allItems.isEmpty()) {
            return null;
        }

        int typeRoll = random.nextInt(100);
        Item.ItemType targetType;

        if (typeRoll < 60) {
            targetType = Item.ItemType.POTION;
        } else if (typeRoll < 85) {
            targetType = Item.ItemType.WEAPON;
        } else {
            targetType = Item.ItemType.ARMOR;
        }

        List<Item> typeItems = new ArrayList<>();
        for (Item item : allItems) {
            if (item.type == targetType) {
                typeItems.add(item);
            }
        }

        if (typeItems.isEmpty()) {
            return allItems.get(0);
        }

        // 从该类型中随机选择一个
        return typeItems.get(random.nextInt(typeItems.size()));
    }
}