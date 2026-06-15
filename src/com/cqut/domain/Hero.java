package com.cqut.domain;

import java.util.ArrayList;

public class Hero extends  Character{
    public ArrayList<String> skillList;
    public ArrayList<BagItem> bag;
    public int Lv;
    public int Exp;
    public BagItem equippedWeapon;

    public Hero() {
        super();
        skillList = new ArrayList<String>();
        bag = new ArrayList<BagItem>();
        Lv = 1;
        Exp = 0;
        equippedWeapon = null;
    }

    public Hero(String name,int HP,int attack,int defense){
        super(name,HP,attack,defense);
        skillList = new ArrayList<String>();
        bag = new ArrayList<BagItem>();
        Lv = 1;
        Exp = 0;
        equippedWeapon = null;
    }

    public void addSkill(String skill){
        skillList.add(skill);
    }

    public String showSkill(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < skillList.size(); i++) {
            sb.append(skillList.get(i));
            if(i!=skillList.size()-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public void showStatusWithEquipment(){
        System.out.println("角色状态：" + name);
        System.out.println("【血量：" + HP + "/" + maxHP + " 攻击：" + attack + " 防御：" + defense + "】");
        System.out.println("等级：" + Lv + " 经验：" + Exp);
        if (equippedWeapon != null) {
            System.out.println("装备武器：" + equippedWeapon.item.name + " (" + equippedWeapon.item.description + ")");
        } else {
            System.out.println("装备武器：无");
        }
        System.out.println("技能：" + showSkill());
    }
}
