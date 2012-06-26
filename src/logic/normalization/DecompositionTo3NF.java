package logic.normalization;

import java.util.ArrayList;

import logic.Analysis.RelationUtils;
import data.Attribute;
import data.FunctionalDependency;
import data.NormalizationResult;
import data.RelationSchema;

/**
 * Normalization to 3.NF using Decomposition
 * 
 * @author Sebastian Theuermann
 */
public class DecompositionTo3NF extends Decomposition {

  @Override
  public void normalize(RelationSchema relationToNormalize,
	  NormalizationResult result, Boolean minimizeFds) {

	if (minimizeFds) {
	  normalizeRelation(getMinimalRelation(relationToNormalize), result);
	} else {
	  normalizeRelation(relationToNormalize, result);
	}

  }

  private void normalizeRelation(RelationSchema relationToNormalize,
	  NormalizationResult result) {

	RelationSchema newSchema;

	ArrayList<FunctionalDependency> violatingFds = checker
	    .checkForThirdNF(relationToNormalize);
	FunctionalDependency violatingFd;

	// If there is no FD violating 3NF ==> nothing to do here
	if (relationToNormalize.getFunctionalDependencies().size() == 0
	    || violatingFds.size() == 0) {
	  // Remove duplicate entry of relation in results
	  for (RelationSchema relation : result.getRelations()) {
		if (relation.equals(relationToNormalize)) {
		  result.getRelations().remove(relation);
		  break;
		}
	  }
	  result.getRelations().add(relationToNormalize);
	  return;
	}

	// FD Y ==> A violates 3NF
	newSchema = new RelationSchema();
	newSchema.setName(RelationUtils.getInstance().getRelationName(
	    relationToNormalize.getName(), result.getRelations()));
	violatingFd = violatingFds.get(0);

	// S2 = YA
	// S1 = S - A
	transferFd(relationToNormalize, newSchema, violatingFd);

	// Update keys
	updatePrimaryAndForeignKeys(relationToNormalize, newSchema,
	    result.getForeignKeys());

	// Recursive calls
	normalizeRelation(newSchema, result);
	normalizeRelation(relationToNormalize, result);

  }

  /**
   * Moves a FD and it's Attributes from one RelationSchema to another
   * 
   * @param oldSchema
   *          the RelationSchema to cut from
   * @param newSchema
   *          the RelationSchema to paste into
   * @param fd
   *          the FunctionalDependency to move
   */
  private void transferFd(RelationSchema oldSchema, RelationSchema newSchema,
	  FunctionalDependency fd) {

	oldSchema.removeFunctionalDependency(fd);

	// Copy everything to new schema
	for (Attribute sourceAttribute : fd.getSourceAttributes()) {
	  newSchema.addAttribute((Attribute) sourceAttribute.getClone());
	}
	for (Attribute targetAttribute : fd.getTargetAttributes()) {
	  newSchema.addAttribute((Attribute) targetAttribute.getClone());
	}
	newSchema.getFunctionalDependencies().add(fd.getClone());

	// reconnect attributes with functional dependencies
	newSchema.restoreReferences();

	// Remove copied things in the old schema
	oldSchema.getAttributes().removeAll(fd.getTargetAttributes());

  }

}
