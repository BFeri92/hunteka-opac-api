package hu.unideb.inf.hunteka_opac_api.endpoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.data.Status;

import hu.unideb.inf.hunteka_opac_api.backend.CatalogQueryBuilder;
import hu.unideb.inf.hunteka_opac_api.backend.SearchQueryBuilder;
import hu.unideb.inf.hunteka_opac_api.dto.backend.CatalogObject;
import hu.unideb.inf.hunteka_opac_api.dto.backend.SearchResult;
import hu.unideb.inf.hunteka_opac_api.dto.backend.SearchResultEntry;
import hu.unideb.inf.hunteka_opac_api.dto.query.Query;
import hu.unideb.inf.hunteka_opac_api.query_parsers.QueryParser;
import hu.unideb.inf.hunteka_opac_api.query_parsers.exceptions.SyntaxErrorException;
import hu.unideb.inf.hunteka_opac_api.response_parsers.ResponseParser;
import hu.unideb.inf.hunteka_opac_api.response_parsers.catalog_result_parsers.CatalogObjectMulticallParserStrategy;
import hu.unideb.inf.hunteka_opac_api.response_parsers.catalog_result_parsers.CustomCatalogDescriptorMulticallParser;
import hu.unideb.inf.hunteka_opac_api.response_parsers.search_result_parsers.FullLineMulticallParser;
import hu.unideb.inf.hunteka_opac_api.response_parsers.search_result_parsers.IdMulticallParser;
import hu.unideb.inf.hunteka_opac_api.response_parsers.search_result_parsers.RegexMulticallParser;
import hu.unideb.inf.hunteka_opac_api.response_parsers.search_result_parsers.SearchResultMulticallParserStrategy;

public class CatalogResource extends ServerResource {
	private final String backend = "http://193.225.140.44/monguz-meliusz/";

	@Get("json|xml")
	public CatalogObject represent() {
		try {
			ResponseParser<CatalogObject> responseParser = new ResponseParser<CatalogObject>();
			CatalogObjectMulticallParserStrategy mcallParserStrategy = new CatalogObjectMulticallParserStrategy();
			mcallParserStrategy.addParser(new CustomCatalogDescriptorMulticallParser());
			responseParser.bindStrategy("#containerTD table:has(td.label, td.keylabel)", mcallParserStrategy);
			CatalogQueryBuilder queryBuilder = new CatalogQueryBuilder(backend, this.getQueryValue("id"));
			Document doc = queryBuilder.getConnection().get();
			List<CatalogObject> resultList = responseParser.parse(doc);
			if (resultList.size()==0)
			{
				this.getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
				return null;
			}
			return resultList.get(0);
		} catch (IOException e) {
			e.printStackTrace();
			this.getResponse().setStatus(Status.SERVER_ERROR_GATEWAY_TIMEOUT);
			return null;
		}
	}
}
