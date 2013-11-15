package com.example.slade_of_the_country;

import java.util.HashMap;
import java.util.Map;

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
	protected Map<String, Bitmap> scaledBitmaps = new HashMap<String, Bitmap>();
	protected int cachedWidth = -1, cachedHeight = -1;
	
	public GameInterfaceDrawer(PictureCollector pictures) {
		this.pictures = pictures;
	}
		
	public void draw(Canvas canvas,Matrix canvasMatrix,Engine engine) {
		canvas.setMatrix(canvasMatrix);
		canvas.drawColor(Color.BLACK);
		//canvas.drawBitmap(pictures.scaledbackground, Constants.MAINSURFACEX, Constants.MAINSURFACEY, paint);
		canvas.drawBitmap(pictures.scaledbuttonup,Constants.UPBUTTONX,Constants.UPBUTTONY,paint);
		canvas.drawBitmap(pictures.scaledbuttonup,Constants.UPBUTTONX,Constants.DOWNBUTTONY,paint);
		canvas.drawBitmap(pictures.scaledbuttonup,Constants.LEFTBUTTONX,Constants.LEFTBUTTONY,paint);
		canvas.drawBitmap(pictures.scaledbuttonup,Constants.RIGHTBUTTONX,Constants.LEFTBUTTONY,paint);
		//canvas.drawBitmap(pictures.scaledwarrior, hero.CalcPixelNumber()[0],hero.CalcPixelNumber()[1],paint);
		paint.setColor(Color.RED);
		paint.setTextSize(Constants.TITLEFONTSIZE);
		canvas.drawText(Texts.TEXT_TITLE, Constants.TITLEX, Constants.TITLEY, paint);
		paint.setColor(Color.WHITE);
		paint.setTextSize(Constants.NORMALFONTSIZE);
		canvas.drawText(Texts.TEXT_DI, Constants.TEXT_DIX, Constants.TEXT_DIY, paint);
		canvas.drawText(Texts.TEXT_FLOOR, Constants.TEXT_FLOORX, Constants.TEXT_DIY, paint);
		paint.setTextSize(Constants.SMALLFONTSIZE);
		canvas.drawText(Texts.TEXT_BLOOD + engine.getAttribute("health").toString(), Constants.TEXT_BLOODX, (Constants.TEXT_DIY + Constants.NORMALFONTSIZE), paint);
		canvas.drawText(Texts.TEXT_ATTACK + engine.getAttribute("attack").toString(), Constants.TEXT_BLOODX, (Constants.TEXT_DIY + Constants.NORMALFONTSIZE + Constants.SMALLFONTSIZE), paint);
		canvas.drawText(Texts.TEXT_DEFENCE + engine.getAttribute("defense").toString(), Constants.TEXT_BLOODX, (Constants.TEXT_DIY + Constants.NORMALFONTSIZE + 2 * Constants.SMALLFONTSIZE), paint);
		canvas.drawText(Texts.TEXT_GOLD + engine.getAttribute("gold").toString(), Constants.TEXT_BLOODX, (Constants.TEXT_DIY + Constants.NORMALFONTSIZE + 3 * Constants.SMALLFONTSIZE), paint);
		canvas.drawText(Texts.TEXT_YELLOW_KEY + engine.getAttribute("key-y").toString(), Constants.TEXT_YELLOWKEYX, (Constants.TEXT_DIY + Constants.NORMALFONTSIZE), paint);
		canvas.drawText(Texts.TEXT_BLUE_KEY + engine.getAttribute("key-b").toString(), Constants.TEXT_YELLOWKEYX,(Constants.TEXT_DIY + Constants.NORMALFONTSIZE + Constants.SMALLFONTSIZE), paint);
		canvas.drawText(Texts.TEXT_RED_KEY + engine.getAttribute("key-r").toString(), Constants.TEXT_YELLOWKEYX, (Constants.TEXT_DIY + Constants.NORMALFONTSIZE + 2 * Constants.SMALLFONTSIZE), paint);
		int z = engine.getCurrentCoordinate().getZ();
		canvas.drawText(Integer.toString(z),Constants.TEXT_DIX + Constants.NORMALFONTSIZE,Constants.TEXT_DIY,paint);
		canvas.drawBitmap(this.getScaledTileBitmap(pictures.save, "save", Constants.SAVEBUTTONWIDTH, Constants.SAVEBUTTONHEIGHT),Constants.SAVEBUTTONX,Constants.SAVEBUTTONY,paint);
		
		StandardTile[][] currenttile = (StandardTile[][]) engine.getLayerTiles(z);
		int X = currenttile.length;
		int Y = currenttile[0].length;
		float tilewidth = ((float)(Constants.MAINGRID_RIGHTX - Constants.MAINGRID_LEFTX) / X);
		float tileheight = ((float)(Constants.MAINGRID_BOTTOMY - Constants.MAINGRID_UPY) / Y);
		paint.setTextSize(tileheight/2);
		for (int i = 0; i < Y;i++ ) {
			for (int j = 0;j < X;j++) {
				if ((Boolean)currenttile[j][i].getRenderingData().get("character") == true) {
					canvas.drawBitmap(this.getScaledTileBitmap(pictures.warrior, "warrior", (int)tilewidth, (int)tileheight), Constants.MAINGRID_LEFTX + tilewidth * i, Constants.MAINGRID_UPY + tileheight * j,paint);
				}
				canvas.drawText((String)currenttile[j][i].getRenderingData().get("text"),Constants.MAINGRID_LEFTX + tilewidth * i, Constants.MAINGRID_UPY + tileheight * (j + 1),paint);
				
			}
		}
		
	}
	
	protected Bitmap getScaledTileBitmap(Bitmap original, String key, int width, int height) {
		if (width != cachedWidth || height == cachedHeight) {
			scaledBitmaps.clear();
		}
		makeScaledTileBitmap(original, key, width, height);
		return scaledBitmaps.get(key);
	}
	
	protected void makeScaledTileBitmap(Bitmap original, String key, int width, int height) {
		if (scaledBitmaps.containsKey(key)) {
			return;
		}
		scaledBitmaps.put(key, Bitmap.createScaledBitmap(original, width, height, true));
	}
}
