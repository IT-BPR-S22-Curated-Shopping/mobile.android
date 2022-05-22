package mobile.android.prototype.ui.profile;

import android.content.Context;
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
    private Context context;

    public DeckAdapter(List<CardItemModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
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
        ((TextView) v.findViewById(R.id.tags)).setText(data.get(position).getTags());

        Glide
                .with(context)
                .load(data.get(position).getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.loading_spinner)
                .into((ImageView) v.findViewById(R.id.idIVCourse));

        return v;
    }
}
