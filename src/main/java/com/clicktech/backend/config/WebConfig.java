package com.clicktech.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Type;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter customJacksonConverter = new MappingJackson2HttpMessageConverter() {
            @Override
            public boolean canRead(Class<?> clazz, MediaType mediaType) {
                if (mediaType != null && mediaType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
                    return true;
                }
                return super.canRead(clazz, mediaType);
            }

            @Override
            public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
                if (mediaType != null && mediaType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
                    return true;
                }
                return super.canRead(type, contextClass, mediaType);
            }

            @Override
            public boolean canWrite(Class<?> clazz, MediaType mediaType) {
                if (mediaType != null && mediaType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
                    return true;
                }
                return super.canWrite(clazz, mediaType);
            }
        };

        converters.add(0, customJacksonConverter);
    }
}
