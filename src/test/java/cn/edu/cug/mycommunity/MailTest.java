package cn.edu.cug.mycommunity;


import cn.edu.cug.mycommunity.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
@ContextConfiguration(classes = MycommunityApplication.class)
public class MailTest {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail(){
        mailClient.sendMail("1721529255@qq.com","Hello World","加油，你可以的！");
    }

    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username","sunday");

        String content = templateEngine.process("/mail/demo",context);
        System.out.println(content);
        mailClient.sendMail("1721529255@qq.com","Html",content);
    }
}
