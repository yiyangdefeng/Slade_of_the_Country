package com.example.slade_of_the_contury;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.slade_of_the_contury.LoadInterfaceDrawer.Position;

import cn.edu.tsinghua.academic.c00740273.magictower.engine.Coordinate;
import cn.edu.tsinghua.academic.c00740273.magictower.engine.DataException;
import cn.edu.tsinghua.academic.c00740273.magictower.standard.StandardGame;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

	int status;
	private boolean tooverride;
	private Paint paint = new Paint();
	private float x = 0;
	private float y = 0;
	private Matrix canvasMatrix;
	private Matrix inverseMatrix;
	private List<Drawer> drawers;
	private StartInterfaceDrawer sid;
	private GameInterfaceDrawer gid;
	private InstructionInterfaceDrawer iid;
	private LoadInterfaceDrawer lid;
	private WarningMessageDrawer wid;
	protected PictureCollector pictures;
	private StandardGame sg;
	private MainActivity ma;

	// private StandardRenderer mr = (StandardRenderer) sg.getRenderer();

	protected enum MoveButton {
		UP, DOWN, LEFT, RIGHT
	}

	public MyView(Context c) {
		super(c);
		if (!(c instanceof MainActivity)) {
			throw new IllegalArgumentException("MainActivity expected.");
		}
		MainActivity ma = (MainActivity) c;
		this.ma = ma;
		canvasMatrix = this.buildMatrix(ma);
		inverseMatrix = new Matrix();
		canvasMatrix.invert(inverseMatrix);
		drawers = new ArrayList<Drawer>();
		status = Constants.STATUS_START;
		tooverride = false;
		
		pictures = new PictureCollector(this);
		sid = new StartInterfaceDrawer(pictures);
		gid = new GameInterfaceDrawer(pictures);
		iid = new InstructionInterfaceDrawer(pictures);
		lid = new LoadInterfaceDrawer(pictures, ma);
		this.invalidate();
	}

	protected Matrix buildMatrix(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		Matrix matrix = new Matrix();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		float expectedHeight = dm.widthPixels * (float) Constants.SCREENSCALE;
		float expectedWidth = dm.heightPixels / (float) Constants.SCREENSCALE;
		float scale = 1, dx = 0, dy = 0;
		if (expectedHeight < dm.heightPixels) {
			dy = (dm.heightPixels - expectedHeight) / 2;
			scale *= expectedHeight / Constants.MYSCREENHEIGHT;
		}
		if (expectedWidth < dm.widthPixels) {
			dx = (dm.widthPixels - expectedWidth) / 2;
			scale *= expectedWidth / Constants.MYSCREENWIDTH;
		}
		matrix = new Matrix();
		matrix.setValues(new float[] { scale, 0, dx, 0, scale, dy, 0, 0, 1 });
		return matrix;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		switch (status) {
		case Constants.STATUS_START:
			sid.draw(canvas, canvasMatrix);
			break;
		case Constants.STATUS_GAME:
			gid.draw(canvas, canvasMatrix, ma.engine);
			break;
		case Constants.STATUS_LOAD:
		case Constants.STATUS_SAVE:
			lid.draw(canvas, canvasMatrix);
			break;
		case Constants.STATUS_INSTRUCTION:
			iid.draw(canvas, canvasMatrix);
			break;
		case Constants.STATUS_WARNING:
			wid.drawWarningMessage(canvas,canvasMatrix);
			break;
		case Constants.STATUS_FIGHT:
			break;
		case Constants.STATUS_DIALOGUE:
			break;
		case Constants.STATUS_SHOP:
			break;
		}

		for (Drawer drawer : this.drawers) {
			drawer.draw(canvas, canvasMatrix);
		}
		drawers.clear();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float[] point = new float[] { event.getX(), event.getY() };
		inverseMatrix.mapPoints(point);
		x = point[0];
		y = point[1];
		if (event.getAction() == MotionEvent.ACTION_DOWN && status == Constants.STATUS_GAME) {
			GameInterfaceDownHandler();
		}
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			switch(status) {
			case Constants.STATUS_GAME:
				GameInterfaceUpHandler();
				break;
			case Constants.STATUS_START:
				StartInterfaceHandler();
				break;
			case Constants.STATUS_LOAD:
				LoadInterfaceHandler();
				break;
			case Constants.STATUS_INSTRUCTION:
				InstructionInterfaceHandler();
				break;
			case Constants.STATUS_SAVE:
				SaveInterfaceHandler();
				break;
			default:
				break;
			}
		}
		invalidate();
		return true;
	}

	protected MoveButton getButtonFromXY(float x, float y) {

		if (x >= Constants.UPBUTTONX
				&& x <= (Constants.UPBUTTONX + Constants.BUTTONWIDTH)
				&& y >= Constants.UPBUTTONY
				&& y <= (Constants.UPBUTTONY + Constants.BUTTONWIDTH)) {
			return MoveButton.UP;
		}

		else if (x >= Constants.UPBUTTONX
				&& x <= (Constants.UPBUTTONX + Constants.BUTTONWIDTH)
				&& y >= Constants.DOWNBUTTONY
				&& y <= (Constants.DOWNBUTTONY + Constants.BUTTONWIDTH)) {
			return MoveButton.DOWN;
		}

		else if (x >= Constants.LEFTBUTTONX
				&& x <= (Constants.LEFTBUTTONX + Constants.BUTTONWIDTH)
				&& y >= Constants.LEFTBUTTONY
				&& y <= (Constants.LEFTBUTTONY + Constants.BUTTONWIDTH)) {
			return MoveButton.LEFT;
		}

		else if (x >= Constants.RIGHTBUTTONX
				&& x <= (Constants.RIGHTBUTTONX + Constants.BUTTONWIDTH)
				&& y >= Constants.LEFTBUTTONY
				&& y <= (Constants.LEFTBUTTONY + Constants.BUTTONWIDTH)) {
			return MoveButton.RIGHT;
		}

		return null;
	}

	protected void reloadGame() throws IOException, DataException {
		byte[] bufferedbyte = new byte[1048576];
		int offset = 0, read;
		InputStream is = ma.getAssets().open("tradclone.json.txt");
		while ((read = is.read(bufferedbyte, offset, bufferedbyte.length
				- offset)) != -1) {
			offset += read;
		}
		is.close();
		sg = new StandardGame(new String(bufferedbyte, 0, offset, "utf-8"));
		ma.engine.loadGame(sg);
	}

	private void LoadInterfaceHandler() {
		if (x > Constants.INSTRUCTION_LOGO_X1
				&& x < (Constants.INSTRUCTION_LOGO_X1 + Constants.INSTRUCTION_LOGOWIDTH)
				&& y > Constants.INSTRUCTION_LOGO_Y) {
			status = Constants.STATUS_START;
		}
		else if (x > Constants.MARGIN && y > Constants.MARGIN
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT)) {
			if (lid.getAvailable(Position.ONE)) {
				ma.engine = lid.getEngine(Position.ONE);
				status = Constants.STATUS_GAME;
			}
		} else if (x > Constants.MARGIN
				&& y > (Constants.MARGIN * 2 + Constants.SAVEOPTIONHEIGHT)
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT) * 2) {
			if (lid.getAvailable(Position.TWO)) {
				ma.engine = lid.getEngine(Position.TWO);
				status = Constants.STATUS_GAME;
			}
		} else if (x > Constants.MARGIN
				&& y > (Constants.MARGIN * 3 + Constants.SAVEOPTIONHEIGHT * 2)
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT) * 3) {
			if (lid.getAvailable(Position.AUTO)) {
				ma.engine = lid.getEngine(Position.AUTO);
				status = Constants.STATUS_GAME;
			}
		}
	}

	public void SaveInterfaceHandler() {
		if (x > Constants.INSTRUCTION_LOGO_X1
				&& x < (Constants.INSTRUCTION_LOGO_X1 + Constants.INSTRUCTION_LOGOWIDTH)
				&& y > Constants.INSTRUCTION_LOGO_Y) {
		}
		else if (x > Constants.MARGIN && y > Constants.MARGIN
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT)) {
			if (lid.getAvailable(Position.ONE)) {
				ma.engine = lid.getEngine(Position.ONE);
			}
		} else if (x > Constants.MARGIN
				&& y > (Constants.MARGIN * 2 + Constants.SAVEOPTIONHEIGHT)
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT) * 2) {
			if (lid.getAvailable(Position.ONE)) {
				ma.engine = lid.getEngine(Position.TWO);
			}
		} else if (x > Constants.MARGIN
				&& y > (Constants.MARGIN * 3 + Constants.SAVEOPTIONHEIGHT * 2)
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT) * 3) {
			if (lid.getAvailable(Position.AUTO)) {
				ma.engine = lid.getEngine(Position.AUTO);
			}
		} else {
			return;
		}
		status = Constants.STATUS_GAME;
	}

	private void InstructionInterfaceHandler() {
		if (x > Constants.INSTRUCTION_LOGO_X1
				&& x < (Constants.INSTRUCTION_LOGO_X1 + Constants.INSTRUCTION_LOGOWIDTH)
				&& y > Constants.INSTRUCTION_LOGO_Y) {
			status = Constants.STATUS_START;
		} else if (x > Constants.INSTRUCTION_LOGO_X2
				&& x < (Constants.INSTRUCTION_LOGO_X2 + Constants.INSTRUCTION_LOGOWIDTH)
				&& y > Constants.INSTRUCTION_LOGO_Y) {
			iid.MoveText(Constants.BACK);
		} else if (x > Constants.INSTRUCTION_LOGO_X3
				&& x < (Constants.INSTRUCTION_LOGO_X3 + Constants.INSTRUCTION_LOGOWIDTH)
				&& y > Constants.INSTRUCTION_LOGO_Y) {
			iid.MoveText(Constants.FORWARD);
		}
	}
	
	private void StartInterfaceHandler() {
		if (x >= Constants.START_LOGO_X
				&& x <= (Constants.START_LOGO_X + Constants.START_LOGOWIDTH)
				&& y >= Constants.START_LOGO_Y1
				&& y <= (Constants.START_LOGO_Y1 + Constants.START_LOGOHEIGHT)) {
			status = Constants.STATUS_GAME;
			try {
				reloadGame();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		if (x >= Constants.START_LOGO_X
				&& x <= (Constants.START_LOGO_X + Constants.START_LOGOWIDTH)
				&& y >= Constants.START_LOGO_Y2
				&& y <= (Constants.START_LOGO_Y2 + Constants.START_LOGOHEIGHT)) {
			status = Constants.STATUS_LOAD;
		}
		if (x >= Constants.START_LOGO_X
				&& x <= (Constants.START_LOGO_X + Constants.START_LOGOWIDTH)
				&& y >= Constants.START_LOGO_Y3
				&& y <= (Constants.START_LOGO_Y3 + Constants.START_LOGOHEIGHT)) {
			status = Constants.STATUS_INSTRUCTION;
		}
		if (x >= Constants.START_LOGO_X
				&& x <= (Constants.START_LOGO_X + Constants.START_LOGOWIDTH)
				&& y >= Constants.START_LOGO_Y4
				&& y <= (Constants.START_LOGO_Y4 + Constants.START_LOGOHEIGHT)) {
			ma.finish();// exit and recycle
		}
	}

	private void GameInterfaceDownHandler() {
		MoveButton button = this.getButtonFromXY(x, y);
		if (button == null) {
			return;
		}
		final int buttonX, buttonY;
		switch (button) {
		case DOWN:
			buttonX = Constants.UPBUTTONX;
			buttonY = Constants.DOWNBUTTONY;
			break;
		case LEFT:
			buttonX = Constants.LEFTBUTTONX;
			buttonY = Constants.LEFTBUTTONY;
			break;
		case RIGHT:
			buttonX = Constants.RIGHTBUTTONX;
			buttonY = Constants.LEFTBUTTONY;
			break;
		case UP:
			buttonX = Constants.UPBUTTONX;
			buttonY = Constants.UPBUTTONY;
			break;
		default:
			buttonX = buttonY = -1;
			break;
		}
		if (buttonX >= 0 && buttonY >= 0) {
			this.drawers.add(new Drawer() {
				public void draw(Canvas canvas, Matrix matrix) {
					canvas.drawBitmap(pictures.scaledbuttondown,
							buttonX, buttonY, paint);
				}
			});
		}
	}
	
	private void GameInterfaceUpHandler() {
		if (x > Constants.SAVEBUTTONX
				&& x < (Constants.SAVEBUTTONX + Constants.SAVEBUTTONWIDTH)
				&& y > Constants.SAVEBUTTONY
				&& y < (Constants.SAVEBUTTONY + Constants.SAVEBUTTONHEIGHT)) {
			status = Constants.STATUS_SAVE;
		}
		MoveButton button = this.getButtonFromXY(x, y);
		if (button != null) {
			Coordinate coord = ma.engine.getCurrentCoordinate();
			int coordx = coord.getX();
			int coordy = coord.getY();
			switch (button) {
			case DOWN:
				coordx++;
				break;
			case LEFT:
				coordy--;
				break;
			case RIGHT:
				coordy++;
				break;
			case UP:
				coordx--;
				break;
			}
			Coordinate newcoord = new Coordinate(coord.getZ(), coordx, coordy);
			if (!newcoord.equals(coord)) {
				try {
					ma.engine.moveTo(newcoord);
				} catch (Exception e) {

				}
			}
		}
	}

}

interface Drawer {
	public void draw(Canvas canvas, Matrix canvasMatrix);
}