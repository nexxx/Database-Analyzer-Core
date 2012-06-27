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

package data.dBTypes;

/**
 * Singelton class with all available Attribute for a MYSQL Database
 * 
 * @author Andreas Freitag
 * 
 */
public class mySql {
  private final String[] types = { "---", "CHAR", "VARCHAR", "TINYTEXT",
	  "TEXT", "BLOB", "MEDIUMTEXT", "MEDIUMBLOB", "LONGTEXT", "LONGBLOB",
	  "TINYINT", "SMALLINT", "MEDIUMINT", "INT", "BIGINT", "FLOAT", "DOUBLE",
	  "DECIMAL", "DATE", "DATETIME", "TIMESTAMP", "TIME", "ENUM", "SET" };
  private static mySql instance = null;

  public String[] getTypes() {
	return types;
  }

  /**
   * Getter for the singelton mySql (thread-save)
   * */
  public synchronized static mySql getInstance() {
	if (instance == null) {
	  synchronized (mySql.class) {
		instance = new mySql();
	  }
	}
	return instance;
  }
}
