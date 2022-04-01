package Server;
import java.sql.*;
import java.io.*;
import com.alibaba.fastjson.*;

/**
* 服务器所以业务代码的父类
* 封装好了，JDBC的一些类，Socket的一些固定代码，还有fastjson的固定部分，
* 看懂逻辑即可，无需修改，
* @author 会编程的cpu
* @version 1.0
*/
public abstract class ModuleFather
{
	//使用的协议
	String Protocol = "xx";//服务器传回给客户端不必在乎协议是什么
	//要用的东西
	String msg;
	PrintStream ps;
	Connection conn;	
	//数据库部分
	PreparedStatement pstmt;
	ResultSet rs;
	String sql;
	//传输用的JSONObject容器
	JSONObject inobject,outobject = new JSONObject();
	public ModuleFather(String msg,PrintStream ps,Connection conn)
	{
		this.msg = msg;
		this.ps = ps;
		this.conn = conn;
	}
	public abstract void task() throws Exception;
	
	public void execute()
	{
		try
		{
			//从客户端传递过来的JSONObject
			inobject = JSONObject.parseObject(ForInet.getRealMsg(msg));
			task();
			//将数据传输给客户端
			ps.println(Protocol+outobject+Protocol);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				//关闭pstmt和rs
				Tools.release(null,pstmt,null,rs);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
}
