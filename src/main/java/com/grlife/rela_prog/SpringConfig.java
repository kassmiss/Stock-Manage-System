package com.grlife.rela_prog;

import com.grlife.rela_prog.repositiory.ApiRepository;
import com.grlife.rela_prog.repositiory.ApiRepositoryImpl;
import com.grlife.rela_prog.repositiory.RelaRepository;
import com.grlife.rela_prog.repositiory.RelaRepositoryImpl;
import com.grlife.rela_prog.service.ApiService;
import com.grlife.rela_prog.service.RelaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public RelaService relaService() {
        return new RelaService(relaRepository());
    }

    @Bean
    public RelaRepository relaRepository() {
        return new RelaRepositoryImpl(dataSource);
    }

    @Bean
    public ApiService apiService() {
        return new ApiService(apiRepository());
    }

    @Bean
    public ApiRepository apiRepository() {
        return new ApiRepositoryImpl(dataSource);
    }

}