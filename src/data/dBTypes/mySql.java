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
