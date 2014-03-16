package chanwook.cooker.sample.config;

import chanwook.cooker.support.mvc.CookerInitializingWebArgumentResolver;
import chanwook.cooker.support.mvc.CookieSynchronizationInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"chanwook.cooker"}, useDefaultFilters = false,
        includeFilters = {@ComponentScan.Filter(value = {Controller.class})})
public class WebContextConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebContextConfig.class);

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.TEXT_HTML);
        configurer.favorParameter(false);
        configurer.favorPathExtension(false);
        configurer.ignoreAcceptHeader(false);
    }

    @Bean
    public ViewResolver getCnvr() {
        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();

        // Setting to ViewResolver List
        ArrayList<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
        viewResolvers.add(getJspViewResolver());
        viewResolvers.add(new BeanNameViewResolver());
        viewResolver.setViewResolvers(viewResolvers);

        // Setting to Default View
        ArrayList<View> defaultViews = new ArrayList<View>();
        defaultViews.add(new MappingJackson2JsonView());
        viewResolver.setDefaultViews(defaultViews);

        return viewResolver;
    }

    //    @Bean
    public ViewResolver getJspViewResolver() {

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);

        return viewResolver;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new CookerInitializingWebArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        CookieSynchronizationInterceptor cookieSyncInterceptor = new CookieSynchronizationInterceptor();
        cookieSyncInterceptor.setDefaultEncoding("UTF-8");

        registry.addInterceptor(cookieSyncInterceptor);
    }
}
