package com.viseo.companion.exception;

/**
 * @author H&I
 *
 */
public class CompanionException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private CompanionExceptionCode codeException;

	public CompanionException() {
		super();
	}

	public CompanionException(final String message) {
		super(message);

	}

	public CompanionException(final String message, final Throwable cause) {
		super(message, cause);

	}

	public CompanionException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public CompanionException(final CompanionExceptionCode code, final String message, final Throwable cause) {
		super(message, cause);
		this.codeException = code;
	}

	public CompanionException(final CompanionExceptionCode code, final String message) {
		super(message);
		this.codeException = code;
	}

	public CompanionException(final CompanionExceptionCode code, final Throwable cause) {
		super(cause);
		this.codeException = code;
	}

	public CompanionException(final Throwable cause) {
		super(cause);

	}

	public CompanionExceptionCode getCodeException() {
		return this.codeException;
	}

	public void setCodeException(final CompanionExceptionCode codeException) {
		this.codeException = codeException;
	}

}
