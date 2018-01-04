package hu.unideb.inf.hunteka_opac_api.response_parsers.catalog_result_parsers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import hu.unideb.inf.hunteka_opac_api.dto.backend.CatalogObject;
import hu.unideb.inf.hunteka_opac_api.dto.backend.CustomCatalogDescriptor;
import hu.unideb.inf.hunteka_opac_api.response_parsers.MulticallParser;

public class CustomCatalogDescriptorMulticallParser implements MulticallParser<CatalogObject> {

	@Override
	public void parse(Element elementToParse, CatalogObject state) {
		Elements rows = elementToParse.select("tr:has(td.label, td.keylabel)");
		for (Element row : rows) {
			Element label = row.select("td.label, td.keylabel").first();
			Element value = row.select("td.value, td.keyvalue").first();
			state.addCustomField(new CustomCatalogDescriptor(label.text(), value.text()));			
		}
	}

}
