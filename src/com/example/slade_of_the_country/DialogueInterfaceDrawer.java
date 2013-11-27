package com.example.slade_of_the_country;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;

public class DialogueInterfaceDrawer {
	private TextDrawer td;
	private PictureCollector pictures;
	private String dialoguecontent;
	private String character;

	public DialogueInterfaceDrawer(PictureCollector pictures) {
		td = new TextDrawer("", Constants.DIALOGUE_INTERFACE_X,
				Constants.DIALOGUE_INTERFACE_Y,
				Constants.DIALOGUE_INTERFACE_WIDTH,
				Constants.DIALOGUE_INTERFACE_HEIGHT, Color.WHITE, Color.BLACK,
				Constants.SMALLFONTSIZE);
		this.pictures = pictures;
		dialoguecontent = "";
		character = "warrior";
	}

	public void draw(Canvas canvas, Matrix canvasMatrix) {
		canvas.setMatrix(canvasMatrix);
		td.SetText(dialoguecontent);
		td.DrawText(canvas);
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
		}
		else {
			td.MoveText(Constants.FORWARD);
			return false;
		}
	}
}
