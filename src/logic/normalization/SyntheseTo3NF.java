package logic.normalization;

import java.util.ArrayList;

import logic.Analysis.GeneralRelationCheck;
import logic.Analysis.RelationUtils;
import data.Attribute;
import data.FunctionalDependency;
import data.Key;
import data.NormalizationResult;
import data.RelationSchema;

/**
 * Synthese-Algorithm for Third-NF
 * 
 * @author Sebastian Theuermann
 */
public class SyntheseTo3NF extends Synthese {
  private GeneralRelationCheck checker;

  public SyntheseTo3NF() {
	checker = new GeneralRelationCheck();
  }

  @Override
  public void normalize(RelationSchema relationToNormalize,
	  NormalizationResult result, Boolean minimizeFds) {
	result.getRelations().clear();
	normalizeRelation(relationToNormalize, result);
  }

  private void normalizeRelation(RelationSchema relationToNormalize,
	  NormalizationResult result) {

	ArrayList<ArrayList<FunctionalDependency>> groups;
	RelationSchema tempSchema;
	ArrayList<Key> candidateKeys = checker
	    .getAllCandidateKeys(relationToNormalize);

	// Make Fd's minimal
	relationToNormalize.setFunctionalDependencies(checker
	    .getMinimalSetOfFds(relationToNormalize.getFunctionalDependencies()));

	// Create Groups
	groups = getGroupsWithSameLeftSide(relationToNormalize
	    .getFunctionalDependencies());

	// Unite mutual dependent Groups
	uniteMutualDepdendentGroups(groups);

	// Create a relation from each set of functional Dependencies
	for (ArrayList<FunctionalDependency> fdList : groups) {
	  tempSchema = getSchemaFromFds(fdList);
	  tempSchema.setName(RelationUtils.getInstance().getRelationName(
		  relationToNormalize.getName(), result.getRelations()));
	  result.getRelations().add(tempSchema);
	}

	// Search for a candidateKey in the Relations
	// If none existing, add new Relation with key
	if (!isAnyRelationContainingAKey(result.getRelations(), candidateKeys)) {
	  tempSchema = new RelationSchema();
	  for (Attribute attr : RelationUtils.getInstance().getKey(candidateKeys)
		  .getAttributes()) {
		tempSchema.addAttribute(attr);
	  }
	  tempSchema.setName(RelationUtils.getInstance().getRelationName(
		  relationToNormalize.getName(), result.getRelations()));
	  result.getRelations().add(tempSchema);
	}

	// Remove relations that are fully contained by other relations,
	// one by one
	RelationSchema fullyContainedRelation = getFullyContainedRelation(result
	    .getRelations());
	while (fullyContainedRelation != null) {
	  result.getRelations().remove(fullyContainedRelation);
	}

	super.updatePrimaryAndForeignKeys(result.getRelations(),
	    result.getForeignKeys());
  }
}
