package com.example.UserLoginSpring.securityConfig;//package com.example.securityConfig;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilderr auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/public/**").permitAll() // URLs públicas que não requerem autenticação
//                .antMatchers("/admin/**").hasRole("ADMIN") // URLs acessíveis apenas para usuários com papel ADMIN
//                .anyRequest().authenticated() // Todas as outras URLs exigem autenticação
//                .and()
//                .formLogin() // Configuração do formulário de login
//                .loginPage("/login") // URL da página de login personalizada
//                .defaultSuccessUrl("/home") // URL de redirecionamento após o login bem-sucedido
//                .and()
//                .logout() // Configuração do logout
//                .logoutUrl("/logout") // URL para o processo de logout
//                .logoutSuccessUrl("/login?logout") // URL de redirecionamento após o logout bem-sucedido
//                .and()
//                .csrf().disable(); // Desabilitar proteção CSRF (somente para exemplo, não recomendado em produção)
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//            .withUser("user").password("{noop}password").roles("USER") // Configuração de usuário em memória
//            .and()
//            .withUser("admin").password("{noop}password").roles("ADMIN"); // Configuração de admin em memória
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
