package com.braveheart.yuvaraj.filesbkp;

public class FilePojo {

	int runid;
	String filename;
	long size;
	long lastmodified;
	String lastmodifiedDate;
	String deleted;
	String copied;
	String destname;
	
	public int getRunid() {
		return runid;
	}
	public void setRunid(int runid) {
		this.runid = runid;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getLastmodified() {
		return lastmodified;
	}
	public void setLastmodified(long lastmodified) {
		this.lastmodified = lastmodified;
	}
	public String getLastmodifiedDate() {
		return lastmodifiedDate;
	}
	public void setLastmodifiedDate(String lastmodifiedDate) {
		this.lastmodifiedDate = lastmodifiedDate;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getCopied() {
		return copied;
	}
	public void setCopied(String copied) {
		this.copied = copied;
	}
	public String getDestname() {
		return destname;
	}
	public void setDestname(String destname) {
		this.destname = destname;
	}
}
