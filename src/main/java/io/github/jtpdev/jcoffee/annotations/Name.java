package io.github.jtpdev.jcoffee.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Jimmy Porto
 *
 */
@Retention(RetentionPolicy.CLASS)
public @interface Name {
	
	String value();

}
