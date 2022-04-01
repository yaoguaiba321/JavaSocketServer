package Server;

/**
* 程序入口，我是采用maven，所以每次启动服务器是在服务器采用如下命令
* mvn exec:java -Dexec.mainClass="Server.Main"
* Server代表maven创建项目后的包名，Main就是如下的类，用于启动整个服务器
* 需要有一定的maven使用基础，至少需要知道，maven如何创建项目，和maven如何编译和执行项目
* @author 会编程的cpu
* @version 1.0
*/
public class Main
{
	public static void main(String[] args)
	{
		AppServer  as = new AppServer();
		as.execute();
	}
}
