package Server;
import java.text.*;
import java.util.*;

/**
* 一个用于时间业务的小工具
* @author 会编程的cpu
* @version 1.0
*/
public class DateTimeHelper
{
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	* 获取今天的日期
	*/
	public static String getToday()
	{
		return sdf.format(new Date());
	}
	/**
	* 获取两个天数之间的间隔
	* @param start 开始的日期
	* @param end 结束的日期
	*/
	public static long getDaysCount(String startdate , String enddate)
	{
		Date start = null;
		Date end = null;
		try
		{
			start = sdf.parse(startdate);
			end = sdf.parse(enddate);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long time1 = start.getTime();
		long time2 = end.getTime();
		return ((time2-time1) / (1000*3600*24));
	}
}	
