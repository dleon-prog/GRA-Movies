package com.example.goldenraspberryawards.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProducerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetProducersIntervalsAgainstCsvData() throws Exception {
        String url = "http://localhost:" + port + "/api/producers/interval";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        Map<String, List<Map<String, Object>>> expectedResponse = Map.of(
                "min", List.of(
                        Map.of(
                                "producer", "Joel Silver",
                                "interval", 1,
                                "previousWin", 1990,
                                "followingWin", 1991
                        )
                ),
                "max", List.of(
                        Map.of(
                                "producer", "Matthew Vaughn",
                                "interval", 13,
                                "previousWin", 2002,
                                "followingWin", 2015
                        )
                )
        );

        Map<String, List<Map<String, Object>>> actualResponse = objectMapper.readValue(response.getBody(), Map.class);

        assertThat(actualResponse)
                .isNotNull()
                .containsExactlyInAnyOrderEntriesOf(expectedResponse);
    }
}
