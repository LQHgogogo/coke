package com.cqut.domain;

public class Enemy extends  Character{
    private String skill;
    private boolean defending;
    private int Lv;

    public Enemy()
    {
    	super();
    }

    public Enemy(String name,int HP,int attack,int defense,String skill)
    {
    	super(name,HP,attack,defense);
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

    public int getLv()
    {
        return Lv;
    }

    public void setLv(int lv)
    {
        Lv=lv;
    }

    public String getSkill()
    {
        return skill;
    }

    public void setSkill(String skill)
    {
        this.skill = skill;
    }

    public boolean isDefending()
    {
        return defending;
    }

    public void setDefending(boolean defending)
    {
        this.defending = defending;
    }
}
