package com.cqut.domain;

import java.util.Scanner;

/**
 * 剧情管理器 - 处理神域螺旋迷宫的核心故事线
 * 6个剧情节点按顺序推进，分布在不同楼层
 */
public class StoryManager {

    // 剧情进度常量
    public static final int NOT_STARTED = 0;
    public static final int SPIRAL_GATE = 1;      // 螺旋之门
    public static final int LIA_CAMPFIRE = 2;     // 莉娅的火堆
    public static final int FORGOTTEN_MINE = 3;   // 被遗忘的矿洞
    public static final int DIVINE_REMAINS = 4;   // 神骸回廊
    public static final int VILANDES_THRONE = 5;  // 维兰德斯王座
    public static final int DIVINE_HEART = 6;     // 神域胎心
    public static final int STORY_COMPLETE = 7;   // 剧情完结

    /**
     * 触发剧情房间。根据Hero的storyProgress和当前楼层，推进剧情。
     * 剧情节点按顺序分布在不同楼层：
     *   楼层1-2  → 螺旋之门（开场）
     *   楼层3-4  → 莉娅的火堆（初遇NPC）
     *   楼层5-6  → 被遗忘的矿洞（发现真相）
     *   楼层7-8  → 神骸回廊（情感与抉择）
     *   楼层9-10 → 维兰德斯王座（高潮对峙）
     *   楼层11   → 神域胎心（最终结局）
     *
     * @return true表示触发了剧情，false表示当前楼层不是剧情楼层
     */
    public static boolean triggerStory(Hero hero, Floor floor) {
        int progress = hero.storyProgress;
        int floorNum = floor.getFloorNum();

        // 剧情已完结
        if (progress >= STORY_COMPLETE) {
            return false;
        }

        // 根据当前进度和楼层判断触发哪个剧情节点
        int targetNode = getTargetNode(progress, floorNum);

        if (targetNode == -1) {
            // 当前楼层没有适合的剧情节点
            System.out.println("\n这个房间空荡荡的，似乎时机未到……");
            return false;
        }

        Scanner sc = new Scanner(System.in);

        switch (targetNode) {
            case SPIRAL_GATE:
                storySpiralGate(hero, sc);
                break;
            case LIA_CAMPFIRE:
                storyLiaCampfire(hero, sc);
                break;
            case FORGOTTEN_MINE:
                storyForgottenMine(hero, sc);
                break;
            case DIVINE_REMAINS:
                storyDivineRemains(hero, sc);
                break;
            case VILANDES_THRONE:
                storyVilandesThrone(hero, sc);
                break;
            case DIVINE_HEART:
                storyDivineHeart(hero, sc);
                break;
        }

        return true;
    }

    //根据当前剧情进度和楼层，判断应该触发哪个节点

    private static int getTargetNode(int progress, int floorNum) {
        switch (progress) {
            case NOT_STARTED:
                return (floorNum >= 1) ? SPIRAL_GATE : -1;
            case SPIRAL_GATE:
                return (floorNum >= 3) ? LIA_CAMPFIRE : -1;
            case LIA_CAMPFIRE:
                return (floorNum >= 5) ? FORGOTTEN_MINE : -1;
            case FORGOTTEN_MINE:
                return (floorNum >= 7) ? DIVINE_REMAINS : -1;
            case DIVINE_REMAINS:
                return (floorNum >= 9) ? VILANDES_THRONE : -1;
            case VILANDES_THRONE:
                return (floorNum >= 11) ? DIVINE_HEART : -1;
            default:
                return -1;
        }
    }

    // ==================== 剧情节点1：螺旋之门（开场） ====================

