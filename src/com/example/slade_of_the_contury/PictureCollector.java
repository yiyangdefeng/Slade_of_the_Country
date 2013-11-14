package com.example.slade_of_the_contury;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class PictureCollector {
	
	//for gameinterface
	//
	//
	//
	private  Bitmap background;// = BitmapFactory.decodeResource(view.getResources(), R.drawable.background);
	private  Bitmap buttonup ;//= BitmapFactory.decodeResource(view.getResources(), R.drawable.buttonup);
	private  Bitmap buttondown ;//= BitmapFactory.decodeResource(view.getResources(), R.drawable.buttondown);
	public  Bitmap warrior ;//= BitmapFactory.decodeResource(myview.getResources(), R.drawable.warrior);
	public  Bitmap scaledbuttonup ;//= Bitmap.createScaledBitmap(buttonup, (int)(Constants.BUTTONWIDTH),(int)(Constants.BUTTONWIDTH), true);
	public  Bitmap scaledbackground ;//= Bitmap.createScaledBitmap(background, (int)(Constants.MYSCREENHEIGHT),(int)(Constants.MYSCREENHEIGHT), true);
	public  Bitmap scaledbuttondown ;//= Bitmap.createScaledBitmap(buttondown, (int)(Constants.BUTTONWIDTH), (int)(Constants.BUTTONWIDTH), true);
	public Bitmap save;
	//public  scaledwarrior = Bitmap.createScaledBitmap(warrior,(int)(hero.getGridHeight()),(int)(hero.getGridWidth()),true);
	
	
	//for startinterface
	//
	//
	//
	private  Bitmap start ;//= BitmapFactory.decodeResource(view.getResources(), R.drawable.start);
	private  Bitmap load ;//= BitmapFactory.decodeResource(view.getResources(), R.drawable.load);
	private  Bitmap instruction ;//= BitmapFactory.decodeResource(view.getResources(), R.drawable.instruction);
	private  Bitmap exit ;//= BitmapFactory.decodeResource(view.getResources(), R.drawable.exit);
	public  Bitmap scaledstart ;//= Bitmap.createScaledBitmap(start, (int)(Constants.START_LOGOHEIGHT),(int)(Constants.START_LOGOWIDTH), true);
	public  Bitmap scaledload ;//= Bitmap.createScaledBitmap(load, (int)(Constants.START_LOGOHEIGHT),(int)(Constants.START_LOGOWIDTH), true);
	public  Bitmap scaledinstruction ;//= Bitmap.createScaledBitmap(instruction, (int)(Constants.START_LOGOHEIGHT), (int)(Constants.START_LOGOWIDTH), true);
	public  Bitmap scaledexit ;//= Bitmap.createScaledBitmap(exit, (int)(Constants.START_LOGOHEIGHT),(int)(Constants.START_LOGOWIDTH), true);
	
	//for instructioninterface
	//
	//
	//
	private Bitmap previouspage;
	private Bitmap nextpage;
	private Bitmap rrreturn;
	public Bitmap scaledpreviouspage;
	public Bitmap scalednextpage;
	public Bitmap scaledrrreturn;
	
	public PictureCollector(View view) {

		 background = BitmapFactory.decodeResource(view.getResources(), R.drawable.background);
		 buttonup = BitmapFactory.decodeResource(view.getResources(), R.drawable.buttonup);
		 buttondown = BitmapFactory.decodeResource(view.getResources(), R.drawable.buttondown);
		 warrior = BitmapFactory.decodeResource(view.getResources(), R.drawable.warrior);
		 save = BitmapFactory.decodeResource(view.getResources(), R.drawable.save);
		 scaledbuttonup = Bitmap.createScaledBitmap(buttonup, (int)(Constants.BUTTONWIDTH),(int)(Constants.BUTTONWIDTH), true);
		 scaledbackground = Bitmap.createScaledBitmap(background, (int)(Constants.MYSCREENHEIGHT),(int)(Constants.MYSCREENHEIGHT), true);
		 scaledbuttondown = Bitmap.createScaledBitmap(buttondown, (int)(Constants.BUTTONWIDTH), (int)(Constants.BUTTONWIDTH), true);
		//public static scaledwarrior = Bitmap.createScaledBitmap(warrior,(int)(hero.getGridHeight()),(int)(hero.getGridWidth()),true);
		
		
		//for startinterface
		//
		//
		//
		 start = BitmapFactory.decodeResource(view.getResources(), R.drawable.start);
		 load = BitmapFactory.decodeResource(view.getResources(), R.drawable.load);
		 instruction = BitmapFactory.decodeResource(view.getResources(), R.drawable.instruction);
		 exit = BitmapFactory.decodeResource(view.getResources(), R.drawable.exit);
		 scaledstart = Bitmap.createScaledBitmap(start, (int)(Constants.START_LOGOWIDTH),(int)(Constants.START_LOGOHEIGHT), true);
		 scaledload = Bitmap.createScaledBitmap(load, (int)(Constants.START_LOGOWIDTH),(int)(Constants.START_LOGOHEIGHT), true);
		 scaledinstruction = Bitmap.createScaledBitmap(instruction, (int)(Constants.START_LOGOWIDTH), (int)(Constants.START_LOGOHEIGHT), true);
		 scaledexit = Bitmap.createScaledBitmap(exit, (int)(Constants.START_LOGOWIDTH),(int)(Constants.START_LOGOHEIGHT), true);
		 
		 //for instruction interface
		 //
		 //
		 //
		 rrreturn = BitmapFactory.decodeResource(view.getResources(), R.drawable.rrreturn);
		 previouspage = BitmapFactory.decodeResource(view.getResources(), R.drawable.previouspage);
		 nextpage = BitmapFactory.decodeResource(view.getResources(), R.drawable.nextpage);
		 scaledrrreturn = Bitmap.createScaledBitmap(rrreturn, (int)(Constants.INSTRUCTION_LOGOWIDTH),(int)(Constants.INSTRUCTION_LOGOHEIGHT), true);
		 scalednextpage = Bitmap.createScaledBitmap(nextpage, (int)(Constants.INSTRUCTION_LOGOWIDTH),(int)(Constants.INSTRUCTION_LOGOHEIGHT), true);
		 scaledpreviouspage = Bitmap.createScaledBitmap(previouspage, (int)(Constants.INSTRUCTION_LOGOWIDTH),(int)(Constants.INSTRUCTION_LOGOHEIGHT), true);
		 

	}
	
}
