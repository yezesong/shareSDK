package magcomm.android.com.sharesdk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by lenovo on 15-10-19.
 */
public class SharedUtils {
    public static final String TAG = SharedUtils.class.getSimpleName();
    /**
     * Boolean indicating whether launch the app first time
     */
    public static final String SHARED_PREF_IS_FIRST_LAUNCH = "shared_pref_is_first_launch";

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * whether launch the app first time
     *
     * @param context
     * @return
     */
    public static boolean isFirstLaunch(final Context context) {
        SharedPreferences sp = getDefaultSharedPreferences(context);
        return sp.getBoolean(SHARED_PREF_IS_FIRST_LAUNCH, true);
    }

    /**
     * mark app launched
     *
     * @param context
     */
    public static void markFirstLaunch(final Context context) {
        SharedPreferences sp = getDefaultSharedPreferences(context);
        sp.edit().putBoolean(SHARED_PREF_IS_FIRST_LAUNCH, false).apply();
    }

}
