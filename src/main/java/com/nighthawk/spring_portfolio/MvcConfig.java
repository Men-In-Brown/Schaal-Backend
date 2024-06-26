package com.nighthawk.spring_portfolio;

import org.springframework.lang.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // set up your own index
    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    /* map path and location for "uploads" outside of application resources
       ... creates a directory outside "static" folder, "file:volumes/uploads"
       ... CRITICAL, without this uploaded file will not be loaded/displayed by frontend
     */
    @Override
    public void addResourceHandlers(final @NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/volumes/uploads/**").addResourceLocations("file:volumes/uploads/");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOrigins("https://men-in-brown.github.io", "http://127.0.0.1:4100", "http://localhost:8091")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
    
}