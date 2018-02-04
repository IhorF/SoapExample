package com.soapexample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.ws.config.annotation.EnableWs;

/**
 * Provides the MultipartResolver beans necessary to use multipart requests/responses.
 *
 * @version 1.1.0
 * @since 1.1.0
 */
@Configuration
public class MultipartResolverConfig {

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}

	@Bean
	public CommonsMultipartResolver filterMultipartResolver() {
		return new CommonsMultipartResolver();
	}


}
