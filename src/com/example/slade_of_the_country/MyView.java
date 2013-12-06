package com.example.slade_of_the_country;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.slade_of_the_country.LoadInterfaceDrawer.Position;

import cn.edu.tsinghua.academic.c00740273.magictower.engine.Coordinate;
import cn.edu.tsinghua.academic.c00740273.magictower.engine.DataException;
import cn.edu.tsinghua.academic.c00740273.magictower.engine.Engine;
import cn.edu.tsinghua.academic.c00740273.magictower.engine.GameTerminationException;
import cn.edu.tsinghua.academic.c00740273.magictower.standard.StandardEvent;
import cn.edu.tsinghua.academic.c00740273.magictower.standard.StandardGame;
import cn.edu.tsinghua.academic.c00740273.magictower.standard.StandardGameFailureTerminationException;
import cn.edu.tsinghua.academic.c00740273.magictower.standard.StandardGameSuccessTerminationException;
import cn.edu.tsinghua.academic.c00740273.magictower.standard.mixin.Fight;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

	int status;
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
	private FailureWarningDrawer failureid;
	private DialogueInterfaceDrawer did;
	protected FightInterfaceDrawer fid;
	protected GameOverInterfaceDrawer gameoverid;
	protected GameWinInterfaceDrawer gamewinid;
	protected FireEyeInterfaceDrawer feid;
	protected ElevatorInterfaceDrawer eid;
	protected ShopInterfaceDrawer shopid;
	protected PictureCollector pictures;
	private StandardGame sg;
	private MainActivity ma;
	private DataManager dm;
	private Position position;
	protected StandardEvent event;
	protected int fightinterval;
	protected MoveButton button;
	protected FightHandler fh;

	// private StandardRenderer mr = (StandardRenderer) sg.getRenderer();

	public enum MoveButton {
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
		position = Position.AUTO;
		pictures = new PictureCollector(this);
		sid = new StartInterfaceDrawer(pictures);
		gid = new GameInterfaceDrawer(pictures);
		iid = new InstructionInterfaceDrawer(pictures);
		lid = new LoadInterfaceDrawer(pictures, ma);
		wid = new WarningMessageDrawer();
		failureid = new FailureWarningDrawer();
		fid = new FightInterfaceDrawer(pictures);
		did = new DialogueInterfaceDrawer(pictures);
		gameoverid = new GameOverInterfaceDrawer(pictures);
		gamewinid = new GameWinInterfaceDrawer(pictures);
		shopid = new ShopInterfaceDrawer(pictures);
		feid = new FireEyeInterfaceDrawer();
		eid = new ElevatorInterfaceDrawer();
		dm = new DataManager(c);
		fightinterval = 50;
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
			lid.LoadSaving();
			lid.draw(canvas, canvasMatrix);
			break;
		case Constants.STATUS_INSTRUCTION:
			iid.draw(canvas, canvasMatrix);
			break;
		case Constants.STATUS_WARNING:
			wid.drawWarningMessage(canvas, canvasMatrix);
			break;
		case Constants.STATUS_FIGHT:
			gid.draw(canvas, canvasMatrix, ma.engine);
			fid.draw(canvas, canvasMatrix);
			break;
		case Constants.STATUS_DIALOGUE:
			did.draw(canvas, canvasMatrix);
			break;
		case Constants.STATUS_SHOP:
			shopid.draw(canvas, canvasMatrix);
			break;
		case Constants.STATUS_FAILURE_WARNING:
			gid.draw(canvas, canvasMatrix, ma.engine);
			failureid.drawWarningMessage(canvas, canvasMatrix);
			break;
		case Constants.STATUS_GAME_OVER:
			gameoverid.draw(canvas, canvasMatrix);
			break;
		case Constants.STATUS_GAME_WIN:
			gamewinid.draw(canvas, canvasMatrix);
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
		if (event.getAction() == MotionEvent.ACTION_DOWN
				&& status == Constants.STATUS_GAME) {
			GameInterfaceDownHandler();
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			switch (status) {
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
			case Constants.STATUS_WARNING:
				WarningInterfaceHandler();
			case Constants.STATUS_FAILURE_WARNING:
				FailureWarningInterfaceHandler();
				break;
			case Constants.STATUS_SHOP:
				ShopInterfaceHandler();
				break;
			case Constants.STATUS_DIALOGUE:
				DialogueInterfaceHandler();
				break;
			case Constants.STATUS_GAME_OVER:
			case Constants.STATUS_GAME_WIN:
			case Constants.STATUS_FIGHT:
				break;
			case Constants.STATUS_ELEVATOR:
				ElevatorInterfaceHandler();
			case Constants.STATUS_FIREEYE:
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

	public void ElevatorInterfaceHandler() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				if (x > Constants.ELEVATOR_LEFT_MARGIN
						+ Constants.ELEVATOR_GRIDWIDTH * i
						&& y > Constants.ELEVATOR_TOP_MARGIN
								+ Constants.ELEVATOR_GRIDHEIGHT * j
						&& x < Constants.ELEVATOR_LEFT_MARGIN
								+ Constants.ELEVATOR_GRIDWIDTH * i
								+ Constants.ELEVATOR_TABWIDTH
						&& y < Constants.ELEVATOR_TOP_MARGIN
								+ Constants.ELEVATOR_GRIDHEIGHT * j
								+ Constants.ELEVATOR_TABHEIGHT) {
					//put warrior near the stair
				}
			}
		}
	}

	private void DialogueInterfaceHandler() {
		//
		//
		//
		//
		//
		//
		//
		//
	}

	private void ShopInterfaceHandler() {
		if (x > Constants.SHOPBUTTON_X && y > Constants.SHOPBUTTON_Y1
				&& x < Constants.SHOPBUTTON_X + Constants.SHOPBUTTON_WIDTH
				&& y < Constants.SHOPBUTTON_Y1 + Constants.SHOPBUTTON_HEIGHT) {

		} else if (x > Constants.SHOPBUTTON_X && y > Constants.SHOPBUTTON_Y2
				&& x < Constants.SHOPBUTTON_X + Constants.SHOPBUTTON_WIDTH
				&& y < Constants.SHOPBUTTON_Y2 + Constants.SHOPBUTTON_HEIGHT) {

		} else if (x > Constants.SHOPBUTTON_X && y > Constants.SHOPBUTTON_Y3
				&& x < Constants.SHOPBUTTON_X + Constants.SHOPBUTTON_WIDTH
				&& y < Constants.SHOPBUTTON_Y3 + Constants.SHOPBUTTON_HEIGHT) {

		}
	}

	private void FailureWarningInterfaceHandler() {
		status = Constants.STATUS_GAME;
	}

	private void LoadInterfaceHandler() {
		if (x > Constants.INSTRUCTION_LOGO_X1
				&& x < (Constants.INSTRUCTION_LOGO_X1 + Constants.INSTRUCTION_LOGOWIDTH)
				&& y > Constants.INSTRUCTION_LOGO_Y) {
			status = Constants.STATUS_START;
		} else if (x > Constants.MARGIN && y > Constants.MARGIN
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
			status = Constants.STATUS_GAME;
		} else if (x > Constants.MARGIN && y > Constants.MARGIN
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT)) {
			if (lid.getAvailable(Position.ONE)) {
				status = Constants.STATUS_WARNING;
				position = Position.ONE;
			} else if (!lid.getAvailable(Position.ONE)) {
				try {
					dm.SaveSavings(ma.engine, "save0.txt");
				} catch (IOException e) {
				}
				status = Constants.STATUS_GAME;
			}
		} else if (x > Constants.MARGIN
				&& y > (Constants.MARGIN * 2 + Constants.SAVEOPTIONHEIGHT)
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT) * 2) {
			if (lid.getAvailable(Position.TWO)) {
				status = Constants.STATUS_WARNING;
				position = Position.TWO;
			} else if (!lid.getAvailable(Position.TWO)) {
				try {
					dm.SaveSavings(ma.engine, "save1.txt");
				} catch (IOException e) {
				}
				status = Constants.STATUS_GAME;
			}
		} else if (x > Constants.MARGIN
				&& y > (Constants.MARGIN * 3 + Constants.SAVEOPTIONHEIGHT * 2)
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT) * 3) {
			if (lid.getAvailable(Position.AUTO)) {
				status = Constants.STATUS_WARNING;
				position = Position.AUTO;
			} else if (!lid.getAvailable(Position.AUTO)) {
				try {
					dm.SaveSavings(ma.engine, "save2.txt");
				} catch (IOException e) {
				}
				status = Constants.STATUS_GAME;
			}
		} else {
			return;
		}

	}

	public void WarningInterfaceHandler() {
		if (x > Constants.CONFIRM_BUTTON_LEFT
				&& x < Constants.CONFIRM_BUTTON_RIGHT
				&& y > Constants.CONFIRM_BUTTON_UP
				&& y < Constants.CONFIRM_TEXT_DOWN) {
			switch (position) {
			case ONE:
				try {
					dm.SaveSavings(ma.engine, "save0.txt");
				} catch (IOException e) {
				}
				status = Constants.STATUS_GAME;
				break;
			case TWO:
				try {
					dm.SaveSavings(ma.engine, "save1.txt");
				} catch (IOException e) {
				}
				status = Constants.STATUS_GAME;
				break;
			case AUTO:
				try {
					dm.SaveSavings(ma.engine, "save2.txt");
				} catch (IOException e) {
				}
				status = Constants.STATUS_GAME;
				break;
			}
		} else if (x > Constants.CANCEL_BUTTON_LEFT
				&& x < Constants.CANCEL_BUTTON_RIGHT
				&& y > Constants.CONFIRM_BUTTON_UP
				&& y < Constants.CONFIRM_BUTTON_DOWN) {
			status = Constants.STATUS_SAVE;
		}

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
				dm.SaveSavings(ma.engine, "save2.txt");
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
		MoveButton tempbutton = this.getButtonFromXY(x, y);
		if (tempbutton == null) {
			return;
		}
		final int buttonX, buttonY;
		switch (tempbutton) {
		case DOWN:
			buttonX = Constants.UPBUTTONX;
			buttonY = Constants.DOWNBUTTONY;
			this.drawers.add(new Drawer() {
				public void draw(Canvas canvas, Matrix matrix) {
					canvas.drawBitmap(pictures.getScaledTileBitmap(
							pictures.button_down_press, "button_down_press",
							(int) (Constants.BUTTONWIDTH),
							(int) (Constants.BUTTONWIDTH)), buttonX, buttonY,
							paint);
				}
			});
			break;
		case LEFT:
			buttonX = Constants.LEFTBUTTONX;
			buttonY = Constants.LEFTBUTTONY;
			this.drawers.add(new Drawer() {
				public void draw(Canvas canvas, Matrix matrix) {
					canvas.drawBitmap(pictures.getScaledTileBitmap(
							pictures.button_left_press, "button_left_press",
							(int) (Constants.BUTTONWIDTH),
							(int) (Constants.BUTTONWIDTH)), buttonX, buttonY,
							paint);
				}
			});
			break;
		case RIGHT:
			buttonX = Constants.RIGHTBUTTONX;
			buttonY = Constants.LEFTBUTTONY;
			this.drawers.add(new Drawer() {
				public void draw(Canvas canvas, Matrix matrix) {
					canvas.drawBitmap(pictures.getScaledTileBitmap(
							pictures.button_right_press, "button_right_press",
							(int) (Constants.BUTTONWIDTH),
							(int) (Constants.BUTTONWIDTH)), buttonX, buttonY,
							paint);
				}
			});
			break;
		case UP:
			buttonX = Constants.UPBUTTONX;
			buttonY = Constants.UPBUTTONY;
			this.drawers.add(new Drawer() {
				public void draw(Canvas canvas, Matrix matrix) {
					canvas.drawBitmap(pictures.getScaledTileBitmap(
							pictures.button_up_press, "button_up_press",
							(int) (Constants.BUTTONWIDTH),
							(int) (Constants.BUTTONWIDTH)), buttonX, buttonY,
							paint);
				}
			});
			break;
		default:
			buttonX = buttonY = -1;
			break;
		}
	}

	private void GameInterfaceUpHandler() {
		if (x > Constants.SAVEBUTTONX
				&& x < (Constants.SAVEBUTTONX + Constants.SAVEBUTTONWIDTH)
				&& y > Constants.SAVEBUTTONY
				&& y < (Constants.SAVEBUTTONY + Constants.SAVEBUTTONHEIGHT)) {
			status = Constants.STATUS_SAVE;
		}
		MoveButton nextbutton = this.getButtonFromXY(x, y);
		if (nextbutton != null) {
			button = nextbutton;
			gid.setButton(button);
			doMove(button);
		}
	}

	void doMove(MoveButton button) {
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
				event = (StandardEvent) ma.engine.moveTo(newcoord);
				eventHandler();
			} catch (StandardGameSuccessTerminationException e) {
				eventHandler();
				status = Constants.STATUS_GAME_WIN;
			} catch (StandardGameFailureTerminationException e) {
				if (e.getAttributeCheck().getAttributeName().equals("health")) {
					eventHandler();
					status = Constants.STATUS_GAME_OVER;
					Log.e("test", "Game Over!");
				} else if (e.getAttributeCheck().getAttributeName()
						.equals("key-y")) {
					failureid.setMessage(Texts.TEXT_NOYELLOWKEY);
					status = Constants.STATUS_FAILURE_WARNING;
				} else if (e.getAttributeCheck().getAttributeName()
						.equals("key-b")) {
					failureid.setMessage(Texts.TEXT_NOBLUEKEY);
					status = Constants.STATUS_FAILURE_WARNING;
				} else if (e.getAttributeCheck().getAttributeName()
						.equals("key-r")) {
					failureid.setMessage(Texts.TEXT_NOREDKEY);
					status = Constants.STATUS_FAILURE_WARNING;
				} else if (e.getAttributeCheck().getAttributeName()
						.equals("key-x")) {
					failureid.setMessage(Texts.TEXT_NOSPECIALKEY);
					status = Constants.STATUS_FAILURE_WARNING;
				} else if (e.getAttributeCheck().getAttributeName()
						.equals("gold")) {
					failureid.setMessage(Texts.TEXT_NOMONEY);
					status = Constants.STATUS_FAILURE_WARNING;
				} else if (e.getAttributeCheck().getAttributeName()
						.equals("exp")) {
					failureid.setMessage(Texts.TEXT_NOEXP);
					status = Constants.STATUS_FAILURE_WARNING;
				} else if (e.getAttributeCheck().getAttributeName()
						.equals("failure")) {
					failureid.setMessage(Texts.TEXT_INFINITELOOP);
					status = Constants.STATUS_FAILURE_WARNING;
				}

			} catch (GameTerminationException e) {
				throw new RuntimeException(e);
			}
			// if (event == null) {
			// not in real game
			// event = (StandardEvent) ma.engine.attemptMoveTo(newcoord);
			// }

		}
	}

	public void eventHandler() {
		@SuppressWarnings("unchecked")
		List<List<Fight.Attributes>> logs = (List<List<Fight.Attributes>>) event
				.getExtraInformation().get("fight-logs");
		if (logs != null) {
			for (List<Fight.Attributes> log : logs) {
				if (log == null) {
					throw new RuntimeException();
				} else {
					status = Constants.STATUS_FIGHT;
					invalidate();
					fh = new FightHandler(log);
					fh.start();
				}
			}
		}
	}

	class FightHandler extends Thread {

		public boolean exec = true;
		private List<Fight.Attributes> log;

		public FightHandler(List<Fight.Attributes> log) {
			this.log = log;
		}

		@Override
		public void run() {
			// [0,1,2,3,4,5] with relationship to [oa,od,oh,sa,sd,sh]
			int[] attributes = new int[6];
			attributes[0] = (int) log.get(0).getOpponentAttack();
			attributes[1] = (int) log.get(0).getOpponentDefense();
			attributes[3] = (int) log.get(0).getSelfAttack();
			attributes[4] = (int) log.get(0).getSelfDefense();
			for (Fight.Attributes logEntry : log) {
				postInvalidate();
				attributes[2] = (int) Math.max(0, logEntry.getOpponentHealth());
				attributes[5] = (int) Math.max(0, logEntry.getSelfHealth());
				fid.setAttributes(attributes);
				postInvalidate();
				try {
					Thread.sleep(fightinterval);
				} catch (Exception e) {
				}
				if (!exec) {
					return;
				}
			}
			// add winning message here
			status = Constants.STATUS_GAME;
			postInvalidate();
		}

	}

	protected void reloadGame() throws IOException, DataException {
		InputStream is = ma.getResources().openRawResource(R.raw.map);
		byte[] bufferedbyte = new byte[is.available()];
		DataInput di = new DataInputStream(is);
		di.readFully(bufferedbyte);
		is.close();
		sg = new StandardGame(new String(bufferedbyte, "gbk"));
		event = (StandardEvent) ma.engine.loadGame(sg);
		ma.engine.setFailureTermination(Engine.Termination.AUTOMATIC);
	}
}

interface Drawer {
	public void draw(Canvas canvas, Matrix canvasMatrix);
}
