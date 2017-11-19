package es.caravelloserver.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.caravelloserver.CaravelloController;
import es.caravelloserver.bean.request.RequestWrapper;
import es.caravelloserver.dao.IMarketDao;
import es.caravelloserver.dao.MarketDao;
import es.caravelloserver.model.MarketData;
import es.caravelloserver.model.MarketDataAsync;
import es.caravelloserver.response.ResponseWrapper;
import es.caravelloserver.response.ResponseWrapperAsync;

@Service
public class MarketService implements IMarketService {
	@Autowired
	private IMarketDao marketDao;
	
	/**
	 * Cache Collection. UUID Id Of Previous Queries
	 */
	private static ConcurrentHashMap<String,List<MarketData>> resultCache = new ConcurrentHashMap<String,List<MarketData>>();
	
	private final Logger logger = LoggerFactory.getLogger(MarketService.class);
	
	/**
	 * Get info data from request parameters.
	 */
	public ResponseWrapper getMarketSurveysList(RequestWrapper rWrapper){
		ResponseWrapper respWrapper = marketDao.getMarketSurveysList(rWrapper);
		resultCache.put(respWrapper.getIdResponse(), respWrapper.getMarketData());
		List<String> keys = new ArrayList<>(resultCache.keySet());
		logger.info("[getMarketSurveysList] registerKeysCache::(" + keys + ")");
		return respWrapper;
	}
	
	
	/**
	 * Get info data from request parameters (Async)
	 */
	public ResponseWrapperAsync getMarketSurveysListAsync(RequestWrapper rWrapper){
		ResponseWrapperAsync respWrapper = marketDao.getMarketSurveysListAsync(rWrapper);
		
		resultCache.put(respWrapper.getIdResponse(), moveFromMarketDataAsync(respWrapper.getMarketData()));
		List<String> keys = new ArrayList<>(resultCache.keySet());
		logger.info("[getMarketSurveysListAsync] registerKeysCache::(" + keys + ")");
		
		return respWrapper;
	}
	

	/**
	 * Get MarketData register from Id
	 */
	public List<MarketData> getMarketSurvey(String id){
		return marketDao.getMarketSurvey(id);
	}
	
	/**
	 * Get previously cached MarketSurvey Response Data
	 */
	public ResponseWrapper getMarketSurveyCache(String id){
		ResponseWrapper responseWrapper = new ResponseWrapper();
		List<MarketData> data = new ArrayList<MarketData>();
		if (resultCache.containsKey(id)){
			data.addAll(resultCache.get(id));
		}
		
		logger.info("[getMarketSurveyCache] id::(" + id + ") data_size::(" + data.size() + ")");
		
		responseWrapper.setIdResponse(id);
		responseWrapper.getMarketData().addAll(data);
		responseWrapper.add(linkTo(methodOn(CaravelloController.class).getMarketSurveysList(responseWrapper.getIdResponse())).withSelfRel());
		
		return responseWrapper;
	}
	
	
	private List<MarketData> moveFromMarketDataAsync(List<MarketDataAsync> data){
		List<MarketData> out = new ArrayList<MarketData>();
		
		for(int i=0; i<data.size(); i++){
			MarketData elem = new MarketData();
			elem.setAge(data.get(i).getAge());
			elem.setChannel(data.get(i).getChannel());
			elem.setCountry(data.get(i).getCountry());
			elem.setCurrency(data.get(i).getCurrency());
			elem.setFrecuency(data.get(i).getFrecuency());
			elem.setGender(data.get(i).getGender());
			elem.setIncome(data.get(i).getIncome());
			elem.setMarketId(data.get(i).getMarketId());
			elem.setProviderId(data.get(i).getProviderId());
			out.add(elem);
		}
		
		return out;
	}
	
	
}
