package Server;
import java.sql.*;
import java.io.*;
import com.alibaba.fastjson.*;


public class GiveUserInfoModule extends ModuleFather
{
	public GiveUserInfoModule(String msg, PrintStream ps,Connection conn)
	{
		super(msg,ps,conn);
	}
  /**
  * 重写这个方法，只需要关注三个有顺序的事情即可，
  * 1、取出客户端传递过来的数据（如果有的话），通过inobject.get("xxx");即可
  * 2、根据客户端传递过来的数据，做任何你想做的事情，
  * 3、通过outobject.put("xxx",xxx);把要传递给客户端的数据，放入fastjson中即可，继承了ModuleFather，后面或自动将outobject发送给客户端
  */
	@Override
	public void task() throws Exception
	{
		/*
			取出inobject中客户端传递过来的数据
      下面是一个示例，可以删除，换成自己的代码
		*/
		//取出数据
		String openid = inobject.getString("openid");
		
		/*
			做任何你想做的事情
       下面是以下示例，可以删除，换成自己的代码
		*/
		//通过openid取出用户的数据,
		sql = "select nickname from users where openid = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,openid);
		rs = pstmt.executeQuery();
		rs.next();
		//取出数据库中的数据
		String nickname = rs.getString(1);


		/*
			把结果装入outobject传回给客户端
      下面是以下示例，可以删除，换成自己的代码
		*/
		//存入JSONObject 
		outobject.put("nickname",nickname);
		
		
		
	}
}
