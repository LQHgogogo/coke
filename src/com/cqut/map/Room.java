package com.cqut.map;

public class Room
{
    protected String name;

    public void intoRoom()
    {
        System.out.println("你已进入房间" + this.name);
    }

    public void leaveRoom()
    {
        System.out.println("你已离开房间" + this.name);
    }
}
