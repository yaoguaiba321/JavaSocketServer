package Server;

public interface ForInet
{
	

  //协议在信息首尾占用的长度
  int protocol_length = 2;

    	
  //协议示例：
	//获取用户信息
  String getUserInfoProtocol = "▽▽";
  
  //......其他协议

  //提供一个方法获取去掉协议的信息
  public static String getRealMsg(String msg)
	{
      return msg.substring(protocol_length,msg.length() - protocol_length);
  }
}
