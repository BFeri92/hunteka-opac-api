package hu.unideb.inf.hunteka_opac_api.response_parsers.search_result_parsers;

import hu.unideb.inf.hunteka_opac_api.dto.backend.SearchResultEntry;
import hu.unideb.inf.hunteka_opac_api.response_parsers.MulticallParserStrategy;

public class SearchResultMulticallParserStrategy extends MulticallParserStrategy<SearchResultEntry> {

	@Override
	protected SearchResultEntry createParsedElement() {
		return new SearchResultEntry();
	}

}
