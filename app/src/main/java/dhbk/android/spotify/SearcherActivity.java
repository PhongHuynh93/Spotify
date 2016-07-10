package dhbk.android.spotify;

import android.content.Intent;
import android.os.Bundle;

public class SearcherActivity extends BaseActivity implements OnSearchItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcher);
    }

    @Override
    public void onSearchItemClick(String artistId) {
        TopArtistSongsFragment topArtistSongsFragment = (TopArtistSongsFragment)getSupportFragmentManager().findFragmentById(R.id.top_artist_songs_fragment);
        if(topArtistSongsFragment == null){
            Intent topArtistSongsIntent = new Intent(SearcherActivity.this, TopArtistSongsActivity.class);
            topArtistSongsIntent.putExtra("artist_id", artistId);
            startActivity(topArtistSongsIntent);
            saveTypeDevice(false);
        }else{
            saveTypeDevice(true);
            topArtistSongsFragment.onSearchArtistTopTracks(artistId);
        }
    }
}
