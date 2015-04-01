package recipes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import recipes.model.Recipe;
import recipes.repository.RecipesRepository;

@RestController
public class RecipesController {

	@Autowired
	private RecipesRepository recipeRepository;

	@RequestMapping(value = "/recipe", method = RequestMethod.GET)
	public List<Recipe> listAll() {
		return recipeRepository.findAll();
	}

	@RequestMapping(value = "/recipe/{id}", method = RequestMethod.GET)
	public Recipe retrieve(@PathVariable int id) {
		Recipe recipe = recipeRepository.findById(id);
		if (recipe == null)
			throw new RecipeNotFoundException(id);
		return recipe;
	}

	@RequestMapping(value = "/recipe", method = RequestMethod.POST)
	public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe input)
			throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		Recipe aux = recipeRepository.findById(input.getId());

		if (aux == null) {
			Recipe nuevo = recipeRepository.save(new Recipe(input.getName(),
					input.getDescription()));

			httpHeaders.setLocation(ServletUriComponentsBuilder
					.fromCurrentRequest().path("/{id}")
					.buildAndExpand(nuevo.getId()).toUri());
		} else
			throw new Exception("ya existe esa receta");

		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/recipe", method = RequestMethod.PUT)
	public ResponseEntity<Recipe> updateRecipe(@RequestBody Recipe input)
			throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		Recipe aux = recipeRepository.findById(input.getId());

		if (aux != null) {
			Recipe old = recipeRepository.findById(input.getId());
			old.setDescription(input.getDescription());
			old.setName(input.getName());
			old.setIngredients(input.getIngredients());
			recipeRepository.save(old);

			httpHeaders.setLocation(ServletUriComponentsBuilder
					.fromCurrentRequest().path("/{id}")
					.buildAndExpand(old.getId()).toUri());
		} else
			throw new RecipeNotFoundException(input.getId());

		return new ResponseEntity<>(null, httpHeaders, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/recipe/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable int id) {
		HttpHeaders httpHeaders = new HttpHeaders();
		Recipe recipe = recipeRepository.findById(id);
		if (recipe == null)
			throw new RecipeNotFoundException(id);
		else {
			recipeRepository.delete((long) id);
			httpHeaders.setLocation(ServletUriComponentsBuilder
					.fromCurrentRequest().path("/{id}")
					.buildAndExpand(recipe.getId()).toUri());
		}

		return new ResponseEntity<>(null, httpHeaders, HttpStatus.NO_CONTENT);
	}
}

@ControllerAdvice
class RecipeControllerAdvice {

	@ResponseBody
	@ExceptionHandler(RecipeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	VndErrors userNotFoundExceptionHandler(RecipeNotFoundException ex) {
		return new VndErrors("error", ex.getMessage());
	}
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class RecipeNotFoundException extends RuntimeException {

	public RecipeNotFoundException(long recipeId) {
		super("There is no recipe with that id: '" + recipeId + "'.");
	}
}
