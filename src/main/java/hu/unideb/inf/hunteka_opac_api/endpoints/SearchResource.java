package hu.unideb.inf.hunteka_opac_api.endpoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.restlet.resource.*;
import org.restlet.data.Status;

import hu.unideb.inf.hunteka_opac_api.backend.SearchQueryBuilder;
import hu.unideb.inf.hunteka_opac_api.dto.backend.SearchResult;
import hu.unideb.inf.hunteka_opac_api.dto.backend.SearchResultEntry;
import hu.unideb.inf.hunteka_opac_api.dto.query.Query;
import hu.unideb.inf.hunteka_opac_api.query_parsers.QueryParser;
import hu.unideb.inf.hunteka_opac_api.query_parsers.exceptions.SyntaxErrorException;
import hu.unideb.inf.hunteka_opac_api.response_parsers.ResponseParser;
import hu.unideb.inf.hunteka_opac_api.response_parsers.search_result_parsers.FullLineMulticallParser;
import hu.unideb.inf.hunteka_opac_api.response_parsers.search_result_parsers.IdMulticallParser;
import hu.unideb.inf.hunteka_opac_api.response_parsers.search_result_parsers.RegexMulticallParser;
import hu.unideb.inf.hunteka_opac_api.response_parsers.search_result_parsers.SearchResultMulticallParserStrategy;

public class SearchResource extends ServerResource{
	
	private final String backend = "http://193.225.140.44/monguz-meliusz/";
	private final int pageSize = 10;

	@Get("json|xml")
	public SearchResult represent()
	{
		List<SearchResultEntry> resultEntries = new ArrayList<SearchResultEntry>();
		String conditionString=getQueryValue("condition");
		QueryParser conditionParser = new QueryParser();
		try {
			Query query = conditionParser.parse(conditionString);
			SearchQueryBuilder queryBuilder = new SearchQueryBuilder(backend, pageSize, query);
			ResponseParser<SearchResultEntry> responseParser = new ResponseParser<SearchResultEntry>();
			SearchResultMulticallParserStrategy mcallParserStrategy = new SearchResultMulticallParserStrategy();
			mcallParserStrategy.addParser(new FullLineMulticallParser());
			mcallParserStrategy.addParser(new IdMulticallParser());
			mcallParserStrategy.addParser(new RegexMulticallParser());
			responseParser.bindStrategy("#containerTD table td>div>span>a.reclink", mcallParserStrategy);
			boolean emptyResult = false;
			do {
				Connection connection = queryBuilder.getConnection();
				queryBuilder.pageForward();
				Document doc = connection.get();
				List<SearchResultEntry> currentResultEntries = responseParser.parse(doc);
				resultEntries.addAll(currentResultEntries);
				if (currentResultEntries.size()==0)
				{
					emptyResult=true;
				}
			} while (!emptyResult);
			SearchResult result = new SearchResult();
			result.setMatches(resultEntries);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			this.getResponse().setStatus(Status.SERVER_ERROR_GATEWAY_TIMEOUT);
			return null;
		} catch (SyntaxErrorException e) {
			System.out.println(e.getExpression());
			this.getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return null;
		}
	}
}
