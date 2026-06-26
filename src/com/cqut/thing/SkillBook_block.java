package com.cqut.thing;

import com.cqut.domain.Hero;

public class SkillBook_block extends SkillBook
{
    public SkillBook_block()
    {
        super(300, "基础格挡要领", "学习以获得技能“格挡”", 1, "格挡");
        setActiveSkill(false);
    }

    @Override
    public void setSkill(Hero player)
    {
        player.setDefense(player.getDefense()+1);
    }
}
