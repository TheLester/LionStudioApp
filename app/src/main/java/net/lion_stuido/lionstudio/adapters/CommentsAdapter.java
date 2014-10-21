package net.lion_stuido.lionstudio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.model.Comment;
import net.lion_stuido.lionstudio.model.Photo;
import net.lion_stuido.lionstudio.utils.AppController;

import java.util.List;

import static net.lion_stuido.lionstudio.utils.Constants.DEFAULT_DOMAIN;

/**
 * Created by lester on 21.10.14.
 */
public class CommentsAdapter extends BaseAdapter {
    private final int TYPE_PHOTO = 0;
    private final int TYPE_LIKE_NUM = 1;
    private final int TYPE_COMMENT = 2;
    private final int VIEW_NUM = 3;

    private Context context;
    private Photo photo;
    private List<Comment> commentList;
    private static LayoutInflater inflater = null;

    public CommentsAdapter(Context context, Photo photo, List<Comment> commentList) {
        this.context = context;
        this.photo = photo;
        this.commentList = commentList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(Comment item) {
        commentList.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return commentList.size() + 2;
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_NUM;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_PHOTO;
        else if (position == 1) return TYPE_LIKE_NUM;
        else return TYPE_COMMENT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        int type = getItemViewType(position);
        if (type == TYPE_PHOTO) {
            // Inflate the layout with image
            v = inflater.inflate(R.layout.list_row_image, parent, false);
            ImageView photoView = (ImageView) v.findViewById(R.id.photo_comments);
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            imageLoader.get(DEFAULT_DOMAIN + photo.getFilename(), ImageLoader.getImageListener(photoView,
                    R.drawable.ic_default, R.drawable.ic_error));
        } else if (type == TYPE_LIKE_NUM) {
            v = inflater.inflate(R.layout.list_row_likes, parent, false);
            TextView likes = (TextView) v.findViewById(R.id.text_likes_num);
            likes.setText(String.valueOf(photo.getLike()) + " лайк(ов)");
        } else {
            v = inflater.inflate(R.layout.list_row_comment, parent, false);
            TextView comment = (TextView) v.findViewById(R.id.text_comment);
            TextView commentDate = (TextView) v.findViewById(R.id.text_comment_date);
            Comment currentComment = commentList.get(position - TYPE_COMMENT);
            comment.setText(currentComment.getText());
            commentDate.setText(currentComment.getData());
        }

        return v;
    }
}