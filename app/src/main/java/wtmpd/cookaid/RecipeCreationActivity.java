package wtmpd.cookaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class RecipeCreationActivity extends AppCompatActivity implements NavigationBar.OnFragmentInteractionListener{

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

    @Override
    public void onHomeClick() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onSearchClick() {
        Intent intent = new Intent(getApplicationContext(), RecipeSearchActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onAddClick() {

    }

    @Override
    public void onInstructionClick() {
        Intent intent = new Intent(getApplicationContext(), InstructionActivity.class);
        startActivityForResult(intent, 0);
    }
}
