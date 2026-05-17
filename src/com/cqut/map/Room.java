package com.cqut.map;

public class Room
{
    protected String name;
    protected boolean into =  false;              // 通过两个布尔变量判断房间是否进入和是否探索完成
    protected boolean finish =  false;

    public void intoRoom()
    {
        System.out.println("你已进入房间" + this.name);
        this.into = true;
    }

    public void leaveRoom()
    {
        System.out.println("你已离开房间" + this.name);
    }

    public void finishRoom()
    {
        this.finish = true;
    }

    public boolean isFinish()
    {
        return finish;
    }

    public boolean isInto()
    {
        return into;
    }
}
