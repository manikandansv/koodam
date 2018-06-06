package in.koodam.communication.enums;

import java.util.HashMap;
import java.util.Map;

public enum MessageType {

	REQUEST ("REQ"),
	
	RESPONSE ("RESP");
	
	private final String id;

	private static Map<String, MessageType> values;

	private String reservationStatus;

	private MessageType(String id) {
		this.id = id;
		registerId(this);
	}

	private static synchronized void registerId(MessageType value) {
		if (values == null) {
			values = new HashMap<String, MessageType>();
		}
		values.put(value.getId(), value);
	}

	public String getId() {
		return id;
	}

	public String getMessageType() {
		return reservationStatus;
	}

	public static MessageType getValueForId(String id) {
		return values.get(id);
	}
	
}
