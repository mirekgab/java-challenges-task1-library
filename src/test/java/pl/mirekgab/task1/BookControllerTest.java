package pl.mirekgab.task1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testFindAll() throws Exception {
        ResponseEntity<BookDTO[]> forEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/api/book/findAll", BookDTO[].class);

        assertTrue(forEntity.getBody().length>0);
    }
}