package com.kurvits.personsectors.integTest;

import com.kurvits.personsectors.PeopleSectorsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = PeopleSectorsApplication.class)
@ActiveProfiles("test")
@Testcontainers
@ContextConfiguration(initializers = BasePersonSectorsAppTest.Initializer.class)
@AutoConfigureMockMvc
public class BasePersonSectorsAppTest {
    private static final PostgreSQLContainer<?> postgresContainer;

    static {
        postgresContainer = new PostgreSQLContainer<>("postgres:14.2")
                .withDatabaseName("person-sectors")
                .withUsername("helmes")
                .withPassword("helmes")
                .withExposedPorts(5432);
        if (!postgresContainer.isRunning()) postgresContainer.start();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresContainer.getUsername(),
                    "spring.datasource.password=" + postgresContainer.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }

    @Test
    void contextLoads() {}
}
