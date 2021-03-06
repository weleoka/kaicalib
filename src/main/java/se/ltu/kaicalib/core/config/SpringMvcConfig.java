/**
 * Configuring the MVC
 *
 * todo A lot of things to do with caching and compressing and serving resources in an effective way!
 */
package se.ltu.kaicalib.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import se.ltu.kaicalib.core.conversion.DateFormatter;
import se.ltu.kaicalib.core.utils.VisitorInterceptor;


/**
 * Custom routes for resoures
 *
 * The resource handlers are created with the intent of improving
 * human readability and maintainability work.
 *
 * Configurations concerning the MVC such as MessageSource is
 * defined.
 *
 * Also formatters used are defined here.
 *
 * A handler for intercepting visitor sessions is introduced. This can be used to get a visitor counter
 * or other things like that. Also useful for debug occasionally.
 *
 * There is one special mapping for favicon which prevents 404 as different
 * user agents will request favicon independently of HTTP instruction.
 *
 *
 * @author
 */
@Configuration
@EnableWebMvc
@ComponentScan
//@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:datasources.yml")
public class SpringMvcConfig implements WebMvcConfigurer {



    /*
     *  Dispatcher configuration for serving static resources
     *  todo find a way of configuring these routes in another way? Low priority.
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {

        // todo these first resource handlers may not be needed.
        //registry.addResourceHandler("/favicon.ico").addResourceLocations("/static/images/favicon.png");
        registry.addResourceHandler("/stylesheet.css").addResourceLocations("/static/css/bootstrap.min.css");


        registry.addResourceHandler("/images/**").addResourceLocations("/static/images/");
        registry.addResourceHandler("/css/**").addResourceLocations("/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/static/js/");

        registry.addResourceHandler("/admin/images/**").addResourceLocations("/static/images/");
        registry.addResourceHandler("/admin/css/**").addResourceLocations("/static/css/");
        registry.addResourceHandler("/admin/js/**").addResourceLocations("/static/js/");

        registry.addResourceHandler("/patron/images/**").addResourceLocations("/static/images/");
        registry.addResourceHandler("/patron/css/**").addResourceLocations("/static/css/");
        registry.addResourceHandler("/patron/js/**").addResourceLocations("/static/js/");
    }




    /*
     *  Message externalization/internationalization
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18/messages-en");
        return messageSource;
    }




    /*
     * Add formatter for {@link java.util.Date} in addition to the one registered by default
     */
    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addFormatter(dateFormatter());
    }

    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter();
    }





    /**
     * Custom utility: visitor counter
     *
     * @param registry
     */
    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(new VisitorInterceptor());
    }




    /**
     * Handles favicon.ico requests
     */
    @Controller
    static class FaviconController {
        @RequestMapping("favicon.*")
        String favicon() {
            return "forward:/images/favicon.png";
        }
    }
}
