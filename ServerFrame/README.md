关于服务框架的代码说明
整个服务器的逻辑是，
从Main进入程序，启动AppServer
然后由AppServer负责监听客户端的连接，监听到后，
就把信息传入到SocketThread这个线程，由SocketThread根据msg中的协议开头判断并执行对应的业务代码（xxxModule）

Main.java无需修改
AppServer.java需要修改server.ini的文件位置，还有server.ini中的等于号的内容，如果有每天都需要执行的内容，可以在rountineTask()方法中增加对应的代码。
GiveUserInfoModule.java是一个ModuleFather.java子类的示例，供大家参考，注释都十分详细了，这里就不多说了。
ModuleFather.java就是一个父类，把绝大多数重复的代码封装好了，这样大家甚至不需要有Socket知识，就可以使用这套框架
SocketThread.java是一个线程，
使用者配置好了之后，需要做的就是
1、在ForInet.java中增加一个协议，
2、写一个xxxModule.java
3、在SocketThead中增加一个if(msg.startsWith(ForInet.xxxx)){ xxxModule module = new xxxModule(msg,ps,conn); module.execute()}
即可

DateTimeHelper只是一个时间工具类，不涉及到框架。


Andorid(java版本）的代码也提供了两个文件供大家参考，可以看AndroidThreadFrame文件夹中的文件
