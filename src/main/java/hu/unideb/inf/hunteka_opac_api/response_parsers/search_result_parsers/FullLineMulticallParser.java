package hu.unideb.inf.hunteka_opac_api.response_parsers.search_result_parsers;

import org.jsoup.nodes.Element;

import hu.unideb.inf.hunteka_opac_api.dto.backend.SearchResultEntry;
import hu.unideb.inf.hunteka_opac_api.response_parsers.MulticallParser;

public class FullLineMulticallParser implements MulticallParser<SearchResultEntry>{

	@Override
	public void parse(Element elementToParse, SearchResultEntry currentState) {
		currentState.setFullLine(elementToParse.ownText());
	}

}
