

import data.Attribute;
import data.FunctionalDependency;
import data.Key;
import data.RelationSchema;
import logic.Analysis.GeneralRelationCheck;
import org.junit.Before;
import org.junit.Test;
import utils.Utilities;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class RelationCheckTest {
  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testGetPrimaryKey() {
	// R=({A,B,C,D,E,F},{A==>B,C==>DE,AC==>F})
	Key primaryKey;
	GeneralRelationCheck checker = new GeneralRelationCheck();
	Attribute attrA = new Attribute("A", true, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", true, false);
	Attribute attrD = new Attribute("D", false, false);
	Attribute attrE = new Attribute("E", false, false);
	Attribute attrF = new Attribute("F", false, false);
	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attrA.setIsForeignKey(true);
	attrC.setIsForeignKey(true);

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);
	attributes.add(attrD);
	attributes.add(attrE);
	attributes.add(attrF);

	RelationSchema schema = new RelationSchema("TestRelation", attributes, null);

	primaryKey = checker.getPrimaryKey(schema);

	assertEquals("A<pk><fk>,C<pk><fk>",
	    Utilities.getStringFromArrayList(primaryKey.getAttributes()));
  }

  @Test
  public void testgetMinimalSetOfFds() {
	GeneralRelationCheck checker = new GeneralRelationCheck();
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrD = new Attribute("D", false, false);

	FunctionalDependency fd = new FunctionalDependency();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	ArrayList<FunctionalDependency> result = new ArrayList<FunctionalDependency>();

	// B==>A
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrA);
	fds.add(fd);

	// D==>A
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrD);
	fd.getTargetAttributes().add(attrA);
	fds.add(fd);

	// AB==>D
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	result = checker.getMinimalSetOfFds(fds);
	assertEquals("D->A,B->D", Utilities.getStringFromArrayList(result));
	assertEquals(true, checker.areFdSetsEquivalent(fds, result));
  }

  @Test
  public void testgetMinimalSetOfFds2() {
	// G= {A==>BC, B==>C, AB==>D}
	GeneralRelationCheck checker = new GeneralRelationCheck();
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);

	FunctionalDependency fd = new FunctionalDependency();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	ArrayList<FunctionalDependency> result = new ArrayList<FunctionalDependency>();

	// A==>BC
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	// B==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	// AB==>D
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	result = checker.getMinimalSetOfFds(fds);
	assertEquals("A->B,B->C,A->D", Utilities.getStringFromArrayList(result));
	assertEquals(true, checker.areFdSetsEquivalent(fds, result));
  }

  @Test
  public void testIsPrimeAttribute() {
	GeneralRelationCheck checker = new GeneralRelationCheck();
	ArrayList<Key> candidateKeys = new ArrayList<Key>();
	Key tmpKey = new Key();
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);
	Attribute attrE = new Attribute("E", false, false);

	tmpKey.getAttributes().add(attrA);
	tmpKey.getAttributes().add(attrB);
	candidateKeys.add(tmpKey);

	tmpKey = new Key();
	tmpKey.getAttributes().add(attrA);
	tmpKey.getAttributes().add(attrC);
	candidateKeys.add(tmpKey);

	assertEquals(true, checker.isPrimeAttribute(attrA, candidateKeys));
	assertEquals(true, checker.isPrimeAttribute(attrB, candidateKeys));
	assertEquals(true, checker.isPrimeAttribute(attrC, candidateKeys));
	assertEquals(false, checker.isPrimeAttribute(attrD, candidateKeys));
	assertEquals(false, checker.isPrimeAttribute(attrE, candidateKeys));
  }

  @Test
  public void testRemoveUnneccessarySourceAttributes() {
	GeneralRelationCheck checker = new GeneralRelationCheck();
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrD = new Attribute("D", false, false);

	FunctionalDependency fd = new FunctionalDependency();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	ArrayList<FunctionalDependency> result = new ArrayList<FunctionalDependency>();

	// B==>A
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrA);
	fds.add(fd);

	// D==>A
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrD);
	fd.getTargetAttributes().add(attrA);
	fds.add(fd);

	// AB==>D
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	result = checker.removeUnneccessarySourceAttributes(fds);
	assertEquals("B->A,D->A,B->D", Utilities.getStringFromArrayList(result));
  }

  @Test
  public void testGetAllCandidateKeys() {
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
	ArrayList<Key> candidateKeys = new ArrayList<Key>();

	// C==>D
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	// B==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	// B==>A
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrA);
	fds.add(fd);

	// AD==>B
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrD);
	fd.getTargetAttributes().add(attrB);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	candidateKeys = checker.getAllCandidateKeys(schema);
	assertEquals("(B),(A,C),(A,D)",
	    Utilities.getStringFromArrayList(candidateKeys));

  }

  @Test
  public void testGetAllCandidateKeys2() {

	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);
	Attribute attrE = new Attribute("E", false, false);
	Attribute attrF = new Attribute("F", false, false);
	Attribute attrG = new Attribute("G", false, false);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;
	ArrayList<Key> candidateKeys = new ArrayList<Key>();
	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);
	attributes.add(attrD);
	attributes.add(attrE);
	attributes.add(attrF);
	attributes.add(attrG);

	// A==>CD
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrC);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	// C==>F
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrF);
	fds.add(fd);

	// FA==>DB
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrF);
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrD);
	fd.getTargetAttributes().add(attrB);
	fds.add(fd);

	// G==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrG);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	// A==>EA
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrE);
	fd.getTargetAttributes().add(attrA);
	fds.add(fd);

	// DC==>AB
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrD);
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrA);
	fd.getTargetAttributes().add(attrB);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	candidateKeys = checker.getAllCandidateKeys(schema);
	assertEquals("(G,A),(G,D)", Utilities.getStringFromArrayList(candidateKeys));
  }

  @Test
  public void testGetAllCandidateKeys3() {
	// R=({A,B,C,D,E}, {AB==>C, CD==>E, DE==>B})
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

	GeneralRelationCheck checker = new GeneralRelationCheck();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;
	ArrayList<Key> candidateKeys = new ArrayList<Key>();

	// AB==>C
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrC);
	fds.add(fd);

	// CD==>E
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getSourceAttributes().add(attrD);
	fd.getTargetAttributes().add(attrE);
	fds.add(fd);

	// DE==>B
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrD);
	fd.getSourceAttributes().add(attrE);
	fd.getTargetAttributes().add(attrB);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	candidateKeys = checker.getAllCandidateKeys(schema);
	assertEquals("(A,D,B),(A,D,C)",
	    Utilities.getStringFromArrayList(candidateKeys));
  }

  @Test
  public void testGetNumberOfMatchingAttributes() {
	GeneralRelationCheck checker = new GeneralRelationCheck();

	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);
	Attribute attrE = new Attribute("E", false, false);
	Attribute attrF = new Attribute("F", false, false);
	Attribute attrG = new Attribute("G", false, false);

	ArrayList<Attribute> list1 = new ArrayList<Attribute>();
	ArrayList<Attribute> list2 = new ArrayList<Attribute>();

	list1.add(attrA);
	list1.add(attrB);
	list1.add(attrC);
	list1.add(attrD);
	list1.add(attrE);
	list1.add(attrF);

	assertEquals(0, checker.getNumberOfMatchingAttributes(list1, list2));

	list2.add(attrA);
	assertEquals(1, checker.getNumberOfMatchingAttributes(list1, list2));

	list2.add(attrG);
	assertEquals(1, checker.getNumberOfMatchingAttributes(list1, list2));

	list2.add(attrB);
	list2.add(attrC);
	list2.add(attrD);
	list2.add(attrE);
	list2.add(attrF);

	assertEquals(6, checker.getNumberOfMatchingAttributes(list1, list2));
  }

  @Test
  public void testgetSubsetOfAttributes() {
	GeneralRelationCheck checker = new GeneralRelationCheck();
	ArrayList<ArrayList<Attribute>> result;
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);

	// Attributes {A,B,C}
	ArrayList<Attribute> attributes = new ArrayList<Attribute>();
	result = new ArrayList<ArrayList<Attribute>>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);

	result = checker.getSubsetOfAttributes(attributes, result);
	assertEquals(6, result.size());

	// Attributes {A,B}
	attributes = new ArrayList<Attribute>();
	result = new ArrayList<ArrayList<Attribute>>();

	attributes.add(attrA);
	attributes.add(attrB);

	result = checker.getSubsetOfAttributes(attributes, result);
	assertEquals(2, result.size());

	// Attributes {A}
	attributes = new ArrayList<Attribute>();
	result = new ArrayList<ArrayList<Attribute>>();

	attributes.add(attrA);

	result = checker.getSubsetOfAttributes(attributes, result);
	assertEquals(0, result.size());

	// Attributes {}
	attributes = new ArrayList<Attribute>();
	result = new ArrayList<ArrayList<Attribute>>();

	result = checker.getSubsetOfAttributes(attributes, result);
	assertEquals(0, result.size());

  }

  @Test
  public void testMakeFdCannonical() {
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);
	Attribute attrE = new Attribute("E", false, false);
	Attribute attrF = new Attribute("F", false, false);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	FunctionalDependency fd;
	ArrayList<FunctionalDependency> result = new ArrayList<FunctionalDependency>();

	// Let the tests begin
	// A==>B
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrB);
	result = checker.makeFdCannonical(fd);
	assertEquals(1, result.size());
	checkRightSideOfFds(result);

	// C==>DE
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrD);
	fd.getTargetAttributes().add(attrE);
	result = checker.makeFdCannonical(fd);
	assertEquals(2, result.size());
	checkRightSideOfFds(result);

	// ABC==>DEF
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getSourceAttributes().add(attrB);
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrD);
	fd.getTargetAttributes().add(attrE);
	fd.getTargetAttributes().add(attrF);
	result = checker.makeFdCannonical(fd);
	assertEquals(3, result.size());
	checkRightSideOfFds(result);

  }

  private void checkRightSideOfFds(ArrayList<FunctionalDependency> fdList) {
	for (FunctionalDependency fd : fdList) {
	  assertEquals(1, fd.getTargetAttributes().size());
	}
  }

  @Test
  public void testIsPKDeterminingEverything() {
	// R=({A,B,C,D,E,F},{A==>B,C==>DE,AC==>F})
	Key testKey;
	GeneralRelationCheck checker = new GeneralRelationCheck();
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

	ArrayList<Attribute> sourceAttributes = new ArrayList<Attribute>();
	ArrayList<Attribute> targetAttributes = new ArrayList<Attribute>();
	ArrayList<Attribute> sourceAttributes2 = new ArrayList<Attribute>();
	ArrayList<Attribute> targetAttributes2 = new ArrayList<Attribute>();
	ArrayList<Attribute> sourceAttributes3 = new ArrayList<Attribute>();
	ArrayList<Attribute> targetAttributes3 = new ArrayList<Attribute>();

	sourceAttributes.add(attrA);
	targetAttributes.add(attrB);
	FunctionalDependency fd1 = new FunctionalDependency(sourceAttributes,
	    targetAttributes);

	sourceAttributes2.add(attrC);
	targetAttributes2.add(attrD);
	targetAttributes2.add(attrE);
	FunctionalDependency fd2 = new FunctionalDependency(sourceAttributes2,
	    targetAttributes2);

	sourceAttributes3.add(attrA);
	sourceAttributes3.add(attrC);
	targetAttributes3.add(attrF);

	FunctionalDependency fd3 = new FunctionalDependency(sourceAttributes3,
	    targetAttributes3);
	ArrayList<FunctionalDependency> fdList = new ArrayList<FunctionalDependency>();
	fdList.add(fd1);
	fdList.add(fd2);
	fdList.add(fd3);

	RelationSchema schema = new RelationSchema("TestRelation", attributes,
	    fdList);

	// Key={A}
	testKey = new Key();
	testKey.getAttributes().add(attrA);
	assertEquals(false, checker.isKeyDeterminingEverything(schema, testKey));

	// Key={C}
	testKey = new Key();
	testKey.getAttributes().add(attrC);
	assertEquals(false, checker.isKeyDeterminingEverything(schema, testKey));

	// Key={AC}
	testKey = new Key();
	testKey.getAttributes().add(attrA);
	testKey.getAttributes().add(attrC);
	assertEquals(true, checker.isKeyDeterminingEverything(schema, testKey));
  }

  @Test
  public void testIsMember() {
	// R=(A,B,C,D,E),{C==>A, BD==>A, D==>BC, D==>BC, A==>E}
	GeneralRelationCheck checker = new GeneralRelationCheck();
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

	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();

	// C==>A
	ArrayList<Attribute> sourceAttributes1 = new ArrayList<Attribute>();
	sourceAttributes1.add(attrC);
	ArrayList<Attribute> targetAttributes1 = new ArrayList<Attribute>();
	targetAttributes1.add(attrA);
	fds.add(new FunctionalDependency(sourceAttributes1, targetAttributes1));

	// BD==>A
	ArrayList<Attribute> sourceAttributes2 = new ArrayList<Attribute>();
	sourceAttributes2.add(attrB);
	sourceAttributes2.add(attrD);
	ArrayList<Attribute> targetAttributes2 = new ArrayList<Attribute>();
	targetAttributes2.add(attrA);
	fds.add(new FunctionalDependency(sourceAttributes2, targetAttributes2));

	// D==>BC
	ArrayList<Attribute> sourceAttributes3 = new ArrayList<Attribute>();
	sourceAttributes3.add(attrD);
	ArrayList<Attribute> targetAttributes3 = new ArrayList<Attribute>();
	targetAttributes3.add(attrB);
	targetAttributes3.add(attrC);
	fds.add(new FunctionalDependency(sourceAttributes3, targetAttributes3));

	// D==>BC
	ArrayList<Attribute> sourceAttributes4 = new ArrayList<Attribute>();
	sourceAttributes4.add(attrD);
	ArrayList<Attribute> targetAttributes4 = new ArrayList<Attribute>();
	targetAttributes4.add(attrB);
	targetAttributes4.add(attrC);
	fds.add(new FunctionalDependency(sourceAttributes4, targetAttributes4));

	// A==>E
	ArrayList<Attribute> sourceAttributes5 = new ArrayList<Attribute>();
	sourceAttributes5.add(attrA);
	ArrayList<Attribute> targetAttributes5 = new ArrayList<Attribute>();
	targetAttributes5.add(attrE);
	fds.add(new FunctionalDependency(sourceAttributes5, targetAttributes5));

	RelationSchema schemaToTest = new RelationSchema("test", attributes, fds);

	// Functional Dependencies to test
	// AC==>B
	ArrayList<Attribute> sourceAttributes6 = new ArrayList<Attribute>();
	sourceAttributes6.add(attrA);
	sourceAttributes6.add(attrC);
	ArrayList<Attribute> targetAttributes6 = new ArrayList<Attribute>();
	targetAttributes6.add(attrB);
	FunctionalDependency testFd1 = new FunctionalDependency(sourceAttributes6,
	    targetAttributes6);

	// BD==>E
	ArrayList<Attribute> sourceAttributes7 = new ArrayList<Attribute>();
	sourceAttributes7.add(attrB);
	sourceAttributes7.add(attrD);
	ArrayList<Attribute> targetAttributes7 = new ArrayList<Attribute>();
	targetAttributes7.add(attrE);
	FunctionalDependency testFd2 = new FunctionalDependency(sourceAttributes7,
	    targetAttributes7);

	assertEquals(false, checker.isMember(schemaToTest, testFd1));
	assertEquals(true, checker.isMember(schemaToTest, testFd2));
  }

  @Test
  public void testGetClosure() {
	// R=({A,B,C,D,E,F},{A==>B,C==>DE,AC==>F})
	ArrayList<Attribute> result = new ArrayList<Attribute>();
	GeneralRelationCheck checker = new GeneralRelationCheck();
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

	ArrayList<Attribute> sourceAttributes = new ArrayList<Attribute>();
	ArrayList<Attribute> targetAttributes = new ArrayList<Attribute>();
	ArrayList<Attribute> sourceAttributes2 = new ArrayList<Attribute>();
	ArrayList<Attribute> targetAttributes2 = new ArrayList<Attribute>();
	ArrayList<Attribute> sourceAttributes3 = new ArrayList<Attribute>();
	ArrayList<Attribute> targetAttributes3 = new ArrayList<Attribute>();

	sourceAttributes.add(attrA);
	targetAttributes.add(attrB);
	FunctionalDependency fd1 = new FunctionalDependency(sourceAttributes,
	    targetAttributes);

	sourceAttributes2.add(attrC);
	targetAttributes2.add(attrD);
	targetAttributes2.add(attrE);
	FunctionalDependency fd2 = new FunctionalDependency(sourceAttributes2,
	    targetAttributes2);

	sourceAttributes3.add(attrA);
	sourceAttributes3.add(attrC);
	targetAttributes3.add(attrF);

	FunctionalDependency fd3 = new FunctionalDependency(sourceAttributes3,
	    targetAttributes3);
	ArrayList<FunctionalDependency> fdList = new ArrayList<FunctionalDependency>();
	fdList.add(fd1);
	fdList.add(fd2);
	fdList.add(fd3);

	// X ={A}
	ArrayList<Attribute> xList = new ArrayList<Attribute>();
	xList.add(attrA);

	RelationSchema schema = new RelationSchema("TestRelation", attributes,
	    fdList);
	result = checker.getClosure(schema, xList);

	assertEquals(2, result.size());
	assertEquals("A,B", Utilities.getStringFromArrayList(result));

	// X ={F}
	xList = new ArrayList<Attribute>();
	xList.add(attrF);
	result = checker.getClosure(schema, xList);
	assertEquals(1, result.size());
	assertEquals("F", Utilities.getStringFromArrayList(result));

	// X ={A,C}
	xList = new ArrayList<Attribute>();
	xList.add(attrA);
	xList.add(attrC);
	result = checker.getClosure(schema, xList);
	assertEquals(6, result.size());
	assertEquals("A,C,B,D,E,F", Utilities.getStringFromArrayList(result));

	// X ={B}
	xList = new ArrayList<Attribute>();
	xList.add(attrB);
	result = checker.getClosure(schema, xList);
	assertEquals(1, result.size());
	assertEquals("B", Utilities.getStringFromArrayList(result));

	// X ={C}
	xList = new ArrayList<Attribute>();
	xList.add(attrC);
	result = checker.getClosure(schema, xList);
	assertEquals(3, result.size());
	assertEquals("C,D,E", Utilities.getStringFromArrayList(result));
  }

  @Test
  public void testAreFdSetsEquivalent() {
	GeneralRelationCheck checker = new GeneralRelationCheck();
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);

	FunctionalDependency tempFd;
	ArrayList<FunctionalDependency> list1;
	ArrayList<FunctionalDependency> list2;

	// F={AB==>C, A==>B, A==>C, B==>C)
	// G={AB==>C, A==>B, , B==>C)
	list1 = new ArrayList<FunctionalDependency>();
	list2 = new ArrayList<FunctionalDependency>();

	// F
	// AB==>C
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrA);
	tempFd.getSourceAttributes().add(attrB);
	tempFd.getTargetAttributes().add(attrC);
	list1.add(tempFd);

	// A==>B
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrA);
	tempFd.getTargetAttributes().add(attrB);
	list1.add(tempFd);

	// A==>C
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrA);
	tempFd.getTargetAttributes().add(attrC);
	list1.add(tempFd);

	// B==>C
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrB);
	tempFd.getTargetAttributes().add(attrC);
	list1.add(tempFd);

	// G
	// AB==>C
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrA);
	tempFd.getSourceAttributes().add(attrB);
	tempFd.getTargetAttributes().add(attrC);
	list2.add(tempFd);

	// A==>B
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrA);
	tempFd.getTargetAttributes().add(attrB);
	list2.add(tempFd);

	// B==>C
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrB);
	tempFd.getTargetAttributes().add(attrC);
	list2.add(tempFd);

	assertEquals(true, checker.areFdSetsEquivalent(list1, list2));
  }

  @Test
  public void testAreFdSetsEquivalent2() {
	GeneralRelationCheck checker = new GeneralRelationCheck();
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);
	Attribute attrE = new Attribute("E", false, false);
	Attribute attrF = new Attribute("F", false, false);

	FunctionalDependency tempFd;
	ArrayList<FunctionalDependency> list1;
	ArrayList<FunctionalDependency> list2;

	// F={C==>A, CE==>B, E==>CD, F==>B)
	// G={C==>A, ,E==>CD, F==>B, E==>A, E==>B)
	list1 = new ArrayList<FunctionalDependency>();
	list2 = new ArrayList<FunctionalDependency>();

	// F
	// C==>A
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrC);
	tempFd.getTargetAttributes().add(attrA);
	list1.add(tempFd);

	// CE==>B
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrC);
	tempFd.getSourceAttributes().add(attrE);
	tempFd.getTargetAttributes().add(attrB);
	list1.add(tempFd);

	// E==>CD
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrE);
	tempFd.getTargetAttributes().add(attrC);
	tempFd.getTargetAttributes().add(attrD);
	list1.add(tempFd);

	// F==>B
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrF);
	tempFd.getTargetAttributes().add(attrB);
	list1.add(tempFd);

	// G
	// C==>A
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrC);
	tempFd.getTargetAttributes().add(attrA);
	list2.add(tempFd);

	// E==>CD
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrE);
	tempFd.getTargetAttributes().add(attrC);
	tempFd.getTargetAttributes().add(attrD);
	list2.add(tempFd);

	// F==>B
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrF);
	tempFd.getTargetAttributes().add(attrB);
	list2.add(tempFd);

	// E==>A
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrE);
	tempFd.getTargetAttributes().add(attrA);
	list2.add(tempFd);

	// E==>B
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrE);
	tempFd.getTargetAttributes().add(attrB);
	list2.add(tempFd);

	assertEquals(true, checker.areFdSetsEquivalent(list1, list2));
  }

  @Test
  public void testAreFdSetsEquivalent3() {
	GeneralRelationCheck checker = new GeneralRelationCheck();
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);
	Attribute attrE = new Attribute("E", false, false);
	Attribute attrH = new Attribute("H", false, false);

	FunctionalDependency tempFd;
	ArrayList<FunctionalDependency> list1;
	ArrayList<FunctionalDependency> list2;

	// F={A==>C, AC==>D, E==>AD, E==>H)
	// G={A==>CD, E==>AH}
	list1 = new ArrayList<FunctionalDependency>();
	list2 = new ArrayList<FunctionalDependency>();

	// F
	// A==>C
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrA);
	tempFd.getTargetAttributes().add(attrC);
	list1.add(tempFd);

	// AC==>D
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrA);
	tempFd.getSourceAttributes().add(attrC);
	tempFd.getTargetAttributes().add(attrD);
	list1.add(tempFd);

	// E==>AD
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrE);
	tempFd.getTargetAttributes().add(attrA);
	tempFd.getTargetAttributes().add(attrD);
	list1.add(tempFd);

	// E==>H
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrE);
	tempFd.getTargetAttributes().add(attrH);
	list1.add(tempFd);

	// G
	// A==>CD
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrA);
	tempFd.getTargetAttributes().add(attrC);
	tempFd.getTargetAttributes().add(attrD);
	list2.add(tempFd);

	// E==>AH
	tempFd = new FunctionalDependency();
	tempFd.getSourceAttributes().add(attrE);
	tempFd.getTargetAttributes().add(attrA);
	tempFd.getTargetAttributes().add(attrH);
	list2.add(tempFd);

	assertEquals(true, checker.areFdSetsEquivalent(list1, list2));
  }
}
