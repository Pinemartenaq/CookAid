package wtmpd.cookaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InstructionActivity extends AppCompatActivity implements NavigationBar.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
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

    }
}
