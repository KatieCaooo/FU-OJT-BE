package ojt.management.data.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@PropertySource({"application.properties"})
@EnableJpaRepositories(
        basePackages = "ojt.management.data.repositories",
        entityManagerFactoryRef = "dataSourceEntityManager",
        transactionManagerRef = "dataSourceTransactionManager"
)
public class DataSourceConfig extends DataSourceBaseConfig {

    public DataSourceConfig(@Value("${spring.db.datasource.dialect}") String dialect,
                            @Value("${spring.db.datasource.dll-auto}") String dllMethod,
                            @Value("${spring.db.datasource.driver}") String driver,
                            @Value("${spring.db.datasource.show-sql}") String showSql,
                            @Value("${spring.db.datasource.package}") String basePackage,
                            @Value("sourceUnit") String sourceUnit) {
        super(dialect, dllMethod, driver, showSql, basePackage, sourceUnit);
    }

    @Bean(name = "datasource")
    @ConfigurationProperties(prefix = "spring.db.datasource")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataSourceEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean dataSourceEntityManager(
            final EntityManagerFactoryBuilder builder,
            @Qualifier("datasource") final DataSource dataSource) {
        // dynamically setting up the hibernate properties for each of the datasource.
        return buildEntityManagerFactory(builder, dataSource);
    }

    @Bean(name = "dataSourceTransactionManager")
    @Primary
    public PlatformTransactionManager dbEntityMgrFactory(
            @Qualifier("dataSourceEntityManager") final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
