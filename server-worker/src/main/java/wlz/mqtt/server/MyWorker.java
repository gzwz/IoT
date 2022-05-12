package wlz.mqtt.server;

import org.fusesource.hawtbuf.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

class MyWorker implements Runnable {

    private Socket socket;

    public MyWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream ins = null;
        try {
            ins = socket.getInputStream();
            byte[] buff = new byte[1];
            int len = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = ins.read(buff,0,buff.length)) != -1){
                bos.write(buff,0,len);
            }
            System.out.println(new String(bos.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (ins == null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}