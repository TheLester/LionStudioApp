package net.lion_stuido.lionstudio.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.model.NavDrawerItem;

import java.util.List;

/**
 * Created by lester on 14.10.14.
 */
public class NavDrawerListAdapter extends ArrayAdapter<NavDrawerItem> {
    Context context;
    List<NavDrawerItem> drawerItemList;
    int layoutResID;

    public NavDrawerListAdapter(Context context, int layoutResourceID,
                               List<NavDrawerItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (TextView) view
                    .findViewById(R.id.title_drawer_item);
            drawerHolder.icon = (ImageView) view.findViewById(R.id.icon_drawer_item);

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }

        NavDrawerItem dItem = (NavDrawerItem) this.drawerItemList.get(position);

        drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                dItem.getIcon()));
        drawerHolder.ItemName.setText(dItem.getTitle());

        return view;
    }

    private static class DrawerItemHolder {
        TextView ItemName;
        ImageView icon;
    }
}
