package Server;

import java.net.*;
import java.sql.*;
import java.io.*;
/**
* 判断并执行业务代码的线程
* @author 会编程的cpu
* @version 1.0
*/
public class SocketThread implements Runnable
{
	private Socket socket;
	private BufferedReader br;
	private PrintStream ps;
	private Connection conn;
	
	//构造器
	public SocketThread(Socket socket, Connection conn) throws IOException
	{
		this.socket = socket;
		this.conn = conn;
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.ps = new PrintStream(socket.getOutputStream());
	}
	@Override
	public void run()
	{
		try
		{
			String msg = br.readLine();
			System.out.println("客户端发来的信息："+msg);
			String realMsg = null;
      
      //以下是两个示例
      
			//登录模块
			if(msg.startsWith(ForInet.loginProtocol))
			{
				LoginModule lm = new LoginModule(msg,ps,conn);
				lm.execute();
			}
			//请求用户信息模块
			if(msg.startsWith(ForInet.getUserInfoProtocol))
			{
				GiveUserInfoModule guim = new GiveUserInfoModule(msg,ps,conn);
				guim.execute();
			}
      
      //示例到此结束，
			
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			try
			{
				//关闭数据库的资源
				Tools.release(conn,null,null,null); //conn关闭 ，其他的在每个模块中解决
				//关闭网络资源
				Tools.releaseSocket(ps,br,socket);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
