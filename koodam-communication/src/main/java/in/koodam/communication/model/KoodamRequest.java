package in.koodam.communication.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import in.koodam.communication.component.KoodamMessageType;

public class KoodamRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private KoodamMessageType messageType;

	private List<?> contentList;

	/** The session id. */
	private String sessionId;

	/** The set id. */
	private int requestId = 0;

	/** The time sent. */
	private long timeSent = 0;

	/** Channel that initiated this request */
	private KoodamChannelInfo channel;
	
	public KoodamRequest(){
		this(null, null, StringUtils.EMPTY, 0, 0, new KoodamChannelInfo());
	}

	public KoodamRequest(KoodamMessageType messageType, List<?> contentList, String sessionId, int requestId,
			long timeSent, KoodamChannelInfo channel) {
		this.messageType = messageType;
		this.contentList = contentList;
		this.sessionId = sessionId;
		this.requestId = requestId;
		this.timeSent = timeSent;
		this.channel = channel;
	}

	public KoodamMessageType getMessageType() {
		return messageType;
	}

	public List<?> getContentList() {
		return contentList;
	}

	public String getSessionId() {
		return sessionId;
	}

	public int getRequestId() {
		return requestId;
	}

	public long getTimeSent() {
		return timeSent;
	}

	public KoodamChannelInfo getChannel() {
		return channel;
	}

	public static final class Builder {

		private KoodamMessageType messageType;

		private List<?> contentList;

		private String sessionId;

		/** The set id. */
		private int requestId = 0;

		/** The time sent. */
		private long timeSent = 0;

		private KoodamChannelInfo channel;

		public Builder(KoodamMessageType messageType) {
			this.messageType = messageType;
		}

		public Builder contentList(List<?> contentList) {
			this.contentList = contentList;
			return this;
		}

		public Builder sessionId(String sessionId) {
			this.sessionId = sessionId;
			return this;
		}

		public Builder requestId(int requestId) {
			this.requestId = requestId;
			return this;
		}

		public Builder timeSent(long timeSent) {
			this.timeSent = timeSent;
			return this;
		}

		public Builder channel(KoodamChannelInfo channel) {
			this.channel = channel;
			return this;
		}

		public KoodamRequest build() {
			return new KoodamRequest(messageType, contentList, sessionId, requestId, timeSent, channel);
		}

	}

}
