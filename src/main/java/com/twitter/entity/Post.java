 package com.twitter.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	private String description;
	private Date addDate;
	@ManyToOne
	private User user;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "post_comment",joinColumns = @JoinColumn(name = "posts",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name="comments",referencedColumnName = "id"))
	private Set<Comment> comment= new HashSet<>();
}
