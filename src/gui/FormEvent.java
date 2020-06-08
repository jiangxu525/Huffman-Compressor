package gui;

import java.util.EventObject;

/**
 * 
 * @ClassName: FormEvent
 * @Description: Form Event, eventType = 0 --> zip files , eventType = 1 --> unzip file
 * @author Xu
 * @date 2020-06-08 10:23:53
 */
public class FormEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private int eventType;

	public FormEvent(Object source) {
		super(source);
	}

	public FormEvent(Object source, int eventType) {
		super(source);
		this.eventType = eventType;
	}

	public int getEventType() {
		return eventType;
	}

}
