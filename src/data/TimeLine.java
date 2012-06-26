package data;

import data.events.Change;
import data.events.ChangeListener;
import data.events.Time;

/**
 * Class representing the Undo/Redo History
 * 
 * @author Sebastian Theuermann
 */
public final class TimeLine extends HistoricObject {
  private static TimeLine instance;
  private static History history;

  private TimeLine() {
	super();
	history = new History();

	super.changeListener = new ChangeListener() {
	  @Override
	  public void Change(Change change) {
		changeSupport.fireChange(change.getTime());
	  }
	};
  }

  /**
   * Notifies Observers about change
   */
  public void notifyAboutChange() {
	changeSupport.fireChange(Time.AFTERCHANGE);
  }

  /**
   * Getter for the singleton options (thread-save)
   * */
  public synchronized static TimeLine getInstance() {
	if (instance == null) {
	  synchronized (TimeLine.class) {
		instance = new TimeLine();
	  }
	}
	return instance;
  }

  /**
   * Resets the History and makes the given Element the first in the
   * History
   * 
   * @param currentElement
   *          the first Element of the new History
   */
  public void initialize(HistoricObject currentElement) {
	history.removeChangeListener(super.changeListener);
	history.initialize(currentElement);
	history.addChangeListener(super.changeListener);
	notifyAboutChange();
  }

  /**
   * Returns if the TimeLine is currently suspended
   * 
   * @return true for suspended, false if not
   */
  public boolean isSuspended() {
	return history.isSuspended();
  }

  /**
   * Sets if the TimeLine fires the ChangedEvent
   * 
   * @param suspended
   *          true ==> fire event, false ==> cease fire
   */
  public void setSuspended(boolean suspended) {
	history.setSuspended(suspended);
  }

  /**
   * Gets the element that is currently selected
   * 
   * @return the current element
   */
  public HistoricObject getCurrentElement() {
	return history.getCurrentElement();
  }

  /**
   * Move forward in the history
   * 
   * @return true for success, false if not possible
   */
  public boolean travelForward() {
	return history.travelForward();
  }

  /**
   * Move backward in the history
   * 
   * @return true for success, false if not possible
   */
  public boolean travelBackward() {
	return history.travelBackward();
  }

  /**
   * Returns if it is possible to move forward
   * 
   * @return true ==> possible, false ==> mission impossible
   */
  public boolean getForwardPossible() {
	return history.getForwardPossible();
  }

  /**
   * Returns if it is possible to move backward
   * 
   * @return true ==> possible, false ==> mission impossible
   */
  public boolean getBackwardPossible() {
	return history.getBackwardPossible();
  }

  /**
   * Adds a completely new Object to History (e.g. for load)
   * 
   * @param newObject
   *          the new Element to be added
   */
  public void addHistoricObject(HistoricObject newObject) {
	history.addHistoricObject(newObject);
  }

  @Override
  public Object getClone() {
	return null;
  }

}
