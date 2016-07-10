package dhbk.android.spotify.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.spotify.R;
import dhbk.android.spotify.adapters.TopArtistTracksAdapter;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.android.MainThreadExecutor;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopArtistSongsFragment extends BaseListFragment implements OnSearchArtistTopTracksListener  {


    @BindView(R.id.top_artist_songs_list)
    ListView topArtistSongsList;
    @BindView(R.id.top_track_error_text)
    TextView errorText;
    private TopArtistTracksAdapter topArtistSongsAdapter;

    public TopArtistSongsFragment newInstance() {
        return new TopArtistSongsFragment();
    }
    public TopArtistSongsFragment() {
        // Required empty public constructor
    }

    // declare adapter
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topArtistSongsAdapter = new TopArtistTracksAdapter();
    }


    @Override
    protected int fragmentLayoutResource() {
        return R.layout.fragment_top_artist_songs;
    }

    @Override
    protected boolean activateRetainInstance() {
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topArtistSongsList.setAdapter(topArtistSongsAdapter);
    }


    // TODO: 7/10/16 after search list of artist, listen when click a particle artist, open a top strack of that artist
    public void onSearchArtistTopTracks(String artistId) {
        getArtistTopTrack(artistId);
    }

    private void getArtistTopTrack(String artistId) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.downloading_title));

        SpotifyApi spotifyApi = new SpotifyApi(Executors.newSingleThreadExecutor(), new MainThreadExecutor());
        SpotifyService spotifyService = spotifyApi.getService();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("country", Locale.getDefault().getCountry());
        spotifyService.getArtistTopTrack(artistId, parameters, new Callback<Tracks>() {
            @Override
            public void success(final Tracks tracks, Response response) {

                if (response.getStatus() == 200) {
                    if (tracks != null && getActivity() != null) {

                        progressDialog.dismiss();
                        errorText.setVisibility(View.GONE);
                        topArtistSongsAdapter.setItems(tracks.tracks);

                    } else {
                        topArtistSongsAdapter.clearData();
                        progressDialog.dismiss();
                        errorText.setVisibility(View.VISIBLE);
                    }
                } else {
                    progressDialog.dismiss();
                    errorText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                errorText.setVisibility(View.VISIBLE);

            }
        });
    }
}
