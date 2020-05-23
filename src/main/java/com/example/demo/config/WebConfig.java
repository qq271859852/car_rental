package com.example.demo.config;

import com.example.demo.interceptor.LoginStatusChecker;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.LocalDate;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring mvc web configuration
 * @author zhouxiong
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginStatusChecker())
            .addPathPatterns("/**")
            .excludePathPatterns("/login")
            .excludePathPatterns("/car/model")
            .excludePathPatterns("/cars");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateConvert());
    }

    private static class LocalDateConvert implements Converter<String, LocalDate> {

        @Override
        public LocalDate convert(String source) {
            if (!NumberUtils.isDigits(source)) {
                return null;
            }
            long timestamp = NumberUtils.toLong(source);
            return new LocalDate(timestamp);
        }
    }

}
