package com.example.slade_of_the_country;

import java.util.Arrays;
import java.util.List;
import com.example.slade_of_the_country.MyView.MoveButton;

import cn.edu.tsinghua.academic.c00740273.magictower.engine.Engine;
import cn.edu.tsinghua.academic.c00740273.magictower.standard.StandardTile;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

public class GameInterfaceDrawer {
	private Paint paint = new Paint();
	private PictureCollector pictures;

	protected MoveButton button;

	public GameInterfaceDrawer(PictureCollector pictures) {
		this.pictures = pictures;
		button = MoveButton.UP;
	}

	public void draw(Canvas canvas, Matrix canvasMatrix, Engine engine) {
		canvas.setMatrix(canvasMatrix);
		canvas.drawColor(Color.BLACK);
		// canvas.drawBitmap(pictures.scaledbackground, Constants.MAINSURFACEX,
		// Constants.MAINSURFACEY, paint);
		canvas.drawBitmap(pictures.getScaledTileBitmap(pictures.buttonup,
				"buttonup", (int) (Constants.BUTTONWIDTH),
				(int) (Constants.BUTTONWIDTH)), Constants.UPBUTTONX,
				Constants.UPBUTTONY, paint);
		canvas.drawBitmap(pictures.getScaledTileBitmap(pictures.buttonup,
				"buttonup", (int) (Constants.BUTTONWIDTH),
				(int) (Constants.BUTTONWIDTH)), Constants.UPBUTTONX,
				Constants.DOWNBUTTONY, paint);
		canvas.drawBitmap(pictures.getScaledTileBitmap(pictures.buttonup,
				"buttonup", (int) (Constants.BUTTONWIDTH),
				(int) (Constants.BUTTONWIDTH)), Constants.LEFTBUTTONX,
				Constants.LEFTBUTTONY, paint);
		canvas.drawBitmap(pictures.getScaledTileBitmap(pictures.buttonup,
				"buttonup", (int) (Constants.BUTTONWIDTH),
				(int) (Constants.BUTTONWIDTH)), Constants.RIGHTBUTTONX,
				Constants.LEFTBUTTONY, paint);
		// canvas.drawBitmap(pictures.scaledwarrior,
		// hero.CalcPixelNumber()[0],hero.CalcPixelNumber()[1],paint);
		paint.setColor(Color.RED);
		paint.setTextSize(Constants.TITLEFONTSIZE);
		canvas.drawText(Texts.TEXT_TITLE, Constants.TITLEX, Constants.TITLEY,
				paint);
		paint.setColor(Color.WHITE);
		paint.setTextSize(Constants.NORMALFONTSIZE);
		canvas.drawText(Texts.TEXT_DI, Constants.TEXT_DIX, Constants.TEXT_DIY,
				paint);
		canvas.drawText(Texts.TEXT_FLOOR, Constants.TEXT_FLOORX,
				Constants.TEXT_DIY, paint);
		paint.setTextSize(Constants.SMALLFONTSIZE);
		canvas.drawText(Texts.TEXT_BLOOD
				+ engine.getAttribute("health").toString(),
				Constants.TEXT_BLOODX,
				(Constants.TEXT_DIY + Constants.NORMALFONTSIZE), paint);
		canvas.drawText(Texts.TEXT_ATTACK
				+ engine.getAttribute("attack").toString(),
				Constants.TEXT_BLOODX, (Constants.TEXT_DIY
						+ Constants.NORMALFONTSIZE + Constants.SMALLFONTSIZE),
				paint);
		canvas.drawText(
				Texts.TEXT_DEFENCE + engine.getAttribute("defense").toString(),
				Constants.TEXT_BLOODX,
				(Constants.TEXT_DIY + Constants.NORMALFONTSIZE + 2 * Constants.SMALLFONTSIZE),
				paint);
		canvas.drawText(
				Texts.TEXT_GOLD + engine.getAttribute("gold").toString(),
				Constants.TEXT_BLOODX,
				(Constants.TEXT_DIY + Constants.NORMALFONTSIZE + 3 * Constants.SMALLFONTSIZE),
				paint);
		canvas.drawText(Texts.TEXT_YELLOW_KEY
				+ engine.getAttribute("key-y").toString(),
				Constants.TEXT_YELLOWKEYX,
				(Constants.TEXT_DIY + Constants.NORMALFONTSIZE), paint);
		canvas.drawText(Texts.TEXT_BLUE_KEY
				+ engine.getAttribute("key-b").toString(),
				Constants.TEXT_YELLOWKEYX, (Constants.TEXT_DIY
						+ Constants.NORMALFONTSIZE + Constants.SMALLFONTSIZE),
				paint);
		canvas.drawText(
				Texts.TEXT_RED_KEY + engine.getAttribute("key-r").toString(),
				Constants.TEXT_YELLOWKEYX,
				(Constants.TEXT_DIY + Constants.NORMALFONTSIZE + 2 * Constants.SMALLFONTSIZE),
				paint);
		int z = engine.getCurrentCoordinate().getZ();
		canvas.drawText(Integer.toString(z), Constants.TEXT_DIX
				+ Constants.NORMALFONTSIZE, Constants.TEXT_DIY, paint);
		canvas.drawBitmap(pictures.getScaledTileBitmap(pictures.save, "save",
				Constants.SAVEBUTTONWIDTH, Constants.SAVEBUTTONHEIGHT),
				Constants.SAVEBUTTONX, Constants.SAVEBUTTONY, paint);

		StandardTile[][] currenttile = (StandardTile[][]) engine
				.getLayerTiles(z);
		int X = currenttile.length;
		int Y = currenttile[0].length;
		float tilewidth = ((float) (Constants.MAINGRID_RIGHTX - Constants.MAINGRID_LEFTX) / X);
		float tileheight = ((float) (Constants.MAINGRID_BOTTOMY - Constants.MAINGRID_UPY) / Y);
		paint.setTextSize(tileheight / 2);
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				if(currenttile[j][i].getRenderingData().get("ismonster") != null) {
					Log.e("test","monster at " + i + "," + j);
				}
				if(Boolean.TRUE.equals(currenttile[j][i].getRenderingData().get("isboss"))) {
					Log.e("test","boss at " + i + "," + j);
					@SuppressWarnings("unchecked")
					List<List<String>> dialogue = (List<List<String>>)currenttile[j][i].getRenderingData().get("dialogue-before");
					for(List<String> dialogueLine: dialogue) {
						Log.e("test",Arrays.toString(dialogueLine.toArray()));
					}
				}
				if ((Boolean) currenttile[j][i].getRenderingData().get(
						"character") == true) {
					if (button != null) {
						switch (button) {
						case UP:
							canvas.drawBitmap(pictures.getScaledTileBitmap(
									pictures.warrior_up, "warrior_up",
									(int) tilewidth, (int) tileheight),
									Constants.MAINGRID_LEFTX + tilewidth * i,
									Constants.MAINGRID_UPY + tileheight * j,
									paint);
							break;
						case DOWN:
							canvas.drawBitmap(pictures.getScaledTileBitmap(
									pictures.warrior_down, "warrior_down",
									(int) tilewidth, (int) tileheight),
									Constants.MAINGRID_LEFTX + tilewidth * i,
									Constants.MAINGRID_UPY + tileheight * j,
									paint);
							break;
						case LEFT:
							canvas.drawBitmap(pictures.getScaledTileBitmap(
									pictures.warrior_left, "warrior_left",
									(int) tilewidth, (int) tileheight),
									Constants.MAINGRID_LEFTX + tilewidth * i,
									Constants.MAINGRID_UPY + tileheight * j,
									paint);
							break;
						case RIGHT:
							canvas.drawBitmap(pictures.getScaledTileBitmap(
									pictures.warrior_right, "warrior_right",
									(int) tilewidth, (int) tileheight),
									Constants.MAINGRID_LEFTX + tilewidth * i,
									Constants.MAINGRID_UPY + tileheight * j,
									paint);
							break;
						}
					}

				}
				canvas.drawText((String) currenttile[j][i].getRenderingData()
						.get("text"), Constants.MAINGRID_LEFTX + tilewidth * i,
						Constants.MAINGRID_UPY + tileheight * (j + 1), paint);

			}
		}

	}

	public void setButton(MoveButton button) {
		this.button = button;
	}
}
