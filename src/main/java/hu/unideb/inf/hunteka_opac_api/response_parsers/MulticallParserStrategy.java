package hu.unideb.inf.hunteka_opac_api.response_parsers;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class MulticallParserStrategy<ReturnType> implements ParserStrategy<ReturnType>{
	
	private List<MulticallParser<ReturnType>> parsers = new ArrayList<MulticallParser<ReturnType>>();

	public void addParser(MulticallParser<ReturnType> parserToAdd)
	{
		parsers.add(parserToAdd);
	}
	
	@Override
	public List<ReturnType> parse(Elements elements) {
		List<ReturnType> result = new ArrayList<ReturnType>();
		for (Element element : elements)
		{
			ReturnType parsedElement = this.createParsedElement();
			for(MulticallParser<ReturnType> parser : parsers)
			{
				parser.parse(element, parsedElement);
			}
			result.add(parsedElement);
		}
		return result;
	}
	
	protected abstract ReturnType createParsedElement();

}
