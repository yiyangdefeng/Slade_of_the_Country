package com.example.slade_of_the_country;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class PictureCollector {
	public Map<String, Bitmap> scaledBitmaps;
	public Map<String, Bitmap> bitmaps;
	// for gameinterface
	//
	//
	//
	public Bitmap background;
	public Bitmap buttonup;// =
	public Bitmap buttondown;
	public Bitmap warrior;
	public Bitmap warrior_left;
	public Bitmap warrior_right;
	public Bitmap warrior_up;
	public Bitmap warrior_down;
	public Bitmap save;

	// for startinterface
	//
	//
	//
	public Bitmap start;
	public Bitmap load;
	public Bitmap instruction;
	public Bitmap exit;
	
	
	//for fight interface
    //
	//
	//
	
	public Bitmap oppo1;
    public Bitmap oppo2;
    public Bitmap oppo3;
    public Bitmap oppo4;
    public Bitmap oppo5;
    public Bitmap oppo6;
    public Bitmap oppo7;
    public Bitmap oppo8;
    public Bitmap oppo9;
    public Bitmap oppo10;
    
	// for instructioninterface
	//
	//
	//
	public Bitmap previouspage;
	public Bitmap nextpage;
	public Bitmap rrreturn;
	

	public PictureCollector(View view) {
		scaledBitmaps = new HashMap<String, Bitmap>();
		bitmaps = new HashMap<String, Bitmap>();
		background = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.background);
		buttonup = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.buttonup);
		buttondown = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.buttondown);
		warrior = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.warrior);
		warrior_left = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.warrior_left);
		warrior_right = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.warrior_right);
		warrior_up = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.warrior_up);
		warrior_down = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.warrior_down);

		save = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.save);
		
		start = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.start);
		load = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.load);
		instruction = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.instruction);
		exit = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.exit);
		
		rrreturn = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.rrreturn);
		previouspage = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.previouspage);
		nextpage = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.nextpage);
		
		oppo1 = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.oppo);
		oppo2 = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.oppo2);
		oppo3 = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.oppo3);
		oppo4 = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.oppo4);
		oppo5 = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.oppo5);
		oppo6 = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.oppo6);
		oppo7 = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.oppo7);
		oppo8 = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.oppo8);
		oppo9 = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.oppo9);
		oppo10 = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.oppo10);

	}
	
	protected void makeScaledTileBitmap(Bitmap original, String key, int width,
			int height) {
		if (scaledBitmaps.containsKey(key)) {
			return;
		}
		scaledBitmaps.put(key,
				Bitmap.createScaledBitmap(original, width, height, true));
	}	
	
	public Bitmap getScaledTileBitmap(Bitmap original, String key,
			int width, int height) {
		makeScaledTileBitmap(original, key, width, height);
		return scaledBitmaps.get(key);
	}
	
	public Bitmap getBitmapFromKey(String key) {
		return scaledBitmaps.get(key);
	}

}
