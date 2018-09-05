/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/

package com.bw.weatherApi.weatherApi.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable() //disapble cors and csrf
                .authorizeRequests()
                .antMatchers("/api/v1/signup").permitAll() // permit to hit signup
//                .antMatchers("/api/v1/admin").hasRole("Admin").
                .anyRequest().authenticated()
                .and()
                .addFilter(jwtAuthenticationFilter())
                .addFilter(jwtAuthorisationFilter())

                // diaable session to be stateless
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .logoutUrl("api/v1/logout")
                .permitAll(true)
//                .logoutSuccessHandler(logoutHandler()); add the hander later

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }





    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        JwtAuthenticationFilter jwtAuthenticationFilter = null;
        try {
            jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        }catch (Exception ex){
            ex.printStackTrace();

        }

        return jwtAuthenticationFilter;
    }

    @Bean JwtAuthorisationFilter jwtAuthorisationFilter(){
        JwtAuthorisationFilter jwtAuthorisationFilter = null;

        try {
            jwtAuthorisationFilter = new JwtAuthorisationFilter(authenticationManager());
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return jwtAuthorisationFilter;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return super.userDetailsService();
//    }



}
