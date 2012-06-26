package data;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import utils.Utilities;
import data.events.Change;
import data.events.ChangeListener;

/**
 * Class representing a Relation
 * 
 * @author Sebastian Theuermann
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "relation")
@XmlType(propOrder = { "name", "attributes", "functionalDependencies" })
public class RelationSchema extends HistoricObject implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -3301439448650204024L;
  private String name;
  private ArrayList<Attribute> attributes;
  private ArrayList<FunctionalDependency> functionalDependencies;
  private static int id = 0;
  private int ownId;

  public RelationSchema() {
	super();
	ownId = id++;
	attributes = new ArrayList<>();
	functionalDependencies = new ArrayList<>();
	super.changeListener = new ChangeListener() {
	  @Override
	  public void Change(Change change) {
		changeSupport.fireChange(change.getTime());
	  }
	};
  }

  public RelationSchema(String name) {
	this();
	this.name = name;
  }

  public RelationSchema(String name, ArrayList<Attribute> attributes) {
	this(name);
	this.attributes = attributes;
  }

  public RelationSchema(String name, ArrayList<Attribute> attributes,
	  ArrayList<FunctionalDependency> functionalDependencies) {
	this(name, attributes);
	this.functionalDependencies = functionalDependencies;
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	if (!name.equals(this.name)) {
	  changeSupport.fireBeforeChange();
	  this.name = name;
	  changeSupport.fireAfterChange();
	}
  }

  /**
   * Assigns the new name without firing the changeEvent!
   * 
   * @param name
   *          the new name for the RelationSchema
   */
  public void setNameWithoutFiring(String name) {
	if (!name.equals(this.name)) {
	  this.name = name;
	}
  }

  /**
   * Renames a given Attribute without firing Change
   * 
   * @param attribute
   *          the Attribute to rename
   * @param newName
   *          the new name for the attribute
   */
  public void renameAttributeWithoutFiring(Attribute attribute, String newName) {
	if (getAttributes().contains(attribute)) {
	  attribute.setNameWithoutFiring(newName);
	}
  }

  public void setOwnId(int ownId) {
	this.ownId = ownId;
  }

  @XmlTransient
  public int getOwnId() {
	return ownId;
  }

  @XmlElementWrapper(name = "attributes")
  @XmlElement(name = "attribute")
  public ArrayList<Attribute> getAttributes() {
	return attributes;
  }

  /**
   * Returns a array containing the names of all Attributes of the
   * relation
   * 
   * @return a array of the attribute-names
   */
  public String[] getAttributesNameArray() {
	String[] attrArray = new String[attributes.size()];

	int i = 0;
	for (Attribute attr : attributes) {
	  attrArray[i++] = attr.getName();
	}

	return attrArray;
  }

  /**
   * Returns a Attribute of the given name
   * 
   * @param name
   *          the name of the attribute to look for
   * @return the attribute with the given name
   */
  public Attribute getAttributeByName(String name) {

	for (Attribute attr : attributes) {
	  if (attr.getName().equals(name)) {
		return attr;
	  }
	}
	return null;
  }

  @XmlElementWrapper(name = "functionaldependencies")
  @XmlElement(name = "fd")
  public ArrayList<FunctionalDependency> getFunctionalDependencies() {
	return functionalDependencies;
  }

  public void setAttributes(ArrayList<Attribute> attributes) {
	changeSupport.fireBeforeChange();
	this.attributes = attributes;
	changeSupport.fireAfterChange();
  }

  public void removeAttribute(Attribute attribute) {
	changeSupport.fireBeforeChange();
	attribute.removeChangeListener(changeListener);
	attributes.remove(attribute);
	updateFunctionalDependencies();
	changeSupport.fireAfterChange();
  }

  public void setFunctionalDependencies(
	  ArrayList<FunctionalDependency> functionalDependencies) {
	changeSupport.fireBeforeChange();
	this.functionalDependencies = functionalDependencies;
	changeSupport.fireAfterChange();
  }

  public boolean addFunctionalDependency(FunctionalDependency dependency) {
	for (FunctionalDependency fd : functionalDependencies) {
	  if (fd.equals(dependency)) {
		return false;
	  }
	}
	changeSupport.fireBeforeChange();
	functionalDependencies.add(dependency);
	changeSupport.fireAfterChange();
	return true;
  }

  public boolean addAttribute(String name) {
	for (Attribute attribute : attributes) {
	  if (attribute.getName().equalsIgnoreCase(name)) {
		return false;
	  }
	}
	return addAttribute(new Attribute(name));
  }

  public boolean addAttribute(Attribute attribute) {
	if (!attributes.contains(attribute)) {
	  attribute.addChangeListener(changeListener);
	  changeSupport.fireBeforeChange();
	  attributes.add(attribute);
	  changeSupport.fireAfterChange();
	  return true;
	}
	return false;
  }

  public void removeFunctionalDependency(FunctionalDependency dependency) {
	changeSupport.fireBeforeChange();
	functionalDependencies.remove(dependency);
	changeSupport.fireAfterChange();
  }

  public void updateFunctionalDependencies() {
	ArrayList<FunctionalDependency> toDelete = new ArrayList<>();

	for (FunctionalDependency fd : functionalDependencies) {
	  cleanReferences(fd.getSourceAttributes());
	  cleanReferences(fd.getTargetAttributes());

	  if (fd.getSourceAttributes().isEmpty()
		  || fd.getTargetAttributes().isEmpty()) {
		toDelete.add(fd);
	  }
	}

	for (FunctionalDependency fd : toDelete) {
	  removeFunctionalDependency(fd);
	}
  }

  /**
   * Removes zombie-Attributes from the functionalDependencies
   * 
   * @param list
   *          the Attributes to check
   */
  private void cleanReferences(ArrayList<Attribute> list) {
	ArrayList<Attribute> toDelete = new ArrayList<>();

	for (Attribute item : list) {
	  if (!isAttributeExisting(item)) {
		toDelete.add(item);
	  }
	}

	for (Attribute attr : toDelete) {
	  list.remove(attr);
	}
  }

  /**
   * Returns if a given Attribute exists in the Relation
   * 
   * @param attribute
   *          the Attribute to check
   * @return true if it exists, false if not
   */
  private boolean isAttributeExisting(Attribute attribute) {
	for (Attribute attr : attributes) {
	  if (attr == attribute) {
		return true;
	  }
	}
	return false;
  }

  @Override
  public String toString() {
	return getName() + " (" + Utilities.getStringFromArrayList(getAttributes())
	    + ")";
  }

  @Override
  public RelationSchema getClone() {
	RelationSchema clone;

	ArrayList<Attribute> clonesAttributes = new ArrayList<>();
	ArrayList<FunctionalDependency> clonesFunctionalDependencies = new ArrayList<>();

	for (Attribute attribute : getAttributes()) {
	  clonesAttributes.add((Attribute) attribute.getClone());
	}

	for (FunctionalDependency dependency : getFunctionalDependencies()) {
	  clonesFunctionalDependencies.add(dependency.getClone());
	}

	clone = new RelationSchema(getName(), clonesAttributes,
	    clonesFunctionalDependencies);

	clone.setOwnId(getOwnId());

	clone.restoreReferences();
	clone.initPropertyChangeListeners();

	clone.setDirty(super.isDirty());

	return clone;
  }

  @Override
  public boolean equals(Object object) {
	if (object instanceof RelationSchema) {
	  RelationSchema otherSchema = (RelationSchema) object;

	  if (!getName().equals(otherSchema.getName())) {
		return false;
	  }

	  if (!getAttributes().equals(otherSchema.getAttributes())) {
		return false;
	  }

	  if (!getFunctionalDependencies().equals(
		  otherSchema.getFunctionalDependencies())) {
		return false;
	  }

	}

	return true;
  }

  /**
   * Initializes Attribute-ChangeListeners by adding them to the
   * Attributes of the relation
   */
  public void initPropertyChangeListeners() {
	for (Attribute attribute : getAttributes()) {
	  attribute.addChangeListener(changeListener);
	}
  }

  /**
   * Restores the References between the FunctionalDependencies and
   * the Attributes of the relation
   */
  public void restoreReferences() {
	for (FunctionalDependency fd : getFunctionalDependencies()) {
	  for (int i = 0; i < fd.getSourceAttributes().size(); i++) {
		fd.getSourceAttributes().set(i,
		    getAttributeByName(fd.getSourceAttributes().get(i).getName()));
	  }
	  for (int i = 0; i < fd.getTargetAttributes().size(); i++) {
		fd.getTargetAttributes().set(i,
		    getAttributeByName(fd.getTargetAttributes().get(i).getName()));
	  }
	}
  }

}
