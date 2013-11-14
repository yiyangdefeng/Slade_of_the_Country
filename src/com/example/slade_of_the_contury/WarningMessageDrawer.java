package com.example.slade_of_the_contury;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class WarningMessageDrawer {
	private Paint paint;
	
	public WarningMessageDrawer() {
		paint = new Paint();
		
		
	}

	public void drawWarningMessage (Canvas canvas,Matrix canvasmatrix) {
		canvas.setMatrix(canvasmatrix);
		paint.setColor(Color.BLACK);
		canvas.drawRect(Constants.WARNINGLEFT, Constants.WARNINGUP, Constants.WARNINGRIGHT, Constants.WARNINGDOWN, paint);
		paint.setColor(Color.WHITE);
		paint.setTextSize(Constants.SMALLFONTSIZE);
		canvas.drawText(Texts.TEXT_WARNING,Constants.WARNING_TEXTX,Constants.WARNING_TEXTY, paint);
		canvas.drawRect(Constants.CONFIRM_BUTTON_LEFT, Constants.CONFIRM_BUTTON_UP, Constants.CONFIRM_BUTTON_RIGHT, Constants.CONFIRM_BUTTON_DOWN, paint);
		canvas.drawRect(Constants.CANCEL_BUTTON_LEFT, Constants.CONFIRM_BUTTON_UP, Constants.CANCEL_BUTTON_RIGHT, Constants.CONFIRM_BUTTON_DOWN, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText(Texts.TEXT_CONFIRM, Constants.CONFIRM_TEXT_LEFT, Constants.CONFIRM_TEXT_DOWN, paint);
		canvas.drawText(Texts.TEXT_CANCEL, Constants.CANCEL_TEXT_LEFT, Constants.CONFIRM_TEXT_DOWN, paint);
	}
}
