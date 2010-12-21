package com.ssq.util;

import java.util.*;

public class CommonUtil {
	public static void main(String[] args) {
		String str = "01 03 07 18 23 27 12 ";
		String sn = getSingleNumber(str);
		//String sn = getRedOutNumber(str);
		System.out.println("红球：" + sn + "==" + sn.split(",").length);
		//str = "6,7,15,5,9,10,1,5,9,13";
		//System.out.println("蓝球：" + getBuleOutNumber(str));

	}
	
	/**
	 * 获取唯一值
	 * @param str
	 * @return
	 */
	public static String getSingleNumber(String str) {
		str = str.replaceAll(" ", ",").replaceAll("0([^,])", "$1");
		String[] ss = str.split(",");
		int len = ss.length;
		Map map = new HashMap();
		// Map map = new LinkedHashMap();
		for (int i = 0; i < len; i++) {
			map.put(ss[i], ss[i]);
		}
		List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
				map.entrySet());

		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1,
					Map.Entry<String, String> o2) {
				return (Integer.parseInt(o1.getValue()) - Integer.parseInt(o2.getValue()));//由小到大1-2，由大到小2-1
			}
		});
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < infoIds.size(); i++) {
			String id = infoIds.get(i).getValue();
			if (i != 0) {
				sb.append(",");
			}
			sb.append(id);
		}

		// sb.append(map.keySet());
		return sb.toString();
	}
	
	/**
	 * 排序map
	 * @param map
	 * @return
	 */
	public static String sortMap(Map map){
		List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
				map.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1,
					Map.Entry<String, String> o2) {
				return (Integer.parseInt(o1.getValue()) - Integer.parseInt(o2.getValue()));//由小到大1-2，由大到小2-1
			}
		});
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < infoIds.size(); i++) {
			String id = infoIds.get(i).getValue();
			if (i != 0) {
				sb.append(",");
			}
			sb.append(id);
		}
		return sb.toString();
	}
	
	/**
	 * 获取篮球号码
	 * @param str
	 * @return
	 */
	public static String getBuleOutNumber(String str){
		Map allmap = new HashMap();
		for (int i = 1; i < 17; i++) {
			allmap.put(i+"", i+"");
		}
		String[] ss = str.split(",");
		int len = ss.length;
		for (int i = 0; i < len; i++) {
			//System.out.print(allmap.get(Integer.parseInt(ss[i])) + "====" +ss[i]);
			if(allmap.get(ss[i]) != null){
				allmap.remove(ss[i]);
			}
		}
		//System.out.print(allmap.keySet());
		String result = sortMap(allmap);
		return result;
	}
	
	/**
	 * 获取红球号码
	 * @param str
	 * @return
	 */
	public static String getRedOutNumber(String str){
		//String number = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33";
		Map allmap = new HashMap();
		for (int i = 1; i < 34; i++) {
			allmap.put(i+"", i+"");
		}
		String[] ss = str.split(",");
		int len = ss.length;
		for (int i = 0; i < len; i++) {
			//System.out.print(allmap.get(Integer.parseInt(ss[i])) + "====" +ss[i]);
			if(allmap.get(ss[i]) != null){
				allmap.remove(ss[i]);
			}
		}
		//System.out.print(allmap.keySet());
		String result = sortMap(allmap);
		return result;
	}

}




