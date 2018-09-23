/**
 * 
 */
package com.crossover.techtrial.controller;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;

/**
 * @author kshah
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {
  
  MockMvc mockMvc;
  
  @Mock
  private PersonController personController;
  
  @Autowired
  private TestRestTemplate template;
  
  @Autowired
  PersonRepository personRepository;
  
  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
  }
  
  @Test
  public void testPanelShouldBeRegistered() throws Exception {
    HttpEntity<Object> person = getHttpEntity(
        "{\"name\": \"test 1\", \"email\": \"test10000000000001@gmail.com\"," 
            + " \"registrationNumber\": \"41DCT\",\"registrationDate\":\"2018-08-08T12:12:12\" }");
    ResponseEntity<Person> response = template.postForEntity(
        "/api/person", person, Person.class);
    //Delete this user
    personRepository.deleteById(response.getBody().getId());
    Assert.assertEquals("test 1", response.getBody().getName());
    Assert.assertEquals(200,response.getStatusCode().value());
  }
  
  
  @Test
  public void testPersonDriverShouldBeRegistered() throws Exception {
    HttpEntity<Object> person = getHttpEntity(
        "{\"name\": \"test 1\", \"email\": \"test10000000000001@gmail.com\"," 
            + " \"registrationNumber\": \"41DCT\",\"registrationDate\":\"2018-08-08T12:12:12\",\"isdriver\":1 }");
    ResponseEntity<Person> response = template.postForEntity(
        "/api/person", person, Person.class);
    //Delete this user
    personRepository.deleteById(response.getBody().getId());
    Assert.assertEquals("test 1", response.getBody().getName());
    Assert.assertEquals(200,response.getStatusCode().value());
    Assert.assertEquals(1,response.getBody().getIsdriver());
  }
  
  @Test
  public void testPanelAllPersonShouldLoad() throws Exception {
   
	  //Load All User
    ResponseEntity<List> response = template.getForEntity(
        "/api/person", List.class);
     
    //assert weather call is successful
    Assert.assertTrue(response.getBody().size() > 0);
    Assert.assertEquals(200,response.getStatusCode().value());
  }
  
  
  @Test
  public void testIdLoadIsOk() throws Exception {
   
	  //Assuming here Id:1 is the test person to be loaded
    ResponseEntity<Person> response = template.getForEntity(
        "/api/person/1", Person.class);
    
    //Checking weather the call was successful or not
    Assert.assertNotNull(response.getBody());
    
  }


  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }

}
