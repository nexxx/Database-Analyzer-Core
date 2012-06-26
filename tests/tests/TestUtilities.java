
import data.Attribute;
import org.junit.Test;
import utils.Utilities;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestUtilities {

  @Test
  public void testTryParse() {
	Integer test;

	assertEquals(null, Utilities.tryParseInt("noNumber"));

	test = 666;
	assertEquals(test, Utilities.tryParseInt("666"));

	assertEquals(null, Utilities.tryParseInt(""));

	assertEquals(null, Utilities.tryParseInt(null));

	test = 12;
	assertEquals(test, Utilities.tryParseInt("12"));

	assertEquals(null, Utilities.tryParseInt("2 x 2"));

  }

  @Test
  public void testGetStringFromArrayList() {
	// String
	ArrayList<String> testList = new ArrayList<String>();
	testList.add("A");
	testList.add("B");
	testList.add("C");

	assertEquals("A,B,C", Utilities.getStringFromArrayList(testList));

	// Integer
	ArrayList<Integer> intList = new ArrayList<Integer>();
	intList.add(1);
	intList.add(2);
	intList.add(3);

	assertEquals("1,2,3", Utilities.getStringFromArrayList(intList));

	// Attribute
	ArrayList<Attribute> attrList = new ArrayList<Attribute>();
	attrList.add(new Attribute("one"));
	attrList.add(new Attribute("two"));
	attrList.add(new Attribute("three"));

	assertEquals("one,two,three", Utilities.getStringFromArrayList(attrList));

  }

}
