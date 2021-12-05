package com.pokedex;

import java.awt.*;

import javax.swing.*;

public class About extends SuperWindow {
	ImageIcon oak;
	JLabel area;
	JButton next;
	int flag;
	String[] info = {
			"<html>Hello there! Welcome to the world of Pokémon! <br> My name is Oak! People call me the Pokémon Prof! This world is inhabited by <br> creatures called Pokémon! For some people, Pokémon are pets. Other use them for fights. Myself… I study Pokémon as a profession.</html>",
			"<html> This is a Pokemon Trainer Management System app that lets you <br> manage and conduct the Pokemon League efficiently every three years.</html>",
			"<html> This app lets you create a database with the details of all the aspiring <br> Pokemon trainers that applied this year by reading data from a CSV file using the 'File' menu</html>",
			"<html> And you can read the data of specific people<br> that you might be interesting in by using the 'Search' feature.</html>",
			"<html> If you want you can observe the greatest of all by just clicking on Rank column ;) </br> as well as for others too</html>",
			"<html> You can also update and modify the data of anyone by double clicking on any field</html>",
			"<html> And finally you can delete any trainers who violate the rules<br> of the Pokemon League by using the 'Delete' button.</html>",
			"<html> Your very own Pokémon legend is about to unfold!<br> A world of dreams and adventures with Pokémon awaits! Let's go!</html>" };

	public About() {
		super(false);
		flag = 0;
		setSize(500, 400);
		setTitle("About");
		setResizable(false);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		oak = new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/oak.jpg"));
		area = new JLabel(info[0]);
		next = new JButton();
		next.setIcon(new ImageIcon("assets/next1.png"));
		next.setBackground(Color.decode("#9ff79f"));
		add(new JLabel(oak), BorderLayout.NORTH);
		add(area, BorderLayout.CENTER);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		panel.add(next, BorderLayout.EAST);
		panel.setBackground(Color.decode("#9ff79f"));
		getContentPane().add(panel, BorderLayout.SOUTH);
		next.addActionListener(e -> {
			if (flag == 7) {
				this.dispose();
				return;
			}
			area.setText(info[++flag]);
		});
		setVisible(true);
	}

}
