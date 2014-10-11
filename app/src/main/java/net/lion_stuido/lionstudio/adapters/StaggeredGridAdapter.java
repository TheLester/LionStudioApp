package net.lion_stuido.lionstudio.adapters;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.etsy.android.grid.util.DynamicHeightImageView;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.model.Album;
import net.lion_stuido.lionstudio.utils.AppController;

import java.util.ArrayList;
import java.util.Random;

import static net.lion_stuido.lionstudio.utils.Constants.DEFAULT_DOMAIN;

/**
 * Created by lester on 10.10.14.
 */
public class StaggeredGridAdapter extends ArrayAdapter<Album> {

    private static final String TAG = "SampleAdapter";

    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
    private ViewHolder vh;

    public StaggeredGridAdapter(Context context, int textViewResourceId,
                                ArrayList<Album> objects) {
        super(context, textViewResourceId, objects);
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mRandom = new Random();
    }

    @Override
    public View getView(final int position, View convertView,
                        final ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.row_grid_item,
                    parent, false);
            vh = new ViewHolder();
            vh.imgView = (DynamicHeightImageView) convertView
                    .findViewById(R.id.imgView);
            vh.description = (TextView) convertView.findViewById(R.id.description);
            vh.date = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        double positionHeight = getPositionRatio(position);

        vh.imgView.setHeightRatio(1.0);
        Album currentAlbum = getItem(position);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(DEFAULT_DOMAIN+currentAlbum.getAva(), ImageLoader.getImageListener(vh.imgView,
                R.drawable.ic_default, R.drawable.ic_error));
        vh.date.setText(currentAlbum.getData());
        vh.description.setText(currentAlbum.getName());
        return convertView;
    }

    static class ViewHolder {
        DynamicHeightImageView imgView;
        TextView date;
        TextView description;

    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5
        // the width
    }
}
