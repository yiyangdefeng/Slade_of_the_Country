package com.example.slade_of_the_country;

import java.util.HashMap;
import java.util.Map;

public class Texts {
	//for game interface
	//
	//
	//
	public static final String TEXT_BLOOD = "血量：";
	public static final String TEXT_ATTACK = "攻击：";
	public static final String TEXT_DEFENCE = "防御：";
	public static final String TEXT_RED_KEY = "红钥匙：";
	public static final String TEXT_BLUE_KEY = "蓝钥匙：";
	public static final String TEXT_YELLOW_KEY = "黄钥匙：";
	public static final String TEXT_GOLD = "金钱：";
	public static final String TEXT_TITLE = "国之利刃";
	public static final String TEXT_DI = "第";
	public static final String TEXT_FLOOR = "层";
	
	//for instruction interface
	//
	//
	//
	public static final String TEXT_RETURN = "返回";
	public static final String TEXT_INSTRUCTION = "        他们是神秘的，虽然存在着，却又好像消失着，他们一个个身怀绝技，外表冷酷，眼神犀利，任何东西都有可能成为他们的武器，死亡对他们似乎就是一场儿戏。他们同时也是国之利刃——特种兵。\n" +
	"        2020年，A国与B国发生战争，A国进行了海陆空天四维一体联合作战，以其独特有效的作战模式一直保持着0伤亡的战绩，B国由于体系作战不够完整，而且总体装备落后，战争初期遭受严重创伤。\n" +
	"        B国军事长官朱阿瑟上将迫不得已启用自己培养多年的眼镜蛇特种部队展开了多次斩首行动，可就在最近一次登岛作战中，眼镜蛇特战队中埋伏几乎全员被俘，关在了有人间地狱之称的魔塔监狱。\n" +
	"        就在A国首脑正在商量如何拿这些人去做谈判的砝码之时，有一个人却在监狱里悄悄的活动着。\n" +
	"        这个人代号“幽灵帝皇”，他是唯一没有被俘的眼镜蛇队员，并且正在一步步计划着从魔塔监狱里救出自己的队友们！\n" +
	"        【游戏说明】：使用左下角的方向键控制人物行动，通过触摸触发其他操作,遇到对话时触摸对话框进入下一段。通过你的努力打败监狱的最高长官，就可以让你和你的队友重获自由！\n" +
	"        游戏中的各种枪支可以帮助你提升自己的攻击力，各种防弹衣则可以提升防御力，医药箱可以帮助你回复体力。\n" +
	"        解锁商店功能后你也可以在那里不断使自己变得强大。\n" +
	"        此外，所有穿黑白相间囚衣的人不是敌人，而是能够给你提供各种信息帮助的队员，希望你谨记他们的话。\n" +
	"        同时请注意钥匙的合理使用，如果没有留下足够的钥匙开门，再强大的你也没有办法取得胜利。\n" +
	"        谨慎的选择你的行动路线，否则你不能打过强大的敌人。" +
	"        祝你好运！";
	
	//for load interface
	//
	//
	//
	public static final String TEXT_FILENOTFOUND = "无存档";
	public static final String TEXT_SAVE0 = "存档1";
	public static final String TEXT_SAVE1 = "存档2";
	public static final String TEXT_SAVEAUTO = "自动存档";
	public static final String TEXT_WARNING = "此位置已有存档，是否要覆盖？";
	public static final String TEXT_CONFIRM = "确定";
	public static final String TEXT_CANCEL = "取消";
	public static final String TEXT_FLOORNUMBER = "层数：";
	
	//warning messages
	//
	//
	//
	public static final String TEXT_NOYELLOWKEY = "你没有足够的黄钥匙哦！TAT";
	public static final String TEXT_NOBLUEKEY = "你没有足够的蓝钥匙哦！:-(";
	public static final String TEXT_NOREDKEY = "你没有足够的红钥匙哦！~555~";
	public static final String TEXT_NOSPECIALKEY = "这是一道特殊的门，目前无法打开，注意收集信息，它总会开的，加油！";
	public static final String TEXT_NOMONEY = "很抱歉，你已经没有足够的钱了，多打点敌人再说吧！";
	public static final String TEXT_NOEXP = "很抱歉，你已经没有足够的经验，或许你该考虑下与敌人战斗了。";
	public static final String TEXT_INFINITELOOP = "有意思的事情发生了，你和这个敌人的实力不分伯仲，你们谁都打不过对方，试试让自己变强大后再来吧。";
	