    private static void storySpiralGate(Hero hero, Scanner sc) {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("         【剧情 · 螺旋之门】");
        System.out.println("═══════════════════════════════════════\n");

        printSlowly("你睁开双眼，发现自己躺在一片冰冷的石质地面上。");
        System.out.println("头顶没有天空,眼前只有无尽的螺旋结构——巨大的石阶、断裂的拱门、");
        System.out.println("悬浮的残垣断壁，以一种不可能的几何方式扭曲盘旋，延伸向不可见的远方。");
        System.out.println(" ... ");
        printSlowly("这就是「神域」——诸神未竟的杰作。");
        printSlowly("");
        printSlowly("你记不起自己是如何来到这里的。只记得一个声音……");
        printSlowly("那个声音在你脑海中回响，如同远古的钟鸣：");
        printSlowly("");
        printSlowly("\"被选中者……前来……补完这残缺的乐章……\"");
        printSlowly("");
        printSlowly("你站起身，发现身后的石门上刻满了古老的符文。");
        printSlowly("符文微微发光，仿佛在回应你的到来。");
        printSlowly("");
        printSlowly("忽然，一道裂隙在你面前展开——虚空的黑暗从中渗出，");
        printSlowly("又被某种金色的力量强行压制回去。");
        printSlowly("");
        printSlowly("一个低沉而疲惫的声音从裂隙深处传来：");
        printSlowly("\"往前走……螺旋的尽头……我在那里等你……\"");
        printSlowly("");
        printSlowly("声音消散了。面前只剩一条通往迷宫深处的石阶。");
        printSlowly("你深吸一口气，迈出了第一步。");

        System.out.println("\n[系统] 剧情「螺旋之门」完成。神域的帷幕已经拉开。");
        hero.storyProgress = SPIRAL_GATE;
    }

    // ==================== 剧情节点2：莉娅的火堆（初遇重要NPC） ====================

    private static void storyLiaCampfire(Hero hero, Scanner sc) {
        printSlowly("\n═══════════════════════════════════════");
        printSlowly("         【剧情 · 莉娅的火堆】");
        printSlowly("═══════════════════════════════════════\n");

        printSlowly("穿过数层迷宫，你疲惫不堪。转过一个拐角，你看到了一点微光。");
        printSlowly("");
        printSlowly("那是一簇篝火。橙色的火光在冰冷的石壁上跳跃。");
        printSlowly("火堆旁坐着一个少女——她有着银白色的长发和淡紫色的眼眸，");
        printSlowly("正用一根树枝拨弄着火苗。");
        printSlowly("");
        printSlowly("她抬起头，对你微微一笑。");
        printSlowly("\"你终于来了。我叫莉娅。\"");
        printSlowly("");
        printSlowly("你警惕地看着她。在这座迷宫中遇到活人，比遇到怪物更令人不安。");
        printSlowly("");
        printSlowly("莉娅似乎看穿了你的心思。");
        printSlowly("\"不用担心。我和你一样，也是被召唤到这里的。\"");
        printSlowly("她指了指自己颈间挂着的一枚残破的符文吊坠。");
        printSlowly("\"只不过……我比你早来了很久。很久很久。\"");
        printSlowly("");
        printSlowly("她在火堆上架起一个小壶，倒出两杯温热的液体递给你。");
        printSlowly("\"这是神域的记忆碎片泡的茶。喝了它，你会看到一些东西。\"");
        printSlowly("");
        printSlowly("你接过杯子，温热的液体滑入喉咙。");
        printSlowly("一瞬间，无数画面涌入脑海——");
        printSlowly("");
        printSlowly("你看到了：诸神围坐于虚空之中，用星光编织世界的经纬。");
        printSlowly("你看到了：一座完美的螺旋城市在光芒中徐徐升起。");
        printSlowly("你看到了：黑暗从裂隙中涌出，吞噬了一切。");
        printSlowly("你看到了：一个高大的身影张开双臂，用自己的身体封住了裂隙。");
        printSlowly("");
        printSlowly("你猛地睁开眼，杯子已经空了。");
        printSlowly("");
        printSlowly("莉娅静静地看着你。\"那个人叫维兰德斯。他是诸神中最年轻的一位。\"");
        printSlowly("\"他牺牲了自己，换来了神域一千年的残存。\"");
        printSlowly("\"而现在，封印正在减弱。\"");
        printSlowly("");
        printSlowly("她站起身，指向迷宫深处。");
        printSlowly("\"往前走。你需要亲眼看到真相。我会在需要的时候出现。\"");
        printSlowly("");
        printSlowly("她的身影在火光中渐渐淡去，仿佛从未存在过。");
        printSlowly("只有那枚符文吊坠留在地上，散发着微弱的暖意。");

        System.out.println("\n[系统] 剧情「莉娅的火堆」完成。获得了「莉娅的符文吊坠」（特殊物品）。");
        System.out.println("[系统] 生命值完全恢复！");
        hero.heal(hero.maxHP);
        hero.storyProgress = LIA_CAMPFIRE;
    }

    // ==================== 剧情节点3：被遗忘的矿洞（发现真相） ====================

