package com.lsr.test;

public class UserServiceImpl implements UserService {

	@Override
	public User getUser(String name) {
		User u=new User();
        u.setUsername(name);
        u.setPassword("123456");
        return u;
	}

}
