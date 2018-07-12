package com.example.realgodjj.rxjavademo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.realgodjj.rxjavademo.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author lesences  2018/6/28 10:26.
 */
public class TimeKeeperView extends View {
    private static final int DEFAULT_RADIUS = 100;
    private static final float DEFAULT_HOUR = 0.3f;
    private static final float DEFAULT_MIN = 0.6f;
    private static final float DEFAULT_SEC = 0.9f;
    private static final int DEFAULT_WIDTH_HOUR = 10;
    private static final int DEFAULT_WIDTH_MIN = 6;
    private static final int DEFAULT_WIDTH_SEC = 3;

    //表盘背景
    private int bgDrawable;
    //表盘颜色
    private int bgColor;
    //小时指针颜色
    private int hourColor;
    //中心点颜色
    private int markColor;
    //分钟指针颜色
    private int minColor;
    //标记点颜色
    private int pointColor;
    //秒数指针颜色
    private int secColor;
    //小时数字颜色
    private int textColor;

    //以下三个为指针长度比例(相对于表盘)
    private float hourRatio;
    private float minRatio;
    private float secRatio;

    //以下三个为指针宽度
    private int hourWidth;
    private int minWidth;
    private int secWidth;
    //中点半径
    private float markRadius;
    //表盘半径
    private float outRadius;
    //标记点半径
    private float pointRadius;

    //模糊半径
    private float lineBlurRadius;
    private float markBlurRadius;

    private float textSize;
    private float strTempH;

    private Paint radiusPaint;
    private Paint textPaint;
    private Paint linePaint;