    private static void storyForgottenMine(Hero hero, Scanner sc) {
        printSlowly("\n═══════════════════════════════════════");
        printSlowly("        【剧情 · 被遗忘的矿洞】");
        printSlowly("═══════════════════════════════════════\n");

        printSlowly("你来到一个巨大的洞穴。这里的石壁上镶嵌着无数发光的水晶，");
        printSlowly("照亮了洞穴的全貌。");
        printSlowly("");
        printSlowly("这不是普通的矿洞。每一块水晶中都封存着一段记忆——");
        printSlowly("你触摸其中一块，画面便涌入脑海：");
        printSlowly("");
        printSlowly("——诸神争论着神域的构造。有人说要建造成完美的圆形，");
        printSlowly("有人说要建成无限延伸的直线。最终，维兰德斯提出了「螺旋」——");
        printSlowly("\"圆形是封闭的，直线是傲慢的。只有螺旋，才能无限接近完美，");
        printSlowly("又永远保留成长的空间。\"");
        printSlowly("");
        printSlowly("你触摸另一块水晶：");
        printSlowly("");
        printSlowly("——虚空裂隙撕裂神域的那一刻。诸神惊慌失措，");
        printSlowly("维兰德斯却异常平静。他转向其他诸神，说了一句你无法听清的话。");
        printSlowly("然后，他走进了裂隙。");
        printSlowly("");
        printSlowly("你触摸第三块水晶：");
        printSlowly("");
        printSlowly("——这不是记忆。这是……预言。");
        printSlowly("画面中出现了你自己。你站在一个巨大的心脏前方，");
        printSlowly("双手伸出，手中握着一枚完整的符文——正是莉娅那枚吊坠的完整形态。");
        printSlowly("");
        printSlowly("一个声音在水晶中回响：");
        printSlowly("\"最后一块拼图……补完之人……竟或未竟……\"");
        printSlowly("");
        printSlowly("你明白了。你不是被随机召唤的。你是「缺失的那一块」。");
        printSlowly("诸神在创造神域时，留了一个空位——一个需要「凡人之心」来填补的空位。");
        printSlowly("");
        printSlowly("而维兰德斯的封印，也一直在等待那个补完之人的到来。");
        printSlowly("");
        printSlowly("你握紧了拳头。前路已经清晰，但选择的重担也压在心头。");

        System.out.println("\n[系统] 剧情「被遗忘的矿洞」完成。你知晓了真相。");
        hero.storyProgress = FORGOTTEN_MINE;
    }

    // ==================== 剧情节点4：神骸回廊（情感与抉择） ====================

    private static void storyDivineRemains(Hero hero, Scanner sc) {
        printSlowly("\n═══════════════════════════════════════");
        printSlowly("         【剧情 · 神骸回廊】");
        printSlowly("═══════════════════════════════════════\n");

        printSlowly("这是一条狭长的回廊。两侧的壁龛中，摆放着诸神的遗物——");
        printSlowly("断裂的长矛、蒙尘的竖琴、凝固在最后一刻的沙漏……");
        printSlowly("");
        printSlowly("这不是坟墓。这是「停滞」。诸神没有死，他们只是被虚空冻结了。");
        printSlowly("每一个壁龛里，都蜷缩着一个微弱的光芒——那是神明的最后意识。");
        printSlowly("");
        printSlowly("你走过一个个壁龛，听到他们在低语：");
        printSlowly("");
        printSlowly("「我们失败了……」");
        printSlowly("「维兰德斯……原谅我们……」");
        printSlowly("「如果再来一次……」");
        printSlowly("");
        printSlowly("在回廊的尽头，你看到了一个特殊的壁龛——它空着。");
        printSlowly("旁边的铭文写道：\"此处留给那个尚未到来的人。\"");
        printSlowly("");
        printSlowly("莉娅的身影再次浮现。她站在空壁龛旁，神情复杂。");
        printSlowly("");
        printSlowly("\"这里本该是你的位置。\"她轻声说。");
        printSlowly("\"诸神在设计神域时，预见到仅靠神力无法完成完美的造物。\"");
        printSlowly("\"所以他们留了一个位置——给一个凡人。一个带着自由意志的人。\"");
        printSlowly("");
        printSlowly("她转向你，眼中闪烁着千年的疲惫。");
        printSlowly("\"我就是上一个被选中的人。但我失败了。\"");
        printSlowly("\"我无法做出那个选择——是补完神域，还是让它永远残缺。\"");
        printSlowly("\"所以我留在这里，等待下一个。等待你。\"");
        printSlowly("");
        printSlowly("她将残破的符文吊坠放在你手心。");
        printSlowly("\"维兰德斯的封印快要支撑不住了。你必须去见他。\"");
        printSlowly("\"当你站在他面前时，你会明白什么是「竟」，什么是「未竟」。\"");
        printSlowly("");
        printSlowly("她轻轻推了你一把。回廊的尽头，一扇巨门缓缓打开。");
        printSlowly("门后是无尽的光芒和——一个等待了千年的身影。");

        System.out.println("\n[系统] 剧情「神骸回廊」完成。获得了完整的「补完符文」。");
        System.out.println("[系统] 生命值完全恢复！");
        hero.heal(hero.maxHP);
        hero.storyProgress = DIVINE_REMAINS;
    }

