package com.cqut.thing;

public class SkillBook extends BagItem
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
}
