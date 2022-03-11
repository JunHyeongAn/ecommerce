package com.ecommerce.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
	@Autowired
	Environment env;
	
	public AuthenticationFilter() {
		super(Config.class);
	}
	
	public static class Config {
		
	}

	
	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			
			if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
				return onError(exchange, "No authoriztion error", HttpStatus.UNAUTHORIZED);
			
			String authoriztionHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt = authoriztionHeader.replace("Bearer", "");
			
			if(!isJwtValid(jwt))
				return onError(exchange, "Jwt toke is not VALID", HttpStatus.UNAUTHORIZED);
			
			
			
			return chain.filter(exchange);
		});
	}


	private boolean isJwtValid(String jwt) {
		// subject가 비었는지 확인
		boolean returnValue = true;
		
		String subject = null;
		
		try {
			subject = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
					.parseClaimsJws(jwt).getBody()
					.getSubject();			
		} catch (Exception e) {
			returnValue = false;
		}
		
		if(subject == null || subject.isEmpty())
			returnValue = false;
		
		return returnValue;
	}

	// Mono, Flux -> Spring WebFlux 단일 값 > mono 여러값 > flux
	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		
		log.error(err);
		return response.setComplete();
	}
}
