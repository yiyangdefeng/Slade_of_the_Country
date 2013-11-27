package com.example.slade_of_the_country;

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

	public void drawWarningMessage(Canvas canvas, Matrix canvasmatrix) {
		canvas.setMatrix(canvasmatrix);
		paint.setColor(Color.BLACK);
		canvas.drawRect(Constants.FIGHT_INTERFACE_LEFT,
				Constants.FIGHT_INTERFACE_TOP, Constants.FIGHT_INTERFACE_RIGHT,
				Constants.FIGHT_INTERFACE_BOTTOM, paint);
		paint.setColor(Color.WHITE);
		paint.setTextSize(Constants.SMALLFONTSIZE);
		String[] strings = StringSpliter(message,
				Constants.FIGHT_INTERFACE_LEFT,
				Constants.FIGHT_INTERFACE_RIGHT, Constants.SMALLFONTSIZE);
		for (int i = 0; i < strings.length; i++) {
			canvas.drawText(
					strings[i],
					(Constants.FIGHT_INTERFACE_LEFT),
					(Constants.FIGHT_INTERFACE_TOP + Constants.SMALLFONTSIZE * (i + 1) + Constants.MARGIN * 10),
					paint);

		}
		canvas.drawRect(Constants.SINGLE_BUTTON_LEFT,
				Constants.SINGLE_BUTTON_UP, Constants.SINGLE_BUTTON_RIGHT,
				Constants.SINGLE_BUTTON_DOWN, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText(Texts.TEXT_CONFIRM, Constants.TEXT_SINGLE_LEFT,
				Constants.TEXT_SINGLE_DOWN, paint);
	}

	public String[] StringSpliter(String message, float left, float right,
			float fontsize) {
		int columncount = (int) ((right - left) / fontsize);
		int column = message.length() / columncount + 1;
		String[] strings = new String[column];
		for (int i = 0; i < column; i++) {
			strings[i] = message.substring(columncount * i, Math.min(columncount
					* (i + 1),message.length()));
		}
		
		return strings;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
