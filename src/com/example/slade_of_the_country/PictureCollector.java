package com.example.slade_of_the_country;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class PictureCollector {
	public Map<String, Bitmap> scaledBitmaps;
	// for gameinterface
	//
	//
	//
	public Bitmap background;
	public Bitmap warrior;
	public Bitmap warrior_left;
	public Bitmap warrior_right;
	public Bitmap warrior_up;
	public Bitmap warrior_down;
	public Bitmap save;
	public Bitmap button_up_pop;
	public Bitmap button_up_press;
	public Bitmap button_down_pop;
	public Bitmap button_down_press;
	public Bitmap button_left_pop;
	public Bitmap button_left_press;
	public Bitmap button_right_pop;
	public Bitmap button_right_press;
	public Bitmap wall;
	public Bitmap floor;
	
	public Bitmap slade_of_the_country;//sacle 3:1

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
    public Bitmap elevator;
    public Bitmap special_warrior;
    public Bitmap ak47;
    public Bitmap banner_trans;
    public Bitmap big_cure_box;
    public Bitmap blue_key;
    public Bitmap bluegate;
    public Bitmap blackcoat;
    public Bitmap silvercoat;
    public Bitmap goldcoat;
    public Bitmap cure_box;
    public Bitmap dog;
    public Bitmap downstairs;
    public Bitmap fire_eye;
    public Bitmap mastiff;
    public Bitmap m16;
    public Bitmap milktea;
    public Bitmap npc;
    public Bitmap pistol;
    public Bitmap purple_kyrin;
    public Bitmap red_key;
    public Bitmap redgate;
    public Bitmap shop;
    public Bitmap sword;
    public Bitmap u95;
    public Bitmap upstairs;
    public Bitmap tgate;
    public Bitmap wolf;
    
   
    
    
    
	// for instructioninterface
	//
	//
	//
	public Bitmap previouspage;
	public Bitmap nextpage;
	public Bitmap rrreturn;

	

	public PictureCollector(View view) {
		scaledBitmaps = new HashMap<String, Bitmap>();
		background = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.background);
		button_up_pop = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.button_up_pop);
		button_up_press = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.button_up_press);
		button_left_pop = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.button_left_pop);
		button_left_press = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.button_left_press);
		button_right_pop = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.button_right_pop);
		button_right_press = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.button_right_press);
		button_down_pop = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.button_down_pop);
		button_down_press = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.button_down_press);
		
		wall = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.wall);
		floor = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.floor);
		
		slade_of_the_country = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.slade_of_the_country);
		
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
				R.drawable.oppo1);
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
		
		elevator = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.elevator);
		special_warrior = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.special_warrior);
		ak47 = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.ak47);
		banner_trans = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.banner_trans);
		big_cure_box = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.big_cure_box);
		blue_key = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.blue_key);
		bluegate = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.bluegate);
		blackcoat = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.blackcoat);
		silvercoat = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.silvercoat);
		goldcoat = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.goldcoat);
		cure_box = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.cure_box);
		dog = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.dog);
		downstairs = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.downstairs);
		fire_eye = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.fire_eye);
		mastiff = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.mastiff);
		m16 = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.m16);
		milktea = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.milktea);
		npc = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.npc);
		pistol = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.pistol);
		purple_kyrin = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.purple_kyrin);
		red_key = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.red_key);
		redgate = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.redgate);
		shop = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.shop);
		sword = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.sword);
		u95 = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.u95);
		upstairs = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.upstairs);
		tgate = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.tgate);
		wolf = BitmapFactory.decodeResource(view.getResources(),
				R.drawable.wolf);
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
