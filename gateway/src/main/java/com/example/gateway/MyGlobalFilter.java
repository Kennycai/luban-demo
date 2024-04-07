package com.example.gateway;

import com.example.commonutils.JsonUtils;
import com.example.commonutils.dto.AuthConst;
import com.example.commonutils.dto.UserRoleInfo;
import com.example.commonutils.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Value("${jwt.key}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        if (request.getURI().getPath().contains("/login") || request.getURI().getPath().startsWith("/product/home")) {
            return chain.filter(exchange);
        }
        UserRoleInfo userRoleInfo = JwtUtils.getUser(secretKey, request);
        if (userRoleInfo == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        ServerHttpRequest mutatedRequest = request.mutate().header(AuthConst.HEADER_AUTH, JsonUtils.toJson(userRoleInfo)).build();
        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
