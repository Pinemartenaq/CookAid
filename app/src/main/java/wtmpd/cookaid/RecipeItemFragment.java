package wtmpd.cookaid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeItemFragment extends Fragment implements View.OnClickListener{

    Recipe recipe;
    int fitness;

    LinearLayout result;

    private OnFragmentInteractionListener mListener;

    public RecipeItemFragment() {
        // Required empty public constructor
    }

    public static RecipeItemFragment newInstance(Recipe recipe, int fitness) {
        RecipeItemFragment fragment = new RecipeItemFragment();

        return fragment;
    }

    public void addRecipe(Recipe recipe){ this.recipe = recipe; }
    public void addFitness(int fitness){ this.fitness = fitness; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_item, container, false);

        result = (LinearLayout) view.findViewById(R.id.recipeResult);
        result.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.recipeResult:
                mListener.onResultClick();
                break;
            default:
                break;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onResultClick();
    }
}
