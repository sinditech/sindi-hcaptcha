/**
 * 
 */
package za.co.sindi.com.hcaptcha;

/**
 * @author Buhake Sindi
 * @since 15 January 2024
 */
public enum ErrorCode {

	MISSING_INPUT_SECRET("missing-input-secret", "Your secret is missing.")
	,INVALID_INPUT_SECRET("invalid-input-secret", "Your secret key is invalid or malformed.")
	,MISSING_INPUT_RESPONSE("missing-input-response", "The response parameter (verification token) is missing.")
	,INVALID_INPUT_RESPONSE("invalid-input-response", "The response parameter (verification token) is invalid or malformed.")
	,BAD_REQUEST("bad-request", "The request is invalid or malformed.")
	,INVALID_OR_ALREADY_SEEN_RESPONSE("invalid-or-already-seen-response", "The response parameter has already been checked, or has another issue.")
	,NOT_USING_DUMMY_PASSCODE("not-using-dummy-passcode", "You have used a testing sitekey but have not used its matching secret.")
	,SITEKEY_SECRET_MISMATCH("sitekey-secret-mismatch", "The sitekey is not registered with the provided secret.")
	;
	private final String code;
	private final String description;
	
	/**
	 * @param code
	 * @param description
	 */
	private ErrorCode(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	public static ErrorCode of(final String code) {
		for (ErrorCode errorCode : values()) {
			if (errorCode.code.equals(code)) {
				return errorCode;
			}
		}
		
		throw new IllegalArgumentException(String.format("Unknown hCaptcha Error code reference '%s'.", code));
	}
}
