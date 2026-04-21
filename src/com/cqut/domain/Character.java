package com.cqut.domain;

public class Character {
   public String name;
   public int HP;
   public int maxHP;
   public int attack;
   public int defense;

    public Character(){}

    public Character(String name,int HP,int attack,int defense){
        this.name=name;
        this.HP=HP;
        this.maxHP=HP;
        this.attack=attack;
        this.defense=defense;
    }

    public boolean isAlive(){
        return HP>0;
    }

    //回血在父类方便添加新角色
    public void heal(int amount){
        HP+=amount;
        if(HP>maxHP){
            HP=maxHP;
        }
    }

    public void takeDamage(int amount){
        HP-=amount;
        if(HP<0){
            HP=0;
        }
    }

    public void attack(Character target){
        int damage=attack-target.defense;
        if(damage<=0){
            damage=1;
        }
        target.takeDamage(damage);
    }

    public String showStatus(){
        return name+"【血量："+HP+" 攻击："+attack+" 防御："+defense+"】";
    }
}
