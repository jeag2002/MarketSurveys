package es.caravelloserver.dao;

import java.util.List;

import es.caravelloserver.bean.request.RequestWrapper;
import es.caravelloserver.model.MarketData;
import es.caravelloserver.response.ResponseWrapper;
import es.caravelloserver.response.ResponseWrapperAsync;

public interface IMarketDao {
	/**
	 * Get List of MarketSurveys from RequestWrapperParameters (Query to DataBase)
	 * @param rWrapper
	 * @return
	 */
	public ResponseWrapper getMarketSurveysList(RequestWrapper rWrapper);
	
	/**
	 * Get List of MarketSurveys from RequestWrapperParameters (Query to DataBase)
	 * @param rWrapper
	 * @return
	 */
	public ResponseWrapperAsync getMarketSurveysListAsync(RequestWrapper rWrapper);
	
	/**
	 * Get MarketSurvey Element from its Id (Query to DataBase)
	 * @param id
	 * @return
	 */
	public List<MarketData> getMarketSurvey(String id);
}
