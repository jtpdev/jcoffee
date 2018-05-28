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

import io.github.jtpdev.jcoffee.annotations.Id;
import io.github.jtpdev.jcoffee.annotations.NoColumn;
import io.github.jtpdev.jcoffee.utils.Logger;
import io.github.jtpdev.jcoffee.utils.LoggerMessage;

/**
 * @author Jimmy Porto
 *
 * @param <T>
 */
public class DynamicDAO<T> {

	private JCoffeeSQLMaker<T> sqlMaker;
	JCoffeeValidation<T> validation;
	Connection connection;
	T entity;

	public DynamicDAO() {
		this(null, null);
	}

	public DynamicDAO(Connection connection, T entity) {
		this.connection = connection;
		this.entity = entity;
		validation = new JCoffeeValidation<T>(this);
		sqlMaker = new JCoffeeSQLMaker<T>(this);
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
		Object idValue = getIdvalue();
		if (idValue == null) {
			Logger.show(LoggerMessage.SAVE_START);
			String sql = sqlMaker.saveSqlMaker();
			PreparedStatement ps = connection.prepareStatement(sql);
			Field[] fields = entity.getClass().getFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (!field.isAnnotationPresent(NoColumn.class)) {
					field.setAccessible(true);
					Object value = field.get(this.entity);
					setValue(ps, i, value);
				}
			}
			if (ps.executeUpdate() > 0) {
				sql = sqlMaker.selectLastSqlMaker();
				setValue(ps, 1, idValue);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					getValue(idValue, rs);

				}
			}
		}
		Logger.show(LoggerMessage.SAVE_END);
	}

	private void getValue(Object idValue, ResultSet rs) throws IllegalAccessException, SQLException {
		if(getStringValue(idValue, rs);
		getNumberValue(idValue, rs);
		getCharValue(idValue, rs);
		getDateValue(idValue, rs);
	}

	private void getDateValue(Object idValue, ResultSet rs) throws IllegalAccessException, SQLException {
		if (idValue instanceof Date) {
			if (idValue instanceof Timestamp) {
				return getFieldId().set(entity, rs.getTimestamp(1));
			} else if (idValue instanceof Time) {
				return getFieldId().set(entity, rs.getTime(1));
			} else {
				return getFieldId().set(entity, rs.getDate(1));
			}
		}
		if (idValue instanceof LocalDate) {
			return getFieldId().set(entity, rs.getDate(1).toLocalDate());
		}
		if (idValue instanceof LocalDateTime) {
			return getFieldId().set(entity, rs.getTimestamp(1).toLocalDateTime());
		}
		if (idValue instanceof LocalTime) {
			return getFieldId().set(entity, rs.getTime(1).toLocalTime());
		}
	}

	private void getCharValue(Object idValue, ResultSet rs) throws SQLException, IllegalAccessException {
		if (idValue instanceof Character) {
			String value = rs.getString(1);
			if (value != null)
				return getFieldId().set(entity, value.charAt(0));
		}
	}

	private void getNumberValue(Object idValue, ResultSet rs) throws IllegalAccessException, SQLException {
		if (idValue instanceof Number) {
			if (idValue instanceof Integer) {
				return getFieldId().set(entity, rs.getInt(1));
			} else if (idValue instanceof Short) {
				return getFieldId().set(entity, rs.getShort(1));
			} else if (idValue instanceof Long) {
				return getFieldId().set(entity, rs.getLong(1));
			} else if (idValue instanceof BigDecimal) {
				return getFieldId().set(entity, rs.getBigDecimal(1));
			} else if (idValue instanceof Double) {
				return getFieldId().set(entity, rs.getDouble(1));
			} else if (idValue instanceof Float) {
				return getFieldId().set(entity, rs.getFloat(1));
			} else if (idValue instanceof Byte) {
				return getFieldId().set(entity, rs.getByte(1));
			} else if (idValue instanceof BigInteger) {
				return getFieldId().set(entity, BigInteger.valueOf(rs.getLong(1)));
			} else {
				return getFieldId().set(entity, rs.getString(1));
			}
		}
	}else

	{
		update();
	}

	return entity;
	}

	private void getStringValue(Object idValue, ResultSet rs) throws IllegalAccessException, SQLException {
		if (idValue instanceof String) {
			return getFieldId().set(entity, rs.getString(1));
		}
	}

	private void setValue(PreparedStatement ps, int i, Object value) throws SQLException {
		setStringValue(ps, i, value);
		setNumberValue(ps, i, value);
		setCharValue(ps, i, value);
		setDateTimeValue(ps, i, value);
	}

	private Object getIdvalue() throws IllegalAccessException {
		Field id = getFieldId();
		id.setAccessible(true);
		Object idValue = id.get(entity);
		return idValue;
	}

	private Field getFieldId() {
		Field id = null;
		for (Field field : this.entity.getClass().getFields()) {
			if (field.isAnnotationPresent(Id.class)) {
				id = field;
			}
		}
		return id;
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