    // ==================== 剧情节点5：维兰德斯王座（高潮对峙） ====================

    private static void storyVilandesThrone(Hero hero, Scanner sc) {
        printSlowly("\n═══════════════════════════════════════");
        printSlowly("        【剧情 · 维兰德斯王座】");
        printSlowly("═══════════════════════════════════════\n");

        printSlowly("光芒散去，你发现自己站在一个巨大的圆形大厅中。");
        printSlowly("");
        printSlowly("大厅中央，一道漆黑的裂隙悬在半空，如同空间本身被撕开了一道伤口。");
        printSlowly("裂隙的边缘燃烧着金色的火焰——那是维兰德斯的余力。");
        printSlowly("");
        printSlowly("而在裂隙前方，一个高大的身影盘膝而坐。");
        printSlowly("他的身体一半是光芒，一半已经被虚空侵蚀成漆黑的结晶。");
        printSlowly("他的双手按在裂隙的两侧，用自己的神力日复一日地对抗着虚空的扩张。");
        printSlowly("");
        printSlowly("他抬起头。那是一张疲惫但平静的脸。");
        printSlowly("");
        printSlowly("\"你来了。\"维兰德斯的声音很轻，却在大厅中回荡。");
        printSlowly("\"我等了一千年。或者说，一千年的这个瞬间。\"");
        printSlowly("");
        printSlowly("你走近他。每一步都能感受到虚空的力量拉扯着你的灵魂。");
        printSlowly("");
        printSlowly("\"你不必害怕。\"维兰德斯说，\"虚空渴望吞噬一切，但它也渴望完整。\"");
        printSlowly("\"这就是为什么它会响应你的到来——因为你是缺失的那一块。\"");
        printSlowly("");
        printSlowly("他艰难地抬起一只手，指向裂隙深处。");
        printSlowly("\"神域的核心就在裂隙的另一端——神域胎心。\"");
        printSlowly("\"当年我们还没来得及将它完成，虚空就撕裂了一切。\"");
        printSlowly("\"现在，你有两个选择。\"");
        printSlowly("");
        printSlowly("他的声音变得无比郑重：");
        printSlowly("");
        printSlowly("\"选择「竟」——将你的灵魂注入胎心，补完神域。\"");
        printSlowly("神域将重新完整，虚空将被永远驱逐。但代价是……你将与神域融为一体，");
        printSlowly("成为它的一部分。你不再是你。\"");
        printSlowly("");
        printSlowly("\"选择「未竟」——摧毁胎心，让神域与虚空一同湮灭。\"");
        printSlowly("这样你就能带着所有记忆回到你的世界。但诸神的梦想将化为乌有，");
        printSlowly("这片土地上的所有存在——包括莉娅——都将消散。\"");
        printSlowly("");
        printSlowly("维兰德斯直视着你的眼睛。");
        printSlowly("\"我不会替你做选择。因为这是凡人的自由——\"");
        printSlowly("\"是诸神自己赋予你的权利。\"");
        printSlowly("");
        printSlowly("他让开了一条路。裂隙中，一条光之路延伸向最深处。");
        printSlowly("\"去吧。神域胎心在等你。做出你的选择。\"");
        printSlowly("");
        printSlowly("你握紧了手中的符文，走进了裂隙。");

        System.out.println("\n[系统] 剧情「维兰德斯王座」完成。最终的选择就在前方。");
        System.out.println("[系统] 生命值完全恢复！");
        hero.heal(hero.maxHP);
        hero.storyProgress = VILANDES_THRONE;
    }

    // ==================== 剧情节点6：神域胎心（最终结局选择） ====================

