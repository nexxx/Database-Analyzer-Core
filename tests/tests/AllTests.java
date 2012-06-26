

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestUtilities.class, RelationCheckTest.class,
    GeneralRelationCheckTest.class, DecompositionTo2NFTest.class,
    DecompositionTo3NFTest.class, DecompositionToBCNFTest.class,
    SyntheseTo3NFTest.class })
public class AllTests {

}
