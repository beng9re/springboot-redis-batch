package web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


@SpringBootTest
class PublishControllerTest {



    @Test
    void publishTest() throws URISyntaxException, IOException {

       ClientHttpRequest request = new RestTemplateBuilder()
               .buildRequestFactory()
               .createRequest(new URI("http://localhost:8081/chat/publish"), HttpMethod.GET)
               ;

        ClientHttpResponse clientHttpResponse = request.execute();

        System.out.println(clientHttpResponse.getBody());


    }

}