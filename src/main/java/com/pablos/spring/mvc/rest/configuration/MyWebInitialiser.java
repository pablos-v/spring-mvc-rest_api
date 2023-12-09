package com.pablos.spring.mvc.rest.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyWebInitialiser extends AbstractAnnotationConfigDispatcherServletInitializer {
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MyConfig.class}; // вместо <param-value>/WEB-INF/applicationContext.xml
    }

    protected String[] getServletMappings() {
        return new String[]{"/"}; // вместо  <servlet-mapping> <url-pattern>/</url-pattern>
    }
}
