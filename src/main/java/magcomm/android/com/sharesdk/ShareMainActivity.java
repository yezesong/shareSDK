package magcomm.android.com.sharesdk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by lenovo on 15-10-17.
 */
public class ShareMainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化SDK
        ShareSDK.initSDK(getApplicationContext());
        setContentView(R.layout.main_layout);
    }

    public void doshared(View view){
        OnekeyShare onekeyShare = new OnekeyShare();
        onekeyShare.setTitle("一键分享");
        onekeyShare.setText("今天是星期六，第一次测试ShareSDK，我为技术代言!!!!!!");
        //onekeyShare.setImagePath("");
        onekeyShare.setImageUrl("http://7xnkdb.com1.z0.glb.clouddn.com/002.jpg");
        onekeyShare.show(ShareMainActivity.this);
    }
}
