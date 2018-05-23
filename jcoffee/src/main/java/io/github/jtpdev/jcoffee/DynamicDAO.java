package io.github.jtpdev.jcoffee;

import java.sql.Connection;

import io.github.jtpdev.jcoffee.annotations.Name;

public class DynamicDAO<T> {
	
	private Connection connection;
	private T entity;
	
	public DynamicDAO() {
		this(null, null);
	}
	
	public DynamicDAO(Connection connection, T entity) {
		this.connection = connection;
		this.entity = entity;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	public T save() throws Exception {
		String sql = sqlMaker();
		connection.prepareStatement(sql);
		return entity;
	}

	private String sqlMaker() {
		String sql = "insert into ";
		Class<? extends Object> clazz = entity.getClass();
		String tableName = clazz.getSimpleName().toLowerCase();
		if(clazz.isAnnotationPresent(Name.class)) {
			tableName = clazz.getAnnotation(Name.class).value().toLowerCase();
		}
		sql += tableName;
		return sql;
	}

}
