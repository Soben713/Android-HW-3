package com.example.androidhw3;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewAnimator;

public class CircularPercentView extends View {
	private float rad, outerBoundWidth;
	private int percent;
	private float startAngle, sweepAngle;
	private String mLabel ;
	
	private PointF mCircleCenter;
	private RectF mCircleBound;
	private float mLabelTextFontSize;
	
	
	private int mOuterBoundColor, mPositiveFilColor, mNegativeFilColor;
	private Paint mCircleTextPain;
	private Paint mCircleOuterBoundPaint;
	private Paint mCirclePositiveFillPaint;
	private Paint mCircleNegativeFillPaint;
	private Paint mCircleFillPaint;
	
	
	public void animatePercentTo(int targetVal){
		System.out.println(" ## animate circular percent to " + targetVal);
		final ObjectAnimator percentFillAnimator = ObjectAnimator.ofInt(this, "percent", 0, targetVal);
		percentFillAnimator.setDuration(1200);
		percentFillAnimator.start();
//		ValueAnimator animation = ValueAnimator.ofInt(0, targetVal);
//		animation.setDuration(1000);
//		animation.start();
//		setPercent(targetVal);
	}
	
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
		computeFilledArea();
		invalidate();
		requestLayout();
	}

	private void computeFilledArea(){
		mCircleFillPaint = mCirclePositiveFillPaint;
		if(percent > 0){
			mCircleFillPaint = mCirclePositiveFillPaint;
			if(percent > 100)
				percent = 100;
		}
		else if (percent < 0){
			mCircleFillPaint = mCircleNegativeFillPaint;
			percent *= -1;
			if(percent > 100)
				percent = 100;
		}
		sweepAngle = -percent*3.6f;
		startAngle = 90 - sweepAngle/2;
		mLabel = percent + "%";
		invalidate();
		requestLayout();
	}
	
	private void initPaint(){
		mCircleTextPain = new Paint();
		mCircleTextPain.setColor(mOuterBoundColor);
		mCircleTextPain.setTextSize(15);
		
		mCircleOuterBoundPaint = new Paint();
		mCircleOuterBoundPaint.setColor(mOuterBoundColor);
		mCircleOuterBoundPaint.setAntiAlias(true); 
		mCircleOuterBoundPaint.setStrokeWidth(outerBoundWidth);
		mCircleOuterBoundPaint.setStyle(Paint.Style.STROKE);

		mCirclePositiveFillPaint = new Paint();
		mCirclePositiveFillPaint.setColor(mPositiveFilColor);

		mCircleNegativeFillPaint = new Paint();
		mCircleNegativeFillPaint.setColor(mNegativeFilColor);
		
	}
	
	public CircularPercentView(Context context) {
		this(context, null);
	}
	public CircularPercentView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public CircularPercentView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		

		TypedArray a = context.getTheme().obtainStyledAttributes(
		        attrs,
		        R.styleable.CircularPercentView,
		        0, 0);

		   try {

			   rad  = a.getInt(R.styleable.CircularPercentView_rad, 200);
			   outerBoundWidth  = a.getInt(R.styleable.CircularPercentView_outer_bound_width, 7);
			   mOuterBoundColor  = a.getColor(R.styleable.CircularPercentView_outer_bound_color,  Color.DKGRAY);
			   mPositiveFilColor = a.getColor(R.styleable.CircularPercentView_positive_fill_color,  Color.GREEN);
			   mNegativeFilColor = a.getColor(R.styleable.CircularPercentView_negative_fill_color,  Color.RED);


			   mLabel = "x %";
			   mLabelTextFontSize = 18;
		   } finally {
		       a.recycle();
		   }
//		System.err.println("@@@ @@ CREATE CIRCULARPERCENT RAD: " + rad + " W:" + outerBoundWidth + " c:" + mOuterBoundColor + " p:" + mPositiveFilColor + " n: " +mNegativeFilColor);
		mCircleCenter = new PointF(rad+outerBoundWidth/2, rad+outerBoundWidth/2);
		mCircleBound = new RectF(outerBoundWidth/2, outerBoundWidth/2, 2*rad+outerBoundWidth/2, 2*rad+outerBoundWidth/2);
//		System.err.println("circlebound " + mCircleBound);
		initPaint();
		setPercent(0);
		
	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
//		System.err.println("# CircularPercentView SIZE CHANGED TO " + w + " x " + h);
	}
	
	/**
	 * set fixed size from mOuterR
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		System.err.println("# MEWASURE CircularPercentView "  );
		int wSpec = resolveSize((int)(rad*2+outerBoundWidth), widthMeasureSpec);
		int hSpec = resolveSize((int)(rad*2+outerBoundWidth), heightMeasureSpec);
		setMeasuredDimension(wSpec, hSpec);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		System.err.println("# DRAW CircularPercentView : " + percent + " " + startAngle + "^ " + sweepAngle + "^");
		canvas.drawArc( mCircleBound, startAngle, sweepAngle, false, mCircleFillPaint);
		canvas.drawCircle(mCircleCenter.x, mCircleCenter.y, rad, mCircleOuterBoundPaint);
		canvas.drawText(mLabel, mCircleCenter.x - mCircleTextPain.measureText(mLabel)/2, mCircleCenter.y, mCircleTextPain);
	}
	
}
