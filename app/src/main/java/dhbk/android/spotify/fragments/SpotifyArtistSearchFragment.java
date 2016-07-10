package dhbk.android.spotify.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import dhbk.android.spotify.R;
import dhbk.android.spotify.adapters.ArtistSearchAdapter;
import dhbk.android.spotify.interfaces.OnSearchItemClickListener;
import dhbk.android.spotify.utils.Utils;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.android.MainThreadExecutor;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpotifyArtistSearchFragment extends BaseListFragment {

    @BindView(R.id.artist_search_list)
    ListView artistAlbumsList;
    @BindView(R.id.artist_search_error_text)
    TextView errorText;

    private OnSearchItemClickListener onSearchItemClickListener;
    private ArtistSearchAdapter artistSearchAdapter;

    public SpotifyArtistSearchFragment() {
        // Required empty public constructor
    }

    public static SpotifyArtistSearchFragment newInstance() {
        return new SpotifyArtistSearchFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchItemClickListener) {
            onSearchItemClickListener = (OnSearchItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artistSearchAdapter = new ArtistSearchAdapter();
    }

    @Override
    protected boolean activateRetainInstance() {
        return true;
    }

    // TODO: 7/10/16 this call onCreateView in baseFragment
    @Override
    protected int fragmentLayoutResource() {
        return R.layout.fragment_spotify_artist_search;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Gson gson = new GsonBuilder().create();
        Type artistAdapterType = new TypeToken<List<Artist>>() {
        }.getType();
        String adapterItems = gson.toJson(artistSearchAdapter.getItems(), artistAdapterType);
        outState.putString("adapter_items", adapterItems);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        artistAlbumsList.setAdapter(artistSearchAdapter);
        // TODO: 7/10/16 start search on spotify
        startArtistSearch((EditText) view.findViewById(R.id.search_spotify_streamer), view.getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.getString("adapter_items") != null) {
                Gson gson = new GsonBuilder().create();
                Type artistAdapterType = new TypeToken<List<Artist>>() {
                }.getType();
                List<Artist> artistList = gson.fromJson(savedInstanceState.getString("adapter_items"), artistAdapterType);
                artistSearchAdapter.setItems(artistList);
            }
        }
    }

    // start search on internet
    private void startArtistSearch(final EditText artistSearcher, final Context context) {
        artistSearcher.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        searchArtistAlbums(context, artistSearcher.getText().toString());
                        Utils.hideKeyBoard(textView);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    // search on spotify to find out artist album
    private void searchArtistAlbums(Context context, String artist) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.downloading_title));

        SpotifyApi spotifyApi = new SpotifyApi(Executors.newSingleThreadExecutor(), new MainThreadExecutor());

        SpotifyService spotifyService = spotifyApi.getService();
        spotifyService.searchArtists(artist, new Callback<ArtistsPager>() {
            @Override
            public void success(final ArtistsPager artistsPager, Response response) {

                if (response.getStatus() == 200) {
                    if (artistsPager != null && getActivity() != null) {
                        if (artistsPager.artists.items.size() > 0) {
                            errorText.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            artistSearchAdapter.setItems(artistsPager.artists.items);

                        } else {
                            artistSearchAdapter.clearData();
                            progressDialog.dismiss();
                            errorText.setVisibility(View.VISIBLE);
                        }
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
