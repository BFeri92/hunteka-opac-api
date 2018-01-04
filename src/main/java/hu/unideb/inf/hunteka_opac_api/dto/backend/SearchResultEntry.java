package hu.unideb.inf.hunteka_opac_api.dto.backend;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResultEntry {
	@XmlElement
	private String fullLine = null;
	@XmlElement
	private String id = null;
	@XmlElement
	private String title = null;
	@XmlElement
	private String documentType = null;
	@XmlElement
	private String author = null;
	@XmlElement
	private String storageClass = null;
	@XmlElement
	private String year = null;
	@XmlElementWrapper(name="others")
	@XmlElement(name="otherProperty")
	private List<String> others = new ArrayList<String>();
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getStorageClass() {
		return storageClass;
	}
	public void setStorageClass(String storageClass) {
		this.storageClass = storageClass;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public List<String> getOthers() {
		return others;
	}
	
	public void addOther(String otherData)
	{
		this.others.add(otherData);
	}
	
	public void setOthers(List<String> others) {
		this.others = new ArrayList<String>(others);
	}
	public String getFullLine() {
		return fullLine;
	}
	public void setFullLine(String fullLine) {
		this.fullLine = fullLine;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return this.getFullLine()+"\n"
				+ "Id: "+getId()+"\n"
				+ "Title: "+getTitle()+"\n"
				+ "Author: "+getAuthor()+"\n"
				+ "Document type: "+getDocumentType()+"\n"
				+ "Storage class: "+getStorageClass()+"\n"
				+ "Year: "+getYear()+"\n"
				+ "Others: "+getOthers()+"\n";
	}
	
}
