package com.rabo;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


public class TestStatementProcessor extends RaboCustomerStatementProcessorApplicationTests{

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testEmployee() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/validateStatement").accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(post("/validateStatement")).andExpect(status().isOk())
				.andExpect((ResultMatcher) content().contentType("application/json"))
				.andExpect((ResultMatcher) jsonPath("$.Reference").value(194261)).andExpect((ResultMatcher) jsonPath("$.AccountNumber").value("NL91RABO0315273637"))
				.andExpect((ResultMatcher) jsonPath("$.Description").value("Clothes from Jan Bakker")).andExpect((ResultMatcher) jsonPath("$.Start Balance").value(21.6))
				.andExpect((ResultMatcher) jsonPath("$.Mutation").value(-41.83)).andExpect((ResultMatcher) jsonPath("$.End Balance").value(-20.23));

	}
	
}
