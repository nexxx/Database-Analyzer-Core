package utils;

import java.util.ArrayList;

/**
 * Provides common functions for all Classes
 * 
 * @author Sebastian Theuermann
 */
public class Utilities {

  private static Utilities instance = new Utilities();

  // Avoid initialization with "new Utils();"
  private Utilities() {
  }

  // Threadsafety first!
  public static synchronized Utilities getInstance() {
	return instance;
  }

  /**
   * Tries to parse a String to an Integer
   * 
   * @param str
   *          the String to be parsed to an Integer
   * @return a Integer when successful, null when not (e.g. no number)
   */
  public static Integer tryParseInt(String str) {
	Integer n = null;

	try {
	  return new Integer(str);

	} catch (NumberFormatException nfe) {
	  return n;
	}
  }

  /**
   * Returns a String containing all elements of the list, separated
   * by ","
   * 
   * @param arr
   *          the ArrayList to work with
   * @return a String with the names of all elements
   */
  public static String getStringFromArrayList(ArrayList<?> arr) {
	String result = "";
	for (int i = 0; i < arr.size(); i++) {
	  if (i == 0) {
		result = result + arr.get(i).toString();
	  } else {
		result = result + "," + arr.get(i).toString();
	  }
	}

	return result;
  }
}
