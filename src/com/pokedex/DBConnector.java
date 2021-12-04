package com.pokedex;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class DBConnector extends SuperWindow implements ActionListener {
	JLabel info, pathTaker, userName, userPasswd;
	JTextField path, name;
	JPasswordField password;
	JButton submit;
	String username, passwd, dbPath;
	File file;

	public DBConnector() {
		super(false);
		setSize(500, 500);
		setLocationRelativeTo(null);
		setTitle("Enter Database Requirements");
		info = new JLabel(
				"<html>Enter port url,username and password of your MYSQL server.Kindly note that username will be \"root\" by default.</html>");
		pathTaker = new JLabel("User port for Database");
		userName = new JLabel("Enter username");
		userPasswd = new JLabel("Enter password");
		path = new JTextField();
		name = new JTextField();
		password = new JPasswordField();
		submit = new JButton("Dive In");

		info.setBounds(20, 30, 400, 40);
		pathTaker.setBounds(30, 100, 150, 40);
		userName.setBounds(30, 170, 150, 40);
		userPasswd.setBounds(30, 240, 150, 40);
		path.setBounds(230, 100, 180, 30);
		name.setBounds(230, 170, 180, 30);
		password.setBounds(230, 240, 180, 30);
		submit.setBounds(200, 320, 100, 40);

		path.addActionListener(this);
		name.addActionListener(this);
		password.addActionListener(this);
		submit.addActionListener(this);

		add(info);
		add(pathTaker);
		add(userName);
		add(userPasswd);
		add(path);
		add(name);
		add(password);
		add(submit);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			if (path.getText().equals("") || name.getText().equals("") || password.getPassword() == null) {
				JOptionPane.showMessageDialog(this, "ALL FIELDS ARE MANDATORY", "WARNING", JOptionPane.WARNING_MESSAGE);
				this.requestFocus();
			} else {
				dbPath = path.getText();
				username = name.getText();
				passwd = new String(password.getPassword());
				new LoadingPane(this, dbPath, username, passwd);
			}
		}
	}

}
