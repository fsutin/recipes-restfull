package recipes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String description;
	
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Ingredients> ingredients;
	
	public List<Ingredients> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredients> ingredients) {
		this.ingredients = ingredients;
	}

	public Recipe() {
		// TODO Auto-generated constructor stub
	}
	
	public Recipe(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Recipe(String name, String description, List<Ingredients> ingredients) {
		this.name = name;
		this.description = description;
		this.ingredients = ingredients;
	}
	
	public String getDescription() {
		return description;
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
