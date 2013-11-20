package com.example.slade_of_the_contury;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class FailureWarningDrawer {
	private Paint paint;
	String message;
	
	public FailureWarningDrawer() {
		paint = new Paint();
		this.message = "";
	}

	public void drawWarningMessage (Canvas canvas,Matrix canvasmatrix) {
		canvas.setMatrix(canvasmatrix);
		paint.setColor(Color.BLACK);
		canvas.drawRect(Constants.WARNINGLEFT, Constants.WARNINGUP, Constants.WARNINGRIGHT, Constants.WARNINGDOWN, paint);
		paint.setColor(Color.WHITE);
		paint.setTextSize(Constants.SMALLFONTSIZE);
		canvas.drawText(message,Constants.WARNING_TEXTX,Constants.WARNING_TEXTY, paint);
		canvas.drawRect(Constants.SINGLE_BUTTON_LEFT, Constants.CONFIRM_BUTTON_UP, Constants.SINGLE_BUTTON_RIGHT, Constants.CONFIRM_BUTTON_DOWN, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText(Texts.TEXT_CONFIRM, Constants.TEXT_SINGLE_LEFT, Constants.CONFIRM_TEXT_DOWN, paint);
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
