package com.example.slade_of_the_country;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class ElevatorInterfaceDrawer {
	protected Paint paint;
	protected int highestZ;

	public ElevatorInterfaceDrawer() {
		paint = new Paint();
		highestZ = 0;
	}

	public void draw(Canvas canvas, Matrix canvasMatrix) {
		canvas.setMatrix(canvasMatrix);
		paint.setColor(Color.WHITE);
		paint.setTextSize(Constants.NORMALFONTSIZE);
		canvas.drawText(Texts.TEXT_STOORCHOICE, Constants.FLOOR_CHOICE_X,
				Constants.FLOOR_CHOICE_Y, paint);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				if (highestZ > (i + j * 5) - 1) {
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

	public void setHighestZ(int Z) {
		this.highestZ = Z;
	}
	
	public int getHighestZ () {
		return this.highestZ;
	}
}
