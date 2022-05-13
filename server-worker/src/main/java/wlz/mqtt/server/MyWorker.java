package wlz.mqtt.server;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class MyWorker implements Runnable {

    private Socket socket;

    public MyWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream ins = null;
        OutputStream outs = null;
        try {
            ins = socket.getInputStream();
            processReqProtocol(ins);
            outs = socket.getOutputStream();
            processRespProtocol(outs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ins == null) ins.close();
                if (outs == null) outs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processRespProtocol(OutputStream outs) {
        byte buff[] = new byte[6];
        buff[0] = 0x20; // 确认链接
        buff[1] = 0x06;
        buff[2] = 0x01; //0x00连接已接受
        buff[3] = 0x02;
        buff[4] = 0x03;
        buff[5] = 0x04;
        try {
            outs.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理协议
     */
    private void processReqProtocol(InputStream ins) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int len = 0;
        try {
            //读取第一个字节 固定报文头 判断报文类型
            byte read = (byte) ins.read();
            bos.write(read);

            // 读取第二个字节-最多到第四个字节变长报文头
            read = (byte) ins.read();
            bos.write(read);
            if ((read & 0x80) == 0x80) {
                // 说明还有第二个字节参与变长
                System.out.println("还有第二个字节长度");
            }
            len = read;
            byte[] buff = new byte[len];
            ins.read(buff, 0, len);
            bos.write(buff, 0, len);
//            while ((len = ins.read(buff,0,buff.length)) != -1){
//                bos.write(buff,0,len);
//            }
            System.out.println("收到报文：" + new String(bos.toByteArray()));
        } catch (Exception e) {
            System.out.println("异常：收到报文：" + new String(bos.toByteArray()));
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}