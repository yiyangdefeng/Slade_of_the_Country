package com.example.slade_of_the_country;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class ElevatorInterfaceDrawer {
	protected Paint paint;

	public ElevatorInterfaceDrawer() {
		paint = new Paint();
	}

	public void draw(Canvas canvas, Matrix canvasMatrix) {
		canvas.setMatrix(canvasMatrix);
		paint.setColor(Color.WHITE);
		paint.setTextSize(Constants.NORMALFONTSIZE);
		canvas.drawText(Texts.TEXT_STOORCHOICE, Constants.FLOOR_CHOICE_X,
				Constants.FLOOR_CHOICE_Y, paint);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				paint.setColor(Color.WHITE);
				canvas.drawRect(Constants.ELEVATOR_LEFT_MARGIN
						+ Constants.ELEVATOR_GRIDWIDTH * i,
						Constants.ELEVATOR_TOP_MARGIN
								+ Constants.ELEVATOR_GRIDHEIGHT * j,
						Constants.ELEVATOR_LEFT_MARGIN
								+ Constants.ELEVATOR_GRIDWIDTH * i
								+ Constants.ELEVATOR_TABWIDTH,
						Constants.ELEVATOR_TOP_MARGIN
								+ Constants.ELEVATOR_GRIDHEIGHT * j
								+ Constants.ELEVATOR_TABHEIGHT, paint);
				paint.setColor(Color.BLACK);
				canvas.drawText(String.valueOf(i + j * 5),
						Constants.ELEVATOR_LEFT_MARGIN
								+ Constants.ELEVATOR_GRIDWIDTH * i
								+ Constants.ELEVATOR_TEXTTOGRIDLEFT,
						Constants.ELEVATOR_TOP_MARGIN
								+ Constants.ELEVATOR_GRIDHEIGHT * j
								+ Constants.ELEVATOR_TEXTTOGRIDLEFT, paint);
			}
		}
	}
}
