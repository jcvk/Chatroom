import java.io.*;
import java.net.Socket;

/**
 * Created by Jvck on 2018/1/8.
 */
public class ServerThread extends Thread {

    private Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        OutputStream os = null;
        PrintWriter pw = null;


        try {

            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String info;
            while ((info = br.readLine()) != null) {
                System.out.println("我是服务器，客户端说: " + info);
                os = socket.getOutputStream();
                pw = new PrintWriter(os);
                pw.println("服务器收到信息");
                pw.flush();


            }

            socket.shutdownInput();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭资源
                if (pw != null)
                    pw.close();
                if (os != null)
                    os.close();
                if (is != null)
                    is.close();
                if (isr != null)
                    isr.close();
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

    }
}
