package Server;
/**
* 这是服务器中的一个固定接口，负责集中存放协议的地方
* 每次增加Module之后，需要把协议在这里写一份，如同示例所示
* @author 会编程的cpu
* @version 1.0
*/
public interface ForInet
{
	

  //协议在信息首尾占用的长度
  int protocol_length = 2;

    	
  //协议示例：
  //获取用户信息，可以删除
  String getUserInfoProtocol = "▽▽";
  
  //......其他协议
	
  //示例到此结束

  //提供一个方法获取去掉协议的信息 ，
  public static String getRealMsg(String msg)
	{
      return msg.substring(protocol_length,msg.length() - protocol_length);
  }
}
