package com.cqut.thing;

public class Drug extends BagItem                  //所有治疗药品继承此类
{

    public Drug()
    {
        super();
    }

    public Drug(int id, String name, String description, int maxCount, int drugEfficacy)
    {
        super(id, name, description, maxCount);
        this.drugEfficacy = drugEfficacy;
    }

    public void setDrugEfficacy(int num)
    {
        this.drugEfficacy = num;
    }

}