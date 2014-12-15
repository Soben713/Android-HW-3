package com.example.androidhw3;


import android.R.string;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Notification.Action;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class RingButton extends Button {
	private static float clickDistToleranse = 5;
	private static float mRingShadowWidth = 12;
	
	private float mInnerR, mOuterR, mRingWidthDiv2;
	private float startAngle, sweepAngle;
	private String mLabel ;
	private float mLabelVerticalOffset, mLabelHorizentalOffset;
	private PointF mRingCenter;
	private RectF ringBound;
	private Path ringPath;
	private float mLabelTextFontSize;
	
	private Paint mRingContexPaint;
	private Paint mRingShadowPaint;
	private Paint mRingTextPaint;
	
	private void computeTextOffset(){
		mLabelHorizentalOffset = (int)(3.14*sweepAngle* (mOuterR-mRingWidthDiv2)/360 - mRingTextPaint.measureText(mLabel)/2);
		mLabelVerticalOffset = mLabelTextFontSize/2;
	}
	
	private void initPaint(){
		mRingContexPaint = new Paint();
		mRingContexPaint.setColor(getResources().getColor(R.color.ring_contex_color));
		mRingContexPaint.setAntiAlias(true); 
		mRingContexPaint.setStrokeWidth(mOuterR-mInnerR-mRingShadowWidth);
		mRingContexPaint.setStyle(Paint.Style.STROKE);

		mRingShadowPaint = new Paint();
		mRingShadowPaint.setColor(getResources().getColor(R.color.ring_shadow_color));
		mRingShadowPaint.setAntiAlias(true);
		mRingShadowPaint.setStrokeWidth(mOuterR-mInnerR);
		mRingShadowPaint.setStyle(Paint.Style.STROKE);
		
		mRingTextPaint   = new Paint(); 
		mRingTextPaint.setColor(getResources().getColor(R.color.ring_text_color));
		mRingTextPaint.setTextSize(mLabelTextFontSize);
		
//		System.err.println("# LABEL OFFSET : " + mLabelOffset);
	}

	private void computeSecondryVariables(){
		mRingWidthDiv2 = (mOuterR-mInnerR)/2;
		mRingCenter = new PointF(mOuterR, mOuterR);
		ringBound = new RectF(mRingWidthDiv2, mRingWidthDiv2, 2*mOuterR-mRingWidthDiv2, 2*mOuterR-mRingWidthDiv2);
		ringPath = new Path();
		ringPath.addArc(ringBound,  startAngle, sweepAngle);
		
	}
	
	public RingButton(Context context) {
		this(context, null);
	}
	
	public RingButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RingButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray a = context.getTheme().obtainStyledAttributes(
		        attrs,
		        R.styleable.RingButton,
		        0, 0);

		   try {
			   mInnerR    = a.getFloat(R.styleable.RingButton_inner_rad  ,   70);
			   mOuterR    = a.getFloat(R.styleable.RingButton_outer_rad  ,  100);
			   startAngle = a.getFloat(R.styleable.RingButton_start_angle, -180);
			   sweepAngle = a.getFloat(R.styleable.RingButton_sweep_angle,  180);
			   mLabel     = a.getString(R.styleable.RingButton_ring_label);
			   if(mLabel == null)
				   mLabel = "";
			   if(mInnerR >  mOuterR){
				   float tmp = mInnerR;
				   mInnerR = mOuterR;
				   mOuterR = tmp;
			   }
			   mLabelTextFontSize = 18;
		   } finally {
		       a.recycle();
		   }

		   
		computeSecondryVariables();
		initPaint();
		computeTextOffset();
		
//		System.err.println("# RINGNUTTON CREATED : " + mInnerR + " " + mOuterR + "  " + startAngle + "^ " + sweepAngle + "^");
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
//		System.err.println("# RINGBUTTON SIZE CHANGED TO " + w + " x " + h);
	}
	
	/**
	 * set fixed size from mOuterR
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		System.err.println("# MEWASURE RINGBUTTON "  );
		int wSpec = resolveSize((int)mOuterR*2, widthMeasureSpec);
		int hSpec = resolveSize((int)mOuterR*2, heightMeasureSpec);
		setMeasuredDimension(wSpec, hSpec);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		System.err.println("# DRAW RINGBUTTON");
		canvas.drawPath(ringPath, mRingShadowPaint);
		canvas.drawPath(ringPath, mRingContexPaint);
		canvas.drawTextOnPath(mLabel, ringPath, mLabelHorizentalOffset, mLabelVerticalOffset, mRingTextPaint);
	}
	boolean haveDownPressed = false;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		double dist = Math.abs( Math.sqrt( (double)(event.getX() - mRingCenter.x)*(event.getX() - mRingCenter.x) +  (event.getY() - mRingCenter.y)*(event.getY() - mRingCenter.y) ) ) ;
		double angle = Math.toDegrees(Math.atan2(mRingCenter.y-event.getY(), mRingCenter.x-event.getX())) + 180;
		if(angle < 0) angle += 360;
		double st = startAngle, fi = startAngle + sweepAngle;
		if(st < 0) st += 360;
		if(fi < 0) fi += 360;
		if(fi < st){
			double tmp = st;
			st = fi;
			fi = tmp;
		}
//		System.err.println("# IS RING CLICKED @" + angle + "^ " + st + "^ " + fi + "^");
		if(dist < mOuterR+clickDistToleranse && dist > mInnerR-clickDistToleranse && angle > st && angle < fi){

			if(event.getAction() == MotionEvent.ACTION_DOWN){
				haveDownPressed = true;
				return true;
			}
			if(event.getAction() != MotionEvent.ACTION_UP){
				haveDownPressed = false;
				return false;
			}
			if(haveDownPressed == false)
				return false;
			
			haveDownPressed = false;
//			System.err.println("# RINGBUTTON PERFORM CLICK @ " + angle + "^");
			performClick();
		}

		haveDownPressed = false;
		return false;
	}
	

}
