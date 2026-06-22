package com.cqut.domain;

import java.util.Random;

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
        rooms = new Room[10]; // 初始化房间数
        Random random = new Random();
        
        // 房间类型定义1=战斗, 2=奖励, 3=boss, 4=剧情, 5=空房间
        int[] roomTypes = new int[10];
        
        // boss房间
        int bossIndex = random.nextInt(10);
        roomTypes[bossIndex] = 3;
        
        // 剧情房间
        int storyIndex;
        do {
            storyIndex = random.nextInt(10);
        } while (storyIndex == bossIndex);
        roomTypes[storyIndex] = 4;
        
        // 战斗房间，随机生�?-3个战斗房�?
        int battleCount = random.nextInt(3) + 1; // 1-3个战斗房�?
        int placedBattle = 0;
        while (placedBattle < battleCount) {
            int index = random.nextInt(10);
            if (roomTypes[index] == 0) { // 还未分配类型
                roomTypes[index] = 1;
                placedBattle++;
            }
        }
        
        // 4. 奖励房间(类型2)至多3个，随机生成0-3�?
        int rewardCount = random.nextInt(4);
        int placedReward = 0;
        while (placedReward < rewardCount) {
            int index = random.nextInt(10);
            if (roomTypes[index] == 0) {
                roomTypes[index] = 2;
                placedReward++;
            }
        }
        
        // 5. 剩余房间填为空房�?
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

        if (floorNum%2==0){
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
        
        //触发房间探索交互功能�?为战斗房间，2为奖励房间，3为剧情房间，4为楼层boss房，5为空房间,6为商店房�?
        public void Trigger(Floor floor)
        {
            if (TypeNUm==1){
                
            } else if (TypeNUm==2) {
                
            }else if (TypeNUm==3) {
                
            }else if (TypeNUm==4) {
                isFinished = true;
                floor.setClear(true);
            }else if (TypeNUm==5){
                isFinished = true;
            }else if (TypeNUm==6){

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
}
