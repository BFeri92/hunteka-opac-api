package hu.unideb.inf.hunteka_opac_api.response_parsers;

import java.util.List;


import org.jsoup.select.Elements;

public interface ParserStrategy<ReturnType> {
	public List<ReturnType> parse(Elements element);
}
