package cn.edu.cug.mycommunity.service;

import cn.edu.cug.mycommunity.dao.AlphaDao;
import cn.edu.cug.mycommunity.dao.DiscussPostMapper;
import cn.edu.cug.mycommunity.dao.UserMapper;
import cn.edu.cug.mycommunity.entity.DiscussPost;
import cn.edu.cug.mycommunity.entity.User;
import cn.edu.cug.mycommunity.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

@Service

public class AlphaService {

    @Autowired
    private DiscussPostMapper postMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AlphaDao alphaDao;

    public String find(){
        return alphaDao.select();
    }

    public AlphaService(){
        System.out.println("实例化AlphaService");
    }
    @PostConstruct
    public void init(){
        System.out.println("初始化AlphaService");
    }

    @PreDestroy
    public void destory(){
        System.out.println("销毁AlphaService");
    }

    //REQUIRED          支持当前事务
    //REQUIRES_NEW      创建一个新的事物，并且暂停外部事物
    //NESTED            如果当前存在事物则嵌套在该事物中执行(独立提交和回滚),否则和REQUIRED一样
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Object save1(){
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5("123"+user.getSalt()));
        user.setEmail("182162@gmail.com");
        user.setHeaderUrl("http://image.nowcoder.com/head/99t.png");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);


        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle("Hello world");
        post.setContent("新人报道");
        post.setCreateTime(new Date());
        postMapper.insertDiscussPost(post);

        Integer.valueOf("abc");

        return "OK";
    }

    public Object save2(){
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                User user = new User();
                user.setUsername("beta");
                user.setSalt(CommunityUtil.generateUUID().substring(0,5));
                user.setPassword(CommunityUtil.md5("123"+user.getSalt()));
                user.setEmail("182162122233@gmail.com");
                user.setHeaderUrl("http://image.nowcoder.com/head/98t.png");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);


                DiscussPost post = new DiscussPost();
                post.setUserId(user.getId());
                post.setTitle("Hello sfsdfdsworld");
                post.setContent("新人sdfsfsfsfsfd报道");
                post.setCreateTime(new Date());
                postMapper.insertDiscussPost(post);

                Integer.valueOf("abc");
                return "OK";
            }
        });
    }


}
