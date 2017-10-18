package me.kirkhorn.knut.assignment_4;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.StyleableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MovieSelectorFragment.OnFragmentMovieInteractionListener{
    @StyleableRes private int selectedImage = 0;
    private TypedArray movieCovers;
    private String[] movieTitles;
    private String[] movieDescriptions;
    private ImageFragment imageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources resources = getResources();
        movieCovers = resources.obtainTypedArray(R.array.movie_covers);
        movieTitles = resources.getStringArray(R.array.move_titles);
        movieDescriptions = resources.getStringArray(R.array.movie_description);
        imageFragment = (ImageFragment) getFragmentManager().findFragmentById(R.id.image_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void previousImage() {
        if (selectedImage <= 0) {
            Toast toast = Toast.makeText(this, "Dette er den første filmen i denne appen", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            selectedImage--;
            TextView movieTitle = (TextView) findViewById(R.id.textView_movie_title);
            movieTitle.setText(movieTitles[selectedImage]);
            OnFragmentMovieInteractionListener(selectedImage, movieDescriptions[selectedImage]);
        }
    }

    public void nextImage() {
        if (selectedImage >= movieTitles.length - 1) {
            Toast toast = Toast.makeText(this, "Dette er den siste filmen i denne appen", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            selectedImage++;
            TextView movieTitle = (TextView) findViewById(R.id.textView_movie_title);
            movieTitle.setText(movieTitles[selectedImage]);
            OnFragmentMovieInteractionListener(selectedImage, movieDescriptions[selectedImage]);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.menu_previous:
                previousImage();
                return true;
            case R.id.menu_next:
                nextImage();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void OnFragmentMovieInteractionListener(int resourceId, String movieDescription) {
        selectedImage = resourceId;
        int resourceID = movieCovers.peekValue(resourceId).resourceId;
        imageFragment.changeImage(resourceID, movieDescription);
    }
}
