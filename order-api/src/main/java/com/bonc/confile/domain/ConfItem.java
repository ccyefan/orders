package com.bonc.confile.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.rest.core.annotation.RestResource;

import com.supermy.domain.BaseObj;
@Entity
@Table(name="t_conf_item")
public class ConfItem extends BaseObj{
	@Column(unique=true)
	private String confKey;
	@Column(columnDefinition = "text comment '脚本代码'")
	private java.lang.String confValue;
	
	@RestResource(exported=false)
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="fileid",nullable=false)
	private FileInfo fileInfo;
	
	public String getConfKey() {
		return confKey;
	}
	public void setConfKey(String confKey) {
		this.confKey = confKey;
	}
	public java.lang.String getConfValue() {
		return confValue;
	}
	public void setConfValue(java.lang.String confValue) {
		this.confValue = confValue;
	}
	public FileInfo getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}
}
