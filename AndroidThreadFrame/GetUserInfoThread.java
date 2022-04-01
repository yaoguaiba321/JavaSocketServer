package com.example.insist.thread;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSONObject;
import com.example.insist.tools.ForInet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.net.ssl.SSLEngineResult;

/**
 * 这是一个继承ThreadFather，重写task的示例
 * 一个用于获取服务器中用户信息的线程
 * @author 细水长流cpu
 * @version 1.0
 */
public class GetUserInfoThread extends ThreadFather{
    //msg.what集中存放在这里
    public static final int UPDATE = 0x1,FAIL=0x2,DIALOG_UPDATE = 0x3,THINGACTION_DONE=0x4,THINGACTION_FAIL=0x5,USELESS=0x6,CARE_DONE=0x7,CARE_FAIL=0x8,
        GETSKILL=0x9,CANCEL = 0x10,UPTHING=0x11,REFRESH_DOWN=0x12,THUMB=0x13,COPPER=0x14,EVENT=0x15;
    


    public GetUserInfoThread(String openid, Handler handler){
        super(openid,0,handler);
    }
    /**
    * 重写这个task()方法只需要如下三个逻辑
    * 1、将数据put到outobject中
    * 2、用ps.println(ForInet.xxx+outobject+ForInet.xxx);
    * 3、等待服务器传回json数据
    * 如果有需要改变message.what只需要在最后加上一个message.what = xxx
    */
    @Override
    public void task() throws Exception {

        //使用JSONObject传输数据
        outobject.put("openid",openid);

        //传输到服务器
        ps.println(ForInet.getUserInfoProtocol+outobject+ForInet.getUserInfoProtocol);
      
        //等待服务器传回JSON数据
        inobject = JSONObject.parseObject(ForInet.getRealMsg(br.readLine()));

        //如果有改变message.what的需要
        //message.what = CHANGE;

    }
}
