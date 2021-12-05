package com.pokedex.db;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import com.pokedex.DataLoader;

public class DBDriver {
	static PreparedStatement ps, ps1;
	static CallableStatement cs;
	static Connection connection;
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	static List<Pokeuser> user;

	private static void sqlLoader() throws ClassNotFoundException, FileNotFoundException, SQLException {
		Class.forName(DRIVER);
		Scanner sc = new Scanner(new FileReader("src/com/pokedex/db/data.txt"));
		connection = DriverManager.getConnection(sc.nextLine(), sc.nextLine(), sc.nextLine());
		sc.close();
	}

	private static void setParams(PreparedStatement ps, Object[] params) throws SQLException {
		if (params == null)
			return;
		for (int i = 0; i < params.length; i++) {
			ps.setObject(i + 1, params[i]);
		}
	}

	private static void setParamsCs(CallableStatement cs, Object[] params) throws SQLException {
		if (params == null)
			return;
		for (int i = 0; i < params.length; i++) {
			cs.setObject(i + 1, params[i]);
		}
	}

	public static boolean csvLoader(File f, DataLoader d) throws FileNotFoundException, SQLException {
		try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader(f)));
			d.publisher("File loaded to cache");
			long lines = Files.lines(Paths.get(f.getPath())).count();
			long count = 0;
			d.setprogress(10);
			d.publisher("Connecting to SQL Database");
			sqlLoader();
			d.setprogress(20);
			cs = connection.prepareCall("{call insertdata(?,?,?,?,?,?,?,?,?)}");
			d.publisher("Loading data to Database...");
			while (sc.hasNextLine()) {
				count++;
				String[] res = sc.nextLine().split(",");
				setParamsCs(cs, res);
				cs.execute();
				d.setprogress(20 + (int) (count * 80 / lines));
			}
			d.publisher("Loading Completed closing connection");
			cs.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}
	}

	public static List<Pokeuser> getQuery(String query, Object[] params)
			throws SQLException, ClassNotFoundException, FileNotFoundException {
		sqlLoader();
		try {
			ps = connection.prepareStatement(query);
			setParams(ps, params);
			ResultSet rs = ps.executeQuery();
			user = new ArrayList<Pokeuser>();
			while (rs.next()) {
				String[] poks = { rs.getString("pok1"), rs.getString("pok2"), rs.getString("pok3") };
				user.add(new Pokeuser(rs.getInt(1), rs.getInt(2), rs.getString(3), (byte) rs.getInt(4), rs.getString(5),
						rs.getString(6), poks));
			}
			return user;
		} finally {
			connection.close();
		}
	}

	public static void insertData(Object[] params)
			throws SQLException, ClassNotFoundException, FileNotFoundException {
		sqlLoader();
		try {
			ps = connection.prepareStatement("call insertdata(?,?,?,?,?,?,?,?,?)");
			setParams(ps, params);
			ps.executeUpdate();
			ps.close();
		} finally {
			connection.close();
		}
	}

	public static void updateData(String query, Object[] params)
			throws SQLException, ClassNotFoundException, FileNotFoundException {
		sqlLoader();
		try {
			ps = connection.prepareStatement(query);
			setParams(ps, params);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	public static void deleteData(int id) throws SQLException, ClassNotFoundException, FileNotFoundException {
		sqlLoader();
		try {
			cs = connection.prepareCall("{call deletedata(?)}");
			cs.setInt(1, id);
			cs.execute();
			cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
}
