package logic.Analysis;

import java.util.ArrayList;

import data.Attribute;
import data.FunctionalDependency;
import data.Key;
import data.NormalForm;
import data.RelationSchema;

/**
 * Interface incorporating all important methods to check a relation
 * 
 * @author Sebastian Theuermann
 */
public interface RelationInformation {

  public abstract NormalForm getNF(RelationSchema schema,
	  ArrayList<FunctionalDependency> violatingFds);

  public NormalForm getNF(ArrayList<RelationSchema> relations);

  public ArrayList<FunctionalDependency> checkForSecondNF(RelationSchema schema);

  public boolean isSecondNF(RelationSchema schema);

  public ArrayList<FunctionalDependency> checkForThirdNF(RelationSchema schema);

  public boolean isThirdNF(RelationSchema schema);

  public ArrayList<FunctionalDependency> checkForBCNF(RelationSchema schema);

  public boolean isBCNF(RelationSchema schema);

  public ArrayList<FunctionalDependency> getMinimalSetOfFds(
	  ArrayList<FunctionalDependency> fds);

  public Key getPrimaryKey(RelationSchema schema);

  public ArrayList<Key> getAllCandidateKeys(RelationSchema schema);

  public boolean isCandidateKey(ArrayList<Attribute> keyToTest,
	  ArrayList<Key> candidateKeys);

  public boolean isKeyDeterminingEverything(RelationSchema schema, Key key);

  public boolean areFdSetsEquivalent(ArrayList<FunctionalDependency> list1,
	  ArrayList<FunctionalDependency> list2);
}
