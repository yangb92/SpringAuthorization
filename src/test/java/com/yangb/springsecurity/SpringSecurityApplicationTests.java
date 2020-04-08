package com.yangb.springsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SpringSecurityApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void encoder() {
        // 加密原始密码
        String hashpw = BCrypt.hashpw("123", BCrypt.gensalt());
        System.out.println(hashpw);
        //校验密码一致性
        boolean checkpw = BCrypt.checkpw("123", "$2a$10$e/Vgw1zKQDdyxd/DvyNSMeeT0TQhaDB9KbUoN8jC2f8MV9lGUobcy");
        System.out.println(checkpw);
        boolean checkpw1 = BCrypt.checkpw("123", "$2a$10$jIueZHE5S7WJjnZrD//HDugBxKsdLdYfuarmXC.R8S6rJqCv3ifEu");
        System.out.println(checkpw1);
    }

}
