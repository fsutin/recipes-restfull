package recipes;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import recipes.model.Recipe;
import recipes.repository.RecipesRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RecipesRestfullApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class RecipeRepositoryTests {
	
	@Autowired
	RecipesRepository repository;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void getAll() throws Exception {
		List<Recipe> recipes = repository.findAll();
		assertThat(recipes.size(), Matchers.is(4));
	}
	
	@Test
	public void getOne() throws Exception {
		Recipe recipe = repository.findById(1);
		assertThat(recipe, Matchers.notNullValue());
	}

}
