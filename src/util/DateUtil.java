package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public String getToday(){
		Date d = new Date();
		
		String s = d.toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(d);
	}
}
