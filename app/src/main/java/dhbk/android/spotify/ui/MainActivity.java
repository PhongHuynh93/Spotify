package dhbk.android.spotify.ui;

import android.content.Intent;
import android.view.View;

import dhbk.android.spotify.R;

public class MainActivity extends BaseActivity {
    // TODO: 7/10/16 this is oncreate view
    @Override
    int contentViewId() {
        return R.layout.activity_main;
    }

    @Override
    boolean activateBackButton() {
        return false;
    }

    // TODO: 7/10/16 start spotify
    public void onStartSpotifyStreamer(View view) {
        Intent spotifySearch = new Intent(MainActivity.this, SearcherActivity.class);
        startActivity(spotifySearch);
    }

}
