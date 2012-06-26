package logic.normalization;

import java.util.ArrayList;

import logic.Analysis.RelationUtils;
import data.Attribute;
import data.FunctionalDependency;
import data.NormalizationResult;
import data.RelationSchema;

/**
 * Normalization to BoyceCodd-NormalForm using Decomposition
 * 
 * @author Sebastian Theuermann
 */
public class DecompositionToBCNF extends Decomposition {

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
	    .checkForBCNF(relationToNormalize);
	FunctionalDependency violatingFd;

	// If there is no FD violating BCNF ==> nothing to do here
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

	// FD Y ==> A violates BCNF
	newSchema = new RelationSchema();
	newSchema.setName(RelationUtils.getInstance().getRelationName(
	    relationToNormalize.getName(), result.getRelations()));

	ArrayList<Attribute> onlyDeterminedAttributes = checker
	    .getOnlyDeterminedAttributes(relationToNormalize);
	violatingFd = violatingFds.get(0);
	for (FunctionalDependency fd : violatingFds) {
	  if (onlyDeterminedAttributes.containsAll(fd.getTargetAttributes())) {
		violatingFd = fd;
		break;
	  }
	}

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

	ArrayList<FunctionalDependency> fdsToDelete = new ArrayList<>();
	ArrayList<Attribute> attributesToDelete = new ArrayList<>();

	for (FunctionalDependency oldFd : oldSchema.getFunctionalDependencies()) {
	  // Taking care of the TargetAttributes
	  for (Attribute attr : oldFd.getTargetAttributes()) {
		if (!oldSchema.getAttributes().contains(attr)) {
		  attributesToDelete.add(attr);
		}
	  }

	  while (attributesToDelete.size() > 0) {
		oldFd.getTargetAttributes().remove(attributesToDelete.get(0));
		attributesToDelete.remove(0);
	  }

	  if (oldFd.getTargetAttributes().size() == 0) {
		fdsToDelete.add(oldFd);
	  }

	  // Taking care of the SourceAttributes
	  for (Attribute targetAttr : oldFd.getSourceAttributes()) {
		if (fdsToDelete.contains(oldFd)) {
		  continue;
		}
		if (!oldSchema.getAttributes().contains(targetAttr)) {
		  fdsToDelete.add(oldFd);
		}
	  }
	}

	for (FunctionalDependency candidate : fdsToDelete) {
	  oldSchema.removeFunctionalDependency(candidate);
	}

	RelationUtils.getInstance().restoreAttributesByFds(oldSchema);

  }

}
