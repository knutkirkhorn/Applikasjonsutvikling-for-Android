package me.kirkhorn.knut.assignment_4;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Knut on 17.10.2017.
 */

public class ImageFragment extends Fragment {
    private TextView textViewMovieDescription;
    private ImageView imageView;

    public ImageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_layout, container, false);
        textViewMovieDescription = (TextView) view.findViewById(R.id.fragment_image_description);
        imageView = (ImageView) view.findViewById(R.id.fragment_image_view);
        return view;
    }

    public void changeImage(int resourceId, String movieDescription) {
        imageView.setImageResource(resourceId);
        textViewMovieDescription.setText(movieDescription);
    }
}
