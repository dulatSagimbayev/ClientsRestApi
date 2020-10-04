package com.example.ClientsApi.controller;

import com.example.ClientsApi.ClientController;
import com.example.ClientsApi.model.Client;
import com.example.ClientsApi.model.PhoneNumber;
import com.example.ClientsApi.repository.ClientRepository;
import com.example.ClientsApi.repository.PhoneNumberRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ClientController.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private PhoneNumberRepository phoneNumberRepository;

    @Test
    public void testCreateClient() throws Exception {
        Client mockClient = new Client();
        mockClient.setId(Long.valueOf(1));
        mockClient.setName("Dmitriy");
        mockClient.setLastName("Moyseev");
        mockClient.setSecondName("Aleksandrovych");

        String inputJson = this.mapToJson(mockClient);
        String URI = "/api/clients";


        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(mockClient);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(inputJson);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputJson = response.getContentAsString();


        assertThat(outputJson).isEqualTo(inputJson);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    @Test
    public void displayClient() throws Exception{
        PhoneNumber phoneNumber1=new PhoneNumber("Office","5-12-32");
        PhoneNumber phoneNumber2=new PhoneNumber("Mobile","87022022233");
        PhoneNumber phoneNumber3=new PhoneNumber("Mobile","87773222238");
        PhoneNumber phoneNumber4=new PhoneNumber("Home","2-14-32");

        Client mockClient1 = new Client();
        mockClient1.setId(Long.valueOf(1));
        mockClient1.setName("Dmitriy");
        mockClient1.setLastName("Moyseev");
        mockClient1.setSecondName("Aleksandrovych");
        mockClient1.getNumbers().add(phoneNumber1);
        mockClient1.getNumbers().add(phoneNumber2);

        Client mockClient2 = new Client();
        mockClient2.setId(Long.valueOf(2));
        mockClient2.setName("Dulat");
        mockClient2.setLastName("Sagimbayev");
        mockClient2.setSecondName("Kayratovich");
        mockClient2.getNumbers().add(phoneNumber3);
        mockClient2.getNumbers().add(phoneNumber4);

        List<Client> clientList = new ArrayList<>();
        clientList.add(mockClient1);
        clientList.add(mockClient2);

        String URI = "/api/clients";
        Mockito.when(clientRepository.findAll()).thenReturn(clientList);

        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .get(URI)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String outputJson =result.getResponse().getContentAsString();


        String expectedJson=this.mapToJson(clientList);

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        assertThat(outputJson).isEqualTo(expectedJson);
    }
    @Test
    public void testUpdateClient() throws Exception{
        Client mockClient1 = new Client();
        mockClient1.setId(Long.valueOf(2));
        mockClient1.setName("Dmitriy");
        mockClient1.setLastName("Moyseev");
        mockClient1.setSecondName("Aleksandrovych");

        Client mockClient2 = new Client();
        mockClient2.setId(Long.valueOf(2));
        mockClient2.setName("Duman");
        mockClient2.setLastName("Sagimbayev");
        mockClient2.setSecondName("Kayratovich");

        String URI="/api/clients/2/refresh";
        String inputJson = mapToJson(mockClient2);
        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(mockClient1));
        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(mockClient2);
        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .put(URI)
                .content(inputJson).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String outputJson =result.getResponse().getContentAsString();
        assertThat(outputJson).isEqualTo(inputJson);
    }
    @Test
    public void testDeleteClient() throws Exception{
        Client client = new Client();
        client.setId((long)1);
        client.setName("Artem");
        client.setLastName("Lisyn");
        client.setSecondName("Dmitryevich");
        PhoneNumber phoneNumber= new PhoneNumber("Office","5-12-32");
        phoneNumber.setId((long)1);
        ArrayList<PhoneNumber> arrayList = new ArrayList<>();
        arrayList.add(phoneNumber);

        client.setNumbers(arrayList);
        String URI="/api/clients/1/remove";
        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(client));
        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .delete(URI)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String outputJson =result.getResponse().getContentAsString();

        assertThat(outputJson).isEqualTo("Client with id= 1 and his numbers are deleted successful");

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
