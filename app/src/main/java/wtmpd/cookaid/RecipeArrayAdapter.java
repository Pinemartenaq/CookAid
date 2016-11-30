package wtmpd.cookaid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Manit on 2016-11-29.
 */

public class RecipeArrayAdapter extends ArrayAdapter {
    private final Context context;
    private final String[] names;
    private final String[] fitnesses;

    public RecipeArrayAdapter(Context context, String[] names, String[] fitnesses){
        super(context, R.layout.fragment_recipe_item,names);
        this.context = context;
        this.names = names;
        this.fitnesses = fitnesses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_recipe_item,parent,false);
        TextView nameView = (TextView) rowView.findViewById(R.id.nameText);
        TextView fitnessView = (TextView) rowView.findViewById(R.id.fitnessText);
        nameView.setText(names[position]);
        fitnessView.setText(fitnesses[position]);

        return rowView;
    }
}
