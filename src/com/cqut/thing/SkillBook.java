package com.cqut.thing;

import com.cqut.domain.Hero;

public abstract class SkillBook extends BagItem
{
    public SkillBook()
    {}

    public SkillBook(int id, String name, String description, int maxCount, String skill)
    {
        super(id, name, description,  maxCount);
        setSkill(skill);
    }

    public void setSkill(String skill)
    {
        this.skill = skill;
    }

    public abstract void setSkill(Hero player);
}
