package ojt.management.data.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceBaseConfig {
    private static final String HBM2DDL_AUTO_ENV = "hibernate.hbm2ddl.auto";
    private static final String DIALECT_ENV = "hibernate.dialect";
    private static final String DRIVER_ENV = "hibernate.connection.driver_class";
    private static final String SHOW_SQL_ENV = "hibernate.show_sql";
    private String dialect;
    private String dllMethod;
    private String driver;
    private String showSql;
    private String basePackage;
    private String sourceUnit;

    private Map<String, String> getProperties() {
        final Map<String, String> properties = new HashMap<>();
        properties.put(HBM2DDL_AUTO_ENV, getDllMethod());
        properties.put(DIALECT_ENV, getDialect());
        properties.put(DRIVER_ENV, getDriver());
        properties.put(SHOW_SQL_ENV, getShowSql());
        return properties;
    }

    protected LocalContainerEntityManagerFactoryBean
    buildEntityManagerFactory(EntityManagerFactoryBuilder builder,
                              DataSource dataSource) {
        final Map<String, String> properties = getProperties();
        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages(getBasePackage())
                .persistenceUnit(getSourceUnit())
                .build();
    }


}
