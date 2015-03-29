package apinetflix;

import apinetflix.pojo.Ad;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApiControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                  MediaType.APPLICATION_JSON.getSubtype(),
                                                  Charset.forName("utf8"));

    private MediaType textPlainContentType = new MediaType(MediaType.TEXT_PLAIN.getType(),
                                                 MediaType.TEXT_PLAIN.getSubtype(),
                                                 Charset.forName("utf8"));

    private MockMvc mockMvc;

    private Ad ad;
    private int account_id;

    // For JSON serialization
    private ObjectMapper mapper;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        ad = new Ad();
        ad.setBody("Lorem Ipsum");
        ad.setListId(1);
        ad.setSubject("Lorem Ipsum 1");
        ad.setCompanyAd(false);

        account_id = 1;

        mapper = new ObjectMapper();
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testGetWithIncorrectUser() throws Exception {
        mockMvc.perform(get("/account/afafafafa/saved_ads"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetWithoutQueryParams() throws Exception {
        mockMvc.perform(get("/account/{account_id}/saved_ads", account_id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void testGetWithQueryParamLim() throws Exception {
        mockMvc.perform(get("/account/{account_id}/saved_ads", account_id).param("lim", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void testGetWithQueryParamOffset() throws Exception {
        mockMvc.perform(get("/account/{account_id}/saved_ads", account_id).param("o", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void testBadRequestParamsPost() throws Exception{
        mockMvc.perform(post("/account/{account_id}/saved_ads", account_id).header("X-NGA-SOURCE", "90"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostWithoutBody() throws Exception {
        mockMvc.perform(post("/account/{account_id}/saved_ads", account_id).header("X-NGA-SOURCE", "90").param("list_id", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostWithIncorrectUser() throws Exception {
        mockMvc.perform(post("/account/adfafa/saved_ads"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPost() throws Exception {

        mockMvc.perform(post("/account/{account_id}/saved_ads", account_id)
                .header("X-NGA-SOURCE", "90")
                .param("list_id", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(ad)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(textPlainContentType));
    }

    @Test
    public void testPostSeveralIds() throws Exception {

        mockMvc.perform(post("/account/{account_id}/saved_ads", account_id)
                .header("X-NGA-SOURCE", "90")
                .param("list_id", "10").param("list_id", "11")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(ad)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(textPlainContentType));
    }
}