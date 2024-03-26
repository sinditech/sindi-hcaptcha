/**
 * 
 */
package za.co.sindi.com.hcaptcha;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Buhake Sindi
 * @since 15 January 2024
 */
public class SiteVerifyResponse implements Serializable {

	private boolean success;
	private Date challengeTimestamp;
	private String hostname;
	private Boolean credit;
	private ErrorCode[] errorCodes;
	private Double score;
	private String[] scoreReason;
	
	/**
	 * @param success
	 * @param challengeTimestamp
	 * @param hostname
	 * @param credit
	 * @param errorCodes
	 * @param score
	 * @param scoreReason
	 */
	public SiteVerifyResponse(boolean success, Date challengeTimestamp, String hostname, Boolean credit,
			ErrorCode[] errorCodes, Double score, String[] scoreReason) {
		super();
		this.success = success;
		this.challengeTimestamp = challengeTimestamp;
		this.hostname = hostname;
		this.credit = credit;
		this.errorCodes = errorCodes;
		this.score = score;
		this.scoreReason = scoreReason;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @return the challengeTimestamp
	 */
	public Date getChallengeTimestamp() {
		return challengeTimestamp;
	}

	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * @return the credit
	 */
	public Boolean getCredit() {
		return credit;
	}

	/**
	 * @return the errorCodes
	 */
	public ErrorCode[] getErrorCodes() {
		return errorCodes;
	}

	/**
	 * @return the score
	 */
	public Double getScore() {
		return score;
	}

	/**
	 * @return the scoreReason
	 */
	public String[] getScoreReason() {
		return scoreReason;
	}
}
