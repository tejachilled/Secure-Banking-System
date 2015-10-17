package com.bankapp.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.dao.UserDAO;
import com.bankapp.model.UserInfo;

@Service("loginService")
public class LoginService implements UserDetailsService{

	@Autowired
	UserDAO userDao;

	@Transactional
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserInfo user=userDao.findUserByUsername(username);
		if(user!=null)
		{
			String password=user.getPassword();
			Collection<GrantedAuthority> roles=new ArrayList<GrantedAuthority>();
			GrantedAuthority grantedAuthority = new GrantedAuthority() {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public String getAuthority() {
	                return user.getRole();
	            }
	        }; 
	        System.out.println("role: "+user.getRole());
	        roles.add(grantedAuthority);
	        System.out.println("role: "+roles.iterator().next().getAuthority());
			User userInfo=new User(username, password, roles);
			return userInfo;
		}
		else
		{
			throw new UsernameNotFoundException("Invalid user!!");
		}
		
	}

}
