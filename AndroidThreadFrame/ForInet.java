package com.example.insist.tools;
/**
* 客户端的ForInet
* @author 会编程的cpu
* @version 1.0
*/
public interface ForInet {

    //服务器ip地址
    String ip = "xxx.xxx.xxx.xxx";
    //服务器端口
    int port = xxxx;
    //允许延迟的最大时间
    int delayMillis = 3000;

    //协议在信息首尾占用的长度
    int protocol_length = 2;

    //获取用户信息（这里和角色信息区分开）
    String getUserInfoProtocol = "▽▽";

   

    //提供一个方法获取去掉协议的信息
    public static String getRealMsg(String msg){
        return msg.substring(protocol_length,msg.length() - protocol_length);
    }

}
