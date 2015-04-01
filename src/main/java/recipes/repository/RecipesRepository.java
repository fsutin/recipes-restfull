package recipes.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import recipes.model.Recipe;

public interface RecipesRepository extends CrudRepository<Recipe, Long> {
	
	List<Recipe> findAll();
	Recipe findById(long id);
	
}
