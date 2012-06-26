package data;

import java.util.ArrayList;

import utils.Utilities;

/**
 * List of Attributes representing a Key
 * 
 * @author Sebastian Theuermann
 */
public class Key {
  private ArrayList<Attribute> attributes;

  public Key() {
	attributes = new ArrayList<>();
  }

  public Key(ArrayList<Attribute> attributes) {
	this.attributes = attributes;
  }

  public ArrayList<Attribute> getAttributes() {
	return attributes;
  }

  @Override
  public String toString() {
	return "(" + Utilities.getStringFromArrayList(getAttributes()) + ")";
  }
}
