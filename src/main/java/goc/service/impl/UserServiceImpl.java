package goc.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import goc.dao.UserMapper;
import goc.pojo.User;
import goc.service.UserService;

@Service("userservice")
public class UserServiceImpl implements UserService {

	@Resource
	public UserMapper userMapper;
	
	public String InsertUser(User user) {
		
		return userMapper.InsertUser(user);
	}

	@Override
	public User SelectByUserid(int userid) {
		
		return this.userMapper.SelectByUserid(userid);
	}

	@Override
	public User SelectByUsername(String username) {
		
		return this.userMapper.SelectByUsername(username);
	}

	@Override
	public User SelectByPhone(String phone) {
		
		return this.SelectByPhone(phone);
	}

	@Override
	public void UpdateByUserid(User user) {
		
		this.userMapper.UpdateByUserid(user);
	}

}
