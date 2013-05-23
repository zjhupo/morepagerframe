package com.junction.android.util;

import java.util.HashMap;
import java.util.HashSet;

import com.junction.android.morepagerframe.R;

public class ValueBean {
	public static int[] noneArray = {R.drawable.ic_ihangzhou_none,R.drawable.ic_liuliangtongji_none,
			R.drawable.ic_zixingche_none,R.drawable.ic_gongjiao_none,R.drawable.ic_qiche_none,
			R.drawable.ic_tingche_none,R.drawable.ic_lukuang_none,R.drawable.ic_daijia_none,
			R.drawable.ic_dianbo_none,R.drawable.ic_xiuxishi_none,R.drawable.ic_yinyue_none}; 
	public static int[] hasArray = {R.drawable.ic_ihangzhou_has,R.drawable.ic_liuliangtongji_has,
			R.drawable.ic_zixingche_has,R.drawable.ic_gongjiao_has,R.drawable.ic_qiche_has,
			R.drawable.ic_tingche_has,R.drawable.ic_lukuang_has,R.drawable.ic_daijia_has,
			R.drawable.ic_dianbo_has,R.drawable.ic_xiuxishi_has,R.drawable.ic_yinyue_has}; 
      
	private static HashSet<Integer> hasSet;//已安装的程序集合
	private static HashMap<Integer,Integer> hasMap;//首页展示程序map
	
	public static HashSet<Integer> getSet(){
		if(hasSet==null){
			hasSet = new HashSet<Integer>();
			hasSet.add(1);
			hasSet.add(2);
			hasSet.add(3);
			hasSet.add(4);
		}
		
		return hasSet ;
	}
	
	public static HashMap<Integer,Integer> getMap(){
		if(hasMap==null){
			hasMap = new HashMap<Integer,Integer>();
			hasMap.put(0, 0);
			hasMap.put(1, 2);
			hasMap.put(2, 3);
			hasMap.put(3, 4);
			hasMap.put(4, 5);
			
		}
		
		return hasMap ;

	}
	
	public static void addApp(int num){
		hasMap.put(hasMap.size(), num);
	}
}
