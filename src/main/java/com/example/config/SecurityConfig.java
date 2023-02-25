package com.example.config;

import com.example.service.CustomLogoutHandler;
import com.example.service.UserService;
import com.example.service.jwt.JWTRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserService userService;
  private final JWTRequestFilter filter;
  private final CustomLogoutHandler customLogoutHandler;

  @Bean
  PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(userService)
        .passwordEncoder(getPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/my", "/profile").authenticated()//.hasRole("USER")
        .antMatchers("/**").permitAll()
        .and()
        .formLogin()
        .loginPage("/signin").failureUrl("/signin")
        .and().logout().addLogoutHandler(customLogoutHandler)
        .logoutUrl("/logout").logoutSuccessUrl("/signin")
        .deleteCookies("token", "sub", "JSESSIONID")
        //.invalidateHttpSession(true).clearAuthentication(true)
        .and().oauth2Login()
        .defaultSuccessUrl("/my").defaultSuccessUrl("/oauth2LoginSuccess")
        .and().oauth2Client();
    //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
  }
}
