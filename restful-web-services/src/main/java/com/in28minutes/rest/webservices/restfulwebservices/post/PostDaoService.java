package com.in28minutes.rest.webservices.restfulwebservices.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.in28minutes.rest.webservices.restfulwebservices.user.User;
import com.in28minutes.rest.webservices.restfulwebservices.user.UserDaoService;

@Component
public class PostDaoService {

	@Autowired
	private UserDaoService userService;

	private static int postCount = 1;

	public List<Post> findAllfromUser(int id) {

		return userService.findOne(id).getPosts();
	}

	public Post save(int userId, Post post) {

		User user = userService.findOne(userId);
		if (user == null) {
			return null;
		}
		post.setId(+postCount);
		user.addPost(post);
		return post;
	}

	public Post findOneFromUser(int userId, int postId) {
		for (User user : userService.findAll()) {
			if (user.getId() == userId) {
				for (Post post : user.getPosts()) {
					if (post.getId() == postId) {
						return post;
					}

				}
			}

		}

		return null;
	}
}
