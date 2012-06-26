package logic.normalization;

import java.util.ArrayList;

import logic.Analysis.GeneralRelationCheck;
import logic.Analysis.RelationUtils;
import data.Attribute;
import data.ForeignKeyConstraint;
import data.RelationSchema;

public abstract class Decomposition implements NormalizationAlgorithm {
  protected GeneralRelationCheck checker = new GeneralRelationCheck();

  public RelationSchema getMinimalRelation(RelationSchema relationToNormalize) {
	RelationSchema relation = relationToNormalize.getClone();

	relation.setFunctionalDependencies(checker.getMinimalSetOfFds(relation
	    .getFunctionalDependencies()));

	RelationUtils.getInstance().uniteFdsWithSameLeftSide(
	    relation.getFunctionalDependencies());

	relation.restoreReferences();

	return relation;
  }

  /**
   * Updates the primary- and foreignKeys of the given relations
   * 
   * @param oldRelation
   *          the old relation
   * @param newRelation
   *          the newly created relation
   * @param foreignKeys
   *          a ArrayList as Container for new foreignKeys
   */
  public void updatePrimaryAndForeignKeys(RelationSchema oldRelation,
	  RelationSchema newRelation, ArrayList<ForeignKeyConstraint> foreignKeys) {

	// set primaryKey for newly created relation
	RelationUtils.getInstance().resetPrimaryKey(newRelation);

	// update foreignkeyConstraints
	for (Attribute newAttr : newRelation.getAttributes()) {
	  for (Attribute oldAttr : oldRelation.getAttributes()) {
		if (newAttr.getIsPrimaryKey()) {
		  if (newAttr.getName().equals(oldAttr.getName())) {
			foreignKeys.add(new ForeignKeyConstraint(oldRelation.getName(),
			    oldAttr.getName(), newRelation.getName(), newAttr.getName()));
			oldAttr.setIsForeignKey(true);
		  }
		}
	  }
	}

  }
}
