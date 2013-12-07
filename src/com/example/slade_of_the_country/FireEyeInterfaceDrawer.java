package com.example.slade_of_the_country;

import cn.edu.tsinghua.academic.c00740273.magictower.engine.Engine;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;


public class FireEyeInterfaceDrawer {
	protected Paint paint;
	protected Engine engine;
	protected PictureCollector pictures;
	
	public FireEyeInterfaceDrawer (Engine engine,PictureCollector pictures) {
		paint = new Paint();
		this.engine = engine;
		this.pictures = pictures;
	}
	
	public void draw(Canvas canvas,Matrix canvasMatrix) {
		
	}
}
