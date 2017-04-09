package com.braveheart.yuvaraj.filesbkp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilesDao {

	Connection conn = null;

	public void connect() throws ClassNotFoundException {
		String dbURL = "jdbc:mysql://localhost:3306/test";
		String username = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, username, password);
			if (conn != null) {
				//System.out.println("Connected");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void insert(FilePojo filePojo) throws SQLException {
		String sql = "INSERT INTO BACKUPLIST (runid, filename, size, lastmodified, lastmodifiedDate, deleted, copied, destname) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		int runid = filePojo.getRunid();
		String filename = filePojo.getFilename();
		long size = filePojo.getSize();
		long lastmodified = filePojo.getLastmodified();
		String lastmodifiedDate = filePojo.getLastmodifiedDate();
		String deleted = filePojo.getDeleted();
		String copied = filePojo.getCopied();
		String destname = filePojo.getDestname();

		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setInt(1, runid);
		statement.setString(2, filename);
		statement.setLong(3, size);
		statement.setLong(4, lastmodified);
		statement.setString(5, lastmodifiedDate);
		statement.setString(6, deleted);
		statement.setString(7, copied);
		statement.setString(8, destname);

		int rowsInserted = statement.executeUpdate();
		if (rowsInserted > 0) {
			System.out.println("A new row was inserted successfully!");
		}
	}

	public void update(FilePojo filePojo) throws SQLException {
		String sql = "update BACKUPLIST set runid = ?, size = ?, lastmodified = ?, lastmodifiedDate = ?, deleted = ?, copied = ?, destname = ? where filename = ?";

		int runid = filePojo.getRunid();
		String filename = filePojo.getFilename();
		long size = filePojo.getSize();
		long lastmodified = filePojo.getLastmodified();
		String lastmodifiedDate = filePojo.getLastmodifiedDate();
		String deleted = filePojo.getDeleted();
		String copied = filePojo.getCopied();
		String destname = filePojo.getDestname();

		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setInt(1, runid);
		statement.setString(8, filename);
		statement.setLong(2, size);
		statement.setLong(3, lastmodified);
		statement.setString(4, lastmodifiedDate);
		statement.setString(5, deleted);
		statement.setString(6, copied);
		statement.setString(7, destname);

		int rowsInserted = statement.executeUpdate();
		if (rowsInserted > 0) {
			System.out.println(rowsInserted + " row was updated successfully!");
		}
	}

	public FilePojo get(String filename) throws SQLException {
		FilePojo filePojo = null;
		String sql = "Select runid, filename, size, lastmodified, lastmodifiedDate, deleted, copied, destname from BACKUPLIST where filename = ?";

		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, filename);

		ResultSet res = statement.executeQuery();
		if (res.next()) {
/*			System.out.println(":" + res.getInt(1));
			System.out.println(":" + res.getString(2));
			System.out.println(":" + res.getInt(3));
			System.out.println(":" + res.getLong(4));
			System.out.println(":" + res.getString(5));
			System.out.println(":" + res.getString(6));
			System.out.println(":" + res.getString(7));
			System.out.println(":" + res.getString(8));*/
			filePojo = new FilePojo();
			filePojo.setRunid(res.getInt(1));
			filePojo.setFilename(res.getString(2));
			filePojo.setSize(res.getInt(3));
			filePojo.setLastmodified(res.getLong(4));
			filePojo.setLastmodifiedDate(res.getString(5));
			filePojo.setDeleted(res.getString(6));
			filePojo.setCopied(res.getString(7));
			filePojo.setDestname(res.getString(8));
			}
		return filePojo;
	}

	public void close() throws SQLException {
		if (conn != null) {
			//System.out.println("Conn closed!");
			conn.close();
		}
	}

	public List<FilePojo> getAll() throws SQLException {
		FilePojo filePojo = null;
		List<FilePojo> filePojoList = new ArrayList<FilePojo>();
		String sql = "Select runid, filename, size, lastmodified, lastmodifiedDate, deleted, copied, destname from BACKUPLIST where copied = 'N'";
		PreparedStatement statement = conn.prepareStatement(sql);
		ResultSet res = statement.executeQuery();
		while (res.next()) {
			filePojo = new FilePojo();
			filePojo.setRunid(res.getInt(1));
			filePojo.setFilename(res.getString(2));
			filePojo.setSize(res.getInt(3));
			filePojo.setLastmodified(res.getLong(4));
			filePojo.setLastmodifiedDate(res.getString(5));
			filePojo.setDeleted(res.getString(6));
			filePojo.setCopied(res.getString(7));
			filePojo.setDestname(res.getString(8));
			filePojoList.add(filePojo);
		}
		return filePojoList;
	}

	public void updateAsCopied(String filename) throws SQLException {
		String sql = "update BACKUPLIST set copied = 'Y' where filename = ?";

		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, filename);

		int rowsInserted = statement.executeUpdate();
		if (rowsInserted > 0) {
			System.out.println(rowsInserted + " row was updated successfully!");
		}
	}
}
