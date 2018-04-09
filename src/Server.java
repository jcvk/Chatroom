/**
 * Created by Jvck on 2018/1/8.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Server sever = new Server();
        sever.run();
    }

    private void run() {

        try {
            ServerSocket server = new ServerSocket(2222);
            Socket mSocket = null;
            int count = 0;//记录客户端数量

            while (true) {
                mSocket = server.accept();
                //开启子线程去回复客户端消息
                new ServerThread(mSocket).start();
                count++;
                System.out.println("客户端数量：" + count);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
