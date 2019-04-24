package edu.rit.iste500.dubai.RapidsCemeteryAPI.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExceptionEnum {

	// Controller validations
	EXCEPTION(0, "exceptionOccurred"),
	BAD_REQUEST(10, "badRequest"),
	NO_RESPONSE(30, "noResponse"),
	NO_TOKEN(40, "noToken"),
	NO_DATA(80, "no_data"),
	PASSWORD_NOT_ENCODED(100, "passwordNotEncoded"),
	NO_EMAIL(130, "noEmail"),
	BAD_CURRENT_PASSWORD(150, "badCurrentPassword"),
	INSUFFICIENT_PRIVILEGES(160, "insufficientPrivileges"),
	NO_EMAIL_BIND_PERSON(170, "noEmailRequestedByPerson"),
	PASSWORD_NOT_STRONG(180, "passwordNotStrongEnough"),
	USER_NOT_FOUND(190, "userNotFound");

	private Integer code;
	private String message;

	private ExceptionEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	@JsonCreator
	public static ExceptionEnum getEnumFromCode(Integer code) {
		ExceptionEnum returnEnum = null;
		for (ExceptionEnum exceptionEnum : ExceptionEnum.values()) {
			if (exceptionEnum.getCode().equals(code)) {
				returnEnum = exceptionEnum;
			}
		}
		return returnEnum;
	}

	@JsonValue
	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return this.message;
	}

}
