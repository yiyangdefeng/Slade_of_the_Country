package com.example.slade_of_the_country;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;



public class InstructionInterfaceDrawer {
    private Map<Long, TextDrawer> tds;
	private PictureCollector pictures;
    
	public InstructionInterfaceDrawer(PictureCollector pictures) {
		tds = new HashMap<Long, TextDrawer>();
		this.pictures = pictures;
	}
	
	protected TextDrawer getTextDrawer(int width, int height) {
		long key = (width << 32) | height;
		if (!tds.containsKey(key)) {
			float[] wh = {width,height};
			tds.put(key,new TextDrawer(Texts.TEXT_INSTRUCTION,0,0,(int)(wh[0]),(int)(wh[1] - Constants.NORMALFONTSIZE - Constants.MARGIN),Color.BLACK,Color.WHITE,Constants.NORMALFONTSIZE));
		}
		return tds.get(key);
	}
	
	public void draw (Canvas canvas, Matrix matrix) {
		canvas.setMatrix(matrix);
		getTextDrawer(Constants.MYSCREENWIDTH,Constants.INSTRUCTION_LOGO_Y).DrawText(canvas);
		canvas.drawBitmap(pictures.getScaledTileBitmap(pictures.rrreturn, "rrreturn", 
				(int) (Constants.INSTRUCTION_LOGOWIDTH),
				(int) (Constants.INSTRUCTION_LOGOHEIGHT)), Constants.INSTRUCTION_LOGO_X1, Constants.INSTRUCTION_LOGO_Y,new Paint());
		canvas.drawBitmap(pictures.getScaledTileBitmap(pictures.nextpage, "nextpage", 
				(int) (Constants.INSTRUCTION_LOGOWIDTH),
				(int) (Constants.INSTRUCTION_LOGOHEIGHT)), Constants.INSTRUCTION_LOGO_X2, Constants.INSTRUCTION_LOGO_Y,new Paint());
		canvas.drawBitmap(pictures.getScaledTileBitmap(pictures.previouspage, "previouspage", 
				(int) (Constants.INSTRUCTION_LOGOWIDTH),
				(int) (Constants.INSTRUCTION_LOGOHEIGHT)), Constants.INSTRUCTION_LOGO_X3, Constants.INSTRUCTION_LOGO_Y,new Paint());
	}
	
	public void MoveText(boolean backorforward) {
		for(TextDrawer td:tds.values()){
			td.MoveText(backorforward);
		}
	}
}

