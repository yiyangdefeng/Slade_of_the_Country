package com.example.slade_of_the_country;

import com.example.slade_of_the_country.MyView.MoveButton;

import cn.edu.tsinghua.academic.c00740273.magictower.engine.Engine;
import cn.edu.tsinghua.academic.c00740273.magictower.standard.StandardTile;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

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
		canvas.drawBitmap(pictures
				.getScaledTileBitmap(pictures.background, "background",
						Constants.MYSCREENWIDTH, Constants.MYSCREENHEIGHT),
				Constants.ZERO, Constants.ZERO, paint);
		
		// canvas.drawBitmap(pictures.scaledbackground, Constants.MAINSURFACEX,
		// Constants.MAINSURFACEY, paint);
		canvas.drawBitmap(pictures.getScaledTileBitmap(pictures.button_up_pop,
				"button_up_pop", (int) (Constants.BUTTONWIDTH),
				(int) (Constants.BUTTONWIDTH)), Constants.UPBUTTONX,
				Constants.UPBUTTONY, paint);
		canvas.drawBitmap(pictures.getScaledTileBitmap(
				pictures.button_down_pop, "button_down_pop",
				(int) (Constants.BUTTONWIDTH), (int) (Constants.BUTTONWIDTH)),
				Constants.UPBUTTONX, Constants.DOWNBUTTONY, paint);
		canvas.drawBitmap(pictures.getScaledTileBitmap(
				pictures.button_left_pop, "button_left_pop",
				(int) (Constants.BUTTONWIDTH), (int) (Constants.BUTTONWIDTH)),
				Constants.LEFTBUTTONX, Constants.LEFTBUTTONY, paint);
		canvas.drawBitmap(pictures.getScaledTileBitmap(
				pictures.button_right_pop, "button_right_pop",
				(int) (Constants.BUTTONWIDTH), (int) (Constants.BUTTONWIDTH)),
				Constants.RIGHTBUTTONX, Constants.LEFTBUTTONY, paint);
		// canvas.drawBitmap(pictures.scaledwarrior,
		// hero.CalcPixelNumber()[0],hero.CalcPixelNumber()[1],paint);
		canvas.drawBitmap(pictures.getScaledTileBitmap(
				pictures.banner_trans, "banner_trans",
				Constants.GAMELOGOWIDTH, Constants.GAMELOGOHEIGHT),
				Constants.TITLEX, Constants.TITLEY, paint);
		paint.setColor(Color.BLACK);
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
		paint.setTextSize(Constants.NORMALFONTSIZE);
		canvas.drawText(Integer.toString(z), Constants.TEXT_DIX
				+ Constants.NORMALFONTSIZE, Constants.TEXT_DIY, paint);
		canvas.drawBitmap(pictures.getScaledTileBitmap(pictures.save, "save",
				Constants.SAVEBUTTONWIDTH, Constants.SAVEBUTTONHEIGHT),
				Constants.SAVEBUTTONX, Constants.SAVEBUTTONY, paint);
		paint.setTextSize(Constants.SMALLFONTSIZE);
		StandardTile[][] currenttile = (StandardTile[][]) engine
				.getLayerTiles(z);
		int X = currenttile.length;
		int Y = currenttile[0].length;
		float tilewidth = ((float) (Constants.MAINGRID_RIGHTX - Constants.MAINGRID_LEFTX) / X);
		float tileheight = ((float) (Constants.MAINGRID_BOTTOMY - Constants.MAINGRID_UPY) / Y);
		int picturewidth = (int)Math.ceil(tilewidth);
		int pictureheight = (int)Math.ceil(tileheight);
		paint.setTextSize(tileheight / 2);
		String imagename;
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				canvas.drawBitmap(pictures.getScaledTileBitmap(pictures.floor,
						"floor",picturewidth,pictureheight),
						Constants.MAINGRID_LEFTX + tilewidth * i,
						Constants.MAINGRID_UPY + tileheight * j,
						paint);
				if ((Boolean) currenttile[j][i].getRenderingData().get(
						"character") == true) {
					if (button != null) {
						switch (button) {
						case UP:
							canvas.drawBitmap(pictures.getScaledTileBitmap(
									pictures.warrior_up, "warrior_up",
									picturewidth,pictureheight),
									Constants.MAINGRID_LEFTX + tilewidth * i,
									Constants.MAINGRID_UPY + tileheight * j,
									paint);
							break;
						case DOWN:
							canvas.drawBitmap(pictures.getScaledTileBitmap(
									pictures.warrior_down, "warrior_down",
									picturewidth,pictureheight),
									Constants.MAINGRID_LEFTX + tilewidth * i,
									Constants.MAINGRID_UPY + tileheight * j,
									paint);
							break;
						case LEFT:
							canvas.drawBitmap(pictures.getScaledTileBitmap(
									pictures.warrior_left, "warrior_left",
									picturewidth,pictureheight),
									Constants.MAINGRID_LEFTX + tilewidth * i,
									Constants.MAINGRID_UPY + tileheight * j,
									paint);
							break;
						case RIGHT:
							canvas.drawBitmap(pictures.getScaledTileBitmap(
									pictures.warrior_right, "warrior_right",
									picturewidth,pictureheight),
									Constants.MAINGRID_LEFTX + tilewidth * i,
									Constants.MAINGRID_UPY + tileheight * j,
									paint);
							break;
						}
					}

				} else if ((imagename = (String) currenttile[j][i]
						.getRenderingData().get("image")) != null) {
					try {
						canvas.drawBitmap(pictures.getScaledTileBitmap(
								(Bitmap) pictures.getClass()
										.getField(imagename).get(pictures),
								imagename,picturewidth,pictureheight),
								Constants.MAINGRID_LEFTX + tilewidth * i,
								Constants.MAINGRID_UPY + tileheight * j, paint);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				} 
			}
		}

	}

	public void setButton(MoveButton button) {
		this.button = button;
	}
}
