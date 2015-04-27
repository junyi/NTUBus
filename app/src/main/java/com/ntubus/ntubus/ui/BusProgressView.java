package com.ntubus.ntubus.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.ntubus.ntubus.R;

public class BusProgressView extends View {
    private int ON_LENGTH = 60;
    private int OFF_LENGTH = 40;
    private final static float ROAD_ANGLE = 60;
    private Camera camera;
    private boolean isAnimating = false;
    private int BUS_COLOR;
    Paint paint = new Paint();
    BitmapShader shader;
    PorterDuffXfermode SRC_IN;

    Paint circlePaint = new Paint();
    Paint bgPaint = new Paint();
    Paint lightPaint = new Paint();
    Paint dashPaint = new Paint();
    Paint diffPaint = new Paint();
    DashPathEffect dashPathEffect;
    Matrix matrix = new Matrix();
    Path path;
    IconicsDrawable busIcon;

    private float phase = 0;
    private ValueAnimator animator;
    private float xTranslation = 0;
    private float yTranslation = 0;

    public BusProgressView(Context context) {
        super(context);
        init();
    }

    public BusProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BusProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }


        BUS_COLOR = getResources().getColor(R.color.md_orange_900);

        busIcon = new IconicsDrawable(getContext(), GoogleMaterial.Icon.gmd_directions_bus);
        busIcon.setColorFilter(new PorterDuffColorFilter(BUS_COLOR, PorterDuff.Mode.SRC_ATOP));

        SRC_IN = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnimating) {
                    stopAnimation();
                } else {
                    startAnimation();
                }
            }
        });

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        paint.setAntiAlias(true);
        paint.setDither(true);

        int minimum = Math.min(canvas.getWidth(), canvas.getHeight());
        int w = minimum;
        int h = minimum;

        if (camera == null) {
            camera = new Camera();
            float cameraDistance = minimum * 10;
            float densityDpi = getResources().getDisplayMetrics().densityDpi;
            camera.setLocation(0, 0, -cameraDistance / densityDpi);
        }

        ON_LENGTH = h / 5;
        OFF_LENGTH = h / 8;
        if (dashPathEffect == null)
            dashPathEffect = new DashPathEffect(new float[]{ON_LENGTH, OFF_LENGTH}, phase);

        int xPos = (int) (0.7 * w / 2);
        int yPos = (int) (h / Math.cos(ROAD_ANGLE / 180f * Math.PI));

        int radius = (int) (Math.min(w, h) / 2);
        paint.setColor(getResources().getColor(R.color.md_blue_300));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(w / 2, h / 2, radius, paint);

        canvas.save();
        paint.setColor(getResources().getColor(R.color.md_green_500));
        paint.setXfermode(SRC_IN);
        canvas.drawCircle(w / 2, (float) (h * 1.2), (float) (radius * 1.5), paint);
        paint.setXfermode(null);
        canvas.restore();

        canvas.save();
        camera.save();
        camera.rotate(ROAD_ANGLE, 0, 0);
        camera.getMatrix(matrix);
        matrix.preTranslate(-w / 2, -h);
        matrix.postTranslate(w / 2, h);
        canvas.concat(matrix);

        paint.setColor(getResources().getColor(R.color.md_grey_700));
        paint.setXfermode(SRC_IN);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(w / 2 - xPos, h - yPos, w / 2 + xPos, h, paint);
        paint.setXfermode(null);

        paint.setStrokeWidth(15);
        paint.setStrokeWidth(w / 10);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(dashPathEffect);
        canvas.drawLine(w / 2, h - yPos, w / 2, h, paint);
        paint.setPathEffect(dashPathEffect);
        paint.setPathEffect(null);

        camera.restore();
        canvas.restore();
        canvas.scale(0.7f, 0.7f, w / 2, h / 2);

        canvas.save();
        canvas.translate(xTranslation, yTranslation);

        int busIconW = w;
        int busIconH = (int) (0.8 * h);

        int rectW = (int) (0.55 * busIconW);
        int rectH = (int) (h / 5);
        paint.setColor(getResources().getColor(R.color.md_yellow_300));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(w / 2 - rectW / 2, (float) (h / 2), w / 2 + rectW / 2, (float) (h / 2 + rectH), paint);

        rectH = (int) (h / 4);
        paint.setColor(0x99ffffff);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(w / 2 - rectW / 2, (float) (h / 6), w / 2 + rectW / 2, (float) (h / 6) + rectH, paint);

        busIcon.setBounds(0, 0, busIconW, busIconH);
//        busIcon.contourColor(0xaaffffff);
//        busIcon.contourWidthPx(w / 30);
        busIcon.draw(canvas);

        paint.reset();

    }

    LinearInterpolator interpolator = new LinearInterpolator();
    CycleInterpolator cycleInterpolator = new CycleInterpolator(0.5f);

    public void startAnimation() {
        isAnimating = true;
        if (animator == null) {
            animator = ValueAnimator.ofFloat(0, ON_LENGTH + OFF_LENGTH);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float phase = (float) animation.getAnimatedValue();
                    setPhase(phase);
//                    xTranslation = cycleInterpolator.getInterpolation(phase / (ON_LENGTH + OFF_LENGTH)) * 5;
                    yTranslation = cycleInterpolator.getInterpolation(phase / (ON_LENGTH + OFF_LENGTH)) * 5;
                }
            });
            animator.setInterpolator(interpolator);
            animator.setRepeatMode(Animation.INFINITE);
            animator.setRepeatCount(Animation.INFINITE);
            animator.setDuration(500);
        }
        animator.start();
    }

    public void stopAnimation() {
        if (animator != null) {
            isAnimating = false;
            animator.end();
        }
    }

    public boolean isAnimating() {
        return isAnimating;
    }

    public void setPhase(float phase) {
        this.phase = phase;
        dashPathEffect = new DashPathEffect(new float[]{ON_LENGTH, OFF_LENGTH}, this.phase);
        dashPaint.setPathEffect(dashPathEffect);
        invalidate();
    }
}
