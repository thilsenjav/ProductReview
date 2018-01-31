package com.demo.product.review.integration;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="classpath:com.demo.product.review.integration",tags= {"@registration"},plugin= {"pretty"})
public class IntegrationTests {
//Test commit
}
