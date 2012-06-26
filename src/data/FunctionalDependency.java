package data;

import utils.Utilities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class representing a Functional dependency
 * 
 * @author Sebastian Theuermann
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "fd")
public class FunctionalDependency implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 166324774376697454L;
  // source (determining set of attributes) ==> target (dependent set
  // of attributes)
  // X(source) ==> Y(target)
  private ArrayList<Attribute> sourceAttributes;
  private ArrayList<Attribute> targetAttributes;

  public FunctionalDependency() {
	sourceAttributes = new ArrayList<>();
	targetAttributes = new ArrayList<>();
  }

  public FunctionalDependency(ArrayList<Attribute> sourceAttributes,
	  ArrayList<Attribute> targetAttributes) {
	this();
	this.sourceAttributes = sourceAttributes;
	this.targetAttributes = targetAttributes;
  }

  public ArrayList<Attribute> getSourceAttributes() {
	return sourceAttributes;
  }

  public void setSourceAttributes(ArrayList<Attribute> sourceAttributes) {
	this.sourceAttributes = sourceAttributes;
  }

  public void setTargetAttributes(ArrayList<Attribute> targetAttributes) {
	this.targetAttributes = targetAttributes;
  }

  public ArrayList<Attribute> getTargetAttributes() {
	return targetAttributes;
  }

  @Override
  public String toString() {
	return "[" + getStringOfAttributes(sourceAttributes) + "] -> ["
	    + getStringOfAttributes(targetAttributes) +"]";
  }

  /**
   * Returns a String containing the Names of the given Attributes
   * 
   * @param attributes
   *          the attributes to work with
   * @return a String with all Attributenames and delimiters
   */
  private String getStringOfAttributes(ArrayList<Attribute> attributes) {
	return Utilities.getStringFromArrayList(attributes);
  }

  @Override
  public boolean equals(Object obj) {
	if (obj instanceof FunctionalDependency) {
	  FunctionalDependency fd = (FunctionalDependency) obj;
	  if (this.getSourceAttributes().equals(fd.getSourceAttributes())) {
		if (this.getTargetAttributes().equals(fd.getTargetAttributes())) {
		  return true;
		}
	  }
	}
	return false;
  }

  public FunctionalDependency getClone() {
	FunctionalDependency clone = new FunctionalDependency();

	for (Attribute sourceAttribute : getSourceAttributes()) {
	  clone.getSourceAttributes().add((Attribute) sourceAttribute.getClone());
	}

	for (Attribute targetAttribute : getTargetAttributes()) {
	  clone.getTargetAttributes().add((Attribute) targetAttribute.getClone());
	}

	return clone;
  }
}
