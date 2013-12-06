package com.example.slade_of_the_country;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class DialogueInterfaceDrawer {
	private TextDrawer td;
	private PictureCollector pictures;
	private String dialoguecontent;
	private String character;
	Paint paint;

	public DialogueInterfaceDrawer(PictureCollector pictures) {
		paint = new Paint();
		td = new TextDrawer("", Constants.DIALOGUE_INTERFACE_X,
				Constants.DIALOGUE_INTERFACE_Y,
				Constants.DIALOGUE_INTERFACE_WIDTH,
				Constants.DIALOGUE_INTERFACE_HEIGHT, Color.BLACK, Color.WHITE,
				Constants.SMALLFONTSIZE);
		this.pictures = pictures;
		dialoguecontent = "";
		character = "warrior_left";
	}

	public void draw(Canvas canvas, Matrix canvasMatrix) {
		canvas.setMatrix(canvasMatrix);
		td.SetText(dialoguecontent);
		td.DrawText(canvas);
		try {
			canvas.drawBitmap(pictures.getScaledTileBitmap((Bitmap) pictures
					.getClass().getField(character).get(character), character
					+ "for dialog", Constants.DIALOGUE_CHARACTER_WIDTH,
					Constants.DIALOGUE_CHARACTER_HEIGHT),
					Constants.DIALOGUE_CHARACTER_X,
					Constants.DIALOGUE_CHARACTER_Y, paint);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setDialogueContent(String dialoguecontent) {
		this.dialoguecontent = dialoguecontent;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public boolean onTouchHandler() {
		if (td.isEnd()) {
			return true;
		} else {
			td.MoveText(Constants.FORWARD);
			return false;
		}
	}
}
