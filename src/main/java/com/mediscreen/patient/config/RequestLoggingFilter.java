package com.mediscreen.patient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
/**
 * This Class is used to define a bean of type CommonsRequestLoggingFilter to enable request logging for all the endpoints
 */
public class RequestLoggingFilter {
    @Bean
    /**
     * Define logFilter attributes
     */
    public CommonsRequestLoggingFilter logFilter()
    {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeClientInfo(true);
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);

        return filter;
    }
}
