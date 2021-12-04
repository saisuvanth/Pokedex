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
			worker();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(welcome, 0, 0, this);
	}

	private void worker() {
		try {
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
		} catch (Exception ex) {
			ex.printStackTrace();
			// Error();
		}
	}
}
