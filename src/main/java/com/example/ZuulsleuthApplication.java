package com.example;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

@SpringBootApplication
@EnableZuulProxy
public class ZuulsleuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulsleuthApplication.class, args);
	}
	
	@Bean
	public MyFilter myFilter() {
		return new MyFilter();
	}
	
	public static class MyFilter extends OncePerRequestFilter implements Ordered {

		private Logger logger = LoggerFactory.getLogger(this.getClass());
		
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
			logger.info("BEFORE");
			try {
				filterChain.doFilter(request, response);
			}
			finally {
				logger.info("AFTER");
			}
		}

		@Override
		public int getOrder() {
			return Ordered.HIGHEST_PRECEDENCE + 6;	// just after Sleuth TraceFilter
		}

	}
}