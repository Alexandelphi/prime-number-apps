package com.home.primenumber.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PrimeNumberController.class)
public class PrimeNumberControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  PrimeNumberGrpcClient grpcClient;

  @Test
  public void shouldReturnListOfPrimeNums() throws Exception {
    Mockito.when(grpcClient.generatePrimeNums(17)).thenReturn(Arrays.asList(2, 3, 5, 7, 11, 13, 17));

    this.mockMvc.perform(get("/prime/{number}", 17))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(7)))
        .andExpect(content().string("[2,3,5,7,11,13,17]"));
  }
}