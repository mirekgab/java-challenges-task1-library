package pl.mirekgab.task1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class WebClientBookControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testFindAll() throws Exception {
        EntityExchangeResult<List<BookDTO[]>> listEntityExchangeResult = webTestClient.get().uri("/api/book/findAll")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDTO[].class)
                .returnResult();

        assertFalse(listEntityExchangeResult.getResponseBody().isEmpty());
    }

    @Test
    void testFindAllWithPage() throws Exception {
        EntityExchangeResult<List<BookDTO[]>> listEntityExchangeResult = webTestClient.get().uri("/api/book/findAll?page=0&size=2")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDTO[].class)
                .returnResult();

        assertFalse(listEntityExchangeResult.getResponseBody().size()==2);
    }

}