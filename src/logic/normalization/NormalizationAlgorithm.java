package logic.normalization;

import data.NormalizationResult;
import data.RelationSchema;

/**
 * Interface as common base for all normalization-Algorithms
 * 
 * @author Sebastian Theuermann
 */
public interface NormalizationAlgorithm {

  public void normalize(RelationSchema relationToNormalize,
	  NormalizationResult result, Boolean minimizeFds);
}
