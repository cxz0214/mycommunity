package cn.edu.cug.mycommunity;
import cn.edu.cug.mycommunity.config.AlphaConfig;
import cn.edu.cug.mycommunity.dao.AlphaDao;
import cn.edu.cug.mycommunity.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = MycommunityApplication.class)
public class TestTransaction {
    @Autowired
    private AlphaService alphaService;

    @Test
    public void testSave1(){
        Object object = alphaService.save1();
        System.out.println(object);
    }


    @Test
    public void testSave2(){
        Object object = alphaService.save2();
        System.out.println(object);
    }
}
