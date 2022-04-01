package Server;

import java.sql.*;
import java.net.*;
import java.io.*;

public class Tools
{
  /**
  * 关闭JDBC数据库资源
  * @param conn 数据库连接
  * @param pstmt 执行sql语句用的
  * @param stmt 也是执行sql语句用的
  * @param rs 查询数据库得到的结果集
  */
	public static void release(Connection conn, PreparedStatement pstmt, Statement stmt,ResultSet rs) throws Exception
	{
		if(rs != null )
		{
			rs.close();
		}
		if(stmt != null)
		{
			stmt.close();
		}
		if(pstmt != null)
		{
			pstmt.close();
		}
		if(conn != null)
		{
			conn.close();
		}
	}
  /**
  * 关闭网络连接资源
  * @param ps 字符输出流
  * @param br 字符输入流
  * @param socket 套接字
  */
	public static void releaseSocket(PrintStream ps, BufferedReader br, Socket socket) throws Exception 
	{
		//关闭网络资源
		if(ps != null)
		{
			ps.close();
		}
		if(br != null)
		{
			br.close();
		}
		if(socket !=null)
		{
			socket.close();
		}
	}


}
