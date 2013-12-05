package com.example.slade_of_the_country;

public class Constants {
	
	public static final int ZERO = 0;
	//for game interface
	//
	//
	//
	public static final int GAMELOGOWIDTH = 240;
	public static final int GAMELOGOHEIGHT = 80;
	public static final int BUTTONWIDTH = 150;
	public static final float SCREENSCALE = (9f / 16);
	public static final int MYSCREENWIDTH = 1280;
	public static final int MYSCREENHEIGHT = 720;
	public static final int MAINSURFACEX = 560;
	public static final int MAINSURFACEY = 0;
	public static final int UPBUTTONX = 205;
	public static final int UPBUTTONY = 370;
	public static final int DOWNBUTTONY = 560;
	public static final int LEFTBUTTONX = 20;
	public static final int LEFTBUTTONY = 465;
	public static final int RIGHTBUTTONX = 390;
	public static final int TITLEFONTSIZE = 80;
	public static final int SMALLFONTSIZE = 30;
	public static final int NORMALSMALLFONTSIZE = 40;
	public static final int NORMALFONTSIZE = 50;
	public static final int TITLEX = 120;
	public static final int TITLEY = 5;
	public static final int TEXT_DIX = 160;
	public static final int TEXT_DIY = 140;
	public static final int TEXT_FLOORX = 260;
	public static final int TEXT_BLOODX = 10;
	public static final int TEXT_YELLOWKEYX = 230;
	
	public static final int MAINGRID_LEFTX = 570;
	public static final int MAINGRID_RIGHTX = 1270;
	public static final int MAINGRID_UPY = 10;
	public static final int MAINGRID_BOTTOMY = 710;
	
	public static final int SAVEBUTTONX = 0;
	public static final int SAVEBUTTONY = 660;
	public static final int SAVEBUTTONWIDTH = 140;
	public static final int SAVEBUTTONHEIGHT = 60;
	
	public static final int MOVE_UP = 1;
	public static final int MOVE_DOWN = 2;
	public static final int MOVE_LEFT = 3;
	public static final int MOVE_RIGHT = 4;
	
	//for status
	//
	//
	//
	public static final int STATUS_START = 0;
	public static final int STATUS_GAME = 1;
	public static final int STATUS_DIALOGUE = 2;
	public static final int STATUS_SHOP = 3;
	public static final int STATUS_FIGHT = 4;
	public static final int STATUS_GAME_WIN = 5;
	public static final int STATUS_GAME_OVER =6;
	public static final int STATUS_LOAD = 7;
	public static final int STATUS_INSTRUCTION = 8;
	public static final int STATUS_SAVE = 9;
	public static final int STATUS_WARNING = 10;
	public static final int STATUS_FAILURE_WARNING = 11;
	public static final int STATUS_FIREEYE = 12;
	public static final int STATUS_ELEVATOR = 13;
	
	//for start interface
	//
	//
	//
	public static final int START_TITLEHEIGHT = 200;
	public static final int START_TITLEWIDTH = 600;
	public static final int START_TITLE_X = 340;
	public static final int START_TITLE_Y = 5;
	
	public static final int START_LOGOHEIGHT = 100;
	public static final int START_LOGOWIDTH = 300;
	public static final int START_LOGO_X = 490;
	public static final int START_LOGO_Y1 = 230;
	public static final int START_LOGO_Y2 = 350;
	public static final int START_LOGO_Y3 = 470;
	public static final int START_LOGO_Y4 = 590;
	
	//for instruction interface
	//
	//
	//
	public static final int MARGIN = 20;
	public static final int INSTRUCTION_LOGOWIDTH = 120;
	public static final int INSTRUCTION_LOGOHEIGHT = 60;
	public static final int INSTRUCTION_LOGO_Y = 655; 
	public static final int INSTRUCTION_LOGO_X1 = 0;
	public static final int INSTRUCTION_LOGO_X2 = 580;
	public static final int INSTRUCTION_LOGO_X3 = 1160;
	
	//for load interface
	//
	//
	//
	public static final int SAVEOPTIONHEIGHT = 190;
	public static final int SCREENHALF_X = 640;
	
