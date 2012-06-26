
import data.Attribute;
import data.FunctionalDependency;
import data.RelationSchema;
import logic.Analysis.GeneralRelationCheck;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GeneralRelationCheckTest {

  @Test
  public void testIsSecondNF() {
	// Relation not in 2.NF because nonprime Attributes dependent on
	// part of the candidate key
	// R=({A,B,C,D,E,F}, {AB==>C,A==>D,B==>EF}
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
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// AB==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	// A==>D
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	// B==>EF
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrE);
	fd.getTargetAttributes().add(attrF);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isSecondNF(schema));
  }

  @Test
  public void testIsSecondNF2() {
	// Relation is in 2.NF
	// R=({A,B,C}, {AB==>C}
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);

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
  }

  @Test
  public void testIsSecondNF3() {
	// Relation is in 2.NF
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
  }

  @Test
  public void testIsSecondNF4() {
	// Relation is not in 2.NF
	// R=({A,B,C,D}, {AB=>C,B=>D}
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
  }

  @Test
  public void testIsThirdNF() {
	// Relation is not in 3.NF
	// R=({A,B,C,D}, {A=>BC,C=>D}
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
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// A==>BC
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	// C==>D
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isThirdNF(schema));
  }

  @Test
  public void testIsThirdNF2() {
	// Relation is not in 3.NF
	// R=({A,B,C,D,E}, {A==>BCDE, BC==>ADE,D==>E})
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);
	Attribute attrE = new Attribute("E", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);
	attributes.add(attrD);
	attributes.add(attrE);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// A==>BCDE
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fd.getTargetAttributes().add(attrD);
	fd.getTargetAttributes().add(attrE);
	fds.add(fd);

	// BC==>ADE
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrA);
	fd.getTargetAttributes().add(attrD);
	fd.getTargetAttributes().add(attrE);
	fds.add(fd);

	// D==>E
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrD);
	fd.getTargetAttributes().add(attrE);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isThirdNF(schema));
  }

  @Test
  public void testIsThirdNF3() {
	// Relation is in 3.NF
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

	assertEquals(true, checker.isThirdNF(schema));
  }

  @Test
  public void testIsThirdNF4() {
	// Relation is in 3.NF
	// R=({A,B}, {A==>B})
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// A==>B
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrB);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(true, checker.isThirdNF(schema));
  }

  @Test
  public void testIsThirdNF5() {
	// Relation is in 3NF
	// R=({A,B,C}, {AC==>B, B==>C})
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);

	GeneralRelationCheck checker = new GeneralRelationCheck();
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

	assertEquals(true, checker.isThirdNF(schema));
  }

  @Test
  public void testIsThirdNF6() {
	// Relation is in 3NF
	// R=({A,B,C}, {AB==>C, C==>B})
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);

	GeneralRelationCheck checker = new GeneralRelationCheck();
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

	assertEquals(true, checker.isThirdNF(schema));
  }

  @Test
  public void testIsThirdNF7() {
	// Relation is not in 3.NF
	// R=({A,B,C,D,E}, {AB==>C,D==>E,C==>DE})
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);
	Attribute attrE = new Attribute("E", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);
	attributes.add(attrD);
	attributes.add(attrE);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// AB==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	// D==>E
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrD);
	fd.getTargetAttributes().add(attrE);
	fds.add(fd);

	// C==>DE
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrD);
	fd.getTargetAttributes().add(attrE);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isThirdNF(schema));
  }

  @Test
  public void testIsThirdNF8() {
	// Relation is not in 3.NF
	// R=({A,B,C,D,E,F}, {C==>BDAE})
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
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// C==>BDAE
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrB);
	fd.getTargetAttributes().add(attrD);
	fd.getTargetAttributes().add(attrA);
	fd.getTargetAttributes().add(attrE);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isThirdNF(schema));
  }

  @Test
  public void testIsThirdNF9() {
	// Relation is in 3.NF
	// R=({A,B,C,D,E,F}, {C==>B, C==>D, D==>A,D==>E, E==>C, E==>F,
	// F==>E})
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

	assertEquals(true, checker.isThirdNF(schema));
  }

  @Test
  public void testIsThirdNF10() {
	// Relation is in BCNF
	// R=({A,B,C,D}, {AB==>D, B==>C, C==>B})
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

	assertEquals(true, checker.isThirdNF(schema));
  }

  @Test
  public void testIsBCNF() {
	// Relation is not in BCNF
	// R=({A,B,C}, {AC==>B, B==>C})
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);

	GeneralRelationCheck checker = new GeneralRelationCheck();
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
  }

  @Test
  public void testIsBCNF2() {
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
  }

  @Test
  public void testIsBCNF3() {
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
  }

  @Test
  public void testIsBCNF4() {
	// Relation is not in BCNF
	// R=({A,B,C}, {AB==>C, C==>B})
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);

	GeneralRelationCheck checker = new GeneralRelationCheck();
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
  }

  @Test
  public void testIsBCNF5() {
	// Relation is not in BCNF
	// R=({A,B,C}, {A==>B, B==>C})
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);

	GeneralRelationCheck checker = new GeneralRelationCheck();
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
  }

  @Test
  public void testIsBCNF6() {
	// Relation is not in BCNF
	// R=({A,B,C,D}, {B==>D, D==>AC})
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
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// B==>D
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	// D==>AC
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrD);
	fd.getTargetAttributes().add(attrA);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isBCNF(schema));
  }

  @Test
  public void testIsBCNF7() {
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
  }

  @Test
  public void testIsBCNF8() {
	// Relation is in BCNF
	// R=({A,B,C,D}, {AB==>D, B==>C, C==>B})
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
  }

}
