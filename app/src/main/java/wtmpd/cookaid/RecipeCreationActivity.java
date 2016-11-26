package wtmpd.cookaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class RecipeCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_edit);
    }

    //Private Methods
    private void addRecipe( String name, int prepTime, RecipeCuisine cuisine, RecipeType type, List<SpecificIngredient> ingredients, String cookingDirections) {
        SingletonStorage storage = SingletonStorage.getInstance();
        Recipe newRecipe = new Recipe(name, prepTime, cuisine, type, ingredients, cookingDirections);
        storage.addRecipe(newRecipe);
    }
}
