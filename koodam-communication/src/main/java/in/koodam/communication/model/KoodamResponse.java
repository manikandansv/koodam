package in.koodam.communication.model;

import java.io.Serializable;
import java.util.List;

import in.koodam.communication.component.KoodamMessageType;

public class KoodamResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private KoodamMessageType messageType;

	/** The content. */
	private Object content;

	/** Whether response represents exception (not successful) */
	private boolean exceptionResponse = false;

	/** The response session IDs. */
	private List<String> responseSessionIds = null;

	/** The client side duration. */
	private long clientSideDuration;

	/** The service execution duration. */
	private long serviceExecutionDuration;

	public KoodamResponse(){
		this(null, null, false, null, 0, 0);
	}

	public KoodamResponse(KoodamMessageType messageType, Object content, boolean exceptionResponse,  List<String> responseSessionIds,
			long clientSideDuration, long serviceExecutionDuration) {
		this.messageType = messageType;
		this.content = content;
		this.exceptionResponse = exceptionResponse;
		this.responseSessionIds = responseSessionIds;
		this.clientSideDuration = clientSideDuration;
		this.serviceExecutionDuration = serviceExecutionDuration;
	}
	
	public KoodamMessageType getMessageType() {
		return messageType;
	}

	public Object getContent() {
		return content;
	}

	public boolean isExceptionResponse() {
		return exceptionResponse;
	}

	public List<String> getResponseSessionIds() {
		return responseSessionIds;
	}

	public long getClientSideDuration() {
		return clientSideDuration;
	}

	public long getServiceExecutionDuration() {
		return serviceExecutionDuration;
	}
	
	public static final class Builder {

		private KoodamMessageType messageType;

		/** The content. */
		private Object content;

		/** Whether response represents exception (not successful) */
		private boolean exceptionResponse = false;

		/** The response session IDs. */
		private List<String> responseSessionIds = null;

		/** The client side duration. */
		private long clientSideDuration;

		/** The service execution duration. */
		private long serviceExecutionDuration;

		public Builder(KoodamMessageType messageType) {
			this.messageType = messageType;
		}

		public Builder content(Object content) {
			this.content = content;
			return this;
		}

		public Builder responseSessionIds(List<String> responseSessionIds) {
			this.responseSessionIds = responseSessionIds;
			return this;
		}

		public Builder clientSideDuration(long clientSideDuration) {
			this.clientSideDuration = clientSideDuration;
			return this;
		}

		public Builder serviceExecutionDuration(long serviceExecutionDuration) {
			this.serviceExecutionDuration = serviceExecutionDuration;
			return this;
		}

		public KoodamResponse build() {
			return new KoodamResponse(messageType, content, exceptionResponse, responseSessionIds, clientSideDuration, serviceExecutionDuration);
		}

	}

}
