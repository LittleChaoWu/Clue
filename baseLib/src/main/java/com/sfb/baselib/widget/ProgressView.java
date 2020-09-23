package com.sfb.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.sfb.baselib.R;
import com.sfb.baselib.utils.CommonUtils;

import androidx.annotation.Nullable;

public class ProgressView extends View {

    private Context context;

    private Paint progressPaint;
    private Paint textPaint;

    private int progressWidth;//线的宽度
    private int max = 100;//上限
    private int progress = 0;//当前进度
    private int progressColor;//进度条颜色
    private int defaultColor;//背景颜色
    private int textColor;//文字的颜色
    private float textSize;//文字大小

    public ProgressView(Context context) {
        super(context);
        //初始化
        init(context);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        progress = typedArray.getInt(R.styleable.ProgressView_progress, 0);
        max = typedArray.getInt(R.styleable.ProgressView_max, 100);
        progressWidth = typedArray.getInt(R.styleable.ProgressView_progress_width, 5);
        defaultColor = typedArray.getColor(R.styleable.ProgressView_default_color, Color.TRANSPARENT);
        progressColor = typedArray.getColor(R.styleable.ProgressView_progress_color, Color.GRAY);
        textColor = typedArray.getColor(R.styleable.ProgressView_text_color, Color.GRAY);
        textSize = typedArray.getFloat(R.styleable.ProgressView_text_size, 14);
        typedArray.recycle();
        //初始化
        init(context);
    }

    /**
     * 初始化
     *
     * @param context Context
     */
    private void init(Context context) {
        this.context = context;
        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setAntiAlias(true);
        progressPaint.setDither(true);
        progressPaint.setStrokeWidth(progressWidth);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setStrokeWidth(1);

    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setProgressWidth(int progressWidth) {
        this.progressWidth = progressWidth;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2;
        int radius = center - progressWidth;
        int left = center - radius;
        int top = center - radius;
        int right = center + radius;
        int bottom = center + radius;
        RectF rectF = new RectF(left, top, right, bottom);

        progressPaint.setColor(defaultColor);
        canvas.drawCircle(center, center, radius, progressPaint);
        progressPaint.setColor(progressColor);
        int angle = progress * 360 / max;
        canvas.drawArc(rectF, -90, angle, false, progressPaint);

        textPaint.setColor(textColor);
        textPaint.setTextSize(CommonUtils.sp2px(context, textSize));
        String text = progress + "%";
        float textWidth = textPaint.measureText(text);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float textHeight = (-fontMetrics.ascent - fontMetrics.descent) / 2;
        float startX = center - (textWidth / 2);
        float startY = center + textHeight;
        canvas.drawText(text, startX, startY, textPaint);
    }

}
