package magcomm.android.com.sharesdk.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by xuxinying on 15-10-19.
 */
public class ShimmerTextView extends TextView {
    private LinearGradient mGradient;
    private Matrix mMatrix;
    private Paint mPaint;

    private int mWidth;
    private int mHeight;
    private int mTranslate = 0;

    public ShimmerTextView(Context context) {
        super(context, null);
    }

    public ShimmerTextView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        mPaint = getPaint();
        mMatrix = new Matrix();
    }

    public ShimmerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasure = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(MeasureSpec.EXACTLY == widthMode){
            mWidth = widthMeasure;
        }else{
            mWidth = widthMeasure + getPaddingLeft() + getPaddingRight();
        }

        if(MeasureSpec.EXACTLY == heightMode){
            mHeight = heightMeasure;
        }else{
            mHeight = heightMeasure + getPaddingTop() + getPaddingBottom();
        }

        mGradient = new LinearGradient(-mWidth, 0, 0, 0, new int[]{0x33FFFFFF, 0xffffffff, 0x33ffffff}, null, LinearGradient.TileMode.CLAMP);
        mPaint.setShader(mGradient);
        setMeasuredDimension(widthMeasure, heightMeasure);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mMatrix != null){
            mTranslate += mWidth / 10;
            if(mTranslate > 2 * mWidth){
                mTranslate = -mWidth;
            }

            mMatrix.setTranslate(mTranslate, 0);
            mGradient.setLocalMatrix(mMatrix);
            postInvalidateDelayed(50);
        }
    }
}
