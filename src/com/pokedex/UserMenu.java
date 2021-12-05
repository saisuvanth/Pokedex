package com.pokedex;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;

import com.pokedex.db.*;

import java.awt.*;
import java.util.List;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class UserMenu extends SuperWindow implements ActionListener {
	JPanel navBar, mainMenu;
	GridBagConstraints gbc;
	JTextField trainerName, trainerRegion, trainerAge, pokemonName;
	JRadioButton greaterFlag, lesserFlag;
	JButton search, delete, create;
	JTable table;
	List<Pokeuser> pokeuser;
	final String QUERY = "select * from trainer natural join poketrainer where ";

	public UserMenu() {
		super(true);
		setSize(900, 800);
		setTitle("Pokedex");
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(5, 5));
		createNavbar();
		open.addActionListener(this);
		fullscreen.addActionListener(this);
		about.addActionListener(this);
		add(navBar, BorderLayout.NORTH);
		mainMenu = new JPanel(new GridLayout(1, 1));
		mainMenu.setBackground(Color.CYAN);
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				delete.setEnabled(true);
			}
		});
		defaultTable();
		mainMenu.add(new JScrollPane(table));
		add(mainMenu, BorderLayout.CENTER);
		JPanel version = new JPanel();
		version.add(new JLabel("version 1.1.0"));
		add(version, BorderLayout.SOUTH);
		setVisible(true);
	}

	private void createNavbar() {
		gbc = new GridBagConstraints();
		navBar = new JPanel(new GridBagLayout());
		JLabel label = new JLabel("Trainer Name");
		trainerName = new JTextField();
		gbc.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(2, 5, 2, 5);

		gbc.gridx = 0;
		gbc.gridy = 0;
		navBar.add(label, gbc);
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		trainerName = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 0;
		navBar.add(trainerName, gbc);

		label = new JLabel("Trainer Age");
		gbc.gridx = 2;
		gbc.gridy = 0;
		navBar.add(label, gbc);
		trainerAge = new JTextField();
		gbc.gridx = 3;
		gbc.gridy = 0;
		navBar.add(trainerAge, gbc);

		label = new JLabel("Pokemon Name");
		gbc.gridx = 0;
		gbc.gridy = 1;
		navBar.add(label, gbc);
		pokemonName = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 1;
		navBar.add(pokemonName, gbc);

		label = new JLabel("Trainer Region");
		gbc.gridx = 2;
		gbc.gridy = 1;
		navBar.add(label, gbc);
		trainerRegion = new JTextField();
		gbc.gridx = 3;
		gbc.gridy = 1;
		navBar.add(trainerRegion, gbc);

		greaterFlag = new JRadioButton("greater");
		gbc.gridx = 1;
		gbc.gridy = 2;
		navBar.add(greaterFlag, gbc);
		greaterFlag.addActionListener(this);
		lesserFlag = new JRadioButton("lesser");
		gbc.gridx = 2;
		gbc.gridy = 2;
		navBar.add(lesserFlag, gbc);
		lesserFlag.addActionListener(this);
		label = new JLabel("If age is selected then");
		gbc.gridx = 0;
		gbc.gridy = 2;
		navBar.add(label, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.NONE;
		create = new JButton("Create");
		navBar.add(create, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		// gbc.ipadx = 2;
		search = new JButton("Search");
		navBar.add(search, gbc);
		delete = new JButton("Delete");
		gbc.gridx = 3;
		gbc.gridy = 3;
		navBar.add(delete, gbc);
		create.addActionListener(this);
		delete.addActionListener(this);
		search.addActionListener(this);
	}

	private void worker() {
		String params[], name = trainerName.getText(), pokemon = pokemonName.getText(),
				region = trainerRegion.getText();
		int age = 0;
		try {
			age = trainerAge.getText().equals("") ? 0 : Integer.parseInt(trainerAge.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Enter valid Integer", "Number Exception", JOptionPane.WARNING_MESSAGE);
			return;
		}
		boolean nameflag = name.equals(""), pokemonflag = pokemon.equals(""), regionflag = region.equals(""),
				less = lesserFlag.isSelected(),
				great = greaterFlag.isSelected();

		String nameQuery = "name like concat('%',?,'%')", ageQuery = less ? "age<? " : great ? "age>? " : "age=? ",
				pokQuery = "(pok1=? or pok2=? or pok3=?) ", regionQuery = "region=? ";

		// checking flags and retrieving data
		try {
			if (!nameflag) {
				if (!pokemonflag || !regionflag || age != 0) {
					JOptionPane.showMessageDialog(this,
							"Name itself is Enough. Why even bother entering other details",
							"AntiDumb Squad",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				params = new String[] { name };
				pokeuser = DBDriver.getQuery(QUERY + nameQuery, params);
				createTable();
			} else {
				if (pokemonflag && regionflag && age == 0) {
					pokeuser = DBDriver.getQuery(QUERY.substring(0, QUERY.lastIndexOf(" w")), null);
					createTable();
					return;
				}
				if (age != 0 && pokemonflag && regionflag) { // single flags
					params = new String[] { age + "" };
					pokeuser = DBDriver.getQuery(QUERY + ageQuery, params);
					createTable();
					return;
				} else if (!regionflag && pokemonflag && age == 0) {
					params = new String[] { region };
					pokeuser = DBDriver.getQuery(QUERY + regionQuery, params);
					createTable();
					return;
				} else if (!pokemonflag && regionflag && age == 0) {
					params = new String[] { pokemon, pokemon, pokemon };
					pokeuser = DBDriver.getQuery(QUERY + pokQuery, params);
					createTable();
					return;
				} else if (pokemonflag && !regionflag && age != 0) { // two flags
					params = new String[] { age + "", region };
					pokeuser = DBDriver.getQuery(QUERY + ageQuery + "and " + regionQuery, params);
					createTable();
					return;
				} else if (regionflag && !pokemonflag && age != 0) {
					params = new String[] { age + "", pokemon, pokemon, pokemon };
					pokeuser = DBDriver.getQuery(QUERY + ageQuery + "and " + pokQuery, params);
					createTable();
					return;
				} else if (!pokemonflag && !regionflag && age == 0) {
					params = new String[] { region, pokemon, pokemon, pokemon };
					pokeuser = DBDriver.getQuery(QUERY + regionQuery + "and " + pokQuery, params);
					createTable();
					return;
				} else if (!regionflag && pokemonflag && age != 0) {
					params = new String[] { age + "", region };
					pokeuser = DBDriver.getQuery(QUERY + ageQuery + "and " + regionQuery, params);
					createTable();
					return;
				} else if (!regionflag && !pokemonflag && age != 0) {
					params = new String[] { age + "", region, pokemon, pokemon, pokemon };
					pokeuser = DBDriver.getQuery(QUERY + ageQuery + "and " + regionQuery + "and " + pokQuery, params);
					createTable();
					return;
				} else if (age == 0 && (less | great)) {
					JOptionPane.showMessageDialog(this, "Hey you forgot to enter age", "Enter age",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this, "SQL Driver is not available to interact", "SQL Driver",
					JOptionPane.ERROR_MESSAGE);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "File used to save your Database cannot be found", "File Not Found",
					JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error occured while connecting with SQL database",
					"Database Connection Failed",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void defaultTable() {
		try {
			pokeuser = DBDriver.getQuery(QUERY.substring(0, QUERY.lastIndexOf(" w")) + " order by trainerank limit 30",
					new String[] {});
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this, "SQL Driver is not available to interact", "SQL Driver",
					JOptionPane.ERROR_MESSAGE);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "File used to save your Database cannot be found", "File Not Found",
					JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error occured while connecting with SQL database",
					"Database Connection Failed",
					JOptionPane.ERROR_MESSAGE);
		}
		createTable();
	}

	private void createTable() {
		table.setModel(new TableModel(pokeuser));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setAutoCreateRowSorter(true);
		table.setRowHeight(20);
		table.setIntercellSpacing(new Dimension(5, 5));
		mainMenu.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == open) {
			JFileChooser file = new JFileChooser();
			file.addChoosableFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
			file.setFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
			int i = file.showOpenDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				File fc = file.getSelectedFile();
				new LoadingPane(fc);
			}
			return;
		} else if (e.getSource() == fullscreen) {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			return;
		} else if (e.getSource() == search) {
			worker();
			return;
		} else if (e.getSource() == create) {
			new CreateUser();
		} else if (e.getSource() == about) {
			new About();
		} else if (e.getSource() == lesserFlag) {
			greaterFlag.setSelected(false);
			return;
		} else if (e.getSource() == greaterFlag) {
			lesserFlag.setSelected(false);
			return;
		} else if (e.getSource() == delete) {
			int index = table.getSelectedRow();
			if (index != -1) {
				try {
					DBDriver.deleteData(pokeuser.get(index).getId());
				} catch (ClassNotFoundException ex) {
					JOptionPane.showMessageDialog(this, "SQL Driver is not available to interact", "SQL Driver",
							JOptionPane.ERROR_MESSAGE);
				} catch (FileNotFoundException ex) {
					JOptionPane.showMessageDialog(this, "File used to save your Database cannot be found",
							"File Not Found",
							JOptionPane.ERROR_MESSAGE);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(this, "Error occured while connecting with SQL database",
							"Database Connection Failed",
							JOptionPane.ERROR_MESSAGE);
				}
				pokeuser.remove(index);
				createTable();
			}
		}
	}
}

class TableModel extends AbstractTableModel {
	String[] cols = { "Id", "Rank", "Name", "Age", "Gender", "Region", "Pokemon 1", "Pokemon 2", "Pokemon 3" };
	String[] sqlcols = { "id", "trainerank", "name", "age", "gender", "region", "pok1", "pok2", "pok3" };
	Class<?>[] classes = { Integer.class, Integer.class, String.class, Byte.class, String.class };

	List<Pokeuser> user;

	TableModel(List<Pokeuser> user) {
		this.user = user;
	}

	@Override
	public int getRowCount() {
		return user.size();
	}

	@Override
	public int getColumnCount() {
		return cols.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Pokeuser temp = user.get(rowIndex);
		String poks[] = temp.getPokemon();
		switch (columnIndex) {
			case 0:
				return temp.getId();
			case 1:
				return temp.getRank();
			case 2:
				return temp.getName();
			case 3:
				return temp.getAge();
			case 4:
				return temp.getGender();
			case 5:
				return temp.getRegion();
			case 6:
				return poks[0];
			case 7:
				return poks[1];
			default:
				return poks[2];
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex < 4)
			return classes[columnIndex];
		else
			return classes[4];
	}

	@Override
	public String getColumnName(int columnIndex) {
		return cols[columnIndex];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		String pokemon[] = user.get(rowIndex).getPokemon();
		switch (columnIndex) {
			case 0:
				JOptionPane.showMessageDialog(null, "Cant change ID of trainer", "AntiDumb Squad",
						JOptionPane.WARNING_MESSAGE);
				return;
			case 1:
				user.get(rowIndex).setRank((int) aValue);
				break;
			case 2:
				user.get(rowIndex).setName((String) aValue);
				break;
			case 3:
				user.get(rowIndex).setAge((byte) aValue);
				break;
			case 4:
				user.get(rowIndex).setGender((String) aValue);
				break;
			case 5:
				user.get(rowIndex).setRegion((String) aValue);
				break;
			case 6:
				user.get(rowIndex).setPokemon(new String[] { (String) aValue, pokemon[1], pokemon[2] });
				break;
			case 7:
				user.get(rowIndex).setPokemon(new String[] { pokemon[0], (String) aValue, pokemon[2] });
				break;
			case 8:
				user.get(rowIndex).setPokemon(new String[] { pokemon[0], pokemon[1], (String) aValue });
		}
		try {
			if (columnIndex < 6)
				DBDriver.updateData("update trainer set " + sqlcols[columnIndex] + "= ? where id=?",
						new Object[] { aValue, user.get(rowIndex).getId() });
			else
				DBDriver.updateData("update poketrainer set " + sqlcols[columnIndex] + "= ? where id=?",
						new Object[] { aValue, user.get(rowIndex).getId() });
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "SQL Driver is not available to interact", "SQL Driver",
					JOptionPane.ERROR_MESSAGE);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File used to save your Database cannot be found", "File Not Found",
					JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error occured while connecting with SQL database",
					"Database Connection Failed",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}