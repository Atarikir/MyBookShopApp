package com.example;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest()
@ActiveProfiles("test")
//@TestPropertySource(locations = {"classpath:application-test.yml"})
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
//    DataSourceTransactionManagerAutoConfiguration.class,
//    HibernateJpaAutoConfiguration.class, LiquibaseAutoConfiguration.class})
public class BaseTest {

}
