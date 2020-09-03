package com.services;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.POJO.AppRole;
import com.entities.User;

@Service
public class UserService {

	private PasswordEncoder passwordEncoder;
	private Map<String, User> users = new HashMap<String, User>();
	
	@Autowired	
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@PostConstruct
	public void UserServiceInit() {
		
		users.put("Bardos_Szabolcs", 
				new User("Bardos_Szabolcs", passwordEncoder.encode("hello1"), "Szabolcs.Bardos@hagyokft.hu", 	AppRole.STUDENT.name()));
		users.put("Nagy_Timea", 
				new User("Nagy_Timea", 		passwordEncoder.encode("hello1"), "Timea.Nagy@hagyokft.hu", 		AppRole.STUDENT.name()));
		users.put("Pota_Richard",
				new User("Pota_Richard", 	passwordEncoder.encode("hello1"), "Richard.Pota@hagyokft.hu", 		AppRole.ADMIN.name()));
		users.put("Teleki_Lajos",
				new User("Teleki_Lajos", 	passwordEncoder.encode("hello1"), "lajos.teleki@hagyokft.hu", 		AppRole.ADMIN.name()));
		users.put("Bertalan_Zsolt",
				new User("Bertalan_Zsolt", 	passwordEncoder.encode("hello1"), "Zsolt.Bertalan@hagyokft.hu", 	AppRole.ADMINTRAINEE.name()));
		users.put("Szunyog_Csaba",
				new User("Szunyog_Csaba", 	passwordEncoder.encode("hello1"), "Csaba.Szunyog@hagyokft.hu", 		AppRole.ADMINTRAINEE.name()));
	}


	
	public User find(String name) {
		return users.get(name);
	}	

	
	public static class UserDataException extends Exception
	{
		private static final long serialVersionUID = 1L;

		UserDataException()
		{
			System.out.println("USER DATAS ARE MISSING, OR USER ALREADY EXISTS");
		}
	}
}
