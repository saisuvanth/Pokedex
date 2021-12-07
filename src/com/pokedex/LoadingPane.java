package com.pokedex;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.pokedex.db.DBConnection;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class LoadingPane extends JWindow {
	Image loading;
	JProgressBar pb;

	public LoadingPane(File f) {
		setSize(400, 200);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		setLocationRelativeTo(null);
		pb = new JProgressBar(0, 100);
		pb.setValue(0);
		pb.setBorder(new EmptyBorder(10, 50, 10, 50));
		pb.setStringPainted(true);
		JTextArea field = new JTextArea();
		field.setBorder(new EmptyBorder(10, 20, 10, 20));
		JLabel l = new JLabel("Loading Pokemon trainer Data");
		l.setHorizontalAlignment(SwingConstants.LEFT);
		add(l);
		add(pb);
		add(field);
		setVisible(true);
		DataLoader dl = new DataLoader(f, this, field);
		dl.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("progress".equals(evt.getPropertyName()))
					pb.setValue((int) evt.getNewValue());
			}
		});
		dl.execute();
	}

	public LoadingPane(DBConnector db, String path, String username, String password) {
		loading = Toolkit.getDefaultToolkit().getImage("assets/loading1.gif");
		ImageIcon loadingIcon = new ImageIcon(loading);
		setSize(loadingIcon.getIconWidth(), loadingIcon.getIconHeight());
		setLocationRelativeTo(null);
		setVisible(true);
		SwingWorker<Boolean, Void> sw = new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() throws Exception {
				try {
					return DBConnection.initialise(path, username, password);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "An Error occured while connecting to Database", "Error",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}

			protected void done() {
				try {
					if (get()) {
						File file = new File("data.txt");
						try {
							FileWriter fw = new FileWriter(file);
							fw.write(path + "\n" + username + "\n" + password);
							fw.close();
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "An error occured while saving file data", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
						db.dispose();
						dispose();
						new UserMenu();
					} else {
						JOptionPane.showMessageDialog(getContentPane(), "Wrong Database Information", "ERROR",
								JOptionPane.ERROR_MESSAGE);
						dispose();
						db.requestFocus();
					}
				} catch (InterruptedException | ExecutionException ex) {
					JOptionPane.showMessageDialog(null, "An Interruption has occured", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		};
		sw.execute();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(loading, 0, 0, this);
	}

}
