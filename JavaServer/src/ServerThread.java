import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


/*
 * 服务器线程处理类
 */

public class ServerThread extends Thread {
    //用于标识手机还是单片机,0为手机，1为单片机
    private static ArrayList<PrintWriter> phoneList = new ArrayList<PrintWriter>();
    private static ArrayList<PrintWriter> MCUList = new ArrayList<PrintWriter>();

    public String what = "";
    private Socket socket = null;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public ServerThread(Socket socket) throws IOException{
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket
                .getInputStream()));
        this.printWriter = new PrintWriter(socket.getOutputStream());
        String info = bufferedReader.readLine();
        if (info.contains("MCU")){
            MCUList.add(printWriter);
            this.what = "MCU";
        }else if (info.contains("PHONE")){
            phoneList.add(printWriter);
            this.what = "PHONE";
        }
    }

    public void run(){

        String info = null;
        try {

            while((info = bufferedReader.readLine()) != null){
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
