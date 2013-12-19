package com.example.slade_of_the_country;

import java.util.HashMap;
import java.util.Map;

public class CharacterInfos {
	public Map<String,String> OPPONAME;
	public Map<String,String> OPPOTITLE;
	
	
	public CharacterInfos() {
		OPPONAME = new HashMap<String,String>();
		OPPOTITLE = new HashMap<String,String>();
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
