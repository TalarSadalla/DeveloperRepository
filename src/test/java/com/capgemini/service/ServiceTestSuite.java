package com.capgemini.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@SuiteClasses({ApartmentServiceTest.class,
        BuildingServiceTest.class,
        ClientServiceTest.class})
public class ServiceTestSuite {

}
