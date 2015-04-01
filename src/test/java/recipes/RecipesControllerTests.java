package recipes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import recipes.model.Recipe;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RecipesRestfullApplication.class)
@WebAppConfiguration
public class RecipesControllerTests {

	private int port;

	private URL base;
	private RestTemplate template;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MediaType contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays
				.asList(converters)
				.stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny().get();

		Assert.assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void getAll() throws Exception {
		mockMvc.perform(get("/recipe")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType));

	}

	@Test
	public void getOne() throws Exception {
		mockMvc.perform(get("/recipe/1")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("id").value(1));
	}

	@Test
	public void createRecipe() throws Exception {
		String recipeJson = json(new Recipe("Strudel", "de manzana"));
		this.mockMvc.perform(
				post("/recipe").contentType(contentType).content(recipeJson))
				.andExpect(status().isCreated());
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o,
				MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
	


}
