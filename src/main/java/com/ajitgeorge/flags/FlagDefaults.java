package com.ajitgeorge.flags;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FlagDefaults {
	private final String packagePrefix;
	private Map<Field, Object> savedValues;

	public FlagDefaults(String packagePrefix) {
		this.packagePrefix = packagePrefix;
	}

	public FlagDefaults save() {
		savedValues = collectValues(packagePrefix);
		return this;
	}

	public void restore() {
		for (Map.Entry<Field, Object> entry : savedValues.entrySet()) {
			Field field = entry.getKey();
			Object value = entry.getValue();
			setValue(field, value);
		}
	}

	private static Map<Field, Object> collectValues(String packagePrefix) {
		HashMap<Field, Object> values = new HashMap<Field, Object>();

		Reflections reflections = new Reflections(packagePrefix, new FieldAnnotationsScanner());
		Set<Field> flaggedFields = reflections.getFieldsAnnotatedWith(Flag.class);
		for (Field flaggedField : flaggedFields) {
			values.put(flaggedField, value(flaggedField));
		}

		return values;
	}

	private static Object value(Field flaggedField) {
		try {
			return flaggedField.get(null);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("unable to get field value", e);
		}
	}

	private static void setValue(Field field, Object value) {
		try {
			field.set(null, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("unable to set value", e);
		}
	}
}
