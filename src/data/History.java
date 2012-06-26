package data;

import java.util.ArrayList;

import data.events.Change;
import data.events.ChangeListener;
import data.events.Time;

/**
 * Class representing a Undo/Redo History
 * 
 * @author Sebastian Theuermann
 */
public final class History extends HistoricObject {
  private ArrayList<HistoricObject> history;
  private HistoricObject currentElement;
  private boolean suspended = false;

  public History() {
	super();
	history = new ArrayList<>();
  }

  public History(HistoricObject historicElement) {
	this();
	initialize(historicElement);
  }

  /**
   * (Re-)Initializes the History
   * 
   * @param currentElement
   *          the Database that is current in use
   */
  public void initialize(HistoricObject currentElement) {
	history.clear();
	this.currentElement = currentElement;
	// disconnect redundant listener
	disconnectChangeListener();

	history.add(currentElement);

	super.changeListener = new ChangeListener() {
	  @Override
	  public void Change(Change change) {
		if (!suspended) {

		  switch (change.getTime()) {
		  case BEFORECHANGE:
			purgeFuture();
			makeHistory();
			setCurrentElementDirty(true);
			notifyOfCurrentElementChange();
			break;

		  case AFTERCHANGE:
			changeSupport.fireChange(change.getTime());
			break;
		  }

		}
	  }
	};

	connectChangeListener();
  }

  /**
   * Returns if the TimeLine is currently suspended
   * 
   * @return true for suspended, false if not
   */
  public boolean isSuspended() {
	return suspended;
  }

  /**
   * Sets if the TimeLine fires the ChangedEvent
   * 
   * @param suspended
   *          true ==> fire event, false ==> cease fire
   */
  public void setSuspended(boolean suspended) {
	this.suspended = suspended;
  }

  private void setCurrentElementDirty(boolean dirty) {
	currentElement.setDirty(dirty);
  }

  /**
   * Moves forward in history
   */
  public boolean travelForward() {
	boolean success = false;
	disconnectChangeListener();

	if (getForwardPossible()) {
	  currentElement = history.get(history.indexOf(currentElement) + 1);
	  success = true;
	}

	connectChangeListener();
	notifyOfCurrentElementChange();
	return success;
  }

  /**
   * Moves backward in history
   */
  public boolean travelBackward() {
	boolean success = false;
	disconnectChangeListener();

	if (getBackwardPossible()) {
	  currentElement = history.get(history.indexOf(currentElement) - 1);
	  success = true;
	}

	connectChangeListener();
	notifyOfCurrentElementChange();
	return success;
  }

  /**
   * Adds a PropertyChangedListener to the currentDatabase
   */
  private void connectChangeListener() {
	currentElement.addChangeListener(changeListener);
  }

  /**
   * Removes the PropertyChangedListener from the currentDatabase
   */
  private void disconnectChangeListener() {
	currentElement.removeChangeListener(changeListener);
  }

  /**
   * Removes all future-Versions of the currentDatabase
   */
  private void purgeFuture() {
	int futureDbCount = history.size() - 1 - history.indexOf(currentElement);
	for (int i = 0; i < futureDbCount; i++) {
	  history.remove(history.size() - 1);
	}
  }

  /**
   * Adds the current Database to the history
   * 
   */
  private void makeHistory() {
	makeHistory((HistoricObject) currentElement.getClone());
  }

  /**
   * Adds a Database to the history
   * 
   * @param oldElement
   *          the old element to add to the history
   */
  private void makeHistory(HistoricObject oldElement) {
	history.set(history.indexOf(currentElement), oldElement);
	history.add(currentElement);
  }

  /**
   * Returns if you can navigate backward in history
   * 
   * @return true if backward is possible, false if not
   */
  public boolean getBackwardPossible() {
	return history.size() > 0 && history.indexOf(currentElement) != 0;
  }

  /**
   * Returns if you can navigate forward in history
   * 
   * @return true ==> forward possible, false ==> forward impossible
   */
  public boolean getForwardPossible() {
	return history.size() > 0
	    && history.indexOf(currentElement) < history.size() - 1;
  }

  /**
   * Adds a Element at the current index and erases all elements past
   * that index
   * 
   * @param newObject
   *          the element to add to the History
   */
  public void addHistoricObject(HistoricObject newObject) {
	purgeFuture();
	makeHistory((HistoricObject) currentElement.getClone());

	// Replace currentElement with new one
	history.remove(currentElement);
	currentElement = newObject;
	history.add(currentElement);
	connectChangeListener();

	notifyOfCurrentElementChange();
  }

  /**
   * Returns the Database that is currently active
   * 
   * @return the current Element
   */
  public HistoricObject getCurrentElement() {
	return currentElement;
  }

  /**
   * Returns the whole History
   * 
   * @return the whole History
   */
  public ArrayList<HistoricObject> getHistory() {
	return history;
  }

  private void notifyOfCurrentElementChange() {
	// Notify listeners that currentDatabase has changed
	changeSupport.fireChange(Time.AFTERCHANGE);
  }

  @Override
  public Object getClone() {
	return null;
  }
}
