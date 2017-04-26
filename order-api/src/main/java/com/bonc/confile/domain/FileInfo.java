package com.bonc.confile.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supermy.domain.BaseObj;
@Entity
@Table(name="t_file")
public class FileInfo extends BaseObj{
	private String fileName;
	private String path;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
