package net.lion_stuido.lionstudio.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lester on 11.10.14.
 */
public abstract class Constants {
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_DOMAIN = "pict_domain";

    public static final String DEFAULT_DOMAIN = "http://lion-studio.net/android";
    public static final String SETTING_URL = "/setting.php";
    public static final String ALBUM_URL = "/album.php";
    public  static final String PHOTO_URL = "/photos.php";
    public static final String COMMENT_URL = "/comments.php";

    public static String getPicturesDomain(Context context){
        SharedPreferences mSettings =  context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return mSettings.getString(APP_PREFERENCES_DOMAIN,"");
    }
}
