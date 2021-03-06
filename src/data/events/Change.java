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
