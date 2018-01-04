package hu.unideb.inf.hunteka_opac_api.response_parsers;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ResponseParser<ParsedType> {
	
	private List<Entry<String, ParserStrategy<ParsedType>>> strategies = new ArrayList<Entry<String, ParserStrategy<ParsedType>>>();
	
	public void bindStrategy(String selector, ParserStrategy<ParsedType> strategy)
	{
		strategies.add(new SimpleEntry<String, ParserStrategy<ParsedType>>(selector, strategy));
	}
	
	public List<ParsedType> parse(Document doc)
	{
		List<ParsedType> result = new ArrayList<ParsedType>();
		for (Entry<String, ParserStrategy<ParsedType>> strategyDef: strategies)
		{
			Elements elementsToParse = doc.select(strategyDef.getKey());
			if (elementsToParse.size()!=0)
			{
				result.addAll(strategyDef.getValue().parse(elementsToParse));
			}
		}
		return result;
	}
}
