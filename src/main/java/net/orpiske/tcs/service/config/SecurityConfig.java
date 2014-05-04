/**
 Copyright 2014 Otavio Rodolfo Piske

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package net.orpiske.tcs.service.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = Logger.getLogger(SecurityConfig.class);

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String user = System.getenv("TCS_USER");
        String password = System.getenv("TCS_PASSWORD");

        if (user == null) {
            logger.fatal("The system username is not provided");
            System.exit(-1);
        }

        if (password == null) {
            logger.fatal("The system password is not provided");
            System.exit(-1);
        }

        auth.inMemoryAuthentication()
                .withUser(user).password(password).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * Disabling CSRF because ... well ... because ... f**** you. I know it's good
         * but I need to research more about it. For now, I just want to get this site
         * up an running.
         *
         * Ref.:
         * http://spring.io/blog/2013/08/21/spring-security-3-2-0-rc1-highlights-csrf-protection/
         */
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/domain/**")
                    .hasRole("USER")
                    .and()
                .httpBasic();

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/domain/**").permitAll();

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/references/**")
                    .hasRole("USER")
                    .and()
                .httpBasic();

        http.authorizeRequests()
                .antMatchers("/tagcloud/**", "/tagcloud/domain/**").permitAll();
    }
}
