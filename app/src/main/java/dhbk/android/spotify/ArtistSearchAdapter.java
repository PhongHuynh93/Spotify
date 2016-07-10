package dhbk.android.spotify;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huynhducthanhphong on 7/10/16.
 */
public class ArtistSearchAdapter extends CommonAdapter<Artist> {

    @Override
    public View getView(int i, View view, @Nullable ViewGroup viewGroup) {

        ArtistsSearchHolder artistsSearchHolder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spotify_streamer_row_layout, viewGroup, false);
            artistsSearchHolder = new ArtistsSearchHolder(view);
            view.setTag(artistsSearchHolder);
        } else {
            artistsSearchHolder = (ArtistsSearchHolder) view.getTag();
        }

        if (getItems().get(i).images.size() > 0 && getItems().get(i).images.get(1).url != null) {
            Picasso.with(viewGroup.getContext())
                    .load(getItems().get(i).images.get(1).url)
                    .resize(200, 200)
                    .into(artistsSearchHolder.artistsImage);
        }
        artistsSearchHolder.artistsName.setText(getItems().get(i).name);

        return view;
    }


    public static class ArtistsSearchHolder {
        @BindView(R.id.artist_image)
        ImageView artistsImage;
        @BindView(R.id.artist_name)
        TextView artistsName;

        public ArtistsSearchHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
