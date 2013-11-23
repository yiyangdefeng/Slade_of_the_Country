package com.example.slade_of_the_country;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;

public class DialogueInterfaceDrawer {
	private TextDrawer td;
	private PictureCollector pictures;
	

	public DialogueInterfaceDrawer(PictureCollector pictures) {
		td = new TextDrawer("", Constants.DIALOGUE_INTERFACE_X,
				Constants.DIALOGUE_INTERFACE_Y,
				Constants.DIALOGUE_INTERFACE_WIDTH,
				Constants.DIALOGUE_INTERFACE_HEIGHT, Color.WHITE, Color.BLACK,
				Constants.SMALLFONTSIZE);
		this.pictures = pictures;
	}

	public void draw(Canvas canvas, Matrix canvasMatrix, String dialoguecontent) {
		canvas.setMatrix(canvasMatrix);
		
		td.SetText(dialoguecontent);
		td.DrawText(canvas);

	}
}