    private static void storyDivineHeart(Hero hero, Scanner sc) {
        printSlowly("\n═══════════════════════════════════════");
        printSlowly("         【剧情 · 神域胎心】");
        printSlowly("═══════════════════════════════════════\n");

        printSlowly("你穿过裂隙，来到一个不可思议的空间。");
        printSlowly("");
        printSlowly("这里没有上下左右，只有无尽的星光在缓慢旋转。");
        printSlowly("在旋转的中心，悬浮着一颗巨大的、半透明的水晶心脏。");
        printSlowly("");
        printSlowly("它残缺不全。表面布满了裂纹，每一次脉动都从中渗出微弱的虚空黑雾。");
        printSlowly("但它仍然在跳动——缓慢、坚定、不肯停止。");
        printSlowly("");
        printSlowly("这就是「神域胎心」。诸神梦想的最后一个未完成的作品。");
        printSlowly("维兰德斯用自己封印了它一千年，但封印的力量已经耗尽了。");
        printSlowly("");
        printSlowly("你手中的符文开始剧烈发光，与胎心产生了共鸣。");
        printSlowly("");
        printSlowly("胎心向你发出了无声的询问——或者说，向你展示了两条路：");
        printSlowly("");
        printSlowly("═══════════════════════════════════════");
        printSlowly("          【竟 · 补完】");
        printSlowly("  将你的灵魂注入胎心，成为神域缺失的最后一块拼图。");
        printSlowly("  神域将完整，虚空将被驱逐，诸神将苏醒。");
        printSlowly("  你将化为神域的一部分——永恒、不朽，却失去自我。");
        printSlowly("═══════════════════════════════════════");
        printSlowly("");
        printSlowly("═══════════════════════════════════════");
        printSlowly("         【未竟 · 终结】");
        printSlowly("  摧毁胎心。让神域与虚空一同湮灭。");
        printSlowly("  你将带着记忆回到原来的世界，像一个从未发生过的梦。");
        printSlowly("  但莉娅、维兰德斯、诸神……所有的一切都将不复存在。");
        printSlowly("═══════════════════════════════════════\n");

        System.out.println("请做出你的最终选择：");
        System.out.println("  1. 「竟」—— 补完神域，成为永恒");
        System.out.println("  2. 「未竟」—— 摧毁一切，回归凡尘");

        int choice = getChoice(sc, 1, 2);

        printSlowly("");
        printSlowly("你做出了决定。");
        printSlowly("");

        if (choice == 1) {
            endingComplete(hero);
        } else {
            endingUnfinished(hero);
        }

        hero.storyProgress = STORY_COMPLETE;
    }

    // ==================== 结局分支 ====================

    private static void endingComplete(Hero hero) {
        printSlowly("═══════════════════════════════════════");
        printSlowly("         【结局 · 竟】");
        printSlowly("═══════════════════════════════════════\n");

        printSlowly("你举起手中的符文，走向神域胎心。");
        printSlowly("");
        printSlowly("胎心的脉动与你的心跳渐渐重合。你感受到一种前所未有的温暖——");
        printSlowly("那是诸神千年前的梦想，是维兰德斯千年的坚守，是莉娅千年的等待。");
        printSlowly("");
        printSlowly("\"原来如此。\"你轻声说。");
        printSlowly("");
        printSlowly("你张开双臂，拥抱了那颗残缺的心脏。");
        printSlowly("");
        printSlowly("光芒爆发了。");
        printSlowly("");
        printSlowly("你的身体化为金色的光点，融入胎心的每一道裂缝。");
        printSlowly("每一道裂缝愈合的同时，虚空的黑暗被驱散一分。");
        printSlowly("");
        printSlowly("神域开始旋转——不再是残缺的螺旋，而是完整的、优美的螺旋。");
        printSlowly("金色的光芒照亮了每一层迷宫，每一个房间，每一个角落。");
        printSlowly("");
        printSlowly("神骸回廊中，壁龛里的光芒重新燃起。诸神苏醒了。");
        printSlowly("");
        printSlowly("莉娅站在回廊尽头，泪水滑落。她手中那枚残破的吊坠，");
        printSlowly("终于变得完整——因为缺失的那一块，已经被填上了。");
        printSlowly("");
        printSlowly("维兰德斯的身影从裂隙中解脱。他望向天空，露出了千年来第一个笑容。");
        printSlowly("\"谢谢你，凡人。谢谢你替我们完成了我们无法完成的事。\"");
        printSlowly("");
        printSlowly("而你呢？");
        printSlowly("");
        printSlowly("你成为了神域本身。每一缕风是你，每一道光是你，");
        printSlowly("每一个新踏入神域的旅人，都会感受到一个温暖的指引。");
        printSlowly("");
        printSlowly("那是你在告诉他们：这条路，值得走下去。");
        printSlowly("");
        printSlowly("═══════════════════════════════════════");
        printSlowly("     「竟」—— 神域完整，你成为永恒。");
        printSlowly("═══════════════════════════════════════\n");

        System.out.println("\n[系统] 主线剧情全部完成！你选择了「竟」——补完神域。");
        System.out.println("[系统] 你可以继续在神域中探索，这里已经是你的家了。");
    }

