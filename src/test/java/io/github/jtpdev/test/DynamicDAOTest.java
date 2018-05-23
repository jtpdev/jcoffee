package io.github.jtpdev.test;

import io.github.jtpdev.jcoffee.DynamicDAO;

/**
 * @author Jimmy Porto
 *
 */
public class DynamicDAOTest {
	
	public static void main(String[] args) throws Exception {
		EntityTest entity = new EntityTest();
		DynamicDAO<EntityTest> dao = new DynamicDAO<EntityTest>(null, entity);
		dao.save();
	}

}
