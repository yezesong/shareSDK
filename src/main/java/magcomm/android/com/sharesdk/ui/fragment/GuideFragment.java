package magcomm.android.com.sharesdk.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import magcomm.android.com.sharesdk.R;
import magcomm.android.com.sharesdk.views.FormView;

/**
 * Created by lenovo on 15-10-19.
 */
public class GuideFragment extends Fragment {
    public static final String TAG = GuideFragment.class.getSimpleName();
    public static final String VIDEO_NAME = "welcome_video.mp4";
    private Activity mActivity;
    private View mRootView;
    private VideoView mVideoView;
    private InputType inputType = InputType.NONE;
    private Button buttonLeft, buttonRight;
    private FormView formView;
    private ViewGroup contianer;
    private TextView appName;
    enum InputType {
        NONE, LOGIN, SIGN_UP;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_guider, container, false);

        mActivity = getActivity();
        findView();

        File videoFile = mActivity.getFileStreamPath(VIDEO_NAME);
        if (!videoFile.exists()) {
            videoFile = copyVideoFile();
        }

        playVideo(videoFile);

        playAnim();
        return mRootView;
    }

    private void findView(){
        mVideoView = (VideoView) mRootView.findViewById(R.id.videoView);
        buttonLeft = (Button) mRootView.findViewById(R.id.buttonLeft);
        buttonRight = (Button) mRootView.findViewById(R.id.buttonRight);
        contianer = (ViewGroup) mRootView.findViewById(R.id.container);
        formView = (FormView) mRootView.findViewById(R.id.formView);
        appName = (TextView) mRootView.findViewById(R.id.appName);
        formView.post(new Runnable() {
            @Override
            public void run() {
                int delta = formView.getTop() + formView.getHeight();
                formView.setTranslationY(-1 * delta);
            }
        });
    }


    private void playVideo(File videoFile) {
        mVideoView.setVideoPath(videoFile.getPath());
        mVideoView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        });
    }


    private void playAnim() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(appName, "alpha", 0,1);
        anim.setDuration(4000);
        anim.setRepeatCount(1);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.start();
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                appName.setVisibility(View.INVISIBLE);
            }
        });
    }

    @NonNull
    private File copyVideoFile() {
        File videoFile;
        try {
            FileOutputStream fos = mActivity.openFileOutput(VIDEO_NAME, Context.MODE_PRIVATE);
            InputStream in = getResources().openRawResource(R.raw.welcome_video);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = in.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        videoFile = mActivity.getFileStreamPath(VIDEO_NAME);
        if (!videoFile.exists())
            throw new RuntimeException("video file has problem, are you sure you have welcome_video.mp4 in res/raw folder?");
        return videoFile;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mVideoView.stopPlayback();
    }
}
