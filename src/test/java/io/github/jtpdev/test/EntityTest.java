package io.github.jtpdev.test;

import java.util.Date;

import io.github.jtpdev.jcoffee.annotations.Name;
import io.github.jtpdev.jcoffee.annotations.NoColumn;

/**
 * @author Jimmy Porto
 *
 */
@Name("entity")
public class EntityTest {
	
	@Name("id")
	private Integer idEntity;
	private String name;
	private Date date;
	private Boolean isCool;
	@NoColumn
	private String noColumn;
	
	public Integer getIdEntity() {
		return idEntity;
	}
	public void setIdEntity(Integer idEntity) {
		this.idEntity = idEntity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Boolean getIsCool() {
		return isCool;
	}
	public void setIsCool(Boolean isCool) {
		this.isCool = isCool;
	}
	public String getNoColumn() {
		return noColumn;
	}
	public void setNoColumn(String noColumn) {
		this.noColumn = noColumn;
	}

}
