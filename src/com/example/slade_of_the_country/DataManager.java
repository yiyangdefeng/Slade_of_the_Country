package com.example.slade_of_the_country;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.edu.tsinghua.academic.c00740273.magictower.engine.Engine;

import android.content.Context;


public class DataManager {

	private Context context;

	public DataManager(Context context) {
		this.context = context;
	}

	public Engine LoadSavings(String filename) {
		Engine engine = new Engine();
		InputStream is = null;
		try {
			is = context.openFileInput(filename);
			engine.unserializeGame(is);
			return engine;
		} catch (IOException ioe) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (ClassNotFoundException e) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	public void SaveSavings(Engine engine, String filename) throws IOException {
		OutputStream os = null;
		try {
			os = context.openFileOutput(filename, Context.MODE_PRIVATE);
			engine.serializeGame(os);
		} finally {
			if (os != null) {
				try {

					os.close();
				} catch (IOException e) {
				}

			}
		}

	}

}
