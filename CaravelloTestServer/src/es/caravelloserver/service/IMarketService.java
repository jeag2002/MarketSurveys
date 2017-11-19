package es.caravelloserver.service;

import java.util.List;

import es.caravelloserver.bean.request.RequestWrapper;
import es.caravelloserver.model.MarketData;
import es.caravelloserver.response.ResponseWrapper;
import es.caravelloserver.response.ResponseWrapperAsync;

public interface IMarketService {
	
	/**
	 * Get List of MarketSurveys from RequestWrapper parameters
	 * @param rWrapper
	 * @return
	 */
	public ResponseWrapper getMarketSurveysList(RequestWrapper rWrapper);
	
	/**
	 * Get List of MarketSurveys from RequestWrapper parameters Async
	 * @param rWrapper
	 * @return
	 */
	public ResponseWrapperAsync getMarketSurveysListAsync(RequestWrapper rWrapper);
	
	/**
	 * Get MarketSurvey data from it Id
	 * @param id
	 * @return
	 */
	public List<MarketData> getMarketSurvey(String id);
	
	/**
	 * Get previous MarketSurvey List cached
	 * @param id
	 * @return
	 */
	public ResponseWrapper getMarketSurveyCache(String id);

}
