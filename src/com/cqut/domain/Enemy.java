package com.cqut.domain;

public class Enemy extends  Character{
    public String skill;
    public String skill2;
    public boolean defending;

    public Enemy()
    {
    	super();
    }

    public Enemy(String name,int HP,int attack,int defense,String skill)
    {
    	super(name,HP,attack,defense);
    	this.skill=skill;
        this.skill2=null;
    }
    public Enemy(String name,int HP,int attack,int defense,String skill,String skill2)
    {
        super(name,HP,attack,defense);
        this.skill=skill;
        this.skill2=skill2;
    }

    public Enemy(Enemy other) {
        super(other.name, other.maxHP, other.attack, other.defense);
        this.HP = other.maxHP;
        this.skill = other.skill;
        this.skill2 = other.skill2;
        this.defending = false;
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