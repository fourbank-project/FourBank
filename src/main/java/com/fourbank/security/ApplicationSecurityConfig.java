package com.fourbank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests() // Authorize Requests
                // Para permitir a página index, assim como todos css e js:
                .antMatchers("/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name())
                .antMatchers("/**", "/css/*", "/js/*").permitAll()
//                .antMatchers("/api/empresa/**").hasAnyRole(ApplicationUserRole.EMPRESA.name())
//                .antMatchers("/api/**").hasRole(ApplicationUserRole.ADMIN.name())
//                .antMatchers(HttpMethod.DELETE, "/api/empresa/**").hasAuthority(ApplicationUserPermition.EMPRESA_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "/api/empresa/**").hasAuthority(ApplicationUserPermition.EMPRESA_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/api/empresa/**").hasAuthority(ApplicationUserPermition.EMPRESA_WRITE.getPermission())
                .anyRequest() // Any Request
                .authenticated() // They must be authenticated(user and password)
                .and()
                .httpBasic(); // Using Basic Authentication
    }

    @Override
    @Bean //Instancia pra nós
    protected UserDetailsService userDetailsService() { // Its how we retrieve our users from the DB. Sim escrevi em inglês mrm xD
        UserDetails sysorUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("1234567")) // Uso de um encoder para criptografar a senha(Obrigatório)
//                .roles(ApplicationUserRole.ADMIN.name()) // ROLES_ADMIN
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
                .build();

        UserDetails userUser = User.builder()
                .username("user")
                .password(passwordEncoder.encode("12345"))
//                .roles(ApplicationUserRole.NORMAL.name()) // ROLES_NORMAL
                .authorities(ApplicationUserRole.NORMAL.getGrantedAuthorities())
                .build();

        UserDetails empresaUser = User.builder()
                .username("empresa")
                .password(passwordEncoder.encode("12345"))
//                .roles(ApplicationUserRole.EMPRESA.name()) // ROLES_NORMAL
                .authorities(ApplicationUserRole.EMPRESA.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(sysorUser, userUser, empresaUser);
    }
}
