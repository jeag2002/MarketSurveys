package es.caravelloserver.response;


import java.util.ArrayList;
import java.util.List;


import es.caravelloserver.model.MarketDataAsync;

public class ResponseWrapperAsync{
	
	private String idResponse;
	private List<MarketDataAsync> marketData;
	
	public ResponseWrapperAsync(){
		idResponse = "";
		marketData = new ArrayList<MarketDataAsync>();
	}
	
	public String getIdResponse() {
		return idResponse;
	}

	public void setIdResponse(String idResponse) {
		this.idResponse = idResponse;
	}

	public List<MarketDataAsync> getMarketData() {
		return marketData;
	}

	public void setMarketData(List<MarketDataAsync> marketData) {
		this.marketData = marketData;
	}
	
	
	

}
