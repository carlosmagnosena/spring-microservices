package com.in28minutes.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.post.Post;
import com.in28minutes.rest.webservices.restfulwebservices.post.PostRepository;

@RestController
public class UserJPAResource {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	// retrieveAllUsers
	@GetMapping(path = "/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	// retrieveUser
	// HATEOAS Example
	@GetMapping(path = "/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("id -" + id);
		}
		EntityModel<User> model = EntityModel.of(user.get());
		WebMvcLinkBuilder linktoUsers = linkTo(methodOn(this.getClass()).retrieveAllUsers());

		// Do not worry about Resource and ControllerLinkBuilder

//		Resource<User> resource = new Resource<User>(user);
//		ControllerLinkBuilder linkTo = 
//				linkTo(methodOn(this.getClass()).retrieveAllUsers());

		model.add(linktoUsers.withRel("all-users"));
		return model;
	}

	// delete User
	@DeleteMapping(path = "/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);

	}

	// createUser
	// input - details
	// output - CREATED & Return the created URI
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

		if ("".equals(user.getName()) || user.getName() == null) {
			throw new NoNameException("A name must be provided");
		}
		User savedUser = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return (ResponseEntity<Object>) ResponseEntity.created(location).build();
	}

	// retrieveAllPostsFromUser
	@GetMapping(path = "/users/{id}/posts")
	public List<Post> retrieveAllPostsFromUser(@PathVariable int id) {

		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("id -" + id);
		}
		return user.get().getPosts();
	}

	// create Post From User
	@PostMapping(path = "/users/{id}/posts")
	public ResponseEntity<Object> createPostFromUser(@PathVariable int id, @RequestBody Post post) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("id -" + id);
		}
		post.setUser(user.get());
		postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
				.toUri();
		System.out.println(location);
		ResponseEntity<Object> r = ResponseEntity.created(location).build();
		return ResponseEntity.created(location).build();
	}

}
