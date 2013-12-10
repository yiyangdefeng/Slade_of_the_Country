package com.example.slade_of_the_country;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.edu.tsinghua.academic.c00740273.magictower.engine.Engine;
import cn.edu.tsinghua.academic.c00740273.magictower.standard.StandardTile;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

public class FireEyeInterfaceDrawer {
	protected Paint paint;
	protected Engine engine;
	protected PictureCollector pictures;
	protected int totalopponum;
	protected int totalpages;
	protected int currentpage;
	protected List<MyBundle> information;
	protected Set<String> opponames;

	public FireEyeInterfaceDrawer(Engine engine, PictureCollector pictures) {
		paint = new Paint();
		this.engine = engine;
		this.pictures = pictures;
		totalpages = 0;
		currentpage = 0;
		totalopponum = 0;
		information = new ArrayList<MyBundle>();
		opponames = new HashSet<String>();
	}

	public void draw(Canvas canvas, Matrix canvasMatrix) {
		Log.e("test", "cp:" + currentpage + " tp:" + totalpages + " ton:"
				+ totalopponum);
		canvas.setMatrix(canvasMatrix);
		paint.setColor(Color.YELLOW);
		paint.setTextSize(Constants.NORMALFONTSIZE);
		canvas.drawText(Texts.TEXT_OPPONAME, Constants.OPPONAME_X,
				Constants.OPPONAME_Y, paint);
		canvas.drawText(Texts.TEXT_OPPOTITLE, Constants.OPPOTITLE_X,
				Constants.OPPONAME_Y, paint);
		canvas.drawText(Texts.TEXT_OPPOATTACK, Constants.OPPOATTACK_X,
				Constants.OPPONAME_Y, paint);
		canvas.drawText(Texts.TEXT_OPPODEFENSE, Constants.OPPODEFENSE_X,
				Constants.OPPONAME_Y, paint);
		canvas.drawText(Texts.TEXT_OPPOHEALTH, Constants.OPPOHEALTH_X,
				Constants.OPPONAME_Y, paint);
		canvas.drawText(Texts.TEXT_SELFHEALTHLOSS, Constants.SELFHEALTHLOSS_X,
				Constants.OPPONAME_Y, paint);

		paint.setColor(Color.WHITE);
		paint.setTextSize(Constants.SMALLFONTSIZE);
		int currentoppo = currentpage * 5;
		for (int i = currentoppo; i < Math.min(currentoppo + 5, totalopponum); i++) {
			MyBundle mb = information.get(i);
			canvas.drawText(mb.opponame, Constants.OPPONAME_X,
					Constants.FIRST_OPPO_Y + (i - currentoppo)
							* Constants.FIREEYE_DELTAY, paint);
			canvas.drawText(mb.oppotitle, Constants.OPPOTITLE_X,
					Constants.FIRST_OPPO_Y + (i - currentoppo)
							* Constants.FIREEYE_DELTAY, paint);
			canvas.drawText(mb.oppoattack, Constants.OPPOATTACK_X,
					Constants.FIRST_OPPO_Y + (i - currentoppo)
							* Constants.FIREEYE_DELTAY, paint);
			canvas.drawText(mb.oppodefense, Constants.OPPODEFENSE_X,
					Constants.FIRST_OPPO_Y + (i - currentoppo)
							* Constants.FIREEYE_DELTAY, paint);
			canvas.drawText(mb.oppohealth, Constants.OPPOHEALTH_X,
					Constants.FIRST_OPPO_Y + (i - currentoppo)
							* Constants.FIREEYE_DELTAY, paint);
			canvas.drawText(mb.selfhealthloss, Constants.SELFHEALTHLOSS_X,
					Constants.FIRST_OPPO_Y + (i - currentoppo)
							* Constants.FIREEYE_DELTAY, paint);
			canvas.drawBitmap(pictures.getScaledTileBitmap(mb.oppoimage,
					mb.opponame + "for fireeye", Constants.FIREEYE_IMAGESIZE,
					Constants.FIREEYE_IMAGESIZE), Constants.PICTURE1_X,
					Constants.PICTURE1_Y + (i - currentoppo)
							* Constants.FIREEYE_DELTAY, paint);
		}
	}

	public void SearchOppo() {
		int z = engine.getCurrentCoordinate().getZ();
		StandardTile[][] currenttile = (StandardTile[][]) engine
				.getLayerTiles(z);
		int X = currenttile.length;
		int Y = currenttile[0].length;
		totalopponum = 0;
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				Object isoppo = currenttile[j][i].getRenderingData().get(
						"isoppo");
				if (isoppo != null) {
					MyBundle mb = new MyBundle();
					String imagename = (String) currenttile[j][i]
							.getRenderingData().get("image");
					Log.e("test",imagename);
					if (!opponames.contains(imagename)) {
						opponames.add(imagename);
						try {
							mb.oppoimage = (Bitmap) pictures.getClass()
									.getField(imagename).get(pictures);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
						mb.oppoattack = "attack";
						mb.oppodefense = "defense";
						mb.oppohealth = "health";
						mb.opponame = imagename;
						mb.oppotitle = "oppotitle";
						mb.selfhealthloss = "selfhealthloss";
						information.add(mb);
					}
				}
			}
		}
		totalopponum = information.size();
		if (totalopponum != 0) {
		totalpages = (int) (Math.ceil((double)totalopponum / 5)) - 1;
		} 
		else {
			totalpages = 0;
		}
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
		totalopponum = 0;
		information.clear();
		opponames.clear();
	}
}

class MyBundle {
	public String opponame;
	public String oppotitle;
	public String oppoattack;
	public String oppodefense;
	public String oppohealth;
	public String selfhealthloss;
	public Bitmap oppoimage;

	public MyBundle() {
		opponame = "";
		oppotitle = "";
		oppoattack = "";
		oppodefense = "";
		oppohealth = "";
		selfhealthloss = "";
		oppoimage = null;
	}
}
