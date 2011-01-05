package com.ssq.util;

import java.util.*;

public class RandomUtil {
	
	private static int outCount = 0;
	
	public static void main(String[] args) {
		//最大数
		int max = 135;
		//最小数
		int min = 75;
		//一次获取的次数
		int number = 5;
		System.out.println("\n===================================");
		//要出的号码
		String red = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33";
		//String red = "01 03 04 06 08 09 11 12 13 16 18 19 22 23 26 29 32 33";
		System.out.println("red==" + red);
		//要出的篮球号码
		String blue = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16";
		//String blue = "04 07 13";
		
		System.out.println("blue==" + blue);
		//上期号码 采集网址：http://www.2caipiao.com/ssq/index.jhtml
		String pre = "06 08 12 17 28 33 05";
		System.out.println("pre==" + pre);
		
		//遗漏值 5-10 的号码 一般必须有一个
		//采集网址：http://zst.2caipiao.com/ssqZst!ssqjbzs.jhtml?flag=jbzs
		String yilou = "7,15,16,18,22,23,27,29";
		System.out.println("yilou==" + yilou);
		System.out.println("===================================\n");
		
		//red,blue,pre,yilou,min,max,number
		Map map = new HashMap();
		map.put("red", red);
		map.put("blue", blue);
		map.put("pre", pre);
		map.put("yilou", yilou);
		map.put("min", min);
		map.put("max", max);
		map.put("number", number);
		//获取多期预测号码
		getMutilNumber(map);

	}
	
	/**
	 * 一次获取多期号码
	 * @param red 要出的红球号码
	 * @param blue 要出的篮球号码
	 * @param pre 上期出的号码
	 * @param max 最大数
	 * @param min 最小数
	 * @param number 个数
	 */
	public static void getMutilNumber(Map map){
//		String red,String blue,String pre,int max,int min,int number
//		Map map = new HashMap();
		String red = (String)map.get("red");
		//替换掉前面的04,6
		red = red.replaceAll(" ", ",").replaceAll("0([^,])", "$1");
		Map<String,String> redmap = StringToMap(red);
		
		String blue = (String)map.get("blue");
		blue = blue.replaceAll(" ", ",").replaceAll("0([^,])", "$1");
		//转成map去掉重复的
		Map<String,String> bluemap = StringToMap(blue);
		
		String pre = (String)map.get("pre");
		pre = pre.replaceAll(" ", ",").replaceAll("0([^,])", "$1");
		List<Integer> prelist = StringToList(pre);
//		Map<String,String> premap = StringToMap(pre);
		
		String yilou = (String)map.get("pre");
		yilou = yilou.replaceAll(" ", ",").replaceAll("0([^,])", "$1");
		List<Integer> yiloulist = StringToList(yilou);
		
		int min = (Integer)map.get("min");
		int max = (Integer)map.get("max");
		int number = (Integer)map.get("number");
		int blueNumber = bluemap.size();
		
		for(int i=1; i<=blueNumber; i++){
			String sn = getNumber(redmap,bluemap,prelist,yiloulist,min,max);
			String blue2 = sn.split("蓝球:")[1].split(" 和值")[0];
			bluemap.remove(blue2);
			outCount ++;
			int oldNumber = map.get("oldNumber")==null? blueNumber:(Integer)map.get("oldNumber");
			//如果蓝球个数小于要求总数，并且输出次数大于要求总数则停止输出
			if(outCount > oldNumber){
				break;
			}
			System.out.println(outCount + "红球" + "：" + sn);
		}
		
		//如果要求总数大于蓝球个数，则要重新输出剩下的次数
		if(blueNumber < number){
			map.put("oldNumber", number);
			number = number-blueNumber;
			map.put("number", number);
			getMutilNumber(map);
		}
		
	}
	
	
	/**
	 * 获取号码
	 * @param red 要出的红球号码
	 * @param blue 要出的篮球号码
	 * @param pre 上期出的号码
	 * @param max 最大数
	 * @param min 最小数
	 * @return
	 */
	public static String getNumber(Map<String,String> redmap,Map<String,String> bluemap,List<Integer> prelist,List<Integer> yiloulist,int max,int min){
		Map<String,String> redmap2 = getRedRandomNumber(redmap,yiloulist,min,max);
		String b1 = getBuleRandomNumber(bluemap);
		
		int total = 0;
		int li = 0;
		int licount = 0;//连号统计
		int qicount = 0;//奇偶统计
		int xwcount = 0;//小尾数统计 
		List<Integer> list = new ArrayList<Integer>();
		//过滤连号，1～2组连号，3连号属于2组连号
		for (Map.Entry<String,String> m : redmap2.entrySet()) {			  
		    //m.getKey() + ":" + m.getValue()
			total += Integer.parseInt(m.getValue());
			int rli = Integer.parseInt(m.getValue());
			if(li==0){
				li = rli;
			}else{
				//相减后绝对值为1表示连号
				if(Math.abs(li-rli)== 1){
					licount ++;
				}
				li = rli;
			}
			if(rli%2 == 1){
				qicount ++;
			}
			//0-5 为小尾数
			if(rli%10 < 5){
				xwcount ++;
			}
			
			list.add(rli);
		}

		if(licount > 2 || licount == 0){
			return getNumber(redmap,bluemap,prelist,yiloulist,min,max);
		}
		
		//2~4个奇数
		if(Integer.parseInt(b1)%2 == 1){
			qicount ++;
		}
		if(qicount < 2 || qicount > 4){
			return getNumber(redmap,bluemap,prelist,yiloulist,min,max);
		}
		
		//每期小尾数 2-4个
		if(Integer.parseInt(b1)%10 < 5){
			xwcount ++;
		}
		if(xwcount < 2 || xwcount > 4){
			return getNumber(redmap,bluemap,prelist,yiloulist,min,max);
		}
		
		//添加蓝球号码
		//redmap2.put(b1, b1);//红球和蓝球不能使用map，如果有重号会导致红球只有5个
		list.add(Integer.parseInt(b1));
		
		
		//过滤包括上期出过的2个以上的情况
		int count = 0;
		Map<Integer,Integer> weihaomap = new HashMap<Integer,Integer>();
		for(int i=0; i<7; i++){
			//包含上期
			if(prelist.get(i) == list.get(i)){
				count ++;
			}
			//尾号
			int weihao = list.get(i);
			if(weihao >= 10){
				weihao = weihao-10;
			}else if(weihao >= 20){
				weihao = weihao-20;
			}else if(weihao >= 30){
				weihao = weihao-30;
			}
			weihaomap.put(weihao, weihao);
		}

		//如果没有包含上期一个号码 或包含两个以上则重新算
		if(count == 0 || count > 2){
			return getNumber(redmap,bluemap,prelist,yiloulist,min,max);
		}
		
		//每期尾号必须有一个重复
		int escount = 7-weihaomap.size();
		if(escount < 1 || escount > 3){
			return getNumber(redmap,bluemap,prelist,yiloulist,min,max);
		}
		
		return CommonUtil.sortMap(redmap2) + " 蓝球:" + b1 + " 和值:" + total;
	}

