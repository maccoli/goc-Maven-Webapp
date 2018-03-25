package goc.dao;

import goc.pojo.User;

public interface UserMapper {

    String InsertUser(User user);
	
	 User SelectByUserid(int userid);
	
	 User SelectByUsername(String username);
	 
	 User SelectByPhone(String phone);
	 
	 void UpdateByUserid(User user);
}
