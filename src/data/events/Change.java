package data.events;

import java.util.EventObject;

/**
 * Event signaling imminent change or already happened change
 * 
 * @author Sebastian Theuermann
 * 
 */
public class Change extends EventObject {

  /**
	 * 
	 */
  private static final long serialVersionUID = -8846307959662456247L;
  private final Time time;

  public Change(Object source, Time time) {
	super(source);
	this.time = time;
  }

  public Time getTime() {
	return time;
  }

}