	/**
	 * 获取在指定数字内红球随机数
	 * @param str
	 * @return
	 */
	public static Map<String,String> getRedRandomNumber(Map<String,String> map,List<Integer> yiloulist,int max,int min) {
		//红球为1~33 选6
		int number = 6;
		int extent = 34;//random是小于1的小数，所以基数要大于最大数1
		Map<String,String> map2 = new HashMap<String,String>();
		int total = 0;
		int[] rs = new int[number];
		for(int i=0; i<number; i++){
			int temp = getExtentRandomNumber(extent);
			if(map.get(temp+"") == null){
				i--;
				continue;
			}
			boolean isHave = false;
			//判断是否存在该随机数
			for(int j=0; j < rs.length; j++){
				if(rs[j] == temp){
					isHave = true;
					break;
				}
			}
			if(isHave == true){
				i--;
				continue;
			}	
			rs[i] = temp;
			total += temp;
			map2.put(temp+"", temp+"");
		}
		//如果和值不在76-135范围内则重新算
		// || map2.size() != 6
		if(total<=min || total>=max){
			return getRedRandomNumber(map,yiloulist,min,max);
		}
		//每期遗漏值 5-10 的号码 一般必须有一个
		int yiloucount = 0;
		for(int i=0; i<yiloulist.size(); i++){
			if(map2.containsKey(yiloulist.get(i) +"")){
				yiloucount ++;
			}
		}
		if(yiloucount < 1 || yiloucount > 2){
			return getRedRandomNumber(map,yiloulist,min,max);
		}
		return map2;
	}
	
	/**
	 * 获取在指定数字内蓝球随机数
	 * @param str
	 * @return
	 */
	public static String getBuleRandomNumber(Map<String,String> map) {
		//篮球为1~16 选1
		int extent = 17;//random是小于1的小数，所以基数要大于最大数1
		int temp = getExtentRandomNumber(extent);
		//如果没有则重新取
		if(map.get(temp+"") == null){
			return getBuleRandomNumber(map);
		}
		return temp+"";
	}
	
	////////////////////////公共方法////////////////////////////////////
	
	/**
	 * 字符串转换成map
	 * @param str
	 * @return
	 */
	public static Map<String,String> StringToMap(String str){
		Map<String,String> map = new HashMap<String,String>();
		if(str == null || str.length() == 0){
			return map;
		}
		String[] ss = str.split(",");
		int len = ss.length;
		
		for (int i = 0; i < len; i++) {
			map.put(ss[i], ss[i]);
		}
		return map;
	}
	
	/**
	 * 字符串转换成list
	 * @param str
	 * @return
	 */
	public static List<Integer> StringToList(String str){
		List<Integer> list = new ArrayList<Integer>();
		if(str == null || str.length() == 0){
			return list;
		}
		String[] ss = str.split(",");
		int len = ss.length;
		
		for (int i = 0; i < len; i++) {
			list.add(Integer.parseInt(ss[i]));
		}
		return list;
	}
	
	/**
	 * 生成指定范围内的整数随机数
	 * @return
	 */
	public static int getExtentRandomNumber(int extent){
		int number = (int) (Math.random()*extent);
		return number;
	}
	
	/**
	 * 生成N个指定范围内的不重复的整数随机数
	 * @return
	 */
	public static int[] getExtentRandomNumbers(int extent,int number){
		int[] rs = new int[number];
		for(int i=0; i<number; i++){
			int temp = getExtentRandomNumber(extent);
			boolean isHave = false;
			//判断是否存在该随机数
			for(int j=0; j < rs.length; j++){
				if(rs[j] == temp){
					isHave = true;
					break;
				}
			}
			if(isHave == true){
				i--;
				continue;
			}	
			rs[i] = temp;
		}
		return rs;
	}
	

	/**
	 * 产生-100-----到-10000的负整随机数
	 * @return
	 */
	public static int getNegativeNumber(){
		Random random = new Random(System.currentTimeMillis());
		int number=random.nextInt(1000001);
		return -number;
	}
	

}
