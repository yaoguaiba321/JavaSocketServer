这个是Android客户端的线程部分代码，其他代码都是和Android基础相关的，就不放上来了，
使用者只需要改变ForInet中的ip和port之后，就可以使用了
用户只需要创建一个ThreadFather.java
然后需要和服务器交互的地方继承ThreadFather参考GetUserInfoThread.java来写就好了，注释很详细
还有一些特殊的情况，比如需要在线程中就把值设置好的情况还有MyHandler，这里就不列举了，对于有一定经验的同学，都懂
