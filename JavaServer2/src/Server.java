
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/*
 * 基于TCP协议的socket通信,实现用户登录
 * 服务器端
 */


public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket= null;
        try {
            serverSocket = new ServerSocket(8889);  //创建绑定到8889端口的服务套接字
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("服务器已启动...");
        while (true){
            try {
                // 未连通前线程阻塞，连通后开启一个socket通道线程后继续监听8888端口
                Socket socket = serverSocket.accept();  //accept方法返回一个套接字
                System.out.println(socket.getInetAddress().getHostAddress()
                        + "连接进入");                   //连接进入时，打印出返回的套接字的IP
                new ServerThread(socket).start();       //start方法启动线程

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

