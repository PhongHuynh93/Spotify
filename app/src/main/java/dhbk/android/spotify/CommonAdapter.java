package dhbk.android.spotify;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huynhducthanhphong on 7/10/16.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    private List<T> items;

    public CommonAdapter(List<T> items) {
        this.items = items;
    }

    public CommonAdapter() {
        this.items = new ArrayList<>();
    }

    public List<T> getItems() {
        return this.items;
    }

    public void clearData() {
        if (this.items != null) {
            this.items.clear();
            notifyDataSetChanged();
        }
    }

    public void setItems(List<T> adapterItems) {
        if (adapterItems != null) {
            clearData();
            this.items.addAll(adapterItems);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return this.items == null ? 0 : this.items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.indexOf(items.get(i));
    }


}