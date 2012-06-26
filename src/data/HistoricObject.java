package data;

import javax.xml.bind.annotation.XmlTransient;

import data.events.ChangeListener;
import data.events.ChangeSupport;

/**
 * Base-Class for all Elements of a History
 * 
 * @author Sebastian Theuermann
 * 
 */
public abstract class HistoricObject {
  private boolean isDirty = false;
  protected ChangeListener changeListener;
  protected ChangeSupport changeSupport;

  public HistoricObject() {
	changeSupport = new ChangeSupport();
  }

  @XmlTransient
  public boolean isDirty() {
	return isDirty;
  }

  public void setDirty(boolean dirty) {
	isDirty = dirty;
  }

  public abstract Object getClone();

  // Change-Support
  public void addChangeListener(ChangeListener listener) {
	changeSupport.addChangeListener(listener);
  }

  public void removeChangeListener(ChangeListener listener) {
	changeSupport.removeChangeListener(listener);
  }
}
