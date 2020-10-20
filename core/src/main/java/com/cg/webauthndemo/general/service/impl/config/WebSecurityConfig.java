package com.cg.webauthndemo.general.service.impl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.devonfw.module.basic.common.api.config.SpringProfileConstants;

/**
 * @author snesarmo
 *
 */
@Configuration
@EnableWebSecurity(debug = true)
@Profile(SpringProfileConstants.NOT_JUNIT)
public class WebSecurityConfig extends BaseWebSecurityConfig {
}
