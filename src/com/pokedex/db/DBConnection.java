package com.pokedex.db;

import java.sql.*;

public class DBConnection {
	static Connection connection;
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

	public static boolean initialise(String path, String username, String password) {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(path, username, password);
			tableGenerator();
			return true;
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	private static void tableGenerator() {
		try {
			Statement table = connection.createStatement();
			table.executeUpdate(
					"CREATE TABLE IF NOT EXISTS trainer(id int,trainerank int,name varchar(20),age int,gender char(1),region varchar(20),primary key(id))");
			table.executeUpdate(
					"CREATE TABLE IF NOT EXISTS poketrainer(id int,pok1 varchar(20),pok2 varchar(20),pok3 varchar(20),foreign key(id) references trainer(id) on update cascade)");
			table.execute("CREATE PROCEDURE deletedata(in i int) " +
					"BEGIN " +
					" delete from poketrainer where id=i; " +
					" delete from trainer where id=i; " +
					"END");
			table.execute(
					"CREATE PROCEDURE insertdata(in tid int,in trank int,in tname varchar(20),in tage int,in tgender char(1),in tregion varchar(20),in tpok1 varchar(20),in tpok2 varchar(20),in tpok3 varchar(20)) "
							+ "BEGIN " + " if exists (select * from trainer where id=tid) then " +
							"  update trainer set trainerank=trank,name=tname,age=tage,gender=tgender,region=tregion where id=tid;  "
							+
							"  update poketrainer set pok1=tpok1,pok2=tpok2,pok3=tpok3 where id=tid;  " +
							" else " +
							" insert into trainer values(tid,trank,tname,tage,tgender,tregion); " +
							" insert into poketrainer values(tid,tpok1,tpok2,tpok3); " +
							" end if; " +
							"END");
			table.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
