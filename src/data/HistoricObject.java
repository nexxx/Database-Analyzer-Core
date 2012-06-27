/******************************************************************************
 * Copyright: GPL v3                                                          *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by       *
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * This program is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU General Public License for more details.                               *
 *                                                                            *
 * You should have received a copy of the GNU General Public License          *
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.      *
 ******************************************************************************/

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
