package com.example.slade_of_the_country;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;



public class TextDrawer {

	private int textX;
	private int textY;
	private int textwidth;
	private int textheight;
	private int fontheight;
	private int pagelinenum;
	private int textbcolor; // 背景颜色
	private int textcolor; // 字体颜色
	private int realline; // 字符串真实的行数
	private int curline;
	private String text;
	private Vector<String> Strings;
	private Paint paint;
	private int textsize;

	public TextDrawer() {
		paint = new Paint();
		Strings = new Vector<String>();
	}

	public TextDrawer(String text, int x, int y, int w, int h, int bgcolor,
			int textcolor, int textsize) {
		paint = new Paint();
		Strings = new Vector<String>();
		this.text = text;
		textX = x;
		textY = y;
		textwidth = w;
		textheight = h;
		textbcolor = bgcolor;
		this.textcolor = textcolor;
		this.textsize = textsize;
		this.SetPaint();
		this.GetTextInBox();
		curline = 0;
	}

	public void SetPaint() {
		paint.setColor(textcolor);
		paint.setTextSize(textsize);
	}

	public void SetFontSize(int textsize) {
		this.textsize = textsize;
	}

	public void SetRect(int x, int y, int w, int h) {
		textX = x;
		textY = y;
		textwidth = w;
		textheight = h;
	}

	public void SetBColor(int bcolor) {
		this.textbcolor = bcolor;
	}

	public void SetTextColor(int textcolor) {
		this.textcolor = textcolor;
	}

	public void SetText(String text) {
		this.text = text;
	}

	public void GetTextInBox() {
		char ch;
		int w = 0;
		int istart = 0;
		FontMetrics fm = paint.getFontMetrics();
		fontheight = (int) Math.ceil(fm.descent - fm.top)
				+ Constants.NORMALMARGIN;

		pagelinenum =(int)((textheight - textsize) /(float) fontheight);

		for (int i = 0; i < text.length(); i++) {
			ch = text.charAt(i);
			float[] widths = new float[1];
			String srt = String.valueOf(ch);
			paint.getTextWidths(srt, widths);

			if (ch == '\n') {
				realline++;
				Strings.addElement(text.substring(istart, i));
				istart = i + 1;
				w = 0;
			} else {
				w += (int) (Math.ceil(widths[0]));
				if (w > textwidth) {
					realline++;
					Strings.addElement(text.substring(istart, i));
					istart = i;
					i--;
					w = 0;
				} else {
					if (i == (text.length() - 1)) {
						realline++;
						Strings.addElement(text.substring(istart, text.length()));
					}
				}
			}
		}
	}

	public void DrawText(Canvas canvas) {
		canvas.drawColor(textbcolor);
		for (int i = curline, j = 0; i < realline; i++, j++) {
			if (j > pagelinenum) {
				break;
			}
			canvas.drawText((String) (Strings.elementAt(i)), textX, textY
					+ fontheight * j + paint.getTextSize()
					+ Constants.NORMALMARGIN, paint);
		}
	}

	public boolean MoveText(boolean backorforward) {
		int moveline = pagelinenum;
		if (backorforward == Constants.BACK) {
			curline = Math.max(0, curline - moveline);
		}
		else if (backorforward == Constants.FORWARD) {
			curline = Math.max(0, Math.min(curline + moveline , realline - moveline));
		}
        return false;
    }
}
