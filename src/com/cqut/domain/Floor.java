package com.cqut.domain;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Floor {
    private Room[] rooms;
    private int floorNum;
    private boolean isClear;
    private Room storeRoom;
    private Floor nextFloor;

    public Floor() {}

    public Floor(int floorNum) {
        this.floorNum = floorNum;
        this.nextFloor=null;
        setRooms();
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
                if (rooms[i].isFinished() == true){
                    System.out.println("第" + floorNum + "层第" + count + "个房间：已探索");
                }else{
                    System.out.println("第" + floorNum + "层第" + count + "个房间：未探索");
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
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
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
                Enemy enemy=enemies.get(random.nextInt(enemies.size()));
                System.out.println("你遇到了"+enemy.name);
                System.out.println(enemy.showStatus());
                int wins=0;
                while(player.isAlive() && enemy.isAlive()){
                    System.out.println(floor.getBlood(player.name, player.HP, player.maxHP));
                    System.out.println(floor.getBlood(enemy.name, enemy.HP, enemy.maxHP));
                    playerTurn(floor,player, enemy,wins);
                    if(!enemy.isAlive()){
                        System.out.println("你击杀了"+enemy.name);
                        handleLootDrop(player,enemy);
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

                    // 随机获得10-100经验值
                    Random random = new Random();
                    int expGained = random.nextInt(91) + 10; // 10-100
                    player.Exp += expGained;
                    System.out.println("\n你获得了 " + expGained + " 点经验值！");
                    System.out.println("当前经验值：" + player.Exp);


                    Item rewardItem = getRandomRewardItem(random);
                    if (rewardItem != null) {
                        if (rewardItem.type == Item.ItemType.GOLD) {
                            // 金币特殊处理：获得10-100个
                            int goldCount = random.nextInt(91) + 10;
                            player.addItem(rewardItem, goldCount);
                            System.out.println("你获得了 " + goldCount + " 个金币！");
                        } else if (rewardItem.type == Item.ItemType.POTION) {
                            // 生成随机数量
                            int potionCount = random.nextInt(1) + 1;
                            player.addItem(rewardItem, potionCount);
                            System.out.println("你获得了 " + potionCount + " 个" + rewardItem.name + "！");
                        } else {
                            player.addItem(rewardItem, 1);
                            System.out.println("你获得了：" + rewardItem.name + "！");
                        }
                    }
                    // 设置房间为已探索
                    isFinished = true;

                } else {
                    System.out.println("\n你选择不打开宝箱，离开了房间。");
                }
                System.out.println("=========================\n");
            }else if (TypeNUm==3) {
                
            }else if (TypeNUm==4) {
                System.out.println("你进入了BOSS房间！");
                Random random = new Random();
                Enemy boss = bosses.get(random.nextInt(bosses.size()));
                System.out.println("你遇到了BOSS：" + boss.name);
                System.out.println(boss.showStatus());

                int wins = 0;
                while(player.isAlive() && boss.isAlive()){
                    System.out.println(floor.getBlood(player.name, player.HP, player.maxHP));
                    System.out.println(floor.getBlood(boss.name, boss.HP, boss.maxHP));
                    playerTurn(floor, player, boss, wins);

                    if(!boss.isAlive()){
                        System.out.println("你击败了BOSS：" + boss.name);
                        handleLootDrop(player, boss);

                        int expGain = random.nextInt(50) + 50;
                        player.addExp(expGain);
                        System.out.println("你获得了" + expGain + "点经验！");

                        isFinished = true;
                        floor.setClear(true);
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
    public static void playerTurn(Floor floor,Hero player, Enemy enemy,int wins){
        System.out.println("===你的回合===");
        Scanner sc = new Scanner(System.in);
        boolean potionUsed = false;
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
                potionUsed = true;
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
        }
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
                sb.append("⬛\uFE0F");
            }else {
                sb.append("⬜\uFE0F");
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
    public static void handleLootDrop(Hero player, Enemy enemy) {
        Random random = new Random();
        int expGain = random.nextInt(50) + 1;
        player.addExp(expGain);
        System.out.println("你获得了" + expGain + "点经验！");
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
}
