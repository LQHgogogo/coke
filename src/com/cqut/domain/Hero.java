package com.cqut.domain;

import com.cqut.thing.*;

import java.util.ArrayList;

public class Hero extends  Character
{
    private ArrayList<String> skillList;
    private ArrayList<BagItem> bag = new ArrayList<BagItem>();
    private int Lv;
    private int Exp;
    private BagItem weapon = null;
    private BagItem armor = null;

    public Hero()
    {
        super();
        skillList = new ArrayList<String>();
        bag = new ArrayList<BagItem>();
    }

    public Hero(String name,int HP,int attack,int defense)
    {
        super(name,HP,attack,defense);
        skillList = new ArrayList<String>();
        Lv = 1;
        Exp = 0;
    }



    public void addSkill(String skill)
    {
        skillList.add(skill);
    }

    public String showSkill()
    {
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

    public ArrayList<String> getSkillList()
    {
        return skillList;
    }

    public void setSkillList(ArrayList<String> skillList)
    {
        this.skillList = skillList;
    }

    public int getLv()
    {
        return Lv;
    }

    public void setLv(int Lv)
    {
        this.Lv = Lv;
    }

    public int getExp()
    {
        return Exp;
    }

    public void setExp(int Exp)
    {
        this.Exp = Exp;
    }

    public void addBagItem(BagItem bagItem, int num)                  //背包增加物品
    {
        if (bag == null)
        {
            bag.add(bagItem);
            bagItem.AddNum(num);
        }
        else if (!isBagHava(bagItem))
        {
            bag.add(bagItem);
            bagItem.AddNum(num);
        }
        else
        {
            bag.get(getBagSame(bagItem)).AddNum(num);
        }
    }

    public void removeBagItem(BagItem thing,  int num)                //背包删减物品
    {
        boolean isNull = !thing.RemoveNum(num);
        if (isNull)
        {
            bag.remove(thing);
        }
    }

    public void showBag()                               //显示背包
    {
        if (bag == null || bag.size() == 0)
        {
            System.out.println("背包内空空如也");;
        }
        else
        {
            System.out.println("背包内物品：");
            for (int i = 0; i < bag.size(); i++)
            {
                System.out.println((i+1) + "." + bag.get(i));
            }
        }
    }

    public boolean isBagHava(BagItem bagItem)              //判断背包内是否有id相同的物品
    {
        boolean have = false;
        for (int i = 0; i < bag.size(); i++)
        {
            BagItem value = bag.get(i);
            if (value.getId() ==  bagItem.getId())
            {
                have = true;
            }
        }
        return have;
    }

    public int getBagSame(BagItem bagItem)                 //用于获取背包内相同id物品的下标
    {
        int same = -1;
        for (int i = 0; i < bag.size(); i++)
        {
            BagItem value = bag.get(i);
            if (value.getId() == bagItem.getId())
            {
                same = i;
            }
        }
        return same;
    }

    public int getBagLength()                                 //方法返回背包长度
    {
        return bag.size();
    }

    public BagItem getBagItem(int index)                //通过下标返回背包内物品
    {
        return bag.get(index);
    }

    public void setWeapon(BagItem bagItem)       //添加武器
    {
        this.weapon = bagItem;
    }

    public void  setArmor(BagItem bagItem)           //添加装备
    {
        this.armor = bagItem;
    }

    public BagItem getWeapon()                     //返回武器状态
    {
        return weapon;
    }

    public BagItem getArmor()                         //返回装备状态
    {
        return armor;
    }
}
