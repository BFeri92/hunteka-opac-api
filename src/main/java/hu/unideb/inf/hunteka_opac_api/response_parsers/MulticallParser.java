package hu.unideb.inf.hunteka_opac_api.response_parsers;

import org.jsoup.nodes.Element;

public interface MulticallParser<ReturnType> {
	public void parse(Element elementToParse, ReturnType state);
}
