package com.carrotins.apigateway.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtRequestFilter : AbstractGatewayFilterFactory<JwtRequestFilter.Config>(Config::class.java) {

    class Config

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val token = exchange.request.headers["Authorization"]
            if (token.isNullOrEmpty()) {
                return@GatewayFilter exchange.handleUnauthorized()
            }
            // token 검증 로직 추가
            return@GatewayFilter chain.filter(exchange)
        }
    }

    private fun ServerWebExchange.handleUnauthorized(): Mono<Void> {
        this.response.statusCode = HttpStatus.UNAUTHORIZED
        return this.response.setComplete()
    }
}
