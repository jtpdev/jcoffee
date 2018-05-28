package io.github.jtpdev.jcoffee;

import java.lang.reflect.Field;

import io.github.jtpdev.jcoffee.annotations.Id;
import io.github.jtpdev.jcoffee.annotations.Name;
import io.github.jtpdev.jcoffee.annotations.NoColumn;

/**
 * @author Jimmy Porto
 *
 * @param <T>
 */
public class JCoffeeSQLMaker<T> {

	private DynamicDAO<T> dao;

	public JCoffeeSQLMaker(DynamicDAO<T> dao) {
		super();
		this.dao = dao;
	}

	String selectLastSqlMaker() {
		String sql = "select ";
		String idName = null;
		for (Field field : dao.entity.getClass().getFields()) {
			if(field.isAnnotationPresent(Id.class)) {
				String name = field.getAnnotation(Id.class).name();
				idName = name != null ? name : field.getName(); 
			}
		}
		dao.validation.verifySQLIdName(idName);
		sql += idName + " from "; 
		String tableName = dao.entity.getClass().getAnnotation(Name.class).value();
		sql += tableName != null ? tableName : dao.entity.getClass().getSimpleName(); 
		return sql;
	}

	String saveSqlMaker() throws Exception {
		String sql = "insert into ";
		Class<? extends Object> clazz = dao.entity.getClass();
		String tableName = getTableName(clazz);
		sql += tableName + "\r\n (";
		Integer count = 0;
		for (Field field : clazz.getFields()) {
			if (!field.isAnnotationPresent(NoColumn.class) && field.getName().equals("serialVersionUID")) {
				String column = field.getName();
				if (field.isAnnotationPresent(Name.class)) {
					column = field.getAnnotation(Name.class).value();
				}
				sql = String.join(", ", sql, column);
				count++;
			}
		}
		sql += ")\r\n values (";
		for (int i = 0; i < count; i++) {
			sql = String.join(", ", sql, "?");
		}
		sql += ")";
		return sql;
	}

	private String getTableName(Class<? extends Object> clazz) {
		String tableName = clazz.getSimpleName().toLowerCase();
		if (clazz.isAnnotationPresent(Name.class)) {
			tableName = clazz.getAnnotation(Name.class).value().toLowerCase();
		}
		return tableName;
	}

}
