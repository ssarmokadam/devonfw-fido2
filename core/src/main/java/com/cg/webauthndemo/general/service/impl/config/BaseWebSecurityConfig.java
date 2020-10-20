package com.cg.webauthndemo.general.service.impl.config;

import java.util.Arrays;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.webauthn4j.WebAuthnManager;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.springframework.security.WebAuthnAuthenticationProvider;
import com.webauthn4j.springframework.security.WebAuthnProcessingFilter;
import com.webauthn4j.springframework.security.WebAuthnRegistrationRequestValidator;
import com.webauthn4j.springframework.security.authenticator.WebAuthnAuthenticatorManager;
import com.webauthn4j.springframework.security.authenticator.WebAuthnAuthenticatorService;
import com.webauthn4j.springframework.security.challenge.ChallengeRepository;
import com.webauthn4j.springframework.security.config.configurers.WebAuthnAuthenticationProviderConfigurer;
import com.webauthn4j.springframework.security.config.configurers.WebAuthnLoginConfigurer;
import com.webauthn4j.springframework.security.options.AssertionOptionsProvider;
import com.webauthn4j.springframework.security.options.AttestationOptionsProvider;
import com.webauthn4j.springframework.security.server.ServerPropertyProvider;

/**
 * Base Web Security class.
 *
 */
public abstract class BaseWebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Inject
  private AuthenticationSuccessHandler authenticationSuccessHandler;

  @Inject
  private AuthenticationFailureHandler authenticationFailureHandler;

  @Inject
  private ObjectConverter objectConverter;

  @Inject
  private WebAuthnAuthenticatorService webAuthnAuthenticatorService;

  @Inject
  private WebAuthnManager webAuthnManager;

  @Inject
  private UserDetailsService userDetailsService;

  @Inject
  private PasswordEncoder passwordEncoder;

  @Inject
  private AuthenticationEntryPoint authenticationEntryPoint;

  @Inject
  private WebAuthnAuthenticationProvider webAuthnAuthenticationProvider;

  @Inject
  private AttestationOptionsProvider attestationOptionsProvider;

  @Inject
  private ChallengeRepository challengeRepository;

  @Inject
  private WebAuthnRegistrationRequestValidator webAuthnRegistrationRequestValidator;

  @Inject
  private AssertionOptionsProvider assertionOptionsProvider;

  @Inject
  private WebAuthnAuthenticatorManager webAuthnAuthenticatorManager;

  @Inject
  private ServerPropertyProvider serverPropertyProvider;

  @Override
  public AuthenticationManager authenticationManager() throws Exception {

    return new ProviderManager(Arrays.asList((AuthenticationProvider) this.webAuthnAuthenticationProvider));
  }

  @Bean
  public WebAuthnProcessingFilter webAuthnProcessingFilter() {

    WebAuthnProcessingFilter webAuthnProcessingFilter = new WebAuthnProcessingFilter();
    webAuthnProcessingFilter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
    webAuthnProcessingFilter.setAuthenticationFailureHandler(this.authenticationFailureHandler);
    webAuthnProcessingFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("*"));
    try {
      webAuthnProcessingFilter.setAuthenticationManager(authenticationManager());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return webAuthnProcessingFilter;
  }

  private CorsFilter getCorsFilter() {

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("OPTIONS");
    config.addAllowedMethod("HEAD");
    config.addAllowedMethod("GET");
    config.addAllowedMethod("PUT");
    config.addAllowedMethod("POST");
    config.addAllowedMethod("DELETE");
    config.addAllowedMethod("PATCH");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.apply(new WebAuthnAuthenticationProviderConfigurer<>(this.webAuthnAuthenticatorService, this.webAuthnManager));
    auth.userDetailsService(this.userDetailsService);
    // auth.authenticationProvider(this.webAuthnAuthenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // WebAuthn Login

    http.apply(WebAuthnLoginConfigurer.webAuthnLogin()).and().csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.GET, "/login").permitAll().antMatchers(HttpMethod.GET, "/signup").permitAll()
        .antMatchers(HttpMethod.POST, "/signup").permitAll().antMatchers(HttpMethod.POST, "/login").permitAll()
        .anyRequest().authenticated().and().addFilterBefore(webAuthnProcessingFilter(), WebAuthnProcessingFilter.class);

  }

}
