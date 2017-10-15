package com.lsr.test;

import javax.jws.WebService;

@WebService
public interface UserService {
	public User getUser(String name);
}
