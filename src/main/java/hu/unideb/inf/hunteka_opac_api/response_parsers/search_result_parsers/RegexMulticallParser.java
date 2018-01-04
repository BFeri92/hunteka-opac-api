package hu.unideb.inf.hunteka_opac_api.response_parsers.search_result_parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import hu.unideb.inf.hunteka_opac_api.dto.backend.SearchResultEntry;
import hu.unideb.inf.hunteka_opac_api.response_parsers.MulticallParser;

public class RegexMulticallParser implements MulticallParser<SearchResultEntry>{

	private String[] documentTypes = {
			"nyomtatott anyag",
			"kotta"
	};
	
	@Override
	public void parse(Element elementToParse, SearchResultEntry state) {
		Pattern propertyExtractorPattern = Pattern.compile("^(?<title>[^\\/\\[]*)\\s*(?:\\/\\s*"
				+ "(?<author>[^\\[]*))?\\s*"
				+ "(?:(?<other>\\[.*\\])(?=.*\\[[^\\]]*\\]))?\\s*"
				+ "(?:\\[(?<documentType>"+String.join("|", documentTypes)+")\\]|(?<finalOther>\\[[^\\]]*\\]))?\\s*"
				+ "(?:,\\s*(?<year>[0-9]+))?\\s*"
				+ "(?:\\((?<storageClass>[^\\)]+)\\))?.*$");
		Matcher propertyExtractorMatcher = propertyExtractorPattern.matcher(elementToParse.ownText());
		if (!propertyExtractorMatcher.matches())
		{
			return;
		}
		state.setTitle(propertyExtractorMatcher.group("title"));
		state.setAuthor(propertyExtractorMatcher.group("author"));
		state.setDocumentType(propertyExtractorMatcher.group("documentType"));
		state.setYear(propertyExtractorMatcher.group("year"));
		state.setStorageClass(propertyExtractorMatcher.group("storageClass"));
		String other = propertyExtractorMatcher.group("other");
		Pattern otherExtractorPattern = Pattern.compile("\\[([^\\]]*)\\]");
		if (other!=null)
		{
			Matcher otherExtractorMatcher = otherExtractorPattern.matcher(other);
			while (otherExtractorMatcher.find())
			{
				state.addOther(otherExtractorMatcher.group());
			}
		}
		if (propertyExtractorMatcher.group("finalOther")!=null)
		{
			Matcher finalOtherExtractorMatcher = otherExtractorPattern.matcher(propertyExtractorMatcher.group("finalOther"));
			while (finalOtherExtractorMatcher.find())
			{
				state.addOther(finalOtherExtractorMatcher.group());
			}
		}
	}

}