    public TimeKeeperView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeKeeperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_SOFTWARE, null);    //关闭硬件加速

        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.TimeKeeperView));

        initPaint();
    }

    private void parseAttributes(TypedArray attributes) {
        bgColor = attributes.getColor(R.styleable.TimeKeeperView_tkv_backgroundColor, Color.WHITE);
//        bgDrawable = attributes.getResources(R.styleable.TimeKeeperView_tkv_background, Drawable)
        hourColor = attributes.getColor(R.styleable.TimeKeeperView_tkv_hourColor, Color.BLACK);
        markColor = attributes.getColor(R.styleable.TimeKeeperView_tkv_markColor, Color.BLACK);
        minColor = attributes.getColor(R.styleable.TimeKeeperView_tkv_minColor, Color.BLACK);
        pointColor = attributes.getColor(R.styleable.TimeKeeperView_tkv_pointColor, Color.BLACK);
        secColor = attributes.getColor(R.styleable.TimeKeeperView_tkv_secColor, Color.BLACK);
        textColor = attributes.getColor(R.styleable.TimeKeeperView_tkv_textColor, Color.BLACK);

        hourRatio = attributes.getFloat(R.styleable.TimeKeeperView_tkv_hourRatio, DEFAULT_HOUR);
        minRatio = attributes.getFloat(R.styleable.TimeKeeperView_tkv_minRatio, DEFAULT_MIN);
        secRatio = attributes.getFloat(R.styleable.TimeKeeperView_tkv_secRatio, DEFAULT_SEC);

        hourWidth = attributes.getDimensionPixelSize(R.styleable.TimeKeeperView_tkv_hourWidth, DEFAULT_WIDTH_HOUR);
        minWidth = attributes.getDimensionPixelSize(R.styleable.TimeKeeperView_tkv_minWidth, DEFAULT_WIDTH_MIN);
        secWidth = attributes.getDimensionPixelSize(R.styleable.TimeKeeperView_tkv_secWidth, DEFAULT_WIDTH_SEC);

        lineBlurRadius = attributes.getDimensionPixelSize(R.styleable.TimeKeeperView_tkv_lineBlurRadius, 4);
        markBlurRadius = attributes.getDimensionPixelSize(R.styleable.TimeKeeperView_tkv_markBlurRadius, 6);

        markRadius = attributes.getDimensionPixelSize(R.styleable.TimeKeeperView_tkv_markRadius, DEFAULT_WIDTH_HOUR * 3);
        pointRadius = attributes.getDimensionPixelSize(R.styleable.TimeKeeperView_tkv_pointRadius, DEFAULT_WIDTH_HOUR);

        textSize = attributes.getDimensionPixelSize(R.styleable.TimeKeeperView_tkv_textSize, DEFAULT_WIDTH_HOUR);
    }

    private void initPaint() {
        radiusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setShadowLayer(lineBlurRadius, 0, lineBlurRadius * 0.5f, Color.parseColor("#98C7FF"));

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        strTempH = (textPaint.getFontMetricsInt().bottom - textPaint.getFontMetricsInt().top) * 0.25f;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(DEFAULT_RADIUS, widthSize);
        } else {
            width = DEFAULT_RADIUS;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(DEFAULT_RADIUS, heightSize);
        } else {
            height = DEFAULT_RADIUS;
        }

        int measuredDimension = Math.min(width, height);
        outRadius = measuredDimension * 0.5f;
        setMeasuredDimension(measuredDimension, measuredDimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        radiusPaint.clearShadowLayer();
        radiusPaint.setStyle(Paint.Style.FILL);
        radiusPaint.setColor(bgColor);
        canvas.drawCircle(outRadius, outRadius, outRadius, radiusPaint);
        radiusPaint.setStyle(Paint.Style.STROKE);
        radiusPaint.setStrokeWidth(2f);
        canvas.drawCircle(outRadius, outRadius, outRadius - 1f, radiusPaint);

        drawHourStr(canvas);
        drawLines(canvas);
    }

    /**
     * 绘制小时数字以及标记点
     */
    private void drawHourStr(Canvas canvas) {
        radiusPaint.clearShadowLayer();

        radiusPaint.setStyle(Paint.Style.FILL);
        radiusPaint.setColor(pointColor);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("3", outRadius * 2 - pointRadius * 7, outRadius + strTempH, textPaint);
        canvas.drawCircle(outRadius * 2 - pointRadius * 3, outRadius, pointRadius, radiusPaint);

        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("9", pointRadius * 7, outRadius + strTempH, textPaint);
        canvas.drawCircle(pointRadius * 3, outRadius, pointRadius, radiusPaint);

        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("6", outRadius, outRadius * 2 - pointRadius * 7, textPaint);
        canvas.drawCircle(outRadius, outRadius * 2 - pointRadius * 3, pointRadius, radiusPaint);

        canvas.drawText("12", outRadius, pointRadius * 7 + strTempH * 3 - textPaint.getFontMetricsInt().descent, textPaint);
        canvas.drawCircle(outRadius, pointRadius * 3, pointRadius, radiusPaint);
    }

    /**
     * 绘制表针和中心点
     */
    private void drawLines(Canvas canvas) {
        radiusPaint.clearShadowLayer();
        if (null == calendar) {
            calendar = Calendar.getInstance(Locale.CHINA);
        }
        float hour = calendar.get(Calendar.HOUR_OF_DAY) % 12;
        float min = calendar.get(Calendar.MINUTE);
        float second = calendar.get(Calendar.SECOND);

        float angleHour = 30.f * hour + min * 0.5f - 90f;
        float angleMin = 6.f * min + second * 0.1f - 90f;
        float angleSec = 6.f * second - 90f;

        linePaint.setStrokeWidth(hourWidth);
        linePaint.setColor(hourColor);
        float[] hours = getCoordinate(angleHour, hourRatio);
        canvas.drawLine(outRadius, outRadius, hours[0], hours[1], linePaint);

        linePaint.setStrokeWidth(minWidth);
        linePaint.setColor(minColor);
        float[] mins = getCoordinate(angleMin, minRatio);
        canvas.drawLine(outRadius, outRadius, mins[0], mins[1], linePaint);


        linePaint.setStrokeWidth(secWidth);
        linePaint.setColor(secColor);
        float[] seconds = getCoordinate(angleSec, secRatio);
        canvas.drawLine(outRadius, outRadius, seconds[0], seconds[1], linePaint);
        radiusPaint.clearShadowLayer();
        radiusPaint.setColor(secColor);
        radiusPaint.setStyle(Paint.Style.FILL);
        radiusPaint.setMaskFilter(new BlurMaskFilter(lineBlurRadius, BlurMaskFilter.Blur.NORMAL));
        canvas.drawCircle(seconds[0], seconds[1], secWidth * 2, radiusPaint);
        radiusPaint.setMaskFilter(null);
        canvas.drawCircle(seconds[0], seconds[1], secWidth * 2, radiusPaint);


        radiusPaint.clearShadowLayer();
        radiusPaint.setShadowLayer(markBlurRadius, 0, markBlurRadius * 0.5f, Color.parseColor("#202B89"));
        radiusPaint.setColor(markColor);
        radiusPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(outRadius, outRadius, markRadius, radiusPaint);

    }

    private float[] getCoordinate(float angle, float ratio) {
        float x = outRadius + (float) (outRadius * ratio * Math.cos(angle * Math.PI / 180));
        float y = outRadius + (float) (outRadius * ratio * Math.sin(angle * Math.PI / 180));
        return new float[]{x, y};
    }

    private Calendar calendar = null;


    /**
     * 设置时间，格式为 Calendar
     */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        invalidate();
    }
}
