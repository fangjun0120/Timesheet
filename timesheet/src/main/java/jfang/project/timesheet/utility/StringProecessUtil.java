package jfang.project.timesheet.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StringProecessUtil {

	private static final String PWD_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random random = new Random();

	public static String randomString(int len) {
		StringBuilder builder = new StringBuilder(len);
		for (int i = 0; i < len; i++) 
			builder.append(PWD_CHARS.charAt(random.nextInt(PWD_CHARS.length())));
		return builder.toString();
	}
	
	public static Date StringToDate(String str) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		return formatter.parse(str);
	}
	
	public static String DateToString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		return df.format(date);
	}
}
