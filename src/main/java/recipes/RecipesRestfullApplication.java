package recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import recipes.model.Ingredients;
import recipes.model.Recipe;
import recipes.repository.RecipesRepository;

@SpringBootApplication
public class RecipesRestfullApplication implements CommandLineRunner {
	
	@Autowired
	RecipesRepository recipeRepository;
	
	// CORS
		@Bean
		FilterRegistrationBean corsFilter(
				@Value("${tagit.origin:http://localhost:8080}") String origin) {
			return new FilterRegistrationBean(new Filter() {
				public void doFilter(ServletRequest req, ServletResponse res,
						FilterChain chain) throws IOException, ServletException {
					HttpServletRequest request = (HttpServletRequest) req;
					HttpServletResponse response = (HttpServletResponse) res;
					String method = request.getMethod();
					// this origin value could just as easily have come from a database
					response.setHeader("Access-Control-Allow-Origin", origin);
					response.setHeader("Access-Control-Allow-Methods",
							"POST,GET,OPTIONS,DELETE");
					response.setHeader("Access-Control-Max-Age", Long.toString(60 * 60));
					response.setHeader("Access-Control-Allow-Credentials", "true");
					response.setHeader(
							"Access-Control-Allow-Headers",
							"Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
					if ("OPTIONS".equals(method)) {
						response.setStatus(HttpStatus.OK.value());
					}
					else {
						chain.doFilter(req, res);
					}
				}

				public void init(FilterConfig filterConfig) {
				}

				public void destroy() {
				}
			});
		}
	
    public static void main(String[] args) {
    	SpringApplication.run(RecipesRestfullApplication.class, args);
           
    }

	@Override
	public void run(String... arg0) throws Exception {
		
		List<Ingredients> ingredientes = new ArrayList<Ingredients>();
		
		Ingredients ing1 = new Ingredients();
		ing1.setName("Flour");
		ing1.setQuantity("2 Cups");
		ingredientes.add(ing1);
		
		Ingredients ing2 = new Ingredients();
		ing2.setName("Brown Sugar");
		ing2.setQuantity("1 Cup");
		ingredientes.add(ing2);
		
		Ingredients ing3 = new Ingredients();
		ing3.setName("White Sugar");
		ing3.setQuantity("1/2 Cup");
		ingredientes.add(ing3);
		
		Ingredients ing4 = new Ingredients();
		ing4.setName("Vanilla");
		ing4.setQuantity("2 Teaspoons");
		ingredientes.add(ing4);
		
		
		recipeRepository.save(new Recipe("Lemon pie","esta es una torta de limon... puto el que lee",ingredientes));
		recipeRepository.save(new Recipe("Cookies","esta es una receta de cookies... puto el que lee"));
		recipeRepository.save(new Recipe("Muffins","esta es una receta de muffins... puto el que lee"));
		recipeRepository.save(new Recipe("Carrot Cake","esta es una carrot cake... puto el que lee"));
		
	}
}
