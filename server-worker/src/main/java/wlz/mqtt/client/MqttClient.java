package wlz.mqtt.client;

import org.fusesource.mqtt.client.*;

public class MqttClient {
    public static void main(String[] args) throws Exception {
        test();
//        feature_test();
    }

    private static void test() {
        MQTT mqc = new MQTT();
        try {
            mqc.setHost("127.0.0.1", 8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mqc.setClientId("123456789");
        mqc.setUserName("test");
        mqc.setPassword("test");
        BlockingConnection connection = mqc.blockingConnection();
        try {
            connection.connect();
            connection.publish("topc-test", "Hello".getBytes(), QoS.AT_LEAST_ONCE, false);
            Message message = connection.receive();
            System.out.println(message.getTopic());
            byte[] payload = message.getPayload();
            // process the message then:
            message.ack();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

//    private static void feature_test() throws Exception {
//        MQTT mqtt = new MQTT();
//        FutureConnection connection = mqtt.futureConnection();
//        Future<Void> f1 = connection.connect();
//        f1.await();
//
//        Future<byte[]> f2 = connection.subscribe(new Topic[]{new Topic(utf8("foo"), QoS.AT_LEAST_ONCE)});
//        byte[] qoses = f2.await();
//
//        // We can start future receive..
//        Future<Message> receive = connection.receive();
//
//        // send the message..
//        Future<Void> f3 = connection.publish("foo", "Hello".getBytes(), QoS.AT_LEAST_ONCE, false);
//
//        // Then the receive will get the message.
//        Message message = receive.await();
//        message.ack();
//
//        Future<Void> f4 = connection.disconnect();
//        f4.await();
//    }


}
