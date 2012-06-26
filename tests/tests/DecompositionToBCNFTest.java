

import data.Attribute;
import data.FunctionalDependency;
import data.NormalizationResult;
import data.RelationSchema;
import logic.Analysis.GeneralRelationCheck;
import logic.normalization.DecompositionToBCNF;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DecompositionToBCNFTest {

  @Test
  public void testNormalize1() {
	// Relation is not in BCNF
	// R=({A,B,C}, {AC==>B, B==>C})
	Attribute attrA = new Attribute("A", true, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", true, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	DecompositionToBCNF decomposition = new DecompositionToBCNF();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// AC==>B
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrB);
	fds.add(fd);

	// B==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isBCNF(schema));
	NormalizationResult result = new NormalizationResult();

	decomposition.normalize(schema, result, true);
	assertEquals("[test1 (B<pk>,C), test (A<pk>,B<fk>)]", result.getRelations()
	    .toString());
  }

  @Test
  public void testNormalize2() {
	// Relation is not in BCNF
	// R=({A,B,C,D}, {A==>BCD, BC==>AD, D==>B})
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);
	attributes.add(attrD);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	DecompositionToBCNF decomposition = new DecompositionToBCNF();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// A==>BCD
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	// BC==>AD
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrA);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	// D==>B
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrD);
	fd.getTargetAttributes().add(attrB);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isBCNF(schema));

	NormalizationResult result = new NormalizationResult();

	decomposition.normalize(schema, result, true);
	assertEquals("[test1 (D<pk>,B), test (A,C,D<fk>)]", result.getRelations()
	    .toString());
  }

  @Test
  public void testNormalize3() {
	// Relation is not in BCNF
	// R=({A,B,C}, {AB==>C, C==>B})
	Attribute attrA = new Attribute("A", true, false);
	Attribute attrB = new Attribute("B", true, false);
	Attribute attrC = new Attribute("C", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	DecompositionToBCNF decomposition = new DecompositionToBCNF();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// AB==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	// C==>B
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrB);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isBCNF(schema));

	NormalizationResult result = new NormalizationResult();

	decomposition.normalize(schema, result, true);
	assertEquals("[test1 (C<pk>,B), test (A<pk>,C<fk>)]", result.getRelations()
	    .toString());
  }

  @Test
  public void testNormalize4() {
	// Relation is in BCNF
	// R=({A,B,C,D}, {A==>BCD, BC==>AD})
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);
	attributes.add(attrD);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	DecompositionToBCNF decomposition = new DecompositionToBCNF();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// A==>BCD
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	// BC==>AD
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrA);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(true, checker.isBCNF(schema));

	NormalizationResult result = new NormalizationResult();

	decomposition.normalize(schema, result, true);
	assertEquals("[test (A,B,C,D)]", result.getRelations().toString());

  }

  @Test
  public void testNormalize5() {
	// Relation is not in BCNF
	// R=({C,T,H,R,S,G}, {C==>T, HR==>C, HT==>R, CS==>G, HS==>R})
	Attribute attrC = new Attribute("C", true, false);
	Attribute attrT = new Attribute("T", false, false);
	Attribute attrH = new Attribute("H", true, false);
	Attribute attrR = new Attribute("R", false, false);
	Attribute attrS = new Attribute("S", true, false);
	Attribute attrG = new Attribute("G", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrC);
	attributes.add(attrT);
	attributes.add(attrH);
	attributes.add(attrR);
	attributes.add(attrS);
	attributes.add(attrG);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	DecompositionToBCNF decomposition = new DecompositionToBCNF();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// C==>T
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrT);
	fds.add(fd);

	// HR==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrH);
	fd.getSourceAttributes().add(attrR);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	// HT==>R
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrH);
	fd.getSourceAttributes().add(attrT);
	fd.getTargetAttributes().add(attrR);
	fds.add(fd);

	// CS==>G
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getSourceAttributes().add(attrS);
	fd.getTargetAttributes().add(attrG);
	fds.add(fd);

	// HS==>R
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrH);
	fd.getSourceAttributes().add(attrS);
	fd.getTargetAttributes().add(attrR);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isBCNF(schema));

	NormalizationResult result = new NormalizationResult();

	decomposition.normalize(schema, result, true);
	assertEquals(
	    "[test1 (C<pk>,S<pk>,G), test2 (C<pk><fk>,T), test3 (H<pk>,R<pk>,C<fk>), test (H<pk><fk>,R<fk>,S<pk><fk>)]",
	    result.getRelations().toString());
  }

  @Test
  public void testNormalize6() {
	// Relation is not in BCNF
	// R=({A,B,C}, {A==>B, B==>C})
	Attribute attrA = new Attribute("A", true, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	DecompositionToBCNF decomposition = new DecompositionToBCNF();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// A==>B
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrB);
	fds.add(fd);

	// B==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isBCNF(schema));

	NormalizationResult result = new NormalizationResult();

	decomposition.normalize(schema, result, true);
	assertEquals("[test1 (B<pk>,C), test (A<pk>,B<fk>)]", result.getRelations()
	    .toString());
  }

  @Test
  public void testNormalize7() {
	// Relation is in BCNF
	// R=({A,B,C,D,E,F}, {C==>B,C==>D,D==>A,D==>E,E==>C,E==>F,F==>E})
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
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
	DecompositionToBCNF decomposition = new DecompositionToBCNF();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// C==>B
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrB);
	fds.add(fd);

	// C==>D
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	// D==>A
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrD);
	fd.getTargetAttributes().add(attrA);
	fds.add(fd);

	// D==>E
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrD);
	fd.getTargetAttributes().add(attrE);
	fds.add(fd);

	// E==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrE);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	// E==>F
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrE);
	fd.getTargetAttributes().add(attrF);
	fds.add(fd);

	// F==>E
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrF);
	fd.getTargetAttributes().add(attrE);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(true, checker.isBCNF(schema));

	NormalizationResult result = new NormalizationResult();

	decomposition.normalize(schema, result, true);
	assertEquals("[test (A,B,C,D,E,F)]", result.getRelations().toString());
  }

  @Test
  public void testNormalize8() {
	// Relation is in BCNF
	// R=({A,B,C,D}, {AB==>D, B==>C, C==>B})
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
	DecompositionToBCNF decomposition = new DecompositionToBCNF();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// AB==>D
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	// B==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	// C==>B
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrB);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isBCNF(schema));

	NormalizationResult result = new NormalizationResult();

	decomposition.normalize(schema, result, true);
	assertEquals("[test1 (B<pk>,C), test (A<pk>,B<pk><fk>,D)]", result
	    .getRelations().toString());

  }

}
