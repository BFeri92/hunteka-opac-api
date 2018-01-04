package hu.unideb.inf.hunteka_opac_api.backend;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import hu.unideb.inf.hunteka_opac_api.dto.query.BinaryLogicCondition.OperatorValue;
import hu.unideb.inf.hunteka_opac_api.dto.query.Query;

public class SearchQueryBuilder implements Pageable {
	private String backend;
	private Integer pageOffset;
	private Integer pageSize;
	private Query query;
	private String session;
	
	public SearchQueryBuilder(String backend, int pageSize, Query query) {
		super();
		this.backend = backend;
		this.pageOffset = 1;
		this.pageSize = pageSize;
		this.query = query;
		this.session = null;
	}

	@Override
	public void pageForward() {
		this.pageOffset+=pageSize;
	}
	
	private String createFirstConnectionString() throws UnsupportedEncodingException {
		//TODO: move this out and inject it as a dependency
		//TODO: get the options and operators automatically from the web page
		ConditionBuilder conditionBuilder = new ConditionBuilder();
		conditionBuilder.specifyOptionId("Szerző", "1003-0");
		conditionBuilder.specifyOptionId("Összes cím", "65-0");
		conditionBuilder.specifyOptionId("Tárgyszó", "21-0");
		conditionBuilder.specifyOptionId("Megjelenés éve", "31-0");
		conditionBuilder.specifyOptionId("Kiadó neve", "1018-0");
		conditionBuilder.specifyOptionId("Dokumentumtípus", "1001-0");
		conditionBuilder.specifyOptionId("Információhordozó", "2009-0");
		conditionBuilder.specifyOptionId("Lelőhely", "2030-0");
		conditionBuilder.specifyOptionId("Cím", "4-0");
		conditionBuilder.specifyOptionId("Sorozat címe", "5-0");
		conditionBuilder.specifyOptionId("Kiadás helye", "59-0");
		conditionBuilder.specifyOptionId("Nyelv", "54-0");
		conditionBuilder.specifyOptionId("Gyűjtemény", "2111-0");
		conditionBuilder.specifyOptionId("Forrásdokumentum", "4-5");
		conditionBuilder.specifyOptionId("ISBN/ISSN", "7-1");
		conditionBuilder.specifyOptionId("Raktári szakjel", "53-0");
		conditionBuilder.specifyOptionId("Szerepnevek (zenei)", "2020-0");
		conditionBuilder.specifyOptionId("Hangszerek, hangfajok", "2091-0");
		conditionBuilder.specifyOptionId("Osszes megjegyzes - 500", "64-0");
		
		conditionBuilder.SpecifyLogicOperatorId(OperatorValue.AND, 1);
		conditionBuilder.SpecifyLogicOperatorId(OperatorValue.OR, 2);
		conditionBuilder.SpecifyLogicOperatorId(OperatorValue.AND_NOT, 3);
		
		return backend+"index.jsp?page=result&new=1&group=0&"
				+ conditionBuilder.buildCondition(query)
				+ "stepsize="+pageSize+"&"
				+ "dbname=database";
	}
	
	private String createConnectionString()
	{
		return backend+"index.jsp?stepsize="+pageSize+"&offset="+pageOffset+"&page=result";
	}
	
	public Connection getConnection() throws IOException {
		Connection result = null;
		if (session==null)
		{
			result = Jsoup.connect(createFirstConnectionString());
			Response response = result.execute();
			System.out.println(createFirstConnectionString());
			session = response.cookie("JSESSIONID");
		} else {
			result = Jsoup.connect(createConnectionString()).cookie("JSESSIONID", session);
			result.execute();
		}
		return result;
	}

}
