package com.project.user.oauth2;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

	@Value("${security.oauth2.client.id}")
	private String CLIENT_ID;

	@Value("${security.oauth2.client.client-secret}")
	private String CLIENT_SECRET;

	@Value("${security.oauth2.client.resource-ids}")
	private Collection<String> RESOURCE_IDS;

	@Value("${security.oauth2.client.access-token-uri}")
	private String ACCESS_TOKEN_URI;


	@Bean
	public RemoteTokenServices remoteTokenService() {
		RemoteTokenServices remoteTokenService = new RemoteTokenServices();
		remoteTokenService.setCheckTokenEndpointUrl(this.ACCESS_TOKEN_URI);
		remoteTokenService.setClientId(this.CLIENT_ID);
		remoteTokenService.setClientSecret(this.CLIENT_SECRET);
		return remoteTokenService;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		this.RESOURCE_IDS.stream().forEach(
				(resourceId) -> resources.resourceId(resourceId));
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/swagger-ui.html").permitAll()
		.antMatchers("/swagger-resources/**").permitAll()
		.antMatchers("/webjars/**").permitAll()
		.antMatchers("/v2/api-docs").permitAll()
		.antMatchers("/health").permitAll()
		.antMatchers("/actuator/**").permitAll()
		.anyRequest().authenticated();
	}

}
