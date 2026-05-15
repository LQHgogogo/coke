package com.cqut.domain;

import java.util.ArrayList;

public class Hero extends  Character{
    private ArrayList<String> skillList;
    private ArrayList<BagItem> bag;
    private int Lv;
    private int Exp;

    public Hero() {
        super();
        skillList = new ArrayList<String>();
        bag = new ArrayList<BagItem>();
    }

    public Hero(String name,int HP,int attack,int defense){
        super(name,HP,attack,defense);
        skillList = new ArrayList<String>();
        Lv = 1;
        Exp = 0;
    }



    public void addSkill(String skill)
    {
        skillList.add(skill);
    }

    public String showSkill(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < skillList.size(); i++)
        {
            sb.append(skillList.get(i));
            if(i!=skillList.size()-1)
            {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public void expUp(int exp)     //经验获得方法
    {
        this.Exp += exp;
    }

    public void LvUp()       //等级提升方法，一般在expUp后配合使用LvUp,自动判断是否提升等级
    {

        if (Lv < 5 && Exp >= 100)
        {
            Lv++;
            expUp(-100);
            this.setMaxHP(this.getMaxHP() + 10);
            this.setHP(this.getHP() + 10);
            this.setAttack(this.getAttack() + 2);
            this.setDefense(this.getDefense() + 1);
        }
        else if (Lv < 10 && Exp >= 200)
        {
            Lv++;
            expUp(-200);
            this.setMaxHP(this.getMaxHP() + 15);
            this.setHP(this.getHP() + 15);
            this.setAttack(this.getAttack() + 3);
            this.setDefense(this.getDefense() + 2);
        }
        else if (Lv >= 10 && Exp >= 300)
        {
            Lv++;
            expUp(-300);
            this.setMaxHP(this.getMaxHP() + 20);
            this.setHP(this.getHP() + 20);
            this.setAttack(this.getAttack() + 5);
            this.setDefense(this.getDefense() +4);
        }
    }

    public ArrayList<String> getSkillList() {
        return skillList;
    }

    public void setSkillList(ArrayList<String> skillList) {
        this.skillList = skillList;
    }

    public ArrayList<BagItem> getBag() {
        return bag;
    }

    public void setBag(ArrayList<BagItem> bag) {
        this.bag = bag;
    }

    public int getLv() {
        return Lv;
    }

    public void setLv(int Lv) {
        this.Lv = Lv;
    }

    public int getExp() {
        return Exp;
    }

    public void setExp(int Exp) {
        this.Exp = Exp;
    }
}
