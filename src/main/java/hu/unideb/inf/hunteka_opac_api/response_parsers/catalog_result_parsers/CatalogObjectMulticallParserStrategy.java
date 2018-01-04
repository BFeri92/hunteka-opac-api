package hu.unideb.inf.hunteka_opac_api.response_parsers.catalog_result_parsers;

import hu.unideb.inf.hunteka_opac_api.dto.backend.CatalogObject;
import hu.unideb.inf.hunteka_opac_api.response_parsers.MulticallParserStrategy;

public class CatalogObjectMulticallParserStrategy extends MulticallParserStrategy<CatalogObject> {

	@Override
	protected CatalogObject createParsedElement() {
		return new CatalogObject();
	}

}
