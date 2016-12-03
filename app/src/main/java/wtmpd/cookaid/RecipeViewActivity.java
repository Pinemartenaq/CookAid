package wtmpd.cookaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecipeViewActivity extends AppCompatActivity implements NavigationBar.OnFragmentInteractionListener{

    private Button editButton;
    private Button deleteButton;

    SingletonStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        storage = SingletonStorage.getInstance(this);

        editButton = (Button)findViewById(R.id.eButton);
        deleteButton = (Button)findViewById(R.id.dButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditClick();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClick();
            }
        });

        Recipe recipe = storage.storedRecipe;

        if(recipe != null) {
            //ID for title - recipeTitle
            TextView titleDisplay = (TextView) findViewById(R.id.recipeTitle);
            titleDisplay.setText(recipe.getName());

            //ID for short description - recipeDesView
            TextView typeAndCuisineDisplay = (TextView) findViewById(R.id.recipeDesView);
            typeAndCuisineDisplay.setText(recipe.getType().getName() + ", " + recipe.getCuisine().getName());

            //ID for ingredients - ingredientsView
            TextView ingredientsDisplay = (TextView) findViewById(R.id.ingredientsView);
            StringBuilder ingredientsString = new StringBuilder();

            for (SpecificIngredient si : recipe.getSpecificIngredients()) {
                ingredientsString.append(si.getMeasurement()).append(" ");
                try {
                    Double.parseDouble(si.getMeasurement());
                } catch (NumberFormatException e) {
                    ingredientsString.append("of ");
                }
                ingredientsString.append(si.getIngredient().getName()).append(", ");
            }

            ingredientsDisplay.setText(ingredientsString.toString());

            //ID for directions - directionsView
            TextView directionsDisplay = (TextView) findViewById(R.id.directionsView);
            directionsDisplay.setText(recipe.getInstructions());
        }
    }

    public void onEditClick(){
        Intent intent = new Intent(getApplicationContext(), RecipeEditActivity.class);
        startActivityForResult(intent, 0);
    }

    public void onDeleteClick(){

        storage.deleteRecipe(storage.storedRecipe);
        storage.storedRecipe = null;

        onHomeClick();
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
        Intent intent = new Intent(getApplicationContext(), RecipeEditActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onInstructionClick() {
        Intent intent = new Intent(getApplicationContext(), InstructionActivity.class);
        startActivityForResult(intent, 0);
    }
}
