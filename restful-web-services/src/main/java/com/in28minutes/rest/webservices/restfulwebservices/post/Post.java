package com.in28minutes.rest.webservices.restfulwebservices.post;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.in28minutes.rest.webservices.restfulwebservices.user.User;

@Entity
public class Post {

	@Id
	@GeneratedValue
	private Integer id;

	private String message;

	private Date creationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;

	public Post(Integer id, String message, Date creationDate) {
		super();
		this.id = id;
		this.message = message;
		this.creationDate = creationDate;
	}

	public Post() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", message=" + message + ", creationDate=" + creationDate + "]";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
