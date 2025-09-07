package pl.mirekgab.task1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.mirekgab.task1.exception.ErrorResponseDTO;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MockMVCBookControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private DataSource dataSource;

    @BeforeEach
    public void beforeEach() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("import-test-data.sql"));
        populator.execute(this.dataSource);
    }


    @Test
    void testCreateBook() throws Exception {

        NewBookDTO dto = new NewBookDTO("test", "test", 2000);

        MvcResult mvcResult = this.mockMvc.perform(
                        post("/api/book/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isCreated())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(result);
        BookDTO bookDTO = objectMapper.readValue(result, BookDTO.class);
        assertAll(
                () -> assertEquals(dto.author(), bookDTO.author(), "author should be the same"),
                () -> assertEquals(dto.title(), bookDTO.title(), "title should be the same"),
                () -> assertEquals(dto.publishYear(), bookDTO.publishYear(), "publish year should be the same")
        );
    }

    @Test
    public void testUpdateBook() throws Exception {
        BookDTO bookDTO = new BookDTO(1L, "title1a", "author1", 2001);

        MvcResult mvcResult = mockMvc.perform(
                patch("/api/book/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO))
        ).andReturn();

        BookDTO responseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BookDTO.class);

        assertAll(
                () -> assertEquals(bookDTO.title(), responseDTO.title(), "title should be the same"),
                () -> assertEquals(bookDTO.author(), responseDTO.author(), "author should be the same"),
                () -> assertEquals(bookDTO.publishYear(), responseDTO.publishYear(), "public year should be the same")
        );
    }

    @Test
    public void testUpdateNotExistedBook() throws Exception {
        BookDTO bookDTO = new BookDTO(1234L, "title1a", "author1", 2001);

        MvcResult mvcResult = mockMvc.perform(
                patch("/api/book/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO))
        ).andReturn();
        ErrorResponseDTO responseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponseDTO.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), responseDTO.status(), "status code should be the same");
    }

    @Test
    public void testFindByIdNotExistedBook() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/book/findBook/1234")).andReturn();
        ErrorResponseDTO responseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponseDTO.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), responseDTO.status());
    }

    @Test
    public void testFindById() throws Exception {
        BookDTO dto = new BookDTO(1L, "title1", "author1", 2001);

        MvcResult mvcResult = mockMvc.perform(get("/api/book/findBook/1")).andReturn();
        BookDTO responseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BookDTO.class);
        assertAll(
                ()->assertEquals(dto.id(), responseDTO.id(), "id should be the same"),
                ()->assertEquals(dto.author(), responseDTO.author(), "author should be the same"),
                ()->assertEquals(dto.title(), responseDTO.title(), "title should be the same"),
                ()->assertEquals(dto.publishYear(), responseDTO.publishYear(), "publish year should be the same")
        );
    }
}
