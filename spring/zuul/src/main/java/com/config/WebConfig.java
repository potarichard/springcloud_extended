package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.jwt.JwtTokenVerifier;
import com.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.services.AppUserDetailsService;


@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfig extends WebSecurityConfigurerAdapter {

	private AppUserDetailsService appuserdetailsservice;		
	
	@Autowired	
	public void setAppuserdetailsservice(AppUserDetailsService appuserdetailsservice) {
		this.appuserdetailsservice = appuserdetailsservice;
	}

	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {        
		auth.userDetailsService(appuserdetailsservice);
    }
	

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{		
	
	    http
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)							// nem tarolunk statet (sessiont) a jwt tokenbe kell benne lennie a lejarati datumnak!
		.and()
		.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))					// ez a sajat filterunk, sajat konstruktorral
		.addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class)
		.authorizeRequests()
//		.antMatchers("/**").permitAll()
		.antMatchers(HttpMethod.POST, "/user/register").permitAll() 	
		.antMatchers(HttpMethod.GET, "/login").permitAll() 
		.antMatchers(HttpMethod.POST, "/login").permitAll() 
		.anyRequest().fullyAuthenticated();		

	}
}

















// regi verzio sima formlogin

//String[] staticResources  =  {
//        "/css/**",
//        "/images/**",
//        "/fonts/**",
//        "/scripts/**",
//        "/thirdparty/**",
//    };

//http.csrf().disable()		// ha ez enabled, akkor a cliennek el kell mentenie a tokent es hozzatenni minden headerhez!
//.authorizeRequests()
////	.antMatchers(HttpMethod.GET,"/admintrainees").hasAuthority("course:read")	// mindezt method szinten akarom
////	.antMatchers(HttpMethod.GET, "/admins").hasAuthority("course:write")		// AppPermission.COURSE_WRITE.name() ez igy nem oke nem ezut adja "course:write"
//	.antMatchers(HttpMethod.POST, "/user/register").permitAll()
//	.antMatchers(staticResources).permitAll()		  		  	
//	.anyRequest().fullyAuthenticated()
//.and()
//	.formLogin().permitAll();
















