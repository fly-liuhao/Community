package cn.edu.tit.community;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommunityApplicationTests {

    @Test
    void contextLoads() {
        Integer a = null;
        int b = a;
        System.out.println(b);
    }

}
