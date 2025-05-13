package org.silsagusi.api.common.config;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
	@Bean
	public Module jtsModule() {
		return new JtsModule();
	}
}
