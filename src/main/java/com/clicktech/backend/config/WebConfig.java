package com.clicktech.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter jacksonConverter) {
                List<MediaType> supportedMediaTypes = new ArrayList<>(jacksonConverter.getSupportedMediaTypes());
                supportedMediaTypes.add(new MediaType("application", "json", StandardCharsets.UTF_8));
                supportedMediaTypes.add(new MediaType("application", "json", Collections.singletonMap("charset", "*")));
                supportedMediaTypes.add(MediaType.valueOf("application/json;charset=UTF-8"));
                supportedMediaTypes.add(MediaType.ALL);
                jacksonConverter.setSupportedMediaTypes(supportedMediaTypes);
            }
        }
    }
}
