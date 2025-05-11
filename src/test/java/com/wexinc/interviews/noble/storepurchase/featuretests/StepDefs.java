package com.wexinc.interviews.noble.storepurchase.featuretests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wexinc.interviews.noble.storepurchase.dto.StorePurchaseDto;
import com.wexinc.interviews.noble.storepurchase.repository.StorePurchaseRepository;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.val;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class StepDefs extends SpringIntegrationTest {

    
    private static Response response;
    ObjectMapper objectMapper = new ObjectMapper();
    
    @Autowired
    StorePurchaseRepository storePurchaseRepository;
    
    @LocalServerPort
    private int port;
    
    @Before
    public void SetUp() {
    	RestAssured.port = port;
    }
    
    @Given("I have made a store purchase with the Id {long}")
    public void I_have_made_a_store_purchase_with_the_Id(Long id) {
    	val existingItem = storePurchaseRepository.findById(id);
    	assertTrue(existingItem.isPresent());
    }
    
    
    
    @When("I create a new store purchase with the payload")
    public void i_create_a_new_store_purchase_with_the_payload(String payload) {
    	RequestSpecification request = RestAssured.given();
    	request.header("Content-Type", "application/json");
		response = request.body(payload).post("/storepurchase");
    }
    
    @When("I retrieve a store purchase with the Id {long} and the country currency {string}")
    public void I_retrieve_a_store_purchase_with_the_Id(Long id, String countryCurrencyDesc) {
    	RequestSpecification request = RestAssured.given();
		response = request.get("/storepurchase/" + id.toString() + "?countryCurrencyDesc=" + countryCurrencyDesc );
    }
    
    @Then("I receive the response")
    public void i_receive_the_response(String expectedResponse) throws JsonMappingException, JsonProcessingException {
    	StorePurchaseDto expectedDto = objectMapper.readValue(expectedResponse, StorePurchaseDto.class);
    	StorePurchaseDto actualDto = response.as(StorePurchaseDto.class);
    	assertEquals(expectedDto.getDescription(), actualDto.getDescription());
    	assertTrue(expectedDto.getPurchaseAmount().compareTo(actualDto.getPurchaseAmount()) == 0);
    	assertEquals(expectedDto.getTransactionDate(), actualDto.getTransactionDate());
    	if(!StringUtils.isEmpty(expectedDto.getCountryCurrencyDesc())) {
    		assertEquals(expectedDto.getCountryCurrencyDesc(), actualDto.getCountryCurrencyDesc());
        	assertEquals(expectedDto.getExchangeRate(), actualDto.getExchangeRate());
        	assertTrue(expectedDto.getExchangePurchaseAmount().compareTo(actualDto.getExchangePurchaseAmount()) == 0);
    	}
    	
    }
}
