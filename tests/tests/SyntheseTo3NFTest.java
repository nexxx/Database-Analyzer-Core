

import data.Attribute;
import data.FunctionalDependency;
import data.NormalizationResult;
import data.RelationSchema;
import logic.Analysis.GeneralRelationCheck;
import logic.normalization.SyntheseTo3NF;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SyntheseTo3NFTest {

  @Test
  public void testNormalize1() {
	// Relation is not in 3.NF
	// R=({A,B,C,D,E,F,G,H,I,J}, {A==>GH, C==>A, B==>IJ, F==>B,
	// CF==>D, G==>AI})
	Attribute attrA = new Attribute("A", false, false);
	Attribute attrB = new Attribute("B", false, false);
	Attribute attrC = new Attribute("C", false, false);
	Attribute attrD = new Attribute("D", false, false);
	Attribute attrE = new Attribute("E", false, false);
	Attribute attrF = new Attribute("F", false, false);
	Attribute attrG = new Attribute("G", false, false);
	Attribute attrH = new Attribute("H", false, false);
	Attribute attrI = new Attribute("I", false, false);
	Attribute attrJ = new Attribute("J", false, false);

	ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	attributes.add(attrA);
	attributes.add(attrB);
	attributes.add(attrC);
	attributes.add(attrD);
	attributes.add(attrE);
	attributes.add(attrF);
	attributes.add(attrG);
	attributes.add(attrH);
	attributes.add(attrI);
	attributes.add(attrJ);

	GeneralRelationCheck checker = new GeneralRelationCheck();
	SyntheseTo3NF synthese = new SyntheseTo3NF();
	ArrayList<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	FunctionalDependency fd;

	// A==>GH
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrA);
	fd.getTargetAttributes().add(attrG);
	fd.getTargetAttributes().add(attrH);
	fds.add(fd);

	// C==>A
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getTargetAttributes().add(attrA);
	fds.add(fd);

	// B==>IJ
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrB);
	fd.getTargetAttributes().add(attrI);
	fd.getTargetAttributes().add(attrJ);
	fds.add(fd);

	// F==>B
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrF);
	fd.getTargetAttributes().add(attrB);
	fds.add(fd);

	// CF==>D
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrC);
	fd.getSourceAttributes().add(attrF);
	fd.getTargetAttributes().add(attrD);
	fds.add(fd);

	// G==>AI
	fd = new FunctionalDependency();
	fd.getSourceAttributes().add(attrG);
	fd.getTargetAttributes().add(attrA);
	fd.getTargetAttributes().add(attrI);
	fds.add(fd);

	RelationSchema schema = new RelationSchema("test");
	schema.getAttributes().addAll(attributes);
	schema.getFunctionalDependencies().addAll(fds);

	assertEquals(false, checker.isThirdNF(schema));

	NormalizationResult result = new NormalizationResult();

	synthese.normalize(schema, result, true);
	assertEquals(
	    "[test1 (A<pk>,G,H,I), test2 (C<pk>,A<fk>), test3 (B<pk>,I,J), test4 (F<pk>,B<fk>), test5 (C<pk><fk>,F<pk><fk>,D), test6 (E<pk>,C<pk><fk>,F<pk><fk>)]",
	    result.getRelations().toString());
  }

}
