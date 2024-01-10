package com.authenticate.AuthenticationService;

import com.authenticate.AuthenticationService.filter.Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthenticationServiceApplication {

	public static void main(String[] args) {


		SpringApplication.run(AuthenticationServiceApplication.class, args);}
		@Bean
		public FilterRegistrationBean getFilter() {
			FilterRegistrationBean frb = new FilterRegistrationBean();
			frb.setFilter(new Filter());
			frb.addUrlPatterns("/bank/updateCustomerDetails/*");
			return frb;

		}
}
