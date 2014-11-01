package net.lion_stuido.lionstudio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.model.Photo;
import net.lion_stuido.lionstudio.utils.AppController;

import java.util.ArrayList;

import static net.lion_stuido.lionstudio.utils.Settings.getPicturesDomain;

/**
 * Created by lester on 12.10.14.
 */
public class PhotoGridAdapter extends ArrayAdapter<Photo> {
    private final LayoutInflater mLayoutInflater;
    private ViewHolder holder;
    private Context context;

    public PhotoGridAdapter(Context context, int layoutResourceId,
                            ArrayList<Photo> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = mLayoutInflater.inflate(R.layout.photo_row_grid_item,
                    parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.photo_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Photo currentPhoto = getItem(position);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(getPicturesDomain(context) + currentPhoto.getFilename(), ImageLoader.getImageListener(holder.image,
                R.drawable.ic_default, R.drawable.ic_error));

        return row;
    }

    static class ViewHolder {
        ImageView image;
    }
}