package gui;

import java.util.EventObject;

public class FormEvent extends EventObject{
	private static final long serialVersionUID = 1L;
	private int eventType;
	//eventType = 0 --> zip files 
	//eventType = 1 --> unzip file
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
