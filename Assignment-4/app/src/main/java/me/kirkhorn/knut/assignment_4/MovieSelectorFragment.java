package me.kirkhorn.knut.assignment_4;

import android.app.Activity;
import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Knut on 17.10.2017.
 */

public class MovieSelectorFragment extends ListFragment {
    private String[] movieTitles;
    private String[] movieDescriptions;
    private OnFragmentMovieInteractionListener movieListener;

    public MovieSelectorFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources resources = getResources();
        movieTitles = resources.getStringArray(R.array.move_titles);
        movieDescriptions = resources.getStringArray(R.array.movie_description);
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                                                               android.R.id.text1, movieTitles));
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            movieListener = (OnFragmentMovieInteractionListener) activity;
        } catch (ClassCastException cce) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentMovieInteractionListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        movieListener.OnFragmentMovieInteractionListener(position, movieDescriptions[position]);
    }

    public interface OnFragmentMovieInteractionListener {
        void OnFragmentMovieInteractionListener(int resourceId, String movieDescription);
    }
}
