package io.github.jtpdev.jcoffee;

import java.lang.reflect.Field;
import java.sql.SQLException;

import io.github.jtpdev.jcoffee.annotations.Id;
import io.github.jtpdev.jcoffee.exs.JCoffeeException;
import io.github.jtpdev.jcoffee.exs.JCoffeeExceptionType;

/**
 * @author Jimmy Porto
 *
 * @param <T>
 */
class JCoffeeValidation<T> {
	
	private DynamicDAO<T> dao;

	public JCoffeeValidation(DynamicDAO<T> dao) {
		this.dao = dao;
	}

	void verifyConnection() throws SQLException {
		if (this.dao.connection == null) {
			throw new JCoffeeException(JCoffeeExceptionType.CONNECTION_IS_NULL);
		}
		if (this.dao.connection.isClosed()) {
			throw new JCoffeeException(JCoffeeExceptionType.CONNECTION_IS_CLOSED);
		}

	}

	void verifyEntity() {
		if (this.dao.entity == null) {
			throw new JCoffeeException(JCoffeeExceptionType.ENTITY_IS_NULL);
		}

	}

	void verifyId() throws Exception {
		String name = null;
		Field id = null;
		for (Field field : this.dao.entity.getClass().getFields()) {
			if(field.isAnnotationPresent(Id.class)) {
				name = field.getAnnotation(Id.class).name();
				id = field;
			}
		}
		if (name == null || name.trim().length() == 0) {
			throw new JCoffeeException(JCoffeeExceptionType.ID_NOT_ANNOTED);
		}
		
		id.setAccessible(true);
		Object idValue = id.get(dao.entity);
		if (idValue == null) {
			throw new JCoffeeException(JCoffeeExceptionType.ENTITY_ID_IS_NULL);
		}
	}

	public void verifySQLIdName(String idName) {
		if(idName == null) {
			throw new JCoffeeException(JCoffeeExceptionType.ID_NOT_ANNOTED);
		}
		
	}
}
