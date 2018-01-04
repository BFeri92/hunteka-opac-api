package hu.unideb.inf.hunteka_opac_api.dto.backend;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Book")
@XmlAccessorType(XmlAccessType.FIELD)
public class CatalogObject {
	@XmlElementWrapper(name="Properties")
	@XmlElement(name="Property")
	private List<CustomCatalogDescriptor> customFields = new ArrayList<CustomCatalogDescriptor>();	//Decided not to use fixed fields due to the high variability of the available properties.

	public List<CustomCatalogDescriptor> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(List<CustomCatalogDescriptor> customFields) {
		this.customFields = customFields;
	}
	
	public void addCustomField(CustomCatalogDescriptor catalogDescriptor)
	{
		this.customFields.add(catalogDescriptor);
	}
	
}
