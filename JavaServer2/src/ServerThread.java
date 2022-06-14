import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


/*
 * 服务器线程处理类
 */

public class ServerThread extends Thread {  //ServerThread类，继承Thread，多线程
    //用于标识手机还是单片机,0为手机，1为单片机
    private static ArrayList<PrintWriter> phoneList = new ArrayList<PrintWriter>(); //PrintWriter的动态数组
    private static ArrayList<PrintWriter> MCUList = new ArrayList<PrintWriter>();

    public String what = "";        //标识MCU还是PHONE
    private Socket socket = null;   //传入的socket
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public ServerThread(Socket socket) throws IOException{  //构造方法，传入一个socket
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket
                .getInputStream()));    //取入数据(使用指向启动线程的socket的输入流对bufferedReader初始化)
        this.printWriter = new PrintWriter(socket.getOutputStream());   //传出数据(...)
        String info = bufferedReader.readLine();    //读取一行
        if (info.contains("MCU")){
            MCUList.add(printWriter);
            this.what = "MCU";
        }else if (info.contains("PHONE")){
            phoneList.add(printWriter);
            this.what = "PHONE";
        }
    }

    public void run(){      //线程的功能代码

        String info = null;
        try {

            while((info = bufferedReader.readLine()) != null){
		System.out.println("收到数据：" + info);     //读入一行非空数据时打印


		//http://147.8.133.247/api/icore/gps?hwid={}&lat=22.234&lng=114.123&height=50&numsat=5
        //request.get

                if()


                if(this.what == "MCU"){
                    System.out.println("MCU发来数据：" + info);
                    if(info.contains("__DATA_")){
                        DataMCU dataMCU = new DataMCU(info);
                        System.out.println("环境温度:" + dataMCU.getEnviorenmentalTemprature());
                        System.out.println("环境湿度:" + dataMCU.getEnviorenmentalHumidity());
                        System.out.println("盒内重量:" + dataMCU.getWeight());
                        System.out.println("盒盖状态:" + dataMCU.getState());
                        System.out.println("经度:" + dataMCU.getLongitude());
                        System.out.println("纬度:" + dataMCU.getLatitude());
                        System.out.println("盒内温度:" + dataMCU.getBoxTemprature());
                        System.out.println("盒内设定温度:" + dataMCU.getSettedTemprature());
                    }
                    for(PrintWriter printWriter:phoneList ){
                        printWriter.write(info + "\r\n");
                        printWriter.flush();
                    }
                }else if(this.what == "PHONE"){
                    System.out.println("手机端发来数据：" + info);
                    for(PrintWriter printWriter:MCUList ){
                        printWriter.write("服务器回显：" + info + "\r\n");
                        printWriter.flush();
                    }
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
