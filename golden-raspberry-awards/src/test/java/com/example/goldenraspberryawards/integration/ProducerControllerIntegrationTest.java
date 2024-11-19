package com.example.goldenraspberryawards.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProducerControllerIntegrationTest {

    @Autowired
    private Environment environment;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetProducersIntervals() {
        String port = environment.getProperty("local.server.port");
        String url = "http://localhost:" + port + "/api/producers/interval";

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).containsKeys("min", "max");

        assertThat(response.getBody().get("min")).asList().isNotEmpty();
        assertThat(response.getBody().get("max")).asList().isNotEmpty();
    }
}