    private static void endingUnfinished(Hero hero) {
        printSlowly("═══════════════════════════════════════");
        printSlowly("        【结局 · 未竟】");
        printSlowly("═══════════════════════════════════════\n");

        printSlowly("你看着那颗残缺的胎心，缓缓摇了摇头。");
        printSlowly("");
        printSlowly("\"不。\"你说。\"这不是我的命运。\"");
        printSlowly("");
        printSlowly("你将手中的符文用力掷向胎心——不是注入，而是摧毁。");
        printSlowly("");
        printSlowly("符文撞击胎心的瞬间，发出一声清脆的碎裂声。");
        printSlowly("裂纹从撞击点向四周蔓延，越来越快，越来越密。");
        printSlowly("");
        printSlowly("虚空的黑雾从裂隙中疯狂涌出，但这一次，它没有吞噬什么——");
        printSlowly("因为一切都在湮灭。虚空与神域，光明与黑暗，在相互湮灭中归于虚无。");
        printSlowly("");
        printSlowly("在最后一刻，你看到了——");
        printSlowly("");
        printSlowly("莉娅站在回廊中，对你微微一笑。她没有责怪，只有释然。");
        printSlowly("\"终于可以休息了。谢谢你。\"");
        printSlowly("");
        printSlowly("维兰德斯放下了双手。千年以来第一次，他的脸上出现了轻松。");
        printSlowly("\"也好。美梦终究会醒。重要的是，我们曾经梦想过。\"");
        printSlowly("");
        printSlowly("一切都在消散。金色的神域、漆黑的虚空、千年的执念——");
        printSlowly("全部化为一片洁白。");
        printSlowly("");
        printSlowly("……");
        printSlowly("");
        printSlowly("你睁开眼。你躺在自己房间的床上。窗外是熟悉的鸟鸣和阳光。");
        printSlowly("");
        printSlowly("你坐起身，发现自己手中握着一枚小小的碎片——");
        printSlowly("那枚符文的碎片，在阳光下闪着微弱的光。");
        printSlowly("");
        printSlowly("你知道那不是梦。你知道神域曾经存在。");
        printSlowly("你知道莉娅、维兰德斯和诸神都曾真实地活过、梦想过、战斗过。");
        printSlowly("");
        printSlowly("你小心地收起碎片，走向窗边。");
        printSlowly("新的一天开始了。你决定——");
        printSlowly("在这个不完美的世界里，用自己的方式，继续那个未竟的梦想。");
        printSlowly("");
        printSlowly("═══════════════════════════════════════");
        printSlowly("  「未竟」—— 万物归无，你带着记忆回到人间。");
        printSlowly("═══════════════════════════════════════\n");

        System.out.println("\n[系统] 主线剧情全部完成！你选择了「未竟」——让一切终结。");
        System.out.println("[系统] 你可以继续在神域中探索，虽然核心已经不存在了。");
    }

    // ==================== 辅助方法 ====================

    /**
     * 逐行打印，模拟剧情节奏
     */
    private static void printSlowly(String text) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println(); // 整段打完换行
        // 段落结尾额外停顿，长文本更长缓冲
        int endDelay = Math.min(text.length() * 8, 600);
        try {
            Thread.sleep(endDelay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 获取用户有效输入
     */
    private static int getChoice(Scanner sc, int min, int max) {
        while (true) {
            if (sc.hasNextInt()) {
                int input = sc.nextInt();
                if (input >= min && input <= max) {
                    return input;
                }
            } else {
                sc.next();
            }
            System.out.println("请输入 " + min + "-" + max + " 之间的数字：");
        }
    }
}