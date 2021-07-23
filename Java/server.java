import java.io.*;
import java.net.*

public class MyTCP{
	private BufferedReader reader;
	private ServerSocket server;
	private Socket socket;
	void getserver(){
		try{
			server = ner ServerSocket(8889);
			System.out.println("Socket创建成功")
			while(true){
				System.out.println("等待来自Client的连接")
				socket = server.accept();	//阻塞待连接响应
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				getClientMessage();
			}
		}catch(Exception e){
			e.printStackTrace(); //输出异常信息
		}

	}
	private void getClientMessage(){
		try{
			while(true){
				//获取客户端信息
				System.out.println("客户机："+reader.readLine());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			if(reader != null){
				reader.close();
			}
			if(socket != null){
				socket.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	public static void main(String[] args){
		My static void main(String[] args){
			MyTcp tcp = new MyTcp();
			tcp.getserver();
		}
	}
}
