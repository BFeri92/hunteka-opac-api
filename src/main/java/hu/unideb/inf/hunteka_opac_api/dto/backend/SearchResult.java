package hu.unideb.inf.hunteka_opac_api.dto.backend;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class SearchResult {
	@XmlElementWrapper(name = "matches")
	@XmlElement(name = "match")
	private List<SearchResultEntry> matches = new ArrayList<SearchResultEntry>();

	public void addMatch(SearchResultEntry match) {
		this.matches.add(match);
	}

	public List<SearchResultEntry> getMatches() {
		return matches;
	}

	public void setMatches(List<SearchResultEntry> matches) {
		this.matches = new ArrayList<SearchResultEntry>(matches);
	}
}
