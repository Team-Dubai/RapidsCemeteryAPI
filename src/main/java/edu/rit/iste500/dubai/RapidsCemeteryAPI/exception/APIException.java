package edu.rit.iste500.dubai.RapidsCemeteryAPI.exception;

import java.util.HashMap;
import java.util.Map;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.enums.ExceptionEnum;

public class APIException extends Exception {

	private static final long serialVersionUID = -6118882796516106194L;

	private Map<Integer, Exception> exceptions = new HashMap<>();

	public APIException(ExceptionEnum exceptionType) {
		super();
		exceptions.put(exceptionType.getCode(), null);
	}

	public APIException(Map<Integer, Exception> exceptions) {
		this.exceptions = exceptions;
	}

	public APIException(ExceptionEnum exceptionType, Exception exception) {
		super();
		exceptions.put(exceptionType.getCode(), exception);
	}

	public void addException(ExceptionEnum exceptionType, Exception exception) {
		exceptions.put(exceptionType.getCode(), exception);
	}

	public Map<Integer, Exception> getExceptions() {
		return exceptions;
	}

}
