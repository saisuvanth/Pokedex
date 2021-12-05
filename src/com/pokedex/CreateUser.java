package com.pokedex;

import javax.swing.*;

import com.pokedex.db.DBDriver;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class CreateUser extends SuperWindow {
	JPanel panel;
	JLabel label;
	JTextField id, rank, name, age, gender, region, pok1, pok2, pok3;
	JButton create;

	public CreateUser() {
		super(false);
		setSize(400, 500);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(10, 2, 15, 15));

		label = new JLabel("Id");
		id = new JTextField();
		// label.setBounds(10, 10, 100, 20);
		add(label);
		// id.setBounds(80, 10, 100, 20);
		add(id);

		label = new JLabel("Rank");
		rank = new JTextField();
		// label.setBounds(10, 40, 100, 20);
		add(label);
		// id.setBounds(80, 40, 100, 20);
		add(rank);

		label = new JLabel("Name");
		name = new JTextField();
		// label.setBounds(10, 70, 100, 20);
		add(label);
		// id.setBounds(80, 70, 100, 20);
		add(name);

		label = new JLabel("Age");
		age = new JTextField();
		// label.setBounds(10, 100, 100, 20);
		add(label);
		// id.setBounds(80, 100, 100, 20);
		add(age);

		label = new JLabel("Gender");
		gender = new JTextField();
		// label.setBounds(10, 130, 100, 20);
		add(label);
		// id.setBounds(80, 130, 100, 20);
		add(gender);

		label = new JLabel("Region");
		region = new JTextField();
		// label.setBounds(10, 160, 100, 20);
		add(label);
		// id.setBounds(80, 160, 100, 20);
		add(region);

		label = new JLabel("Pokemon 1");
		pok1 = new JTextField();
		// label.setBounds(10, 190, 100, 20);
		add(label);
		// id.setBounds(80, 190, 100, 20);
		add(pok1);

		label = new JLabel("Pokemon 2");
		pok2 = new JTextField();
		// label.setBounds(10, 220, 100, 20);
		add(label);
		// id.setBounds(80, 220, 100, 20);
		add(pok2);

		label = new JLabel("Pokemon 3");
		pok3 = new JTextField();
		// label.setBounds(10, 240, 100, 20);
		add(label);
		// id.setBounds(80, 240, 100, 20);
		add(pok3);

		add(new JLabel());
		create = new JButton("Create");
		// create.setBounds(50, 280, 150, 30);
		add(create);
		setVisible(true);

		create.addActionListener(e -> {
			try {
				if (id.getText().equals("") | rank.getText().equals("") | name.getText().equals("")
						| age.getText().equals("") |
						gender.getText().equals("") | region.getText().equals("") | pok1.getText().equals("")
						| pok2.getText().equals("")
						| pok3.getText().equals("")) {
					JOptionPane.showMessageDialog(this, "Enter all details", "Warning", JOptionPane.WARNING_MESSAGE);
					this.requestFocus();
					return;
				}
				try {
					int i = Integer.parseInt(id.getText());
					i = Integer.parseInt(rank.getText());
					i = Integer.parseInt(age.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Enter number in ID,Rank,Age", "Format Error",
							JOptionPane.ERROR_MESSAGE);
					this.requestFocus();
					return;
				}
				if (!(gender.getText().equals("M")) | !(gender.getText().equals("F"))) {
					DBDriver.insertData(new Object[] { id.getText(), rank.getText(), name.getText(), age.getText(),
							gender.getText(), region.getText(), pok1.getText(), pok2.getText(), pok3.getText() });
				} else {
					JOptionPane.showMessageDialog(this, "Person can be either M or F", "Gender confirmation",
							JOptionPane.ERROR_MESSAGE);
					this.requestFocus();
					return;
				}
				dispose();
			} catch (ClassNotFoundException ex) {
				JOptionPane.showMessageDialog(this, "SQL Driver is not available to interact", "SQL Driver",
						JOptionPane.ERROR_MESSAGE);
			} catch (FileNotFoundException ex) {
				JOptionPane.showMessageDialog(this, "File used to save your Database cannot be found",
						"File Not Found",
						JOptionPane.ERROR_MESSAGE);
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, "A person with given id exists or connection failed",
						"Database Connection Failed",
						JOptionPane.ERROR_MESSAGE);
			}
			dispose();
		});
	}
}
