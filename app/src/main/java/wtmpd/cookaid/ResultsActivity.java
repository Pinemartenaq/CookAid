package wtmpd.cookaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class ResultsActivity extends AppCompatActivity implements NavigationBar.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ListView listView = (ListView)findViewById(R.id.resultList);

        final SingletonStorage storage = SingletonStorage.getInstance(this);

        String[] names = new String[storage.recipeNames.size()];
        String[] fitnesses = new String[storage.recipeFitnesses.size()];

        names = storage.recipeNames.toArray(names);
        fitnesses = storage.recipeFitnesses.toArray(fitnesses);

        //storage.deleteRecipe(storage.getRecipes().get(0));
        
        String[] backupname = {};
        String[] backupfit = {};

        RecipeArrayAdapter adapter = new RecipeArrayAdapter(this, names.length > 0 ? names: backupname, fitnesses.length > 0 ? fitnesses : backupfit);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id){
                final String item = (String)parent.getItemAtPosition(position);
                storage.storedRecipe = storage.getRecipes().get(position);
                Intent intent = new Intent(getApplicationContext(), RecipeViewActivity.class);
                startActivityForResult(intent, 0);
            }
        });
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
        SingletonStorage storage = SingletonStorage.getInstance(this);
        storage.storedRecipe = null;
        Intent intent = new Intent(getApplicationContext(), RecipeEditActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onInstructionClick() {
        Intent intent = new Intent(getApplicationContext(), InstructionActivity.class);
        startActivityForResult(intent, 0);
    }
}
