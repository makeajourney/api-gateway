package com.carrotins.apigateway

import com.carrotins.apigateway.filter.JwtRequestFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class DisApiRoutingConfiguration constructor(
    @Value("\${dis.url}")
    private val url: String,
){

    @Bean
    fun disRouteLocator(builder: RouteLocatorBuilder, jwtRequestFilter: JwtRequestFilter) = builder.routes {
        route(id = "dis") {
            path("$API_PREFIX/**")
            filters {
                filter(jwtRequestFilter.apply(JwtRequestFilter.Config()))
                stripPrefix(PREFIX_SIZE)
            }
            uri(url)
        }
    }
    companion object {
        private const val API_PREFIX = "/dis"
        private const val PREFIX_SIZE = 1
    }
}
