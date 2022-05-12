package wlz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SwApplication {
    public static void main(String[] args) {
        System.out.println(System.getProperty("file.encoding"));
        System.out.println("你好中国￥");
        SpringApplication.run(SwApplication.class);
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
