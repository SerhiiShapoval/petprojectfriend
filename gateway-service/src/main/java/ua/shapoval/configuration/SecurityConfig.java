package ua.shapoval.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfig {


    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity httpSecurity){

        return httpSecurity
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/api/v1/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer().jwt()
                .and()
                .and()
                .build();

    }

}
