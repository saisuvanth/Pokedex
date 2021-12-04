package com.pokedex;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.pokedex.db.DBDriver;

public class DataLoader extends SwingWorker<Boolean, String> {
	File f;
	JTextArea area;
	LoadingPane lp;

	public DataLoader(File f, LoadingPane lp, JTextArea area) {
		this.f = f;
		this.lp = lp;
		this.area = area;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		return DBDriver.csvLoader(f, this);
	}

	@Override
	protected void done() {
		try {
			if (get()) {
				lp.dispose();
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void process(List<String> chunks) {
		area.append(chunks + "\n");
	}

	public void publisher(String data) {
		publish(data);
	}

	public void setprogress(int prog) {
		setProgress(prog);
	}
}
