package data;

import java.util.ArrayList;

/**
 * Class representing the result of a normalization
 * 
 * @author Sebastian Theuermann
 */
public class NormalizationResult {
  private ArrayList<RelationSchema> relations;
  private ArrayList<ForeignKeyConstraint> foreignKeys;

  public NormalizationResult() {
	relations = new ArrayList<>();
	foreignKeys = new ArrayList<>();
  }

  public NormalizationResult(ArrayList<RelationSchema> relations,
	  ArrayList<ForeignKeyConstraint> foreignKeys) {
	this.relations = relations;
	this.foreignKeys = foreignKeys;
  }

  /**
   * @return the relations
   */
  public ArrayList<RelationSchema> getRelations() {
	return relations;
  }

  /**
   * @param relations
   *          the relations to set
   */
  public void setRelations(ArrayList<RelationSchema> relations) {
	this.relations = relations;
  }

  /**
   * @return the foreignKeys
   */
  public ArrayList<ForeignKeyConstraint> getForeignKeys() {
	return foreignKeys;
  }

  /**
   * @param foreignKeys
   *          the foreignKeys to set
   */
  public void setForeignKeys(ArrayList<ForeignKeyConstraint> foreignKeys) {
	this.foreignKeys = foreignKeys;
  }

  @SuppressWarnings("unchecked")
  public Object getClone() {
	NormalizationResult clone = new NormalizationResult();
	clone.setRelations((ArrayList<RelationSchema>) getRelations().clone());
	clone.setForeignKeys((ArrayList<ForeignKeyConstraint>) getForeignKeys()
	    .clone());
	return clone;
  }
}
