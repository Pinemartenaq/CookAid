package wtmpd.cookaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ResultsActivity extends AppCompatActivity implements NavigationBar.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ListView listView = (ListView)findViewById(R.id.resultList);

        SingletonStorage storage = SingletonStorage.getInstance(this);

        String[] names = new String[storage.recipeNames.size()];
        String[] fitnesses = new String[storage.recipeFitnesses.size()];

        String[] testStringNames = {"n1","n2", "n3"};
        String[] testStringFitnesses = {"f1","f2", "f3"};

        names = (String[])storage.recipeNames.toArray(names);
        fitnesses = (String[])storage.recipeFitnesses.toArray(fitnesses);

        RecipeArrayAdapter adapter = new RecipeArrayAdapter(this, testStringNames, testStringFitnesses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id){
                final String item = (String)parent.getItemAtPosition(position);

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
        Intent intent = new Intent(getApplicationContext(), RecipeEditActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onInstructionClick() {
        Intent intent = new Intent(getApplicationContext(), InstructionActivity.class);
        startActivityForResult(intent, 0);
    }
}
