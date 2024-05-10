package com.example.soloproject.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.soloproject.models.Chore;
import com.example.soloproject.models.LoginUser;
import com.example.soloproject.models.User;
import com.example.soloproject.repositories.UserRepository;

    
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;
    
    public User register(User newUser, BindingResult result) {
    	
    	Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());
    	
    	if(potentialUser.isPresent()) {
    		result.rejectValue("email", "Matches", "An account with that email already exists!");
    	}

    	if(!newUser.getPassword().equals(newUser.getConfirm())) {
    	    result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
    	}

        if(result.hasErrors()) {
        	return null;
        }
    
        String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
        newUser.setPassword(hashed);
        return userRepo.save(newUser);
    }

    public User login(LoginUser newLoginUser, BindingResult result) {
        
    	Optional<User> potentialUser = userRepo.findByEmail(newLoginUser.getEmail());
    	
    	if(!potentialUser.isPresent()) {
    		result.rejectValue("email", "Matches", "User not found!");
    		return null;
    	}
    	
    	User user = potentialUser.get();
        
    	if(!BCrypt.checkpw(newLoginUser.getPassword(), user.getPassword())) {
    	    result.rejectValue("password", "Matches", "Invalid Password!");
    	}
    	
    	if(result.hasErrors()) {
        	return null;
        }

    	return user;
    }
    
    public User findById(Long id) {
    	Optional<User> potentialUser = userRepo.findById(id);
    	if(potentialUser.isPresent()) {
    		return potentialUser.get();
    	}
    	return null;
    }

    public User save(User user) {
        return userRepo.save(user);
    }
    
    public void addChoreToUser(User user, Chore chore) {
        user.getChores().add(chore);
        userRepo.save(user);
    }

}
