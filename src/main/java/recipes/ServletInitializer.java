package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import recipes.repository.RecipesRepository;

public class ServletInitializer extends SpringBootServletInitializer {

	@Autowired
	RecipesRepository recipeRepository;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RecipesRestfullApplication.class);
	}

	
}

