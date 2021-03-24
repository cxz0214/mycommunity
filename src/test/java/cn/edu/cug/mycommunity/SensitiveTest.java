package cn.edu.cug.mycommunity;


import cn.edu.cug.mycommunity.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;




@SpringBootTest
@ContextConfiguration(classes = MycommunityApplication.class)
public class SensitiveTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter(){
        String test = "赌◇博加微信";
        test = sensitiveFilter.filter(test);
        System.out.println(test);
    }
}
