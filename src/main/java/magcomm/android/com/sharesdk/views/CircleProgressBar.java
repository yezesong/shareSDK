package magcomm.android.com.sharesdk.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

import magcomm.android.com.sharesdk.R;

/**
 * Created by lenovo on 15-10-19.
 */
public class CircleProgressBar extends ProgressBar {
    private static final String TAG = CircleProgressBar.class.getSimpleName();

    private int mOutSideColor;
    private int mInnerColor;
    private int mSideWidth;
    private int mTextSize;

    private int mWidth;
    private int mHeight;
    private int mCenterX;
    private int mCenterY;
    private int mRadius;

    private Paint mPaint;
    private Paint mTextPaint;
    private RectF mRect;
    private Rect mTextBound = new Rect();
    private String mProgressText;

    public CircleProgressBar(Context context) {
        super(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        mOutSideColor = typedArray.getColor(R.styleable.CircleProgressBar_outcircle,
                getResources().getColor(R.color.outcircle_default));

        mInnerColor = typedArray.getColor(R.styleable.CircleProgressBar_innercircle,
                getResources().getColor(R.color.innercircle_default));

        mSideWidth = typedArray.getDimensionPixelOffset(R.styleable.CircleProgressBar_sidewidth,
                getResources().getDimensionPixelOffset(R.dimen.circleside_default));

        mTextSize = typedArray.getDimensionPixelSize(R.styleable.CircleProgressBar_progress_text_size,
                getResources().getDimensionPixelOffset(R.dimen.progress_text_default));
        typedArray.recycle();

        mPaint = new Paint();
        mRect = new RectF();

        mTextPaint = new Paint();
        mTextPaint.setColor(getResources().getColor(R.color.progress_text));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasure = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode == MeasureSpec.EXACTLY){
            mWidth = widthMeasure;
        }else{
            mWidth = getPaddingLeft() + widthMeasure + getPaddingRight();
        }

        if(heightMode == MeasureSpec.EXACTLY){
            mHeight = heightMeasure;
        }else{
            mHeight = getPaddingTop() + mHeight + getPaddingBottom();
        }

        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
        mRadius = Math.min(mWidth, mHeight) / 2 - mSideWidth;

        mRect.set(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
        setMeasuredDimension(widthMeasure, heightMeasure);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Log.i(TAG, "onDraw is called getProgress = " + getProgress() + " and max = " + getMax());
        float sweep = getProgress() / getMax();
        mProgressText = getProgress() + "%";

        // 得到text绘制范围
        mTextPaint.getTextBounds(mProgressText, 0, mProgressText.length(), mTextBound);

        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mOutSideColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mSideWidth);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
        mPaint.setAntiAlias(true);

        mPaint.setColor(mInnerColor);
        android.util.Log.e("wangcunxi","getProgress() * 36f"+getProgress() * 36f);
        canvas.drawArc(mRect, -90f, getProgress() * 3.6f, false, mPaint);

        canvas.drawText(mProgressText, mCenterX - mTextBound.width() /2, mCenterY + mTextBound.height()/2, mTextPaint);
    }
}
