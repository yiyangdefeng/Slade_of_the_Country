package com.example.slade_of_the_country;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class FightInterfaceDrawer {
	protected Paint paint;
	protected PictureCollector pictures;
	protected int[] attributes;

	public FightInterfaceDrawer(PictureCollector pictures) {
		this.pictures = pictures;
		this.attributes = new int[6];
		this.paint = new Paint();
	}

	public void draw(Canvas canvas, Matrix canvasMatrix) {
		canvas.setMatrix(canvasMatrix);
		paint.setColor(Color.BLACK);
		canvas.drawRect(Constants.FIGHT_INTERFACE_LEFT, Constants.FIGHT_INTERFACE_TOP, Constants.FIGHT_INTERFACE_RIGHT,Constants.FIGHT_INTERFACE_BOTTOM,paint);	
		paint.setColor(Color.RED);
		paint.setTextSize(Constants.NORMALSMALLFONTSIZE);
		canvas.drawText(Texts.TEXT_BLOOD + attributes[2],Constants.OPPOHEALTH_LEFT, Constants.OPPOHEALTH_BOTTOM, paint);
		//oh
		canvas.drawText(Texts.TEXT_BLOOD + attributes[5],Constants.SELFHEALTH_LEFT, Constants.OPPOHEALTH_BOTTOM, paint);
		//sh
		paint.setColor(Color.WHITE);
		canvas.drawText(Texts.TEXT_ATTACK + attributes[0],Constants.OPPOHEALTH_LEFT, Constants.OPPOATTACK_BOTTOM, paint);
		//oa
		canvas.drawText(Texts.TEXT_DEFENCE + attributes[1],Constants.OPPOHEALTH_LEFT, Constants.OPPODEFENSE_BOTTOM, paint);
		//od
		canvas.drawText(Texts.TEXT_ATTACK + attributes[3],Constants.SELFHEALTH_LEFT, Constants.OPPOATTACK_BOTTOM, paint);
		//sa
		canvas.drawText(Texts.TEXT_DEFENCE + attributes[4],Constants.SELFHEALTH_LEFT, Constants.OPPODEFENSE_BOTTOM, paint);
		//sd
	}
	
	public void setAttributes(int[] attributes) {
		this.attributes = attributes;
	}
}
