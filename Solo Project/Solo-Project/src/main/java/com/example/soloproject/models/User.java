package com.example.soloproject.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
    
@Entity
@Table(name="users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message="Name is required!")
    @Size(min=3, max=30, message="Name must be between 3 and 30 characters")
    private String username;
    
    @NotEmpty(message="Email is required!")
    @Email(message="Please enter a valid email!")
    private String email;
    
    @NotEmpty(message="Password is required!")
    @Size(min=8, max=128, message="Password must be between 8 and 128 characters")
    private String password;
    
    @Transient
    @NotEmpty(message="Confirm Password is required!")
    @Size(min=8, max=128, message="Confirm Password must be between 8 and 128 characters")
    private String confirm;
    
    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<Chore> Chore;
    
    
    public List<Chore> getChores() {
		return Chore;
	}

	public void setChore(List<Chore> Chores) {
		this.Chore = Chores;
	}
	public User() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        User user = (User) obj;
	        return username.equals(user.username) && email.equals(user.email) && (password != null ? password.equals(user.password) : user.password == null);
	    }

	    @Override
	    public int hashCode() {
	        int result = username.hashCode();
	        result = 31 * result + email.hashCode();
	        result = 31 * result + (password != null ? password.hashCode() : 0);
	        return result;
	    }
  
}


