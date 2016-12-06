package wtmpd.cookaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.List;

public class RecipeEditActivity extends AppCompatActivity implements NavigationBar.OnFragmentInteractionListener {

    private TextView mTextView;
    private Button mButton;
    private LinearLayout mLinLayout;
    private EditText mEditText;
    private Button doneButton;
    private SingletonStorage storage = SingletonStorage.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_edit);
        mTextView = (TextView)findViewById(R.id.txtTip);
        mButton = (Button)findViewById(R.id.pButton);
        mButton.setOnClickListener(onClickAddIngredient());
        doneButton = (Button)findViewById(R.id.doneButton);
        TextView textView = new TextView(this);
        mLinLayout = (LinearLayout) findViewById(R.id.mLinLayout);
        mEditText = (EditText) findViewById(R.id.mEditText);

       doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDoneClick();
            }
        });

        Recipe recipe = storage.storedRecipe;

        if(recipe != null){
            TextView title = (TextView)findViewById(R.id.editTitle);
            title.setText("Recipe Editor");

            EditText name = (EditText)findViewById(R.id.editName);
            name.setText(recipe.getName());

            EditText typeAndCuisine = (EditText)findViewById(R.id.editTypeAndCuisine);
            typeAndCuisine.setText(recipe.getType().getName() + ", " + recipe.getCuisine().getName());

            EditText instructions = (EditText)findViewById(R.id.editInstruction);
            instructions.setText(recipe.getInstructions());

            for(SpecificIngredient si : recipe.getSpecificIngredients()){
                EditText et;
                mLinLayout.addView(et = createNewTextView(mTextView.getText().toString()));
                et.setText(si.getIngredient().getName() + ", " + si.getMeasurement());
            }
        }
    }

    private void onDoneClick(){

        List<SpecificIngredient> ingredients = new LinkedList<>();

        //Grab type and cuisine String
        String[] typeAndCuisineString = ((EditText)findViewById(R.id.editTypeAndCuisine)).getText().toString().split(", ");

        //Clean them
        for (String s : typeAndCuisineString)
            s.trim();

        //Convert to type if possible, else None
        RecipeType type = (typeAndCuisineString.length == 0 || typeAndCuisineString[0] == null || "".equals(typeAndCuisineString[0])) ?
                storage.getType("Any"):
                storage.getType(typeAndCuisineString[0]);

        //Convert to cuisine if possible, else None
        RecipeCuisine cuisine = (typeAndCuisineString.length < 2 || typeAndCuisineString[1] == null || "".equals(typeAndCuisineString[1])) ?
                storage.getCuisine("Any"):
                storage.getCuisine(typeAndCuisineString[1]);

        //Get ingredient Strings
        for(int i = 0; i < mLinLayout.getChildCount(); i++) {
            if( mLinLayout.getChildAt( i ) instanceof EditText ) {
                //Get ingredient String
                String[] siStringArray = (((EditText)mLinLayout.getChildAt(i)).getText()).toString().split(", ");
                //If it isn't empty add it to ingredients
                if(!(siStringArray.length == 0 || siStringArray[0] == null || "".equals(siStringArray[0])))
                    ingredients.add(new SpecificIngredient(storage.getIngredient(siStringArray[0]), siStringArray.length < 2 ? "As Desired": siStringArray[1]));
            }
        }

        if(storage.storedRecipe == null){
            storage.addRecipe(storage.storedRecipe = new Recipe(
                    ((EditText)findViewById(R.id.editName)).getText().toString(),
                    0,
                    cuisine,
                    type,
                    ingredients,
                    ((EditText)findViewById(R.id.editInstruction)).getText().toString()
            ));
        }
        else {
            String id = storage.storedRecipe.getID();
            Recipe temp = storage.storedRecipe;
            storage.storedRecipe =  new Recipe(
                    ((EditText)findViewById(R.id.editName)).getText().toString(),
                    0,
                    cuisine,
                    type,
                    ingredients,
                    ((EditText)findViewById(R.id.editInstruction)).getText().toString()
            );
            storage.storedRecipe.setID(Integer.parseInt(id));
            storage.updateRecipe(storage.storedRecipe);
            storage.storedRecipe = storage.getRecipes().get(storage.getRecipes().indexOf(temp));
        }

        Intent intent = new Intent(getApplicationContext(), RecipeViewActivity.class);
        startActivityForResult(intent, 0);
    }

    private View.OnClickListener onClickAddIngredient() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mLinLayout.addView(createNewTextView(mTextView.getText().toString()));
            }
        };
    }

    private EditText createNewTextView(String text) {
        final EditText textView = new EditText(this);
        textView.setHint("Ingredient Name, Quantitiy");
        textView.setLayoutParams(mEditText.getLayoutParams());
        textView.setBackgroundColor(-1);
        return textView;
    }

    //Private Methods

    /**
     * Creates a new recipe object and database entry
     *
     * @param name
     * @param prepTime
     * @param cuisine
     * @param type
     * @param ingredients
     * @param cookingDirections
     */
    private void addRecipe(String name, int prepTime, RecipeCuisine cuisine, RecipeType type, List<SpecificIngredient> ingredients, String cookingDirections) {
        SingletonStorage storage = SingletonStorage.getInstance(this);
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
