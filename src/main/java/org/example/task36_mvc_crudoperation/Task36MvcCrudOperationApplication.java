package org.example.task36_mvc_crudoperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Task36MvcCrudOperationApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

        return builder.sources(Task36MvcCrudOperationApplication.class);
    }

    public static void main(String[] args) {

        SpringApplication.run(Task36MvcCrudOperationApplication.class, args);
    }

}
