package io.github.jtpdev.jcoffee;

import java.sql.SQLException;

import io.github.jtpdev.jcoffee.exs.JCoffeeException;
import io.github.jtpdev.jcoffee.exs.JCoffeeExceptionType;
import io.github.jtpdev.jcoffee.interfaces.JCoffeeEntity;

class JCoffeeValidation<T extends JCoffeeEntity> {
	
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
		if (!(this.dao.entity instanceof JCoffeeEntity)) {
			throw new JCoffeeException(JCoffeeExceptionType.ENTITY_NOT_IS_ISNTANCE_OF_JCOFFEEENTITY);
		}

	}

	void verifyId() {
		if (this.dao.entity.getId() == null) {
			throw new JCoffeeException(JCoffeeExceptionType.ENTITY_ID_IS_NULL);
		}
	}
}
