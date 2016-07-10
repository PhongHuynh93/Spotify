package dhbk.android.spotify;

import android.media.Image;

import java.util.List;

/**
 * Created by huynhducthanhphong on 7/10/16.
 */

public class Artist extends ArtistSimple {
    public Followers followers;
    public List<String> genres;
    public List<Image> images;
    public Integer popularity;

    public Artist() {
    }
}
