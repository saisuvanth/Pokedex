package com.pokedex;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class SuperWindow extends JFrame {
	JMenuBar menubar;
	JMenu file, view, help;
	JMenuItem open, fullscreen, about;

	public SuperWindow(boolean flag) {
		setLayout(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("assets/icon.png"));
		getContentPane().setBackground(Color.decode("#9ff79f"));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			System.out.println(e);
		}
		if (flag) {
			menubar = new JMenuBar();
			file = new JMenu("File");
			view = new JMenu("View");
			help = new JMenu("Help");

			// file.setMargin(new Insets(0, 30, 0, 30));
			// view.setMargin(new Insets(0, 30, 0, 30));
			// help.setMargin(new Insets(0, 30, 0, 30));
			// exit.setMargin(new Insets(0, 30, 0, 30));

			file.setBorder(new EmptyBorder(2, 10, 2, 10));
			view.setBorder(new EmptyBorder(2, 10, 2, 10));
			help.setBorder(new EmptyBorder(2, 10, 2, 10));

			open = new JMenuItem("Open CSV");
			fullscreen = new JMenuItem("Fullscreen");
			about = new JMenuItem("About");

			file.add(open);
			view.add(fullscreen);
			help.add(about);

			menubar.add(file);
			menubar.add(view);
			menubar.add(help);

			setJMenuBar(menubar);
		}
	}
}
