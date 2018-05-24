package io.github.jtpdev.jcoffee.interfaces;

import java.io.Serializable;

/**
 * @author Jimmy Porto
 *
 * @param <T>
 */
public interface JCoffeeEntity<T> extends Serializable {
	
	T getId();

}
