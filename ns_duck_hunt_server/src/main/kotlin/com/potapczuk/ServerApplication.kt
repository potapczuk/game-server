package com.potapczuk

import com.google.common.collect.Lists
import com.potapczuk.util.LoggingInterceptor
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@SpringBootApplication
class ServerApplication : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LoggingInterceptor()).addPathPatterns("/**")
    }

    @Bean
    fun docket(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(javaClass.getPackage().name))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(generateApiInfo())
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(listOf(apiKey()))
    }

    private fun generateApiInfo(): ApiInfo {
        return ApiInfo("Server Applicant Service", "This service ....",
                "Version 1.0 - mw", "urn:tos", Contact("potapczuk", "potapczuk.com", "contact@potapczuk.com"),
                "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", emptyList())
    }

    private fun apiKey(): ApiKey {
        return ApiKey("JWT", "Authorization", "header")
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build()
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return Lists.newArrayList(
                SecurityReference("JWT", authorizationScopes))
    }

    companion object {
        private const val DEFAULT_INCLUDE_PATTERN = "/v1/.*"

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ServerApplication::class.java, *args)
        }
    }
}