package se.ltu.kaicalib.account.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SecurityConfig(
        @Qualifier("UserDetailsService")
        UserDetailsService userDetailsService,
        AccessDeniedHandler accessDeniedHandler,
        @Qualifier("accountsDataSource")
        DataSource dataSource)
    {
        this.userDetailsService = userDetailsService;
        this.accessDeniedHandler = accessDeniedHandler;
        this.dataSource = dataSource;
    }

    private UserDetailsService userDetailsService;

    private final AccessDeniedHandler accessDeniedHandler;

    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Patterns are always evaluated in the order they are defined.
        // Thus it is important that more specific patterns are defined higher in the list than less specific patterns.
        // https://stackoverflow.com/questions/41480102/how-spring-security-filter-chain-works?rq=1
        //
        // todo When to use AntPathRequestMatcher or not.
        http
            .csrf() // Cross Site Request Forgery enabled
            .disable() // now a fresh AbstractHttpConfigurer is used

            .authorizeRequests() // Routes and resources for all
            .antMatchers(
                "/console/**", // todo Disable for production.
                "/login/auto",               // todo Disable for production.
                "/login/admin/auto",         // todo Disable for production.

                "/librarian_home/**",

                "/test",
                "/signup",
                "/logout",

                "/",
                "/favicon.*",
                "/js/**",
                "/css/**",
                "/images/**",

                "/search",
                "/search_results",
                "/search_selection"
            )
            .permitAll()
            .anyRequest().authenticated()

            .and()
            .formLogin()
            .loginPage("/login")
            .failureUrl("/login?error=true")
            .loginProcessingUrl("/authenticate") // The login form POST action route (to disguise Spring Security).
            .defaultSuccessUrl("/patron/profile")
            .permitAll()

            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout=true")
            .permitAll()

            .and()
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler)

/*            .and()
            .rememberMe()
            .rememberMeServices(rememberMeServices())
            .key("remember-me-key")*/

            .and() // todo Disable for production.
            .headers().frameOptions().disable(); // To allow H2 console.
    }


    /**
     * Authentication details
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        final String USERS_QUERY = "select username, email, password, active from user where username=?";
        final String ROLES_QUERY = "select u.email, r.role from user u inner join user_roles ur on (u.id = ur.user_id) inner join role r on (ur.role_id=r.role_id) where u.username=?";

        auth
            .eraseCredentials(true)
            //.jdbcAuthentication()
            .userDetailsService(userDetailsService) // Can be jdbcAuth or this type using DAO's.
            //.usersByUsernameQuery(USERS_QUERY)
            //.authoritiesByUsernameQuery(ROLES_QUERY)
            //.dataSource(dataSource)
            .passwordEncoder(passwordEncoder());

/*        // In memory authentication. DISABLED. This could be for auto login of universal root user for example.
        auth.inMemoryAuthentication()
            .withUser(adminUsername).password(adminPassword).roles("ADMIN");*/
    }


    // Present as bean but is called interanlly anyhow.
    @Bean(name = "authenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




/*    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices("remember-me-key", userDetailsService);
    }*/


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}




    /*
    // Not used but here for reference.
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
            .jdbcAuthentication()
            .dataSource(accountsDataSource)
            .usersByUsernameQuery(USERS_QUERY)
            .authoritiesByUsernameQuery(ROLES_QUERY)
            //.groupAuthoritiesByUsername(ROLES_BY_USERNAME_QUERY)
            .passwordEncoder(bCryptPasswordEncoder);
    }*/


    /*
    // Not used but here for reference.
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
                .authenticated()
            .and()
                .csrf().disable()
                .formLogin().loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/patron/profile")
                .usernameParameter("username")
                .passwordParameter("password")
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
            .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60*60)
            .and()
                .exceptionHandling().accessDeniedPage("/access_denied");
            .and()
            .authorizeRequests()
                .antMatchers("/admin/**")
                .hasAuthority("ADMIN").anyRequest().authenticated()

            .and()
            .authorizeRequests()
                .antMatchers("/patron/**")
                .hasAuthority("PATRON").anyRequest().authenticated()
    }
*/




