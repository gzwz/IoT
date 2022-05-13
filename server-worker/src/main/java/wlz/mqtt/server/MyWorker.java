package wlz.mqtt.server;


import java.io.ByteArrayOutputStream;
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
            processProtocol(ins);
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

    private void processProtocol(InputStream ins) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buff = new byte[1];
        int len = 0;
        try {
            ins.read();
            while ((len = ins.read(buff,0,buff.length)) != -1){
                bos.write(buff,0,len);
            }
        }catch (Exception e){
            System.out.println(new String(bos.toByteArray()));
            e.printStackTrace();
        }finally {
        if (bos == null) {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


        System.out.println(new String(bos.toByteArray()));
    }
}