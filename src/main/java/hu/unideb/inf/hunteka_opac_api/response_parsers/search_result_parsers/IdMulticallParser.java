package hu.unideb.inf.hunteka_opac_api.response_parsers.search_result_parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import hu.unideb.inf.hunteka_opac_api.dto.backend.SearchResultEntry;
import hu.unideb.inf.hunteka_opac_api.response_parsers.MulticallParser;

public class IdMulticallParser implements MulticallParser<SearchResultEntry>{

	@Override
	public void parse(Element elementToParse, SearchResultEntry state) {
		String href = elementToParse.attr("href");
		Pattern termFindingPattern = Pattern.compile("(?:term=([^&]*))&");
		Matcher termFindingMatcher = termFindingPattern.matcher(href);
		if (!termFindingMatcher.find())
		{
			System.out.println(href);
			return;
		}
		state.setId(termFindingMatcher.group(1));
	}
	
}
