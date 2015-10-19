package magcomm.android.com.sharesdk.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import magcomm.android.com.sharesdk.R;
import magcomm.android.com.sharesdk.ui.fragment.GuideFragment;
import magcomm.android.com.sharesdk.ui.fragment.SplashFragment;
import magcomm.android.com.sharesdk.util.SharedUtils;

/**
 * Created by lenovo on 15-10-19.
 */
public class MainActivity extends FragmentActivity{

    private static final String TAG = MainActivity.class.getSimpleName();

    private SplashFragment mSplashFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            /*
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            */
        }
        setContentView(R.layout.activyt_guider);
        FragmentManager fm = getSupportFragmentManager();

        if (SharedUtils.isFirstLaunch(this)) {
            splash(fm);
        } else {
            guide(fm);
        }
    }

    void splash(FragmentManager fm) {

        mSplashFragment = new SplashFragment();

        if (fm.findFragmentByTag(SplashFragment.TAG) == null) {
            fm.beginTransaction()
                    .add(R.id.container, mSplashFragment, SplashFragment.TAG)
                    .commit();
        }
    }

    void guide(FragmentManager fm) {
        if (fm.findFragmentByTag(GuideFragment.TAG) == null) {
            fm.beginTransaction()
                    .add(R.id.container, new GuideFragment(), GuideFragment.TAG)
                    .commit();
        }
    }

    public void doClose(View view){
        Log.i(TAG, "doClose is called....");
        finish();
    }
}
