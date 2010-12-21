package com.ssq.util;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetURLContent {
	
	public static void main(String[] args) {
		//String url = "http://www.baidu.com";
		//String bodyStr = GetURLContent.getBodyStr(url);
		//System.out.println(bodyStr);
	}
	
	public static String getBodyStr(String urlname) {
		if ("".equals(getBody(urlname))) {
			return "";
		}
		//java中替换\r\n必须用\\r\\n,双反斜杠才表示一个反斜杠
		return getBody(urlname).replaceAll("\\r\\n|\\n|\\r", "").replaceAll("\\s{2,}", " ");
	}

	public static String getBody(String urlname) {
		if ("".equals(getContent(urlname))) {
			return "";
		}
		String str = "";
		String body = "";
		try {
			str = getContent(urlname).split("<body")[1];
			str = str.split("body>")[0];
			body = "<body" + str + "body>";
		} catch (Exception e) {
			return "";
		}
		return body;
	}

	public static String getContent(String urlname) {
		String message = "";
		try {
			URL url = new URL(urlname);
			HttpURLConnection connect = (HttpURLConnection) url
					.openConnection();
			connect.setRequestProperty("User-agent", "Mozilla/4.0");
			connect.connect();
			InputStream is = connect.getInputStream();
			StringBuffer content = new StringBuffer();

			while ((is.read()) != -1) {
				int all = is.available();
				byte[] b = new byte[all];
				is.read(b);
				content.append(new String(b, "utf-8"));
			}

			is.close();
			url = null;
			message = content.toString();

		} catch (IOException ex) {
			return "";
		} catch (Exception e) {
			System.out.println("出现错误" + e.getStackTrace());
		}
		return message;
	}
}
