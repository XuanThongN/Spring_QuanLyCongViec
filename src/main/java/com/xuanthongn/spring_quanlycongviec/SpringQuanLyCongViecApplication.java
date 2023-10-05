package com.xuanthongn.spring_quanlycongviec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@SpringBootApplication
public class SpringQuanLyCongViecApplication implements WebMvcConfigurer {

    private final LocaleChangeInterceptor localeChangeInterceptor;
    public SpringQuanLyCongViecApplication(LocaleChangeInterceptor localeChangeInterceptor) {
        this.localeChangeInterceptor = localeChangeInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(localeChangeInterceptor);
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringQuanLyCongViecApplication.class, args);
    }

}
