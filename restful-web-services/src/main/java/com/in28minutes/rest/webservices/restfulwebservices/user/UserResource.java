package com.in28minutes.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

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

@RestController
public class UserResource {

	@Autowired
	private UserDaoService userDaoService;

	// retrieveAllUsers
	@GetMapping(path = "memo/users")
	public List<User> retrieveAllUsers() {
		return userDaoService.findAll();
	}

	// retrieveUser
	// HATEOAS Example
	@GetMapping(path = "memo/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = userDaoService.findOne(id);
		if (user == null) {
			throw new UserNotFoundException("id -" + id);
		}
		EntityModel<User> model = EntityModel.of(user);
		WebMvcLinkBuilder linktoUsers = linkTo(methodOn(this.getClass()).retrieveAllUsers());

		// Do not worry about Resource and ControllerLinkBuilder

//		Resource<User> resource = new Resource<User>(user);
//		ControllerLinkBuilder linkTo = 
//				linkTo(methodOn(this.getClass()).retrieveAllUsers());

		model.add(linktoUsers.withRel("all-users"));
		return model;
	}

	// delete User
	@DeleteMapping(path = "old/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = userDaoService.deleteById(id);
		if (user == null) {
			throw new UserNotFoundException("id -" + id);
		}

	}

	// createUser
	// input - details
	// output - CREATED & Return the created URI
	@PostMapping("memo/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

		if ("".equals(user.getName()) || user.getName() == null) {
			throw new NoNameException("A name must be provided");
		}
		User savedUser = userDaoService.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

}
