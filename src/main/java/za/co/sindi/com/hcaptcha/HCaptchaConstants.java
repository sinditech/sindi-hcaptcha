/**
 * 
 */
package za.co.sindi.com.hcaptcha;

/**
 * @author Buhake Sindi
 * @since 15 January 2024
 */
public final class HCaptchaConstants {

	public static final String HCAPTCHA_SITEVERIFY_URL = " https://api.hcaptcha.com/siteverify";
	
	//Client-side response token
	public static final String HCAPTCHA_CLIENT_RESPONSE = "h-captcha-response";
	
	// **** JSON response keys ****
	public static final String HCAPTCHA_JSON_RESPONSE_SUCCESS_KEY = "success";
	public static final String HCAPTCHA_JSON_RESPONSE_CHALLENGE_TS_KEY = "challenge_ts";
	public static final String HCAPTCHA_JSON_RESPONSE_HOSTNAME_KEY = "hostname";
	public static final String HCAPTCHA_JSON_RESPONSE_CREDIT_KEY = "credit";
	public static final String HCAPTCHA_JSON_RESPONSE_ERROR_CODES_KEY = "error_codes";
	public static final String HCAPTCHA_JSON_RESPONSE_SCORE_KEY = "score";
	public static final String HCAPTCHA_JSON_RESPONSE_SCORE_REASON_KEY = "score_reason";
	
	private HCaptchaConstants() {
		throw new AssertionError("Private Constructor.");
	}
}
