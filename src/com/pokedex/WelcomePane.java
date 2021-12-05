package com.pokedex;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class WelcomePane extends JWindow {
	Image welcome;

	public WelcomePane() {
		welcome = Toolkit.getDefaultToolkit().getImage("assets/welcome.gif");
		ImageIcon helcome = new ImageIcon(welcome);
		setSize(helcome.getIconWidth(), helcome.getIconHeight());
		setLocationRelativeTo(null);
		setVisible(true);
		try {
			Thread t = new Thread() {
				public void run() {
					try {
						worker();
					} catch (InterruptedException e) {
						JOptionPane.showMessageDialog(null, "An Interruption has occured while processing", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			};
			t.start();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "An error occured while opening the files", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(welcome, 0, 0, this);
	}

	private void worker() throws InterruptedException {
		File file = new File("src/com/pokedex/db/data.txt");
		if (file.exists()) {
			Thread.sleep(2000);
			this.dispose();
			new UserMenu();
		} else {
			Thread.sleep(2000);
			this.dispose();
			new DBConnector();
		}
	}
}
