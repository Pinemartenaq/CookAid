package wtmpd.cookaid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NavigationBar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NavigationBar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationBar extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    ImageButton homeBttn;
    ImageButton searchBttn;
    ImageButton addBttn;
    ImageButton instructionBttn;

    public NavigationBar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NavigationBar.
     */
    // TODO: Rename and change types and number of parameters
    public static NavigationBar newInstance() {
        NavigationBar fragment = new NavigationBar();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation_bar, container, false);

        homeBttn = (ImageButton)view.findViewById(R.id.homeButton);
        searchBttn = (ImageButton)view.findViewById(R.id.searchButton);
        addBttn = (ImageButton)view.findViewById(R.id.addButton);
        instructionBttn = (ImageButton)view.findViewById(R.id.instructionButton);

        homeBttn.setOnClickListener(this);
        searchBttn.setOnClickListener(this);
        addBttn.setOnClickListener(this);
        instructionBttn.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.homeButton:
                mListener.onHomeClick();
                break;
            case R.id.searchButton:
                mListener.onSearchClick();
                break;
            case R.id.addButton:
                mListener.onAddClick();
                break;
            case R.id.instructionButton:
                mListener.onInstructionClick();
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onHomeClick();
        void onSearchClick();
        void onAddClick();
        void onInstructionClick();
    }
}
