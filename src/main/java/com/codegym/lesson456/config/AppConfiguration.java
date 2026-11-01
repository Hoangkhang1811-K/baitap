package com.codegym.lesson456.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@Configuration @EnableWebMvc @EnableTransactionManagement @org.springframework.context.annotation.EnableAspectJAutoProxy
@ComponentScan("com.codegym.lesson456") @PropertySource("classpath:application.properties")
public class AppConfiguration implements WebMvcConfigurer {
    private final ApplicationContext context;
    @Value("${upload.directory}") private String uploadDirectory;
    public AppConfiguration(ApplicationContext context) { this.context = context; }
    @Bean public SpringResourceTemplateResolver templateResolver() {
        var r = new SpringResourceTemplateResolver(); r.setApplicationContext(context);
        r.setPrefix("/WEB-INF/views/"); r.setSuffix(".html"); r.setTemplateMode(TemplateMode.HTML);
        r.setCharacterEncoding("UTF-8"); return r;
    }
    @Bean public SpringTemplateEngine templateEngine() {
        var e = new SpringTemplateEngine(); e.setTemplateResolver(templateResolver()); return e;
    }
    @Bean public ThymeleafViewResolver viewResolver() {
        var r = new ThymeleafViewResolver(); r.setTemplateEngine(templateEngine());
        r.setCharacterEncoding("UTF-8"); return r;
    }
    @Bean public StandardServletMultipartResolver multipartResolver() { return new StandardServletMultipartResolver(); }
    @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
        try { Files.createDirectories(Path.of(uploadDirectory)); }
        catch (Exception e) { throw new IllegalStateException("Cannot create upload directory", e); }
        registry.addResourceHandler("/image/**").addResourceLocations(Path.of(uploadDirectory).toAbsolutePath().normalize().toUri().toString());
        Path musicDirectory = Path.of(uploadDirectory, "music").toAbsolutePath().normalize();
        try { Files.createDirectories(musicDirectory); }
        catch (Exception e) { throw new IllegalStateException("Cannot create music upload directory", e); }
        registry.addResourceHandler("/audio/**").addResourceLocations(musicDirectory.toUri().toString());
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
    }
    @Bean public DataSource dataSource(@Value("${db.driver}") String driver,
            @Value("${db.url}") String url,
            @Value("${db.username}") String username, @Value("${db.password}") String password) {
        var ds = new DriverManagerDataSource(); ds.setDriverClassName(driver);
        ds.setUrl(url); ds.setUsername(username); ds.setPassword(password); return ds;
    }
    @Bean public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        var f = new LocalContainerEntityManagerFactoryBean(); f.setDataSource(dataSource);
        f.setPackagesToScan("com.codegym.lesson456.model"); f.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties p = new Properties(); p.setProperty("hibernate.hbm2ddl.auto", "update");
        p.setProperty("hibernate.show_sql", "true"); p.setProperty("hibernate.format_sql", "true");
        f.setJpaProperties(p); return f;
    }
    @Bean public PlatformTransactionManager transactionManager(EntityManagerFactory emf) { return new JpaTransactionManager(emf); }
}
