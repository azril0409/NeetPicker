package com.neetoffice.library.neetpicker;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Deo-chainmeans on 2015/8/13.
 */
public class PickerView extends ScrollView {
    private boolean lock;
    private TextLayout textLayout;
    private float verticalPadding = 50;
    private int textSzie = 15;
    private int lineWidth = 1;
    private Paint mainPaint;
    private Paint linePaint;
    private float itemHeight;
    private float centerX;
    private float centerY;
    private int initialY;
    private int mainColor;
    private int color;
    private int index = 0;
    private OnPickerSelectListener onPickerSelectListener;

    public PickerView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public PickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }


    public PickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PickerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setVerticalScrollBarEnabled(false);
        CharSequence[] texts = null;
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PickerView, defStyleAttr, defStyleRes);
            texts = a.getTextArray(R.styleable.PickerView_neet_pickerview_texts);
            textSzie = a.getDimensionPixelSize(R.styleable.PickerView_neet_pickerview_textsize, textSzie);
            lineWidth = a.getDimensionPixelSize(R.styleable.PickerView_neet_pickerview_linewidth, lineWidth);
            mainColor = a.getColor(R.styleable.PickerView_neet_pickerview_maincolor, Color.parseColor("#0288ce"));
        } else {
            mainColor = Color.parseColor("#0288ce");
        }
        color = Color.argb(0, Color.red(mainColor), Color.green(mainColor), Color.blue(mainColor));
        {
            Rect bounds = new Rect();
            mainPaint = new Paint();
            mainPaint.setColor(mainColor);
            mainPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSzie, getResources().getDisplayMetrics()));
            mainPaint.getTextBounds("A", 0, "A".length(), bounds);
            itemHeight = bounds.height() + verticalPadding * 2;
            centerX = bounds.centerX();
            centerY = bounds.centerY();
            if (itemHeight / 2f > verticalPadding) {
                verticalPadding = itemHeight / 2f;
            }
            linePaint = new Paint();
            linePaint.setStrokeWidth(lineWidth);

        }
        textLayout = new TextLayout(context);
        if (texts != null) {
            for (CharSequence text : texts) {
                textLayout.texts.add(text.toString());
            }
        }
        addView(textLayout, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    public int getSelected() {
        return index;
    }

    public void setSelect(int index) {
        this.index = index;
        smoothScrollTo(0, (int) (index * itemHeight));
    }

    public void setTexts(Collection<String> texts) {
        index = 0;
        scrollTo(0, 0);
        removeAllViews();
        textLayout = new TextLayout(getContext());
        textLayout.texts = new ArrayList<>(texts);
        addView(textLayout, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    public void setOnPickerSelectListener(OnPickerSelectListener onPickerSelectListener) {
        this.onPickerSelectListener = onPickerSelectListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (lock) {
            return super.onTouchEvent(ev);
        }
        if (MotionEvent.ACTION_UP == ev.getAction()) {
            startScrollerTask();
        }
        return super.onTouchEvent(ev);
    }

    private void startScrollerTask() {
        initialY = getScrollY();
        lock = true;
        postDelayed(task,50);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        final float width = getMeasuredWidth();
        final float height = getMeasuredHeight();
        final float horizontalPadding = width / 6f;
        final float startX = horizontalPadding;
        final float stopX = width - horizontalPadding;
        final float y = height / 2f - itemHeight / 2f + getScrollY();
        linePaint.setShader(new LinearGradient(startX, y, stopX, y, new int[]{color, mainColor, mainColor, color}, new float[]{0, 0.2f, 0.8f, 1}, Shader.TileMode.REPEAT));
        canvas.drawLine(startX, y, stopX, y, linePaint);
        canvas.drawLine(startX, y + itemHeight, stopX, y + itemHeight, linePaint);
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            int newY = getScrollY();
            if (initialY - newY == 0) {//has stopped
                index = (int) (newY / itemHeight);
                int toy;
                if ((newY / itemHeight * 10) % 10 > 5) {
                    index++;
                }
                toy = (int) (index * itemHeight);
                smoothScrollTo(0, toy);
                if (onPickerSelectListener != null) {
                    post(postListener);
                }
                lock = false;
            } else {
                startScrollerTask();
            }
        }
    };

    private final Runnable postListener = new Runnable() {
        @Override
        public void run() {
            if (onPickerSelectListener != null) {
                onPickerSelectListener.onPickerSelected(index);
            }
        }
    };

    public class TextLayout extends FrameLayout {
        List<String> texts;

        public TextLayout(Context context) {
            super(context);
            this.texts = new ArrayList<>();
            init(context, null, 0, 0);
        }

        @Override
        protected void dispatchDraw(Canvas canvas) {
            final float width = getMeasuredWidth();
            final float height = getMeasuredHeight();
            final float top = PickerView.this.getMeasuredHeight() / 2f - itemHeight;
            for (int i = 0; i < texts.size(); i++) {
                Rect bounds = new Rect();
                mainPaint.getTextBounds(texts.get(i), 0, texts.get(i).length(), bounds);
                canvas.drawText(texts.get(i), width / 2f - centerX, itemHeight * (i + 1) + top - centerY, mainPaint);
            }
        }

        private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            setDrawingCacheEnabled(true);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            final float top = PickerView.this.getMeasuredHeight() / 2f - itemHeight;
            setMeasuredDimension(widthSize, (int) (itemHeight * (texts.size() + 1) + top * 2));
        }
    }
}
