package mobile.android.prototype.ui.profile;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import mobile.android.prototype.R;

import java.util.ArrayList;
import java.util.List;

public class DeckAdapter extends BaseAdapter {

    private List<CardItemModel> data;
    private final Context context;

    public DeckAdapter(List<CardItemModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (data != null)
            return data.size();
        return 0;
    }

    @Override
    public CardItemModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_profile_carditem, parent, false);
        }
        ((TextView) v.findViewById(R.id.product_name)).setText(data.get(position).getName());

        ((TextView) v.findViewById(R.id.tags)).setText(data.get(position).toTagString());

        if (data.get(position).getImageUrl() != null) {
            Glide
                    .with(context)
                    .load(data.get(position).getImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.loading_spinner)
                    .into((ImageView) v.findViewById(R.id.idIVCourse));
        }


        return v;
    }

    public void addItems(List<CardItemModel> cardItemModels) {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.addAll(cardItemModels);
        notifyDataSetInvalidated();
    }
}
