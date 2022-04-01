package com.example.insist.thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.insist.tools.ForInet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.example.insist.thread.GetUserInfoThread.FAIL;
import static com.example.insist.thread.GetUserInfoThread.UPDATE;

/**
* 这是一个Android客户端线程的父类，封装了很多固定的逻辑，继承了ThreadFather之后，使用者的工作量和注意力都只需要放在数据的处理上。甚至不需要网络部分的知识
* 只需要会fastjson的put和get即可
* @author 会编程的cpu
* @version 1.0
*/
public abstract class ThreadFather extends Thread{
    //协议
    String Protocol;
    //一些大概率会用到的数据
    String openid;
    Handler handler;
    //网络数据流
    Socket socket = new Socket();
    InetSocketAddress isa = new InetSocketAddress(ForInet.ip,ForInet.port);
    PrintStream ps;
    BufferedReader br;
    //接收和发送数据用的JSONObject
    JSONObject inobject,outobject = new JSONObject();;
    //传递给Handler的Message
    Message message = new Message();

    //构造器
    public ThreadFather(String openid,int life,Handler handler){
        this.openid = openid;
        this.life = life;
        this.handler = handler;

    }
    abstract void task() throws Exception;
    @Override
    public void run(){
        try {
            //建立连接
            socket.connect(isa, ForInet.delayMillis);
            //获取输出流
            ps = new PrintStream(socket.getOutputStream());
            //获取输入流
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //先设置message.what，如果task中需要特殊的what，就在task中修改就好了
            message.what = UPDATE;
            //执行任务
            task();
            //执行成功后
            message.obj = inobject;
            handler.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
            //如果出错了，就用message通知handler出错了
            message.what = FAIL;
            Log.d("getThingInfo","线程出错了");
            handler.sendMessage(message);
        }
        finally {
            try{
                //关闭资源
                if(ps != null){
                    ps.close();
                }
                if(br != null){
                    br.close();
                }
                if(socket != null){
                    socket.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
