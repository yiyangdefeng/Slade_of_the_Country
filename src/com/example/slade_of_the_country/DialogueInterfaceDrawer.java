package com.example.slade_of_the_country;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;


public class DialogueInterfaceDrawer {
	private PictureCollector pictures;
	private String dialoguecontent;
	private String character;
	private CharacterInfos ci;
	Paint paint;

	public DialogueInterfaceDrawer(PictureCollector pictures) {
		paint = new Paint();
		this.pictures = pictures;
		dialoguecontent = "";
		character = "warrior_left";
		ci = new CharacterInfos();
	}

	public void draw(Canvas canvas, Matrix canvasMatrix) {
		Log.e("test","dialog interface did draw");
		canvas.setMatrix(canvasMatrix);
		paint.setColor(Color.BLACK);
		canvas.drawRect(Constants.DIALOGUE_INTERFACE_X,
				Constants.DIALOGUE_INTERFACE_Y,
				Constants.FIGHT_INTERFACE_RIGHT, Constants.MESSAGE_BOTTOM,
				paint);
		String charactername = ci.OPPONAME.get(character);
		if (charactername != null) {
			paint.setColor(Color.YELLOW);
			paint.setTextSize(Constants.NORMALFONTSIZE);
			canvas.drawText(charactername, Constants.DIALOGUE_INTERFACE_X
					+ Constants.MARGIN * 3, Constants.DIALOGUE_INTERFACE_Y + Constants.NORMALFONTSIZE
					, paint);
		}
		paint.setColor(Color.WHITE);
		paint.setTextSize(Constants.SMALLFONTSIZE);
		String[] strings = Texts.StringSpliter(dialoguecontent,
				Constants.FIGHT_INTERFACE_LEFT + Constants.MARGIN * 1,
				Constants.FIGHT_INTERFACE_RIGHT - Constants.MARGIN * 1,
				Constants.SMALLFONTSIZE);
		for (int i = 0; i < strings.length; i++) {
			canvas.drawText(strings[i],
					(Constants.DIALOGUE_INTERFACE_X + Constants.MARGIN * 1),
					(Constants.DIALOGUE_INTERFACE_Y + Constants.SMALLFONTSIZE
							* (i + 1) + Constants.NORMALFONTSIZE + Constants.SMALLMARGIN * 10), paint);

		}
		try {
			canvas.drawBitmap(pictures.getScaledTileBitmap((Bitmap) pictures
					.getClass().getField(character).get(pictures), character
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

}
