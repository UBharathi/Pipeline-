import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;

import static org.daisy.common.file.URIs.asURI;
import org.daisy.pipeline.braille.common.AbstractBrailleTranslator;
import org.daisy.pipeline.braille.common.BrailleTranslator;
import org.daisy.pipeline.braille.common.BrailleTranslatorProvider;
import org.daisy.pipeline.braille.common.CSSStyledText;
import org.daisy.pipeline.braille.common.Query;
import org.daisy.pipeline.braille.common.TransformProvider;

import org.ops4j.pax.exam.util.PathUtils;

import org.osgi.service.component.annotations.Component;

import org.slf4j.Logger;

public class UppercaseTransform extends AbstractBrailleTranslator implements BrailleTranslator {
	
	private final URI href = asURI(new File(new File(PathUtils.getBaseDir()), "target/test-classes/uppercase.xpl"));
	
	public FromStyledTextToBraille fromStyledTextToBraille() {
		return fromStyledTextToBraille;
	}
	
	private FromStyledTextToBraille fromStyledTextToBraille = new FromStyledTextToBraille() {
		public Iterable<String> transform(Iterable<CSSStyledText> styledText, int from, int to) {
			if (from != 0 && to >= 0)
				throw new UnsupportedOperationException();
			List<String> ret = new ArrayList<String>();
			for (CSSStyledText t : styledText)
				ret.add(t.getText().toUpperCase());
			return ret;
		}
	};
	
	@Override
	public XProc asXProc() {
		return new XProc(href, null, null);
	}
	
	private static final Iterable<UppercaseTransform> empty = Optional.<UppercaseTransform>absent().asSet();
	
	private static final Iterable<UppercaseTransform> instance = Optional.of(new UppercaseTransform()).asSet();
	
	@Component(
		name = "UppercaseTransform.Provider",
		service = {
			BrailleTranslatorProvider.class,
			TransformProvider.class
		}
	)
	public static class Provider implements BrailleTranslatorProvider<UppercaseTransform> {
		private Logger logger;
		public Provider() {
		}
		private Provider(Logger context) {
			logger = context;
		}
		public Iterable<UppercaseTransform> get(Query query) {
			if (query.toString().equals("(uppercase)")) {
				if (logger != null)
					logger.info("Selecting " + instance);
				return instance; }
			else
				return empty;
		}
		public TransformProvider<UppercaseTransform> withContext(Logger context) {
			return new Provider(context);
		}
	}
}