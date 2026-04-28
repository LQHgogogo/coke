package com.cqut.domain;

public class Enemy extends  Character{
    public String skill;
    public boolean defending;

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
        if(defending){
            super.takeDamage(damage/2);
            defending=false;
        }else{
            super.takeDamage(damage);
        }
    }
}
