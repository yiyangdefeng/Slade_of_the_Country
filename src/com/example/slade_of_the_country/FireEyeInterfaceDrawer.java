package com.example.slade_of_the_country;

import java.util.HashMap;
import java.util.Map;

import cn.edu.tsinghua.academic.c00740273.magictower.engine.Engine;
import cn.edu.tsinghua.academic.c00740273.magictower.standard.StandardTile;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class FireEyeInterfaceDrawer {
	protected Paint paint;
	protected Engine engine;
	protected PictureCollector pictures;
	protected int totalpages;
	protected int currentpage;
	protected Map<Bitmap, String[]> information;

	public FireEyeInterfaceDrawer(Engine engine, PictureCollector pictures) {
		paint = new Paint();
		this.engine = engine;
		this.pictures = pictures;
		totalpages = 0;
		currentpage = 0;
		information = new HashMap<Bitmap, String[]>();
	}

	public void draw(Canvas canvas, Matrix canvasMatrix) {
		
	}

	public void SearchOppo()  {
		int z = engine.getCurrentCoordinate().getZ();
		StandardTile[][] currenttile = (StandardTile[][]) engine
				.getLayerTiles(z);
		int X = currenttile.length;
		int Y = currenttile[0].length;
		int totalopponum = 0;
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				Object isoppo = currenttile[j][i].getRenderingData().get("isoppo");
				if (isoppo != null) {
					String imagename = (String)currenttile[j][i].getRenderingData().get("image");
					String[] oppoinfo = new String[4];
					try {
						information.put((Bitmap)pictures.getClass().getField(imagename).get(pictures),oppoinfo);
					} catch (Exception e) {
						throw new RuntimeException(e);
					} 
				}
			}
		}
		totalopponum = information.size();
		totalpages = totalopponum / 5;
	}
	
	public boolean getIsEnd() {
		if (currentpage == totalpages) {
			return true;
		} else {
			return false;
		}
	}

	public void goNextPage() {
		currentpage++;
	}

	public void Reset() {
		currentpage = 0;
		totalpages = 0;
		information.clear();
	}
}
