package hu.unideb.inf.hunteka_opac_api.backend;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;

public class CatalogQueryBuilder {
	
	private String backend;
	private String id;
	
	public CatalogQueryBuilder(String backend, String id) {
		super();
		this.backend = backend;
		this.id = id;
	}

	private String createConnectionString(String id)
	{
		return backend+"index.jsp?page=details&dbname=database&bib1id=4&bib1field=0&term="+id;
	}
	
	public Connection getConnection() throws IOException {
		Connection result = null;
		result = Jsoup.connect(createConnectionString(id));
		result.execute();
		return result;
	}
}
