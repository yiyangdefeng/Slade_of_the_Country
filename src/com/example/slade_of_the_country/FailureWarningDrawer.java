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
		canvas.drawRect(Constants.FIGHT_INTERFACE_LEFT + Constants.MARGIN * 2,
				Constants.MESSAGE_TOP, Constants.FIGHT_INTERFACE_RIGHT - Constants.MARGIN * 2,
				Constants.MESSAGE_BOTTOM, paint);
		paint.setColor(Color.WHITE);
		paint.setTextSize(Constants.SMALLFONTSIZE);
		String[] strings = Texts.StringSpliter(message,
				Constants.FIGHT_INTERFACE_LEFT + Constants.MARGIN * 3,
				Constants.FIGHT_INTERFACE_RIGHT - Constants.MARGIN * 3, Constants.SMALLFONTSIZE);
		for (int i = 0; i < strings.length; i++) {
			canvas.drawText(
					strings[i],
					(Constants.FIGHT_INTERFACE_LEFT + Constants.MARGIN * 3),
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


	public void setMessage(String message) {
		this.message = message;
	}
}
