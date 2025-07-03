// package com.roboholic.roboholicweb.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeHttpRequests(auth -> auth
//                 // .requestMatchers("/", "/login", "/css/**").permitAll()
//                 .requestMatchers("/", "/login", "/css/**", "/js/**", "/stylesheet/**", "/images/**").permitAll()
//                 .anyRequest().authenticated()
//             )
//             .formLogin(form -> form
//                 .loginPage("/login")
//                 // .defaultSuccessUrl("/admin/dashboard")
//                 .defaultSuccessUrl("/index")
//                 .permitAll()
//             )
//             .logout(logout -> logout
//                 .logoutSuccessUrl("/login?logout")
//                 .permitAll()
//             );
//         return http.build();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }

package com.roboholic.roboholicweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Public resources
                .requestMatchers("/", "/login", "/css/**", "/js/**", "/stylesheet/**", "/images/**").permitAll()
                // Public product views
                .requestMatchers("/listingUserView", "/listingUserView/searchname", "/listingUserView/searchprice").permitAll()
                .requestMatchers("/listingUserView/{id}/viewItem").permitAll()
                // Public FAQ view
                .requestMatchers("/faqUserView", "/faqUserView/search").permitAll()
                // Public Resource view
                .requestMatchers("/resourcelistingUserView", "/resourcelistingUserView/searchresource", "resourcelistingUserView/{id}/viewResource").permitAll()
                // Admin-only paths
                .requestMatchers("/addnewproduct", "/listing", "/listing/{id}/viewItem").hasRole("ADMIN")
                .requestMatchers("/listing/{id}/edit", "/listing/{id}/delete").hasRole("ADMIN")
                .requestMatchers("/listing/searchname", "/listing/searchprice").hasRole("ADMIN")
                // Admin-only FAQ paths
                .requestMatchers("/faq","/faq/search", "/faq/add", "/faq/{id}/edit", "/faq/{id}/delete").hasRole("ADMIN")
                // Admin-only Resource paths
                .requestMatchers("/resourcelisting", "/addnewresources", 
                                 "/resourcelisting/{id}/update", "/resourcelisting/{id}/delete",
                                 "/resourcelisting/searchresource", "/resourcelisting/{id}/viewResource").hasRole("ADMIN")
                // Admin-only Admin Dashboard
                .requestMatchers("/admin/dashboard").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/admin/dashboard")
                .permitAll()
            )
            // .logout(logout -> logout
            //     .logoutSuccessUrl("/login?index")
            //     .permitAll()
            // );
            .logout(logout -> logout
                .logoutUrl("/logout")  // The URL that triggers logout
                .logoutSuccessUrl("/login?logout")  // Where to redirect after logout
                .invalidateHttpSession(true)  // Invalidate session
                .clearAuthentication(true)    // Clear authentication
                .deleteCookies("JSESSIONID")  // Delete session cookie
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}