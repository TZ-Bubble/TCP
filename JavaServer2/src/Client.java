import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


/*
 * 客户端
 */
public class Client {
    public static void main(String[] args){
        try {
//1.创建Socket,用于向服务器端发送请求
            Socket socket = new Socket("localhost",8889);
//2.创建Socket字节输出流
            OutputStream os = socket.getOutputStream();
//3.将字节输出流包装成Print流
            PrintWriter pw = new PrintWriter(os);
            pw.write("用户名:zh;密码:456");
            pw.flush();
            socket.shutdownOutput();//关闭输出流
//4.创建输入流接收服务器端响应的数据
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while((info=br.readLine())!=null){
                System.out.println("我是客户端,服务器说: "+info);
            }
            socket.shutdownInput();//关闭输入流
//5.关闭资源
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

