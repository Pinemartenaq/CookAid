package wtmpd.cookaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements NavigationBar.OnFragmentInteractionListener{

    SingletonStorage storage;

    //Getter
    public SingletonStorage getStorage(){
        return storage;
    }

    //Button functions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storage = SingletonStorage.getInstance(this);

        Ingredient chicken = new Ingredient("Chicken");
        Ingredient cheese = new Ingredient("Cheese");
        Ingredient tomato = new Ingredient("Tomato");

        storage.add(chicken);
        storage.add(cheese);
        storage.add(tomato);

        LinkedList<SpecificIngredient> chickenParmIngredients = new LinkedList<>();
        chickenParmIngredients.add(new SpecificIngredient(chicken, "1"));
        chickenParmIngredients.add(new SpecificIngredient(cheese, "10g"));

        LinkedList<SpecificIngredient> pizzaIngredients = new LinkedList<>();
        pizzaIngredients.add(new SpecificIngredient(tomato, "2"));
        pizzaIngredients.add(new SpecificIngredient(cheese, "100g"));

        Recipe r0 = new Recipe("Chicken Parmesan", 45, new RecipeCuisine("Italien"), new RecipeType("Main"), chickenParmIngredients, "Chicken instructions");
        Recipe r1 = new Recipe("Pizza", 60, new RecipeCuisine("Italien"), new RecipeType("Main"), pizzaIngredients, "Pizza instructions");

        storage.addRecipe(r0);
        storage.addRecipe(r1);
    }

    @Override
    public void onHomeClick() {

    }

    @Override
    public void onSearchClick() {
        Intent intent = new Intent(getApplicationContext(), RecipeSearchActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onAddClick() {
        Intent intent = new Intent(getApplicationContext(), RecipeEditActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onInstructionClick() {
        Intent intent = new Intent(getApplicationContext(), InstructionActivity.class);
        startActivityForResult(intent, 0);
    }
}
