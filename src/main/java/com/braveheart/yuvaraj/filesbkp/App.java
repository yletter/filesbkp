package com.braveheart.yuvaraj.filesbkp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws Exception {
		System.out.println("Hello World!");
		ArrayList<String> backuplistArray = new ArrayList<String>();
		String backuplist = "C:\\Users\\YuvaPC\\Documents\\01 Documents\\02 Proj\\File System Backup POC\\BackupList.txt";
		String flatFilePath = "C:\\Users\\YuvaPC\\Documents\\01 Documents\\02 Proj\\File System Backup POC\\Dest Files\\Flat Files\\";
		String destFilePath = "C:\\Users\\YuvaPC\\Documents\\01 Documents\\02 Proj\\File System Backup POC\\Dest Files\\";
		// Open the file
		FileInputStream fstream = new FileInputStream(backuplist);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		// Read File Line By Line
		while ((strLine = br.readLine()) != null) {
			// Print the content on the console
			backuplistArray.add(strLine);
			System.out.println(strLine);
		}

		// Close the input stream
		br.close();
		FilesDao filesDao = new FilesDao();
		filesDao.connect();
		for (int i = 0; i < backuplistArray.size(); i++) {
			File file = new File(backuplistArray.get(i));
			if (file.isFile()) {
				processFile(flatFilePath, filesDao, file);
			} else {
				processFolder(destFilePath, filesDao, file, file.getName() + "\\");
			}
		}
		
		processCopy(filesDao);
		
		filesDao.close();
	}

	private static void processCopy(FilesDao filesDao) throws SQLException {
		List<FilePojo> filePojoList = filesDao.getAll();
		for (int i = 0; i < filePojoList.size(); i++) {
			if(copyFile(new File(filePojoList.get(i).getFilename()), new File(filePojoList.get(i).getDestname()))) {
				filesDao.updateAsCopied(filePojoList.get(i).getFilename());				
			}
		}
	}

	private static void processFolder(String destFilePath, FilesDao filesDao,
			File folder, String pathPrefix) throws SQLException {
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				processFile(destFilePath + "\\" + pathPrefix , filesDao, listOfFiles[i]);
			} else if (listOfFiles[i].isDirectory()) {
				processFolder(destFilePath + "\\" + pathPrefix + "\\", filesDao, listOfFiles[i], listOfFiles[i].getName() + "\\");
			}
		}
	}

	private static void processFile(String flatFilePath, FilesDao filesDao,
			File file) throws SQLException {
		FilePojo filePojo = new FilePojo();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		filePojo.setFilename(file.getAbsolutePath());
		filePojo.setLastmodified(file.lastModified());
		filePojo.setLastmodifiedDate(sdf.format(file.lastModified()));
		filePojo.setSize(file.length());
		filePojo.setRunid(1);
		filePojo.setCopied("N");
		filePojo.setDeleted("N");

		File file2 = new File(flatFilePath + file.getName());
		filePojo.setDestname(file2.getAbsolutePath());
		FilePojo filePojoDB = filesDao.get(file.getAbsolutePath());
		if (filePojoDB != null) {
			if (!(filePojo.getSize() == filePojoDB.getSize() && filePojo
					.getLastmodified() == filePojoDB.getLastmodified())) {
				filesDao.update(filePojo);
				System.out.println("In update - ");
			}
		} else {
			filesDao.insert(filePojo);
			System.out.println("In insert - ");
		}
	}

	public static boolean copyFile(File afile, File bfile) {
		InputStream inStream = null;
		OutputStream outStream = null;
		try {
			File filec = new File(bfile.getParent());
			if (!filec.exists())
				filec.mkdirs();
			inStream = new FileInputStream(afile);
			outStream = new FileOutputStream(bfile);
			byte[] buffer = new byte[1024];
			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}
			inStream.close();
			outStream.close();
			System.out.println("File is copied successful – "
					+ afile.getAbsolutePath());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}

/*
 * 
 * create table test.BACKUPLIST(runid INT NOT NULL, 
filename VARCHAR(255) default NULL, 
size INT default NULL, 
lastmodified BIGINT default NULL, 
lastmodifiedDate VARCHAR(255) default NULL, 
deleted char(1),
copied char(1),
destname VARCHAR(255) default NULL, 
PRIMARY KEY (filename));

select * from test.BACKUPLIST;
delete from test.BACKUPLIST;
drop table test.BACKUPLIST;
 */
