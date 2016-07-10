package dhbk.android.spotify.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import dhbk.android.spotify.R;
import dhbk.android.spotify.fragments.TopArtistSongsFragment;
import dhbk.android.spotify.interfaces.OnSearchItemClickListener;

public class SearcherActivity extends BaseActivity implements OnSearchItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcher);
    }

    @Override
    public void onSearchItemClick(String artistId) {
        TopArtistSongsFragment topArtistSongsFragment = (TopArtistSongsFragment) getSupportFragmentManager().findFragmentById(R.id.top_artist_songs_fragment);
        if (topArtistSongsFragment == null) {
            Intent topArtistSongsIntent = new Intent(SearcherActivity.this, TopArtistSongsActivity.class);
            topArtistSongsIntent.putExtra("artist_id", artistId);
            startActivity(topArtistSongsIntent);
            saveTypeDevice(false);
        } else {
            saveTypeDevice(true);
            topArtistSongsFragment.onSearchArtistTopTracks(artistId);
        }
    }


    private void saveTypeDevice(boolean isTablet){
        SharedPreferences sharedPreferences = getSharedPreferences("type_device",MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("type",isTablet).apply();
    }
}
