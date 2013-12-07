package com.example.slade_of_the_country;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
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
	protected Queue<Runnable> next;
	protected boolean nextDisabled;

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
		feid = new FireEyeInterfaceDrawer(ma.engine,pictures);
		eid = new ElevatorInterfaceDrawer();
		dm = new DataManager(c);
		next = new LinkedList<Runnable>();
		nextDisabled = false;
		fightinterval = 200;
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
			gid.draw(canvas, canvasMatrix, ma.engine);
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
		case Constants.STATUS_ELEVATOR:
			eid.draw(canvas, canvasMatrix);
			break;
		case Constants.STATUS_FIREEYE:
			feid.draw(canvas,canvasMatrix);
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
				break;
			case Constants.STATUS_FIGHT:
				FightInterfaceHandler();
				break;
			case Constants.STATUS_ELEVATOR:
				ElevatorInterfaceHandler();
			case Constants.STATUS_FIREEYE:
				FireEyeInterfaceHandler();
				break;
			default:
				break;
			}
			doNext();
		}
		invalidate();
		return true;
	}

	protected void FightInterfaceHandler() {
		disableNext();
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

	public void FireEyeInterfaceHandler() {
		if (feid.getIsEnd()) {
			feid.Reset();
			setNextStatus(Constants.STATUS_GAME);
		}
		else {
			feid.goNextPage();
		}
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
					int floor = i + j * 5;
					if (floor < eid.getHighestZ() + 1) {
						try {
							int specialfloor = ((Number) ma.engine
									.getAttribute("specialfloor")).intValue();
							int size = ((Number) ma.engine.getAttribute("size"))
									.intValue();
							ma.engine
									.moveTo(new Coordinate(specialfloor,
											(floor - floor % size) / size,
											floor % size));
						} catch (GameTerminationException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
		}
		setNextStatus(Constants.STATUS_GAME);
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
		int specialfloor = ((Number) ma.engine.getAttribute("specialfloor"))
				.intValue();
		int shopx = ((Number) ma.engine.getAttribute("shopx")).intValue();
		Runnable noMoney = new Runnable() {
			@Override
			public void run() {
				failureid.setMessage(Texts.TEXT_NOMONEY);
				status = Constants.STATUS_FAILURE_WARNING;
			}
		};
		if (x > Constants.SHOPBUTTON_X && y > Constants.SHOPBUTTON_Y1
				&& x < Constants.SHOPBUTTON_X + Constants.SHOPBUTTON_WIDTH
				&& y < Constants.SHOPBUTTON_Y1 + Constants.SHOPBUTTON_HEIGHT) {
			try {
				int attackupy = ((Number) ma.engine.getAttribute("attackupy"))
						.intValue();
				ma.engine
						.moveTo(new Coordinate(specialfloor, shopx, attackupy));
				setNextStatus(Constants.STATUS_GAME);
			} catch (GameTerminationException e) {
				next.add(noMoney);
			}
		} else if (x > Constants.SHOPBUTTON_X && y > Constants.SHOPBUTTON_Y2
				&& x < Constants.SHOPBUTTON_X + Constants.SHOPBUTTON_WIDTH
				&& y < Constants.SHOPBUTTON_Y2 + Constants.SHOPBUTTON_HEIGHT) {
			try {
				int defenseupy = ((Number) ma.engine.getAttribute("defenseupy"))
						.intValue();
				ma.engine
						.moveTo(new Coordinate(specialfloor, shopx, defenseupy));
				setNextStatus(Constants.STATUS_GAME);
			} catch (GameTerminationException e) {
				next.add(noMoney);
			}
		} else if (x > Constants.SHOPBUTTON_X && y > Constants.SHOPBUTTON_Y3
				&& x < Constants.SHOPBUTTON_X + Constants.SHOPBUTTON_WIDTH
				&& y < Constants.SHOPBUTTON_Y3 + Constants.SHOPBUTTON_HEIGHT) {
			try {
				int healthupy = ((Number) ma.engine.getAttribute("healthupy"))
						.intValue();
				ma.engine
						.moveTo(new Coordinate(specialfloor, shopx, healthupy));
				setNextStatus(Constants.STATUS_GAME);
			} catch (GameTerminationException e) {
				next.add(noMoney);
			}
		} else {
			setNextStatus(Constants.STATUS_GAME);
		}
	}

	private void FailureWarningInterfaceHandler() {
		setNextStatus(Constants.STATUS_GAME);
	}

	private void LoadInterfaceHandler() {
		if (x > Constants.INSTRUCTION_LOGO_X1
				&& x < (Constants.INSTRUCTION_LOGO_X1 + Constants.INSTRUCTION_LOGOWIDTH)
				&& y > Constants.INSTRUCTION_LOGO_Y) {
			setNextStatus(Constants.STATUS_START);
		} else if (x > Constants.MARGIN && y > Constants.MARGIN
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT)) {
			if (lid.getAvailable(Position.ONE)) {
				ma.engine = lid.getEngine(Position.ONE);
				setNextStatus(Constants.STATUS_GAME);
			}
		} else if (x > Constants.MARGIN
				&& y > (Constants.MARGIN * 2 + Constants.SAVEOPTIONHEIGHT)
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT) * 2) {
			if (lid.getAvailable(Position.TWO)) {
				ma.engine = lid.getEngine(Position.TWO);
				setNextStatus(Constants.STATUS_GAME);
			}
		} else if (x > Constants.MARGIN
				&& y > (Constants.MARGIN * 3 + Constants.SAVEOPTIONHEIGHT * 2)
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT) * 3) {
			if (lid.getAvailable(Position.AUTO)) {
				ma.engine = lid.getEngine(Position.AUTO);
				setNextStatus(Constants.STATUS_GAME);
			}
		}
	}

	public void SaveInterfaceHandler() {
		if (x > Constants.INSTRUCTION_LOGO_X1
				&& x < (Constants.INSTRUCTION_LOGO_X1 + Constants.INSTRUCTION_LOGOWIDTH)
				&& y > Constants.INSTRUCTION_LOGO_Y) {
			setNextStatus(Constants.STATUS_GAME);
		} else if (x > Constants.MARGIN && y > Constants.MARGIN
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT)) {
			if (lid.getAvailable(Position.ONE)) {
				setNextStatus(Constants.STATUS_WARNING);
				position = Position.ONE;
			} else if (!lid.getAvailable(Position.ONE)) {
				try {
					dm.SaveSavings(ma.engine, "save0.txt");
				} catch (IOException e) {
				}
				setNextStatus(Constants.STATUS_GAME);
			}
		} else if (x > Constants.MARGIN
				&& y > (Constants.MARGIN * 2 + Constants.SAVEOPTIONHEIGHT)
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT) * 2) {
			if (lid.getAvailable(Position.TWO)) {
				setNextStatus(Constants.STATUS_WARNING);
				position = Position.TWO;
			} else if (!lid.getAvailable(Position.TWO)) {
				try {
					dm.SaveSavings(ma.engine, "save1.txt");
				} catch (IOException e) {
				}
				setNextStatus(Constants.STATUS_GAME);
			}
		} else if (x > Constants.MARGIN
				&& y > (Constants.MARGIN * 3 + Constants.SAVEOPTIONHEIGHT * 2)
				&& x < (Constants.MYSCREENWIDTH - Constants.MARGIN)
				&& y < (Constants.MARGIN + Constants.SAVEOPTIONHEIGHT) * 3) {
			if (lid.getAvailable(Position.AUTO)) {
				setNextStatus(Constants.STATUS_WARNING);
				position = Position.AUTO;
			} else if (!lid.getAvailable(Position.AUTO)) {
				try {
					dm.SaveSavings(ma.engine, "save2.txt");
				} catch (IOException e) {
				}
				setNextStatus(Constants.STATUS_GAME);
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
			try {
				switch (position) {
				case ONE:
					dm.SaveSavings(ma.engine, "save0.txt");
					break;
				case TWO:
					dm.SaveSavings(ma.engine, "save1.txt");
					break;
				case AUTO:
					dm.SaveSavings(ma.engine, "save2.txt");
					break;
				}
			} catch (IOException e) {
			}
			setNextStatus(Constants.STATUS_GAME);
		} else if (x > Constants.CANCEL_BUTTON_LEFT
				&& x < Constants.CANCEL_BUTTON_RIGHT
				&& y > Constants.CONFIRM_BUTTON_UP
				&& y < Constants.CONFIRM_BUTTON_DOWN) {
			setNextStatus(Constants.STATUS_GAME);
		}

	}

	private void InstructionInterfaceHandler() {
		if (x > Constants.INSTRUCTION_LOGO_X1
				&& x < (Constants.INSTRUCTION_LOGO_X1 + Constants.INSTRUCTION_LOGOWIDTH)
				&& y > Constants.INSTRUCTION_LOGO_Y) {
			setNextStatus(Constants.STATUS_START);
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
			try {
				reloadGame();
				dm.SaveSavings(ma.engine, "save2.txt");
				setNextStatus(Constants.STATUS_GAME);
			} catch (Exception e) {

				throw new RuntimeException(e);
			}
		}
		if (x >= Constants.START_LOGO_X
				&& x <= (Constants.START_LOGO_X + Constants.START_LOGOWIDTH)
				&& y >= Constants.START_LOGO_Y2
				&& y <= (Constants.START_LOGO_Y2 + Constants.START_LOGOHEIGHT)) {
			setNextStatus(Constants.STATUS_LOAD);
		}
		if (x >= Constants.START_LOGO_X
				&& x <= (Constants.START_LOGO_X + Constants.START_LOGOWIDTH)
				&& y >= Constants.START_LOGO_Y3
				&& y <= (Constants.START_LOGO_Y3 + Constants.START_LOGOHEIGHT)) {
			setNextStatus(Constants.STATUS_INSTRUCTION);
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
			setNextStatus(Constants.STATUS_SAVE);
		} else if (x > Constants.FIRE_EYE_X
				&& x < (Constants.FIRE_EYE_X + Constants.TOOL_SIZE)
				&& y > Constants.TOOL_Y
				&& y < Constants.TOOL_Y + Constants.TOOL_SIZE) {
			setNextStatus(Constants.STATUS_FIREEYE);
		} else if (x > Constants.ELEVATOR_X
				&& x < (Constants.ELEVATOR_X + Constants.TOOL_SIZE)
				&& y > Constants.TOOL_Y
				&& y < Constants.TOOL_Y + Constants.TOOL_SIZE) {
			setNextStatus(Constants.STATUS_ELEVATOR);
		} else if (x > Constants.SHOP_X
				&& x < (Constants.SHOP_X + Constants.TOOL_SIZE)
				&& y > Constants.TOOL_Y
				&& y < Constants.TOOL_Y + Constants.TOOL_SIZE) {
			setNextStatus(Constants.STATUS_SHOP);
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
				int Z = event.getCoordinate().getZ();
				int highestZ = ((Number) ma.engine.getAttribute("highestfloor"))
						.intValue();
				if (Z > highestZ) {
					ma.engine.setAttribute("highestfloor", Z);
					eid.setHighestZ(Z);
				}
				eventHandler();
			} catch (StandardGameSuccessTerminationException e) {
				eventHandler();
				setNextStatus(Constants.STATUS_GAME_WIN);
			} catch (StandardGameFailureTerminationException e) {
				if (e.getAttributeCheck().getAttributeName().equals("health")) {
					eventHandler();
					setNextStatus(Constants.STATUS_GAME_OVER);
				} else if (e.getAttributeCheck().getAttributeName()
						.equals("key-y")) {
					failureid.setMessage(Texts.TEXT_NOYELLOWKEY);
					setNextStatus(Constants.STATUS_FAILURE_WARNING);
				} else if (e.getAttributeCheck().getAttributeName()
						.equals("key-b")) {
					failureid.setMessage(Texts.TEXT_NOBLUEKEY);
					setNextStatus(Constants.STATUS_FAILURE_WARNING);
				} else if (e.getAttributeCheck().getAttributeName()
						.equals("key-r")) {
					failureid.setMessage(Texts.TEXT_NOREDKEY);
					setNextStatus(Constants.STATUS_FAILURE_WARNING);
				} else if (e.getAttributeCheck().getAttributeName()
						.equals("key-x")) {
					failureid.setMessage(Texts.TEXT_NOSPECIALKEY);
					setNextStatus(Constants.STATUS_FAILURE_WARNING);
				} else if (e.getAttributeCheck().getAttributeName()
						.equals("gold")) {
					failureid.setMessage(Texts.TEXT_NOMONEY);
					setNextStatus(Constants.STATUS_FAILURE_WARNING);
				} else if (e.getAttributeCheck().getAttributeName()
						.equals("exp")) {
					failureid.setMessage(Texts.TEXT_NOEXP);
					setNextStatus(Constants.STATUS_FAILURE_WARNING);
				} else if (e.getAttributeCheck().getAttributeName()
						.equals("failure")) {
					failureid.setMessage(Texts.TEXT_INFINITELOOP);
					setNextStatus(Constants.STATUS_FAILURE_WARNING);
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
		handleToasts("toast-before");
		@SuppressWarnings("unchecked")
		List<List<Fight.Attributes>> logs = (List<List<Fight.Attributes>>) event
				.getExtraInformation().get("fight-logs");
		if (logs != null) {
			for (List<Fight.Attributes> log : logs) {
				if (log == null) {
					throw new RuntimeException();
				} else {
					final List<Fight.Attributes> finalLog = log;
					next.add(new Runnable() {
						@Override
						public void run() {
							status = Constants.STATUS_FIGHT;
							fh = new FightHandler(finalLog);
							fh.start();
						}
					});
				}
			}
		}
		handleToasts("toast-after");
	}
	
	public void handleToasts(String key) {
		@SuppressWarnings("unchecked")
		List<String> toastMessages = (List<String>) event.getExtraInformation().get(key);
		if (toastMessages != null) {
			for (String toastMessage : toastMessages) {
				final String toastText = toastMessage;
				next.add(new Runnable() {
					@Override
					public void run() {
						post(new Runnable(){
							@Override
							public void run() {
								Context context = ma.getApplicationContext();
								int duration = Constants.TOAST_DURATION;
								Toast toast = Toast.makeText(context, toastText, duration);
								toast.show();
								doNext();
							}
						});
					}
				});
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
			setNextStatus(Constants.STATUS_GAME);
			doNext();
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

	private void disableNext() {
		nextDisabled = true;
	}
	
	protected void doNext() {
		if (nextDisabled == true) {
			nextDisabled = false;
			return;
		}
		if (!next.isEmpty()) {
			Log.i("SOTC", "Running next in status " + status);
			next.poll().run();
		}
	}
	
	protected void setNextStatus(int nextStatus) {
		final int finalNextStatus = nextStatus;
		next.add(new Runnable() {
			@Override
			public void run() {
				status = finalNextStatus;
				if (status == Constants.STATUS_GAME) {
					next.clear();
				}
			}
		});
	}
}

interface Drawer {
	public void draw(Canvas canvas, Matrix canvasMatrix);
}
