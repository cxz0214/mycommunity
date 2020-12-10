package cn.edu.cug.mycommunity.service;


import cn.edu.cug.mycommunity.dao.UserMapper;
import cn.edu.cug.mycommunity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id){
        return userMapper.selectById(id);
    }
}
