package hu.unideb.inf.hunteka_opac_api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import hu.unideb.inf.hunteka_opac_api.endpoints.CatalogResource;
import hu.unideb.inf.hunteka_opac_api.endpoints.SearchResource;

public class ApiServer extends Application {
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.setDefaultMatchingQuery(true);
		//router.attach("http://localhost:8111/book/{id}", BookResource.class);
		router.attach("http://localhost:8111/search?condition={condition}", SearchResource.class);
		router.attach("http://localhost:8111/catalog?id={id}", CatalogResource.class);
		return router;
	}
}
