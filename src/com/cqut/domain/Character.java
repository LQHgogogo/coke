package com.cqut.domain;

public class Character {
   private String name;
   private int HP;
   private int maxHP;
   private int attack;
   private int defense;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
