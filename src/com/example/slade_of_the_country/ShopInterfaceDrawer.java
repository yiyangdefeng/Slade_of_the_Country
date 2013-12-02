package com.example.slade_of_the_country;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class ShopInterfaceDrawer {
	protected PictureCollector pictures;
	protected Paint paint;

	public ShopInterfaceDrawer(PictureCollector pictures) {
		this.pictures = pictures;
		this.paint = new Paint();
	}

	public void draw(Canvas canvas, Matrix canvasMatrix) {
		canvas.setMatrix(canvasMatrix);
		paint.setColor(Color.BLACK);
		canvas.drawRect(Constants.MAINSURFACEX, Constants.MAINSURFACEY,
				Constants.MYSCREENWIDTH, Constants.MYSCREENHEIGHT, paint);
		paint.setColor(Color.WHITE);
		canvas.drawRect(Constants.SHOPBUTTON_X, Constants.SHOPBUTTON_Y1,
				Constants.SHOPBUTTON_X + Constants.SHOPBUTTON_WIDTH,
				Constants.SHOPBUTTON_Y1 + Constants.SHOPBUTTON_HEIGHT, paint);
		canvas.drawRect(Constants.SHOPBUTTON_X, Constants.SHOPBUTTON_Y2,
				Constants.SHOPBUTTON_X + Constants.SHOPBUTTON_WIDTH,
				Constants.SHOPBUTTON_Y2 + Constants.SHOPBUTTON_HEIGHT, paint);
		canvas.drawRect(Constants.SHOPBUTTON_X, Constants.SHOPBUTTON_Y3,
				Constants.SHOPBUTTON_X + Constants.SHOPBUTTON_WIDTH,
				Constants.SHOPBUTTON_Y3 + Constants.SHOPBUTTON_HEIGHT, paint);
		paint.setTextSize(Constants.SMALLFONTSIZE);
		canvas.drawText(Texts.TEXT_NOTICE, Constants.SHOP_TEXT_NOTICEX, Constants.SHOP_TEXT_NOTICEY, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText(Texts.TEXT_ATTACKUP, Constants.SHOP_TEXT1_X,Constants.SHOPBUTTON_Y1 + Constants.SHOP_TEXT_YTOTOP, paint);
		canvas.drawText(Texts.TEXT_DEFENCEUP, Constants.SHOP_TEXT2_X,Constants.SHOPBUTTON_Y2 + Constants.SHOP_TEXT_YTOTOP, paint);
		canvas.drawText(Texts.TEXT_BLOODUP, Constants.SHOP_TEXT3_X,Constants.SHOPBUTTON_Y3 + Constants.SHOP_TEXT_YTOTOP, paint);
	}
}