	//for warning messages
	public static final int WARNINGLEFT = 300;
	public static final int WARNINGRIGHT = 980;
	public static final int WARNINGUP = 200;
	public static final int WARNINGDOWN = 520;
	public static final int WARNING_TEXTX = 400;
	public static final int WARNING_TEXTY = 400;
	public static final int CONFIRM_BUTTON_LEFT = 350;
	public static final int CONFIRM_BUTTON_RIGHT = 430;
	public static final int CONFIRM_BUTTON_UP = 450;
	public static final int CONFIRM_BUTTON_DOWN = 500;
	public static final int CANCEL_BUTTON_LEFT = 850;
	public static final int CANCEL_BUTTON_RIGHT = 930;
	public static final int CONFIRM_TEXT_DOWN = 490;
	public static final int CONFIRM_TEXT_LEFT = 360;
	public static final int CANCEL_TEXT_LEFT = 860;
	public static final int SINGLE_BUTTON_LEFT = 880;
	public static final int SINGLE_BUTTON_RIGHT = 960;
	public static final int TEXT_SINGLE_LEFT = 890;
	public static final int TEXT_SINGLE_DOWN = 690;
	public static final int SINGLE_BUTTON_UP = 650;
	public static final int SINGLE_BUTTON_DOWN = 700;
	
	//for text
	//
	//
	//
	public static final int SMALLMARGIN = 1;
	public static final int NORMALMARGIN = 2;
	public static final boolean FORWARD = true;
	public static final boolean BACK = false;
	
	//for fight interface
	//
	//
	//
	public static final int FIGHT_INTERFACE_LEFT = 560;
	public static final int FIGHT_INTERFACE_RIGHT = 1280;
	public static final int FIGHT_INTERFACE_TOP = 180;
	public static final int FIGHT_INTERFACE_BOTTOM = 540;
	public static final int FIGHT_LOGO_LEFT = 870;
	public static final int FIGHT_LOGO_TOP = 310;
	public static final int OPPOHEALTH_LEFT = 580;
	public static final int OPPOHEALTH_BOTTOM = 410;
	public static final int OPPOATTACK_BOTTOM = 460;
	public static final int OPPODEFENSE_BOTTOM = 510;
	public static final int SELFHEALTH_LEFT = 1000;
	public static final int FIGHT_CHARACTER_SCALE = 100;
	public static final int FIGHT_WARRIOR_X = 950;
	public static final int FIGHT_WARRIOR_Y = 260;
	public static final int FIGHT_OPPO_X = 790;
	public static final int FIGHT_OPPO_Y = 260;
	
	//for dialogue interface
	//
	//
	//
	public static int DIALOGUE_INTERFACE_X = 560;
	public static int DIALOGUE_INTERFACE_Y = 432;
	public static int DIALOGUE_INTERFACE_WIDTH = 720;
	public static int DIALOGUE_INTERFACE_HEIGHT = 288;
	
	//for elevator interface
	//
	//
	//
	public static int FLOOR_CHOICE_X = 40;
	public static int FLOOR_CHOICE_Y = 80;
	public static int ELEVATOR_LEFT_MARGIN = 60;
	public static int ELEVATOR_TOP_MARGIN = 100;
	public static int ELEVATOR_TABWIDTH = 200;
	public static int ELEVATOR_TABHEIGHT = 120;
	public static int ELEVATOR_GRIDWIDTH = 240;
	public static int ELEVATOR_GRIDHEIGHT = 160;
	public static int ELEVATOR_TEXTTOGRIDLEFT = 75;
	public static int ELEVATOR_TEXTTOGRIDUP = 85;
	
	//for shop interface
	public static int SHOPBUTTON_X = 720;
	public static int SHOPBUTTON_Y1 = 300;
	public static int SHOPBUTTON_Y2 = 450;
	public static int SHOPBUTTON_Y3 = 600;
	public static int SHOPBUTTON_WIDTH = 400;
	public static int SHOPBUTTON_HEIGHT = 100;
	public static int SHOP_TEXT1_X = 830;
	public static int SHOP_TEXT2_X = 830;
	public static int SHOP_TEXT3_X = 810;
	public static int SHOP_TEXT_YTOTOP = 60;
	public static int SHOP_TEXT_NOTICEX = 680;
	public static int SHOP_TEXT_NOTICEY = 270;
}
