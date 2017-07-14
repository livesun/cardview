package livesun.cardpager.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import livesun.cardpager.R;

/**
 * Created by 29028 on 2017/7/14.
 * 自定义圆点指示器
 */

public class IndicatorView extends View {

    private int mDefultColor;
    private int mSelectColor;
    private int mCount;//数量
    private int mPading,mRadius;
    private Paint mDefultPaint;
    private Paint mSelectPaint;
    private int checkPosition=0;
    public IndicatorView(Context context) {
        this(context,null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray td = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        mDefultColor = td.getColor(R.styleable.IndicatorView_indicator_defaultColor, Color.WHITE);
        mSelectColor = td.getColor(R.styleable.IndicatorView_indicator_selectColor, Color.BLACK);
        mCount = td.getInteger(R.styleable.IndicatorView_indicator_count, 1);
        mPading = td.getDimensionPixelSize(R.styleable.IndicatorView_indicator_pading, 0);
        mRadius = td.getDimensionPixelSize(R.styleable.IndicatorView_indicator_radius, px2dp(5));
        td.recycle();
        mDefultPaint= getPaint(mDefultColor);
        mSelectPaint = getPaint(mSelectColor);
    }

    private Paint getPaint(int color){
        Paint paint=  new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heighMode = MeasureSpec.getMode(heightMeasureSpec);
        if(heighMode==MeasureSpec.AT_MOST){
            int height=mRadius*2+getPaddingTop()+getPaddingBottom();
            heightSize=Math.min(heightSize,height);
        }
        if(widthMode==MeasureSpec.AT_MOST){
            widthSize=mRadius*2*mCount+mPading*(mCount-1)+getPaddingLeft()+getPaddingRight();
        }
        setMeasuredDimension(widthSize,heightSize);
    }

    private int px2dp(int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,px,getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startX = getPaddingLeft();
        float startY = getPaddingTop();
        for(int i=0;i<mCount;i++){

            int cx= (int) (startX+mRadius+i*(2*mRadius+mPading));
            int cy= (int) (startY+mRadius);
            if(i==checkPosition){
                canvas.drawCircle(cx,cy,mRadius,mSelectPaint);
            }else{
                canvas.drawCircle(cx,cy,mRadius,mDefultPaint);
            }

        }
    }

    public void setCheckPosition(int checkPosition){
        this.checkPosition=checkPosition;
        invalidate();
    }

    public void setCount(int count){
        mCount=count;
    }
}
