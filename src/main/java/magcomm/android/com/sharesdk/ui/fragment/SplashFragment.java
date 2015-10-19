package magcomm.android.com.sharesdk.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import magcomm.android.com.sharesdk.R;
import magcomm.android.com.sharesdk.views.CircleProgressBar;

/**
 * Created by lenovo on 15-10-19.
 */
public class SplashFragment extends Fragment {
    public static final String TAG = SplashFragment.class.getSimpleName();
    private static final int MSG_WAIT = 0x001;
    private CircleProgressBar mProgressBar;
    private View mRootView;
    private int pro = 0;

    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_WAIT:
                    mProgressBar.setProgress(pro);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_splash, container, false);

        mProgressBar = (CircleProgressBar) mRootView.findViewById(R.id.circle_progressbar);
        mProgressBar.setMax(100);

        start();
        return mRootView;
    }

    private void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int max = mProgressBar.getMax();
                try {
                    //子线程循环间隔消息
                    while (pro < max) {
                        pro += 20;
                        Message msg = new Message();
                        msg.what = MSG_WAIT;
                        mHandle.sendMessage(msg);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
