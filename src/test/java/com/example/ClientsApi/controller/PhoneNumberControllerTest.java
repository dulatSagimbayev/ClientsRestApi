package com.example.ClientsApi.controller;

import com.example.ClientsApi.PhoneNumberController;

import com.example.ClientsApi.model.Client;
import com.example.ClientsApi.model.PhoneNumber;
import com.example.ClientsApi.repository.ClientRepository;
import com.example.ClientsApi.repository.PhoneNumberRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

import java.io.IOException;
import java.util.ArrayList;

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
        ArrayList<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(phoneNumber);

        Client mockClient2 = new Client();
        mockClient2.setId((long) 1);
        mockClient2.setName("Bulat");
        mockClient2.setLastName("Muhamed");
        mockClient2.setSecondName("Alishev");
        mockClient2.setNumbers(phoneNumbers);

        String inputJson = mapToJson(phoneNumber);
        String URI = "/api/clients/1/numbers";

        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(mockClient1));
        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(mockClient2);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson);
        MvcResult mvcResult= mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response= mvcResult.getResponse();
        String outJson = response.getContentAsString();
        String expectedJson = mapToJson(mockClient2);

        assertThat(expectedJson).isEqualTo(outJson);


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
        ArrayList<PhoneNumber> phoneNumbers1 = new ArrayList<>();
        phoneNumbers1.add(phoneNumber1);

        mockClient1.setNumbers(phoneNumbers1);

        PhoneNumber phoneNumber2 = new PhoneNumber("Mobile","89999993322");
        phoneNumber2.setId((long) 1);
        ArrayList<PhoneNumber> phoneNumbers2 = new ArrayList<>();
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
    public void testDeletePhoneNumber() throws Exception {
        Client mockClient1 = new Client();
        mockClient1.setId((long) 1);
        mockClient1.setName("Bulat");
        mockClient1.setLastName("Muhamed");
        mockClient1.setSecondName("Alishev");

        PhoneNumber phoneNumber1 = new PhoneNumber("Mobile","87773332290");
        phoneNumber1.setId((long) 1);
        ArrayList<PhoneNumber> phoneNumbers1 = new ArrayList<>();
        phoneNumbers1.add(phoneNumber1);

        String URI = "/clients/1/numbers/1/remove";

        mockClient1.setNumbers(phoneNumbers1);
        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(mockClient1));
        Mockito.when(phoneNumberRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(phoneNumber1));

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

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}