	public static final String TEXT_CANNOTBEAT = "你太弱了，打不过强大的敌人。\n" + "试试其他的路线吧~";
	
	//for elevator interface
	//
	//
	//
	public static final String TEXT_STOORCHOICE = "请选择想去的楼层：";
	
	//for shop interface
	//
	//
	//
	public static final String TEXT_NOTICE = "你可以消耗30金币加强自己的实力！";
	public static final String TEXT_ATTACKUP = "增加5点攻击";
	public static final String TEXT_DEFENCEUP = "增加5点防御";
	public static final String TEXT_BLOODUP = "增加1000点生命";
	
	public static final String TEXT_WARRIOR = "幽灵帝皇";
	public static final String TEXT_WARRIORTITLE = "眼镜蛇";
	
	//for fireeye interface
	//
	//
	//
	public static final String TEXT_OPPONAME = "敌人姓名";
	public static final String TEXT_OPPOTITLE = "敌人称号";
	public static final String TEXT_OPPOATTACK = "攻击";
	public static final String TEXT_OPPODEFENSE = "防御";
	public static final String TEXT_OPPOHEALTH = "血量";
	public static final String TEXT_SELFHEALTHLOSS = "血量损失";
	
	
	
	public static String[] StringSpliter(String message, float left, float right,
			float fontsize) {
		int columncount = (int) ((right - left) / fontsize);
		int column = message.length() / columncount + 1;
		String[] strings = new String[column];
		for (int i = 0; i < column; i++) {
			strings[i] = message.substring(columncount * i, Math.min(columncount
					* (i + 1),message.length()));
		}
		return strings;
	}
	
	public static Map<String,String> OPPONAME;
	public static Map<String,String> OPPOTITLE;
	
	public Texts() {
		Map<String,String> OPPONAME = new HashMap<String,String>();
		Map<String,String> OPPOTITLE = new HashMap<String,String>();
		OPPONAME.put("oppo1", "大头兵");
		OPPOTITLE.put("oppo1","土狼");
		OPPONAME.put("oppo2", "军士长");
		OPPOTITLE.put("oppo2","恶狼");
		OPPONAME.put("oppo3", "低级军官");
		OPPOTITLE.put("oppo3","老狼");
		OPPONAME.put("oppo4", "中级军官");
		OPPOTITLE.put("oppo4","野狼");
		OPPONAME.put("oppo5", "高级军官");
		OPPOTITLE.put("oppo5","猛狼");
		OPPONAME.put("oppo6", "普通特工");
		OPPOTITLE.put("oppo6","黑狼");
		OPPONAME.put("oppo7", "秘密特工");
		OPPOTITLE.put("oppo7","白狼");
		OPPONAME.put("oppo8", "士兵首领");
		OPPOTITLE.put("oppo8","孤狼");
		OPPONAME.put("oppo9", "特工首领");
		OPPOTITLE.put("oppo9","毒狼");
		OPPONAME.put("oppo10", "最高长官");
		OPPOTITLE.put("oppo10","狼牙");
		OPPONAME.put("dog", "警犬");
		OPPOTITLE.put("dog","动物");
		OPPONAME.put("mastiff", "藏獒");
		OPPOTITLE.put("mastiff","野兽");
		OPPONAME.put("wolf", "恶狼");
		OPPOTITLE.put("wolf","猛兽");
		OPPONAME.put("purple_kyrin", "紫麒麟");
		OPPOTITLE.put("purple_kyrin","神兽");
		OPPONAME.put("warrior","幽灵帝皇");
		OPPOTITLE.put("warrior","眼镜蛇");
	}
}  
