package net.lion_stuido.lionstudio.adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.fragments.PhotoCommentFragment;
import net.lion_stuido.lionstudio.model.Photo;
import net.lion_stuido.lionstudio.model.ResponseStatus;
import net.lion_stuido.lionstudio.utils.AppController;
import net.lion_stuido.lionstudio.utils.GsonRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.senab.photoview.PhotoViewAttacher;

import static net.lion_stuido.lionstudio.utils.Constants.DEFAULT_DOMAIN;
import static net.lion_stuido.lionstudio.utils.Constants.PHOTO_URL;
import static net.lion_stuido.lionstudio.utils.Constants.getPicturesDomain;

/**
 * Created by lester on 19.10.14.
 */
public class FullScreenImageAdapter extends PagerAdapter {
    private final String TAG = "FullScreenImageAdapter";
    private List<Photo> photoList;
    private Activity activity;
    private PhotoViewAttacher mAttacher;
    private Button like;
    private Button comment;

    public FullScreenImageAdapter(List<Photo> photoList, Activity activity) {
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

        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);
        imgDisplay = (ImageView) viewLayout.findViewById(R.id.photo_full_size);
        like = (Button) viewLayout.findViewById(R.id.button_like);
        comment = (Button) viewLayout.findViewById(R.id.button_comment);
        Photo currentPhoto = photoList.get(position);
        setButtonLikeListener(currentPhoto.getId());
        setButtonCommentListener(currentPhoto);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(getPicturesDomain(activity) + currentPhoto.getFilename(), ImageLoader.getImageListener(imgDisplay,
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

    private void setButtonLikeListener(final int photo_id) {
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("photoid", String.valueOf(photo_id));
                GsonRequest<ResponseStatus> photosReq = new GsonRequest<ResponseStatus>(DEFAULT_DOMAIN + PHOTO_URL,
                        ResponseStatus.class, headers, null, new Response.Listener<ResponseStatus>() {
                    @Override
                    public void onResponse(ResponseStatus responseStatus) {
                        if (responseStatus.getStatus().equals("OK"))
                            Toast.makeText(activity, "Вам понравилось фото!", Toast.LENGTH_LONG).show();
                        else Toast.makeText(activity, "Ошибка", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                });
                AppController.getInstance().addToRequestQueue(photosReq);
            }
        });
    }

    private void setButtonCommentListener(final Photo photo) {
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoCommentFragment commentFragment = PhotoCommentFragment.newInstance(photo);
                FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, commentFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
