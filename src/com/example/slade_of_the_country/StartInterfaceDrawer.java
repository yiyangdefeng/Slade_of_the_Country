package com.example.slade_of_the_country;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class StartInterfaceDrawer {
	private Paint paint = new Paint();
	private PictureCollector pictures;
	
	public StartInterfaceDrawer(PictureCollector pictures) {
		this.pictures = pictures;
	}
	
	public void draw(Canvas canvas,Matrix canvasMatrix) {
		canvas.setMatrix(canvasMatrix);
		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(pictures.scaledstart, Constants.START_LOGO_X, Constants.START_LOGO_Y1, paint);
		canvas.drawBitmap(pictures.scaledload,Constants.START_LOGO_X,Constants.START_LOGO_Y2,paint);
		canvas.drawBitmap(pictures.scaledinstruction,Constants.START_LOGO_X,Constants.START_LOGO_Y3,paint);
		canvas.drawBitmap(pictures.scaledexit,Constants.START_LOGO_X,Constants.START_LOGO_Y4,paint);
	}
}
