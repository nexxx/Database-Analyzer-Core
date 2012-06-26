
import data.Attribute;
import data.FunctionalDependency;
import data.NormalizationResult;
import data.RelationSchema;
import logic.Analysis.GeneralRelationCheck;
import logic.normalization.DecompositionTo2NF;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DecompositionTo2NFTest {

  @Test
  public void testNormalize1() {
	// Relation is not in 2.NF ==> decomposition necessary!
	// R=({A,B,C,D,E,F}, {A,B==>C, A==>D,B==>EF})
	Attribute attrA = new Attribute("A", true, false);
	Attribute attrB = new Attribute("B", true, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);
	Attribute attrE = new Attribute("E", false, false);
	Attribute attrF = new Attribute("F", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);
	attributes.add(attrD);
	attributes.add(attrE);
	attributes.add(attrF);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	DecompositionTo2NF decomposition = new DecompositionTo2NF();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// A,B==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	// A ==>D
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	// B==>E,F
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrE);
	fd.getTargetAttributes().add(attrF);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isSecondNF(schema));

	NormalizationResult result = new NormalizationResult();

	decomposition.normalize(schema, result, true);
	assertEquals(
	    "[test1 (A<pk>,D), test2 (B<pk>,E,F), test (A<pk><fk>,B<pk><fk>,C)]",
	    result.getRelations().toString());
  }

  @Test
  public void testNormalize2() {
	// Relation is in 2.NF ==> No decomposition necessary
	// R=({A,B,C}, {AB==>C}
	Attribute attrA = new Attribute("A", true, false);
	Attribute attrB = new Attribute("B", true, false);
	Attribute attrC = new Attribute("C", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);

	DecompositionTo2NF decomposition = new DecompositionTo2NF();
	GeneralRelationCheck checker = new GeneralRelationCheck();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// AB==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(true, checker.isSecondNF(schema));

	NormalizationResult result = new NormalizationResult();

	decomposition.normalize(schema, result, true);

	assertEquals("[test (A<pk>,B<pk>,C)]", result.getRelations().toString());
  }

  @Test
  public void testNormalize3() {
	// Relation is in 2.NF ==> No decomposition necessary
	// R=({A,B,C,D,E,F,G}, {B=>ACDE,E=>FG}
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);
	Attribute attrE = new Attribute("E", false, false);
	Attribute attrF = new Attribute("F", false, false);
	Attribute attrG = new Attribute("G", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);
	attributes.add(attrD);
	attributes.add(attrE);
	attributes.add(attrF);
	attributes.add(attrG);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	DecompositionTo2NF decomposition = new DecompositionTo2NF();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// B==>ACDE
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrA);
	fd.getTargetAttributes().add(attrC);
	fd.getTargetAttributes().add(attrD);
	fd.getTargetAttributes().add(attrE);
	fds.add(fd);

	// E==>FG
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrE);
	fd.getTargetAttributes().add(attrF);
	fd.getTargetAttributes().add(attrG);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(true, checker.isSecondNF(schema));

	NormalizationResult result = new NormalizationResult();

	decomposition.normalize(schema, result, true);

	assertEquals("[test (A,B,C,D,E,F,G)]", result.getRelations().toString());
  }

  @Test
  public void testNormalize4() {
	// Relation is not in 2.NF ==> decompostion necessary
	// R=({A,B,C,D}, {AB=>C,B=>D}
	Attribute attrA = new Attribute("A", true, false);
	Attribute attrB = new Attribute("B", true, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);
	attributes.add(attrD);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	DecompositionTo2NF decomposition = new DecompositionTo2NF();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// AB==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	// B==>D
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isSecondNF(schema));

	NormalizationResult result = new NormalizationResult();

	decomposition.normalize(schema, result, true);

	assertEquals("[test1 (B<pk>,D), test (A<pk>,B<pk><fk>,C)]", result
	    .getRelations().toString());
  }

  @Test
  public void testNormalize5() {
	// Relation is not in 2.NF ==> decompostion necessary
	// R=({A,B,C,D,E,F,G,H}, {ABC=>D,B=>EF,A==>G,C==>H}
	Attribute attrA = new Attribute("A", true, false);
	Attribute attrB = new Attribute("B", true, false);
	Attribute attrC = new Attribute("C", true, false);
	Attribute attrD = new Attribute("D", false, false);
	Attribute attrE = new Attribute("E", false, false);
	Attribute attrF = new Attribute("F", false, false);
	Attribute attrG = new Attribute("G", false, false);
	Attribute attrH = new Attribute("H", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);
	attributes.add(attrD);
	attributes.add(attrE);
	attributes.add(attrF);
	attributes.add(attrG);
	attributes.add(attrH);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	DecompositionTo2NF decomposition = new DecompositionTo2NF();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// ABC==>D
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrB);
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	// B==>EF
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrE);
	fd.getTargetAttributes().add(attrF);
	fds.add(fd);

	// A==>G
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrG);
	fds.add(fd);

	// C==>H
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrH);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isSecondNF(schema));

	NormalizationResult result = new NormalizationResult();

	decomposition.normalize(schema, result, true);

	assertEquals(
	    "[test1 (B<pk>,E,F), test2 (A<pk>,G), test3 (C<pk>,H), test (A<pk><fk>,B<pk><fk>,C<pk><fk>,D)]",
	    result.getRelations().toString());
  }

}
