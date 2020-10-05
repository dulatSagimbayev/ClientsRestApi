package com.example.ClientsApi.controller;

import com.example.ClientsApi.model.Client;
import com.example.ClientsApi.model.PhoneNumber;
import com.example.ClientsApi.repository.ClientRepository;
import com.example.ClientsApi.repository.PhoneNumberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.HashSet;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PhoneNumberController.class)
public class PhoneNumberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private PhoneNumberRepository phoneNumberRepository;

    @Test
    public void testCreatePhoneNumber()throws Exception{
        Client mockClient1 = new Client();
        mockClient1.setId((long) 1);
        mockClient1.setName("Bulat");
        mockClient1.setLastName("Muhamed");
        mockClient1.setSecondName("Alishev");

        PhoneNumber phoneNumber = new PhoneNumber("Mobile","89999993322");
        phoneNumber.setId((long) 1);
        HashSet<PhoneNumber> phoneNumbers = new HashSet<>();
        phoneNumbers.add(phoneNumber);

        String inputJson = mapToJson(phoneNumber);
        String URI = "/api/clients/1/numbers";

        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(mockClient1));
        Mockito.when(phoneNumberRepository.save(Mockito.any(PhoneNumber.class))).thenReturn(phoneNumber);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson);
        MvcResult mvcResult= mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response= mvcResult.getResponse();
        String outJson = response.getContentAsString();

        assertThat(inputJson).isEqualTo(outJson);


    }
    @Test
    public void testUpdatePhoneNumbers() throws Exception{
        Client mockClient1 = new Client();
        mockClient1.setId((long) 1);
        mockClient1.setName("Bulat");
        mockClient1.setLastName("Muhamed");
        mockClient1.setSecondName("Alishev");

        PhoneNumber phoneNumber1 = new PhoneNumber("Mobile","87773332290");
        phoneNumber1.setId((long) 1);
        HashSet<PhoneNumber> phoneNumbers1 = new HashSet<>();
        phoneNumbers1.add(phoneNumber1);

        mockClient1.setNumbers(phoneNumbers1);

        PhoneNumber phoneNumber2 = new PhoneNumber("Mobile","89999993322");
        phoneNumber2.setId((long) 1);
        HashSet<PhoneNumber> phoneNumbers2 = new HashSet<>();
        phoneNumbers2.add(phoneNumber2);

        Client mockClient2 = new Client();
        mockClient2.setId((long) 1);
        mockClient2.setName("Bulat");
        mockClient2.setLastName("Muhamed");
        mockClient2.setSecondName("Alishev");
        mockClient2.setNumbers(phoneNumbers2);

        String URI="/api/clients/1/refresh/1";
        String inputJson = mapToJson(phoneNumber2);

        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(mockClient1));
        Mockito.when(phoneNumberRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(phoneNumber1));
        Mockito.when(phoneNumberRepository.save(Mockito.any(PhoneNumber.class))).thenReturn(phoneNumber2);


        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .put(URI)
                .content(inputJson).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String outputJson =result.getResponse().getContentAsString();
        assertThat(outputJson).isEqualTo(inputJson);
    }
    @Test
    public void testDeleteNumber() throws Exception{
        Client mockClient = new Client();
        mockClient.setId((long) 1);
        mockClient.setName("Dulat");
        mockClient.setLastName("Sag");
        mockClient.setSecondName("Nok");

        PhoneNumber mockPhoneNumber= new PhoneNumber();
        mockPhoneNumber.setId((long) 1);
        mockPhoneNumber.setType("Office");
        mockPhoneNumber.setPhoneNumber("5-12-32");
        mockPhoneNumber.setClient(mockClient);

        String inputJson = mapToJson(mockPhoneNumber);
        String URI = "/api/clients/1/numbers/1/remove";

        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(mockClient));
        Mockito.when(phoneNumberRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(mockPhoneNumber));

        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .delete(URI)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String outputJson =result.getResponse().getContentAsString();

        assertThat(outputJson).isEqualTo("Phone number with id= 1 is deleted successful");
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}
