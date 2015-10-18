package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtillity {
	public String getToday(){
		Date d = new Date();
		
		String s = d.toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(d);
	}
	
	/**
	 * 특정년 월 일 이면 계산하기.
	 * @param startDate 시작 날짜 선정
	 * @return
	 */
	public String calDate( String startDate, int year, int month, int day ){
		System.out.println(year);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar(Locale.KOREA);
		Date date = null;
		try {
			date = sdf.parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, (year*-1));
		c.add(Calendar.MONTH,(month*-1));
		c.add(Calendar.DATE, (day*-1));
		return sdf.format(c.getTime());
	}
}
