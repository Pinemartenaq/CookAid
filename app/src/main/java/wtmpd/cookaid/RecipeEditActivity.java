package wtmpd.cookaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

public class RecipeEditActivity extends AppCompatActivity implements NavigationBar.OnFragmentInteractionListener {

    private LinearLayout mLayout;
    private TextView mTextView;
    private Button mButton;
    private LinearLayout mLinLayout;
    private EditText mEditText;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_edit);
        mLayout = (LinearLayout)findViewById(R.id.mLayout);
        mTextView = (TextView)findViewById(R.id.textView7);
        mButton = (Button)findViewById(R.id.pButton);
        mButton.setOnClickListener(onClickAddIngredient());
        doneButton = (Button)findViewById(R.id.doneButton);
        TextView textView = new TextView(this);
        //textView.setText("New text");
        mLinLayout = (LinearLayout) findViewById(R.id.mLinLayout);
        mEditText = (EditText) findViewById(R.id.mEditText);

       doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDoneClick();
            }
        });

    }

    //TODO: make it so that app returns to previous view
    private void onDoneClick(){

    }

    private View.OnClickListener onClickAddIngredient() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //mLayout.addView(createNewTextView(mTextView.getText().toString()));
                mLinLayout.addView(createNewTextView(mTextView.getText().toString()));
            }
        };
    }

    private EditText createNewTextView(String text) {
        //final ActionBar.LayoutParams lparams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT*);
        final EditText textView = new EditText(this);
        textView.setHint("add additional Ingredient");
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
