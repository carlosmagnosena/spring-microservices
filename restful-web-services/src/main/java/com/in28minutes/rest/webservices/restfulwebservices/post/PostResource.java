package com.in28minutes.rest.webservices.restfulwebservices.post;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class PostResource {

	@Autowired
	private PostDaoService postService;

	private PostRepository postRepository;

	// retrieveAllPostsFromUser
	@GetMapping(path = "memo/users/{id}/posts")
	public List<Post> retrieveAllPostsFromUser(@PathVariable int id) {
		return postService.findAllfromUser(id);
	}

	// create Post From User
	@PostMapping(path = "memo/users/{id}/posts")
	public ResponseEntity<Object> createPostFromUser(@PathVariable int id, @RequestBody Post post) {
		Post savedPost = postService.save(id, post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId())
				.toUri();
		return (ResponseEntity<Object>) ResponseEntity.created(location).build();
	}

	// retrieveDetailPostFromUser
	@GetMapping(path = "memo/users/{id}/posts/{post_id}")
	public Post retrieveDetailPostsFromUser(@PathVariable int userId, @PathVariable int postId) {
		return postService.findOneFromUser(userId, postId);
	}

}
