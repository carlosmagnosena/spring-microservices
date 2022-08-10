package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.in28minutes.rest.webservices.restfulwebservices.post.Post;

@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<User>();

	private static int usersCount = 3;

	static {
		Post postTest = new Post(1, "Hello everybody!", new Date());
		User user = new User(1, "Adam", new Date());
		user.addPost(postTest);
		users.add(user);
		users.add(new User(2, "Eve", new Date()));
		users.add(new User(3, "Jack", new Date()));
	}

	public List<User> findAll() {

		return users;
	}

	public User save(User user) {

		if (user.getId() == null) {
			user.setId(++usersCount);

		}
		users.add(user);
		return user;
	}

	public User findOne(int id) {
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}

		}
		return null;
	}

	public User deleteById(int id) {
		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
			User u = iterator.next();
			if (u.getId() == id) {
				iterator.remove();
				return u;
			}

		}
		return null;

	}
}
