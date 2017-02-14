package com.rest.consumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rest.security.model.Person;

@SpringBootApplication
public class RestConsumerApplication {
	private final static org.slf4j.Logger log = LoggerFactory.getLogger(RestConsumerApplication.class);

	// private RestConsumerApplication() {}
	public static void main(String[] args) {
		SpringApplication.run(RestConsumerApplication.class, args);

		try {
			// Implemented CRUD operation
			postEntity();
			getEntity();
			deleteEntity();
			getEntity();
			updateEntity();
			getEntity();
			deleteEntityByRequestParam();
			getEntity();
		} catch (IOException e) {
			log.error("Error Occured while consuming REST API ", e);
		}

	}

	public static void getEntity() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		String getUrl = "http://localhost:8085/person/user/getPerson";
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));

		ResponseEntity<String> getResponse = restTemplate.exchange(getUrl, HttpMethod.GET, entity, String.class);

		if (getResponse.getBody() != null) {
			/*
			 * ObjectMapper mapper = new ObjectMapper(); List<Person> persons =
			 * new ArrayList<>(); try { persons =
			 * mapper.readValue(getResponse.getBody(),
			 * mapper.getTypeFactory().constructCollectionType(List.class,
			 * Person.class)); } catch (JsonParseException e) {
			 * log.error(e.getMessage()); } catch (JsonMappingException e) {
			 * log.error(e.getMessage()); } catch (IOException e) {
			 * log.error(e.getMessage()); }
			 */
			// persons.stream().map(p ->
			// p.getName()).forEach(System.out::println);
			log.info("Response for Get Request: " + getResponse.getBody());

		} else {
			log.info("Response for Get Request: NULL");
		}
	}

	public static void postEntity() throws JsonParseException, JsonMappingException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String getUrl = "http://localhost:8085/person/addPersons";
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));

		// Creating Dummy Data
		Person p1 = new Person();
		p1.setId("3");
		p1.setName("Test3");
		p1.setCity("Noida");

		Person p2 = new Person();
		p2.setId("4");
		p2.setName("Test4");
		p2.setCity("Delhi");
		List<Person> persons = new ArrayList<>();
		persons.add(p1);
		persons.add(p2);
		ObjectMapper mapper = new ObjectMapper();
		String value = mapper.writeValueAsString(persons);
		/*
		 * value = value.replace("[", ""); value = value.replace("]", "");
		 */

		HttpEntity<String> entity = new HttpEntity<String>(value, headers);

		ResponseEntity<String> getResponse = restTemplate.exchange(getUrl, HttpMethod.POST, entity, String.class);

		if (getResponse.getBody() != null) {
			log.info("Response for Get Request: " + getResponse.getBody());

		} else {
			log.info("Response for Get Request: NULL");
		}
	}

	public static void deleteEntity() throws JsonParseException, JsonMappingException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String getUrl = "http://localhost:8085/person/deletePerson/3";
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> getResponse = restTemplate.exchange(getUrl, HttpMethod.DELETE, entity, String.class);

		if (getResponse.getBody() != null) {
			log.info("Response for Get Request: " + getResponse.getBody());

		} else {
			log.info("Response for Get Request: NULL");
		}
	}

	public static void deleteEntityByRequestParam() throws JsonParseException, JsonMappingException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String getUrl = "http://localhost:8085/person/deletePersonByParam";
		// Query parameters
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getUrl)
				// Add query parameter
				.queryParam("id", "2");

		log.info("URI : " + builder.buildAndExpand().toUri().toString());

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));
		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<String> getResponse = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.DELETE,
				entity, String.class);

		if (getResponse.getBody() != null) {
			log.info("Response for delete of id 2 : " + getResponse.getBody());

		} else {
			log.info("Response for Get Request: NULL");
		}
	}

	public static void updateEntity() throws JsonParseException, JsonMappingException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String getUrl = "http://localhost:8085/person/updatePerson";
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));

		Person p2 = new Person();
		p2.setId("4");
		p2.setName("Test4 Update");
		p2.setCity("New Delhi");
		/*
		 * List<Person> persons = new ArrayList<>(); persons.add(p2);
		 */
		ObjectMapper mapper = new ObjectMapper();
		String value = mapper.writeValueAsString(p2);
		/*
		 * value = value.replace("[", ""); value = value.replace("]", "");
		 */

		HttpEntity<String> entity = new HttpEntity<String>(value, headers);

		ResponseEntity<String> getResponse = restTemplate.exchange(getUrl, HttpMethod.PUT, entity, String.class);

		if (getResponse.getBody() != null) {
			log.info("After Update Response for put Request: " + getResponse.getBody());

		} else {
			log.info("Response for Get Request: NULL");
		}
	}
}
