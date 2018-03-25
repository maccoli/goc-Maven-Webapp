package goc.service;

import goc.pojo.User;

public interface UserService {

	public String InsertUser(User user);
	
	public User SelectByUserid(int userid);
	
	public User SelectByUsername(String username);
	
	public User SelectByPhone(String phone);
	
	public void UpdateByUserid(User user);
}
