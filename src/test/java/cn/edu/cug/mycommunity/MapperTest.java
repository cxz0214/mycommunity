package cn.edu.cug.mycommunity;

import cn.edu.cug.mycommunity.dao.DiscussPostMapper;
import cn.edu.cug.mycommunity.dao.LoginTicketMapper;
import cn.edu.cug.mycommunity.dao.MessageMapper;
import cn.edu.cug.mycommunity.dao.UserMapper;
import cn.edu.cug.mycommunity.entity.DiscussPost;
import cn.edu.cug.mycommunity.entity.LoginTicket;
import cn.edu.cug.mycommunity.entity.Message;
import cn.edu.cug.mycommunity.entity.User;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;


@SpringBootTest
@ContextConfiguration(classes = MycommunityApplication.class)
public class MapperTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void testSelectUser() {
        User user = userMapper.selectById(101);
        System.out.println(user);

        user = userMapper.selectByName("liubei");
        System.out.println(user);

        user = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user);
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("1234544");
        user.setSalt("and");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());
    }

    @Test
    public void testUpdateUser() {
        int rows = userMapper.updateStatus(150, 1);
        System.out.println(rows);
        rows = userMapper.updateHeader(150, "http://www.nowcoder.com/102.png");
        System.out.println(rows);
        rows = userMapper.updatePassword(150, "hello");
        System.out.println(rows);

    }

    @Test
    public void testSelectPosts(){
        List<DiscussPost>  list = discussPostMapper.selectDiscussPosts(149,0,10);
        for(DiscussPost post : list)
            System.out.println(post);

        int rows = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(rows);
    }

    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(200);
        loginTicket.setStatus(0);
        loginTicket.setTicket("acg");
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 60 * 1000 * 10 ));
        loginTicketMapper.insertLogginTicket(loginTicket);

    }

    @Test void testSelectLoginTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("acg");
        if(loginTicket != null)
           loginTicketMapper.updateStatus("acg",1);
        else
            System.out.println("更新失败 !");
    }

    @Test
    public void testselectConversations(){
        List<Message> list = messageMapper.selectConversations(111,0,20);
        for(Message message : list){
            System.out.println(message);
        }
    }
    @Test
    public void testselectConversationCount(){
        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);
    }

    @Test
    public void testselectLetters(){
        List<Message> list = messageMapper.selectLetters("111_112",0,20);
        for(Message message : list){
            System.out.println(message);
        }
    }

    @Test
    public void testselectLetterCount(){
        int count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);
    }
    @Test
    public void testselectLetterUnreadCount(){
        int count = messageMapper.selectLetterUnreadCount(111,"111_112");
        System.out.println(count);
    }

}
