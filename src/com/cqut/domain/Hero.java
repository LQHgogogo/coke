package com.cqut.domain;

import java.util.ArrayList;

public class Hero extends  Character{
    public ArrayList<String> skillList;
    public int Lv;
    public int Exp;

    public Hero() {
        super();
        skillList = new ArrayList<String>();
    }

    public Hero(String name,int HP,int attack,int defense){
        super(name,HP,attack,defense);
        skillList = new ArrayList<String>();
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
}
