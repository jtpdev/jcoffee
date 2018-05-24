package io.github.jtpdev.jcoffee;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import io.github.jtpdev.jcoffee.annotations.Name;
import io.github.jtpdev.jcoffee.annotations.NoColumn;
import io.github.jtpdev.jcoffee.interfaces.JCoffeeEntity;
import io.github.jtpdev.jcoffee.utils.Logger;
import io.github.jtpdev.jcoffee.utils.LoggerMessage;

/**
 * @author Jimmy Porto
 *
 * @param <T>
 */
public class DynamicDAO<T extends JCoffeeEntity> {

	private JCoffeeValidation<T> validation;
	Connection connection;
	T entity;
	

	public DynamicDAO() {
		this(null, null);
	}

	public DynamicDAO(Connection connection, T entity) {
		this.connection = connection;
		this.entity = entity;
		validation = new JCoffeeValidation<T>(this);
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public T save() throws Exception {
		validation.verifyConnection();
		validation.verifyEntity();
		if (this.entity.getId() == null) {
			Logger.show(LoggerMessage.SAVE_START);
			String sql = saveSqlMaker();
			PreparedStatement ps = connection.prepareStatement(sql);
			Field[] fields = entity.getClass().getFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (!field.isAnnotationPresent(NoColumn.class)) {
					field.setAccessible(true);
					Object value = field.get(this.entity);
					setStringValue(ps, i, value);
					setNumberValue(ps, i, value);
					setCharValue(ps, i, value);
					setDateTimeValue(ps, i, value);
				}
			}
			Logger.show(LoggerMessage.SAVE_END);
		} else {
			update();
		}
		
		return entity;
	}
	
	public T update() throws Exception {
		validation.verifyConnection();
		validation.verifyEntity();
		validation.verifyId();
		// TODO update here
		return null;
	}

	private void setDateTimeValue(PreparedStatement ps, int i, Object value) throws SQLException {
		if (value instanceof Date) {
			if (value instanceof Timestamp) {
				ps.setTimestamp(i + 1, (Timestamp) value);
			} else if (value instanceof Time) {
				ps.setTime(i + 1, (Time) value);
			} else {
				ps.setDate(i + 1, new java.sql.Date(((Date) value).getTime()));
			}
		}
		if (value instanceof LocalDate) {
			ps.setDate(i + 1, java.sql.Date.valueOf((LocalDate) value));
		}
		if (value instanceof LocalDateTime) {
			ps.setTimestamp(i + 1, Timestamp.valueOf((LocalDateTime) value));
		}
		if (value instanceof LocalTime) {
			ps.setTime(i + 1, Time.valueOf((LocalTime) value));
		}
	}

	private void setCharValue(PreparedStatement ps, int i, Object value) throws SQLException {
		if (value instanceof Character) {
			ps.setString(i + 1, String.valueOf(value));
		}
	}

	private void setStringValue(PreparedStatement ps, int i, Object value) throws SQLException {
		if (value instanceof String) {
			ps.setString(i + 1, (String) value);
		}
	}

	private void setNumberValue(PreparedStatement ps, int i, Object value) throws SQLException {
		if (value instanceof Number) {
			if (value instanceof Integer) {
				ps.setInt(i + 1, (Integer) value);
			} else if (value instanceof Short) {
				ps.setShort(i + 1, (Short) value);
			} else if (value instanceof Long) {
				ps.setLong(i + 1, (Long) value);
			} else if (value instanceof BigDecimal) {
				ps.setBigDecimal(i + 1, (BigDecimal) value);
			} else if (value instanceof Double) {
				ps.setDouble(i + 1, (Double) value);
			} else if (value instanceof Float) {
				ps.setFloat(i + 1, (Float) value);
			} else if (value instanceof Byte) {
				ps.setByte(i + 1, (Byte) value);
			} else if (value instanceof BigInteger) {
				ps.setLong(i + 1, ((BigInteger) value).longValue());
			} else {
				ps.setString(i + 1, value.toString());
			}
		}
	}

	private String saveSqlMaker() throws Exception {
		String sql = "insert into ";
		Class<? extends Object> clazz = entity.getClass();
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

	public void close() throws SQLException {
		if (this.connection != null && !this.connection.isClosed()) {
			connection.close();
		}
	}

	public void close(PreparedStatement ps, ResultSet rs) {
		close(rs, ps);
	}

	public void close(ResultSet rs, PreparedStatement ps) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		close(ps);
	}

	public void close(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
