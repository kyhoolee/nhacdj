package com.superarrow.vietedm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;


public class Utils {

	public static int _512K = 512 * 1024;
	public static int _1M = 1024 * 1024;
	public static int _2M = 2 * 1024 * 1024;
	public static int _3M = 3 * 1024 * 1024;
	public static int _4M = 4 * 1024 * 1024;
	public static int _5M = 5 * 1024 * 1024;
	public static int _6M = 6 * 1024 * 1024;

	public static final String baseImageUri = "http://42.112.31.182:9090/x-keam/images/";

	public static final long _1DAY = 24 * 60 * 60 * 1000L;

	public static Properties loadPropertiesResource(String file) {
		Properties props = new Properties();
		try {
			props.load(Utils.class.getClassLoader().getResourceAsStream(file));
		} catch (Exception e) {
			LoggerUtil.getRootLogger().info(ExceptionUtils.getStackTrace(e));
		}
		return props;
	}
	
	public static long timeStamp() {
		return (new Date()).getTime();
	}

	public static String getMd5(byte[] bytes) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] mdbytes = md.digest(bytes);
		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	public static SimpleDateFormat getDateFomat(ETimeUnit timeUnit) {
		switch (timeUnit) {
		case d:

			return new SimpleDateFormat("yyyy:MM:dd");

		case m:

			return new SimpleDateFormat("yyyy:MM:dd:HH:mm");
		case M:

			return new SimpleDateFormat("yyyy:MM");

		case H:

			return new SimpleDateFormat("yyyy:MM:dd:HH");

		default:
			break;
		}
		return new SimpleDateFormat("yyyy:MM:dd");
	}

	public static long getTimeValue(ETimeUnit timeUnit) {
		switch (timeUnit) {
		case d:

			return _1DAY;

		default:
			break;
		}
		return 0;
	}

	public static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException localIOException) {
			if (br != null)
				try {
					br.close();
				} catch (IOException localIOException1) {
				}
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException localIOException2) {
				}
		}
		return sb.toString();
	}

	public static byte[] getFileFromServer(String videoUrl) throws NoSuchAlgorithmException, IOException {
		HttpURLConnection conn = null;
		try { // open a URL connection to the Servlet
			URL url = new URL(videoUrl);
			conn = (HttpURLConnection) url.openConnection();
			return IOUtils.toByteArray(conn.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

	public static String getLookUpFile(String lookupUrl) throws NoSuchAlgorithmException, IOException {
		HttpURLConnection conn = null;
		try { // open a URL connection to the Servlet
			URL url = new URL(lookupUrl);
			conn = (HttpURLConnection) url.openConnection();
			return IOUtils.toString(conn.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public static String normalizeMarkUpperCase(String text) {
		return Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
				.matcher(Normalizer.normalize(text.toLowerCase(), Normalizer.Form.NFD)).replaceAll("").replace('đ', 'd')
				.trim();
	}

	public static byte getTrunks(int videoLength) {
		if (videoLength <= _512K)
			return 1;
		if (videoLength <= _1M)
			return 2;
		return (byte) (2 + ((videoLength - 1) >> 20));
	}

	public static int getStart(int trunk) {
		if (trunk <= 0)
			return 0;
		if (trunk == 1)
			return _512K;
		if (trunk == 2)
			return _1M;
		return (trunk - 1) << 20;
	}

	public static int getLength(int trunk) {
		if (trunk < 0)
			return Integer.MAX_VALUE;
		if (trunk == 0)
			return _512K;
		if (trunk == 1)
			return _512K;
		return _1M;
	}
	
	public static String toString(Object o) throws Exception {
		return ResourceUtil.getObjectMapper().writeValueAsString(o);
	}

	public static void main(String args[]) throws Exception {

		// MessageDigest md = MessageDigest.getInstance("MD5");
		// String a = "aaaaaaaaaaaaaaaa";
		// String b = "hbsdfhbdsfdsfsdfdsf";
		// String _md5 = getMd5((a + b).getBytes());
		//
		// byte[] aaa = md.digest((a + b).getBytes());
		// byte[] bbb = md.digest(b.getBytes());
		//
		// MessageDigest md5 = MessageDigest.getInstance("MD5");
		// md5.update(a.getBytes());
		// md5.update(b.getBytes());
		//
		// byte[] mdbytes = md5.digest();
		//
		// StringBuffer sb = new StringBuffer();
		// for (int i = 0; i < mdbytes.length; i++) {
		// sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100,
		// 16).substring(1));
		// }
		// System.out.println(_md5);
		// System.out.println(sb.toString());

		int videoLength = (int) (3 * 1024 * 1024);
		System.out.println(2 + (videoLength - 1) / (1024 * 1024));
		System.out.println(getTrunks(videoLength));

		System.out.println(getStart(0));
		System.out.println(getStart(1));
		System.out.println(getStart(2));
		System.out.println(getStart(3));
		System.out.println(getStart(4));
		System.out.println(getStart(5));
		System.out.println(getStart(6));
	}
}
