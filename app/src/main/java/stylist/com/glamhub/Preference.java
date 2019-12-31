package stylist.com.glamhub;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by OSCAR on 11/9/2016.
 */

public class Preference {

    static SharedPreferences pref;
    static SharedPreferences.Editor editor;
    Context _context;

    //SharedPref Mode
    int PRIVATE_MODE =0;

    // Shared preferences file name
    private static final String PREF_NAME = "welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    public Preference(Context context){
        this._context=context;
        pref=_context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=pref.edit();}


    public static void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public static boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}

