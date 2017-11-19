package es.caravelloserver.response;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import es.caravelloserver.CaravelloController;

import es.caravelloserver.model.MarketData;

public class ResponseWrapper extends ResourceSupport{
	
	private String idResponse;
	private List<MarketData> marketData;
	
	public ResponseWrapper(){
		idResponse = "";
		marketData = new ArrayList<MarketData>();
	}
	
	public String getIdResponse() {
		return idResponse;
	}

	public void setIdResponse(String idResponse) {
		this.idResponse = idResponse;
		//this.add(linkTo(methodOn(CaravelloController.class).getMarketSurveysList(idResponse)).withSelfRel());
	}

	public List<MarketData> getMarketData() {
		return marketData;
	}

	public void setMarketData(List<MarketData> marketData) {
		this.marketData = marketData;
	}
	
	
	

}
