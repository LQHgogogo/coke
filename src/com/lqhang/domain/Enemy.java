package com.lqhang.domain;

public class Enemy extends  Character{
    public String skill;
    public boolean denfending;

    public Enemy()
    {
    	super();
    }

    public Enemy(String name,int HP,int atack,int defense,String skill)
    {
    	super(name,HP,atack,defense);
    	this.skill=skill;
    }

    @Override
    public void takeDamage(int damage) {
        if(denfending){
            super.takeDamage(damage/2);
            denfending=false;
        }else{
            super.takeDamage(damage);
        }
    }
}
