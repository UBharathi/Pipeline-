import org.daisy.pipeline.junit.AbstractXSpecAndXProcSpecTest;

import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestProbeBuilder;

public class XProcSpecTest extends AbstractXSpecAndXProcSpecTest {
	
	@Override
	protected String[] testDependencies() {
		return new String[] {
			pipelineModule("common-utils"),
			pipelineModule("css-utils"),
			pipelineModule("daisy3-utils"),
			// pipelineModule("dtbook-tts"),
			pipelineModule("dtbook-break-detection"),
			pipelineModule("dtbook-utils"),
			pipelineModule("fileset-utils"),
			pipelineModule("file-utils"),
			pipelineModule("smil-utils"),
			pipelineModule("nlp-omnilang-lexer"),
			pipelineModule("audio-encoder-lame"),
			pipelineModule("tts-common"),
		};
	}
	
	@ProbeBuilder
	public TestProbeBuilder probeConfiguration(TestProbeBuilder probe) {
		probe.setHeader("Bundle-Name", "test-module");
		// FIXME: can not delete this yet because it can not be generated with maven-bundle-plugin
		probe.setHeader("Service-Component", "OSGI-INF/module.xml,OSGI-INF/mock-tts.xml");
		return probe;
	}
}
