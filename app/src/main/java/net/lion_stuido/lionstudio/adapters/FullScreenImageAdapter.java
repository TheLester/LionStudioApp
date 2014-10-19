package net.lion_stuido.lionstudio.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.model.Photo;
import net.lion_stuido.lionstudio.utils.AppController;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

import static net.lion_stuido.lionstudio.utils.Constants.DEFAULT_DOMAIN;

/**
 * Created by lester on 19.10.14.
 */
public class FullScreenImageAdapter extends PagerAdapter {
    private List<Photo> photoList;
    private Activity activity;
    private PhotoViewAttacher mAttacher;
    public FullScreenImageAdapter(List<Photo> photoList,Activity activity) {
        this.photoList = photoList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;
        ImageButton like;
        ImageButton comment;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);
        imgDisplay = (ImageView) viewLayout.findViewById(R.id.photo_full_size);
        like = (ImageButton) viewLayout.findViewById(R.id.imageButtonLike);
        comment = (ImageButton) viewLayout.findViewById(R.id.imageButtonComment);
        Photo currentPhoto = photoList.get(position);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(DEFAULT_DOMAIN+currentPhoto.getFilename(), ImageLoader.getImageListener(imgDisplay,
                R.drawable.ic_default, R.drawable.ic_error));
        mAttacher = new PhotoViewAttacher(imgDisplay);
        ((ViewPager) container).addView(viewLayout);
        return viewLayout;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
