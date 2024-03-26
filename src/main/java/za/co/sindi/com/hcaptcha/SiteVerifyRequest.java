/**
 * 
 */
package za.co.sindi.com.hcaptcha;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;

/**
 * @author Buhake Sindi
 * @since 15 January 2024
 */
public class SiteVerifyRequest {

	private String siteVerifyUrl;
	
	/**
	 * 
	 */
	public SiteVerifyRequest() {
		this(HCaptchaConstants.HCAPTCHA_SITEVERIFY_URL);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param siteVerifyUrl
	 */
	public SiteVerifyRequest(String siteVerifyUrl) {
		super();
		this.siteVerifyUrl = siteVerifyUrl;
	}

	public SiteVerifyResponse siteVerify(final String secret, final String response) throws URISyntaxException, IOException, InterruptedException, ParseException {
		return siteVerify(secret, response, null, null);
	}
	
	public SiteVerifyResponse siteVerify(final String secret, final String response, final String remoteIP, final String sitekey) throws URISyntaxException, IOException, InterruptedException, ParseException {
		if (secret == null || secret.isEmpty()) {
			throw new IllegalArgumentException("No shared key was provided.");
		}
		
		if (response == null || response.isEmpty()) {
			throw new IllegalArgumentException("No user response token was provided.");
		}
		
		Map<String, String> formData = new LinkedHashMap<>();
		formData.put("secret", secret);
		formData.put("response", response);
		if (remoteIP != null && !remoteIP.isEmpty()) {
			formData.put("remoteip", remoteIP);
		}
		if (sitekey != null && !sitekey.isEmpty()) {
			formData.put("sitekey", sitekey);
		}
		
		HttpRequest httpRequest = HttpRequest.newBuilder()
				 .uri(new URI(siteVerifyUrl))
				 .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(formData)))
				 .build();
		HttpResponse<InputStream> httpResponse = HttpClient.newBuilder()
						  .build()
						  .send(httpRequest, BodyHandlers.ofInputStream());
		JsonReader jsonReader = Json.createReader(httpResponse.body());
		JsonObject reCAPTCHAResponseJson = jsonReader.readObject();
		return new SiteVerifyResponse(reCAPTCHAResponseJson.getBoolean(HCaptchaConstants.HCAPTCHA_JSON_RESPONSE_SUCCESS_KEY), 
				convertToDate(reCAPTCHAResponseJson.getString(HCaptchaConstants.HCAPTCHA_JSON_RESPONSE_CHALLENGE_TS_KEY), "yyyy-MM-dd'T'HH:mm:ssZZ"), 
				reCAPTCHAResponseJson.containsKey(HCaptchaConstants.HCAPTCHA_JSON_RESPONSE_HOSTNAME_KEY) ? reCAPTCHAResponseJson.getString(HCaptchaConstants.HCAPTCHA_JSON_RESPONSE_HOSTNAME_KEY) : null, 
				reCAPTCHAResponseJson.containsKey(HCaptchaConstants.HCAPTCHA_JSON_RESPONSE_CREDIT_KEY) ? reCAPTCHAResponseJson.getBoolean(HCaptchaConstants.HCAPTCHA_JSON_RESPONSE_CREDIT_KEY) : null,
				reCAPTCHAResponseJson.containsKey(HCaptchaConstants.HCAPTCHA_JSON_RESPONSE_ERROR_CODES_KEY) ? (ErrorCode[])reCAPTCHAResponseJson.getJsonArray(HCaptchaConstants.HCAPTCHA_JSON_RESPONSE_ERROR_CODES_KEY).getValuesAs(v -> {
					JsonString js = (JsonString)v;
					return ErrorCode.of(js.getString());
				}).toArray() : null,
				reCAPTCHAResponseJson.containsKey(HCaptchaConstants.HCAPTCHA_JSON_RESPONSE_SCORE_KEY) ? reCAPTCHAResponseJson.getJsonNumber(HCaptchaConstants.HCAPTCHA_JSON_RESPONSE_SCORE_KEY).doubleValue() : null,
				reCAPTCHAResponseJson.containsKey(HCaptchaConstants.HCAPTCHA_JSON_RESPONSE_SCORE_REASON_KEY) ? reCAPTCHAResponseJson.getJsonArray(HCaptchaConstants.HCAPTCHA_JSON_RESPONSE_SCORE_REASON_KEY).getValuesAs(JsonString::getString).stream().toArray(String[]::new) : null); 
				
	}
	
	private String getFormDataAsString(Map<String, String> formData) {
	    StringBuilder formBodyBuilder = new StringBuilder();
	    for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
	        if (formBodyBuilder.length() > 0) {
	            formBodyBuilder.append("&");
	        }
	        formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
	        formBodyBuilder.append("=");
	        formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
	    }
	    return formBodyBuilder.toString();
	}
	
	private Date convertToDate(final String date, final String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		return sdf.parse(date);
	}
}
