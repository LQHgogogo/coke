package com.cqut.map;

public class Floor
{
    private int level;

    public Floor(int level)
    {
        StoryRoom storyRoom = new StoryRoom();

        WarehouseRoom[] warehouseRoom = new WarehouseRoom[1 + level / 5];
        for (int i = 0; i < warehouseRoom.length; i++)
        {
            warehouseRoom[i].setEnemyNumber(level / 3);
            warehouseRoom[i].setName("储物间" + (i + 1));
        }

        FightRoom[] fightRoom = new FightRoom[3 + level / 4];
        for (int i = 0; i < fightRoom.length; i++)
        {
            fightRoom[i].setEnemyNumber(2 + level / 3);
            fightRoom[i].setName("战场" + (i + 1));
        }
    }
}
