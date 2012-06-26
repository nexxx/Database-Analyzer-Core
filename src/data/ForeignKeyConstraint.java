package data;

import java.io.Serializable;

/**
 * Class representing a ForeignKey-Constraint
 * 
 * @author Sebastian Theuermann
 */
public final class ForeignKeyConstraint implements Serializable {

  public ForeignKeyConstraint() {
  }

  public ForeignKeyConstraint(String sourceRelationName,
	  String sourceAttributeName, String targetRelationName,
	  String targetAttributeName) {
	this.sourceRelationName = sourceRelationName;
	this.sourceAttributeName = sourceAttributeName;
	this.targetRelationName = targetRelationName;
	this.targetAttributeName = targetAttributeName;
  }

  /**
   * 
   */
  private static final long serialVersionUID = 3011555081376590937L;
  private String sourceRelationName;
  private String targetRelationName;
  private String targetAttributeName;
  private String sourceAttributeName;

  /**
   * @return the targetRelationName
   */
  public String getTargetRelationName() {
	return targetRelationName;
  }

  /**
   * @param targetRelationName
   *          the targetRelationName to set
   */
  public void setTargetRelationName(String targetRelationName) {
	this.targetRelationName = targetRelationName;
  }

  /**
   * @return the targetAttributeName
   */
  public String getTargetAttributeName() {
	return targetAttributeName;
  }

  /**
   * @param targetAttributeName
   *          the targetAttributeName to set
   */
  public void setTargetAttributeName(String targetAttributeName) {
	this.targetAttributeName = targetAttributeName;
  }

  /**
   * @return the sourceRelationName
   */
  public String getSourceRelationName() {
	return sourceRelationName;
  }

  /**
   * @param sourceRelationName
   *          the sourceRelationName to set
   */

  public void setSourceRelationName(String sourceRelationName) {
	this.sourceRelationName = sourceRelationName;
  }

  /**
   * @return the sourceAttributeName
   */
  public String getSourceAttributeName() {
	return sourceAttributeName;
  }

  /**
   * @param sourceAttributeName
   *          the sourceAttributeName to set
   */
  public void setSourceAttributeName(String sourceAttributeName) {
	this.sourceAttributeName = sourceAttributeName;
  }

  @Override
  public boolean equals(Object obj) {
	if (obj instanceof ForeignKeyConstraint) {
	  ForeignKeyConstraint fk = (ForeignKeyConstraint) obj;
	  if (fk.getSourceAttributeName().equals(getSourceAttributeName())) {
		if (fk.getTargetRelationName().equals(getTargetRelationName())) {
		  if (fk.getTargetAttributeName().equals(getTargetAttributeName())) {
			return true;
		  }
		}
	  }
	}
	return false;
  }

  public Object getClone() {
	ForeignKeyConstraint fk = new ForeignKeyConstraint();
	fk.setSourceRelationName(getSourceRelationName());
	fk.setSourceAttributeName(getSourceAttributeName());
	fk.setTargetRelationName(getTargetRelationName());
	fk.setTargetAttributeName(getTargetAttributeName());
	return fk;
  }

  @Override
  public String toString() {
	return getSourceRelationName() + "." + getSourceAttributeName() + "==>"
	    + getTargetRelationName() + "." + getTargetAttributeName();
  }

}
