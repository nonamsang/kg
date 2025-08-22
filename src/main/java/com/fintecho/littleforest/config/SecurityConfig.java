package com.fintecho.littleforest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.fintecho.littleforest.service.admin.AdminUserService;

@Configuration
public class SecurityConfig {

	/** 🔑 비밀번호 암호화기 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Order(1)
	@Profile("dev")
	public SecurityFilterChain devAdminChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/admin/**").authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				.csrf(csrf -> csrf.disable());
		return http.build();
	}

	@Bean
	@Order(1)
	@Profile("prod")
	public SecurityFilterChain adminChain(HttpSecurity http, AdminUserService adminUserService) throws Exception {
		http.securityMatcher("/admin/**")
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/admin/admin-login", "/admin/css/**", "/admin/js/**", "/admin/img/**")
						.permitAll().anyRequest().hasRole("ADMIN"))
				.formLogin(form -> form.loginPage("/admin/admin-login").loginProcessingUrl("/admin/admin-login")
						.usernameParameter("username").passwordParameter("password")
						.successHandler((req, res, auth) -> {
							var cache = new HttpSessionRequestCache();
							SavedRequest saved = cache.getRequest(req, res);
							String target = (saved != null) ? saved.getRedirectUrl() : "/admin/members";
							new DefaultRedirectStrategy().sendRedirect(req, res, target);
						}).failureUrl("/admin/admin-login?error").permitAll())
				.logout(l -> l.logoutUrl("/admin/logout").logoutSuccessUrl("/admin/admin-login")
						.invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID"))
				.userDetailsService(adminUserService).headers(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain appChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/tree", "/attendance/**", "/css/**", "/js/**", "/img/**", "/favicon.ico")
				.permitAll().anyRequest().permitAll()).formLogin(form -> form.disable())
				.httpBasic(basic -> basic.disable()).csrf(csrf -> csrf.disable());
		return http.build();
	}

	@Bean
	public HttpFirewall allowUrlEncodedPercentHttpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowUrlEncodedPercent(true);
		firewall.setAllowSemicolon(true);
		firewall.setAllowUrlEncodedSlash(true);
		return firewall;
	}
}
