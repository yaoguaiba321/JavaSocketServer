package Server;
import java.net.*;
import java.sql.*;
import java.util.*;
import com.mchange.v2.c3p0.*;
import java.io.*;
import java.util.concurrent.*;

/**
* 这是服务器中的一个固定类，无需修改，只需要知道逻辑，然后修改对应的server.ini文件的位置和内容即可
* @author 会编程的cpu
* @version 1.0
*/
public class AppServer
{
	//服务器接收端
	ServerSocket serverSocket;
	
	//数据库部分
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	String sql;

	//文件的路径
	private String inifile= "/home/project2/Insist1/src/main/java/server.ini";	//存储一些必要的信息，比如端口，ip，数据库连接之类的
	

	//获取配置数据
	Properties props = new Properties();
	//配置用的数据
	private String url;
	private String user;
	private String password;
	private int maxpoolsize;
	private int minpoolsize;
	private int initialpoolsize;
	private int maxstatement;
	private int threadpoolsize;

	//线程池
	ExecutorService pool;

	//连接池
	ComboPooledDataSource ds;

	//构造器，用于初始化一些东西
	public AppServer()
	{
		try
		{
			System.out.println("数据库正在初始化");
			//获取配置数据
			props.load(new FileInputStream(inifile));
			url = props.getProperty("url","");
			user = props.getProperty("user","");
			password = props.getProperty("password","");
			maxpoolsize = Integer.valueOf(props.getProperty("maxpoolsize",""));
			minpoolsize = Integer.valueOf(props.getProperty("minpoolsize",""));
			initialpoolsize = Integer.valueOf(props.getProperty("initialpoolsize",""));
			maxstatement = Integer.valueOf(props.getProperty("maxstatement",""));
			threadpoolsize = Integer.valueOf(props.getProperty("threadpoolsize",""));			
			
			//初始化数据库
			//使用出c3p0
			//创建连接池实例
			ds = new ComboPooledDataSource();
			//设置连接池连接数据库所需的驱动
			ds.setDriverClass("com.mysql.cj.jdbc.Driver");
			//设置连接数据库的URL
			ds.setJdbcUrl(url);
			//设置连接数据库的URL
			ds.setUser(user);
			//设置连接数据库的密码
			ds.setPassword(password);
			//设置连接池的最大连接数
			ds.setMaxPoolSize(maxpoolsize);
			//设置连接池的最小连接数
			ds.setMinPoolSize(minpoolsize);
			//设置连接池的初始连接数
			ds.setInitialPoolSize(initialpoolsize);
			//设置连接池的缓存Statement的最大数
			ds.setMaxStatements(maxstatement);
			
			
			
			//创建一个具有固定线程数的线程池
			pool = Executors.newFixedThreadPool(threadpoolsize);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	* 用于启动服务器，开始监听是否有Socket接入客户端
	*/
	public void execute() //运行服务器
	{
		
		try
		{
			
			
			//初始化服务器监听
			serverSocket = new ServerSocket(ForInet.port);
			System.out.println("服务器开始运行"+serverSocket.getInetAddress()+":"+serverSocket.getLocalPort());
			//开始循环监听
			while(true)
			{
				try
				{
					conn = ds.getConnection();
					//查看记录在Properties中的日期
					String recordDate = props.getProperty("recordDate");
					String today = DateTimeHelper.getToday();
					System.out.println(recordDate+":"+today);
					if(!recordDate.equals(today)) //如果记录的日期不是今天
					{
						//就把日期更换成今天的日期
						props.setProperty("recordDate",today);
						//保存一下
						props.store(new FileOutputStream(inifile),"comment line");
						//然后做一些其他事情
						//根据间隔的天数，给用户添加记录
						long days = DateTimeHelper.getDaysCount(recordDate,today);
						System.out.println("间隔天数："+days);
						for(int i = 0; i < days; i++)
						{
							//做一些每天都会做的事情
							routineTask();
						}
					}

					//接收客户端的请求
					Socket s = serverSocket.accept();
					System.out.println("服务器接收到一个请求");
					pool.submit(new SocketThread(s,conn));//然后从线程池中取出一个线程执行SocketThread
				}
				catch(SQLException sqle)
				{
					sqle.printStackTrace();
				}
				finally
				{
					//使用Tools回收除了conn以外的资源，看懂逻辑即可理解回收除了conn以外的资源
					try
					{
						//关闭数据库的资源
						Tools.release(null,null,pstmt,rs); //conn关闭 ，其他的在每个模块中解决
					
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
				
			}
				
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		

	}
	/**
	* 固定每天做的一些事情，这个要弄一个线程才行，不然的话，太多了会变得很卡
	* 或者把某一些任务，放到用户的请求上，只有用户请求了才会，添加，不过这是不行的
	* 可以弄成定时任务每天4点钟集中做完
	*/
	public void routineTask() throws SQLException
	{
		
		//写一些，你每天要固定做的事情
		

	}
	

}
