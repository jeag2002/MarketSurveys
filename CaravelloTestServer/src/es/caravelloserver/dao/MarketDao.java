package es.caravelloserver.dao;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.caravelloserver.CaravelloController;
import es.caravelloserver.bean.request.RequestWrapper;
import es.caravelloserver.model.MarketData;
import es.caravelloserver.model.MarketDataAsync;
import es.caravelloserver.response.ResponseWrapper;
import es.caravelloserver.response.ResponseWrapperAsync;
import es.caravelloserver.utils.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class MarketDao implements IMarketDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired 
	private JdbcTemplate jtm;
	
	private final Logger logger = LoggerFactory.getLogger(MarketDao.class);
	
	public ResponseWrapperAsync getMarketSurveysListAsync(RequestWrapper rWrapper){
		String sql = "SELECT * FROM MarketData where providerId = '" + rWrapper.getProvider().getId().toLowerCase() + "'";
			   sql += " and gender = '" + rWrapper.getSurvey().getTarget().getGender().toString().toLowerCase() + "'";
			   if (rWrapper.getSurvey().getTarget().getAge().size() == 2){
				   sql += " and age between " + rWrapper.getSurvey().getTarget().getAge().get(0) + " and " + rWrapper.getSurvey().getTarget().getAge().get(1) + " ";
			   }
			   sql += " and currency = '" + rWrapper.getSurvey().getTarget().getIncome().getCurrency().toString().toLowerCase() + "'";
			   if (rWrapper.getSurvey().getTarget().getIncome().getRange().size() == 2){
				   sql += " and income between " + rWrapper.getSurvey().getTarget().getIncome().getRange().get(0) + " and " + rWrapper.getSurvey().getTarget().getIncome().getRange().get(1) + " ";
			   }
			   sql += " and country = '" + rWrapper.getSurvey().getCountry().toString().toLowerCase() + "'";
			   sql += " and frecuency = '" + rWrapper.getSubscription().getFrecuency().toString().toLowerCase() + "'";
			   
			   if (rWrapper.getSubscription().getChannel().size() > 0){
				   sql += " and channel in ("; 
				   for(int i=0; i<rWrapper.getSubscription().getChannel().size(); i++){
					   sql += "'" + rWrapper.getSubscription().getChannel().get(i).toString().toLowerCase() + "'";
					   if (i < (rWrapper.getSubscription().getChannel().size()-2)){
						  sql += ","; 
					   }
				   }
				   sql += ")";
			   } 
			   
		List<MarketDataAsync> data = jtm.query(sql, new BeanPropertyRowMapper(MarketDataAsync.class));
		
		logger.info("[getMarketSurveysList] sql::(" + sql + ") result::(" + data + ")");
		
		String uuid = StringUtils.getUUID();
		
		ResponseWrapperAsync responseWrapper = new ResponseWrapperAsync();
		responseWrapper.getMarketData().addAll(data);
		responseWrapper.setIdResponse(uuid);
		
		return responseWrapper;
	}
	
	
	
	
	
	
	
	/**
	 * Query Market Survey data from RequestWrapper input parameters
	 */
	
	public ResponseWrapper getMarketSurveysList(RequestWrapper rWrapper){
		String sql = "SELECT * FROM MarketData where providerId = '" + rWrapper.getProvider().getId().toLowerCase() + "'";
			   sql += " and gender = '" + rWrapper.getSurvey().getTarget().getGender().toString().toLowerCase() + "'";
			   if (rWrapper.getSurvey().getTarget().getAge().size() == 2){
				   sql += " and age between " + rWrapper.getSurvey().getTarget().getAge().get(0) + " and " + rWrapper.getSurvey().getTarget().getAge().get(1) + " ";
			   }
			   sql += " and currency = '" + rWrapper.getSurvey().getTarget().getIncome().getCurrency().toString().toLowerCase() + "'";
			   if (rWrapper.getSurvey().getTarget().getIncome().getRange().size() == 2){
				   sql += " and income between " + rWrapper.getSurvey().getTarget().getIncome().getRange().get(0) + " and " + rWrapper.getSurvey().getTarget().getIncome().getRange().get(1) + " ";
			   }
			   sql += " and country = '" + rWrapper.getSurvey().getCountry().toString().toLowerCase() + "'";
			   sql += " and frecuency = '" + rWrapper.getSubscription().getFrecuency().toString().toLowerCase() + "'";
			   
			   if (rWrapper.getSubscription().getChannel().size() > 0){
				   sql += " and channel in ("; 
				   for(int i=0; i<rWrapper.getSubscription().getChannel().size(); i++){
					   sql += "'" + rWrapper.getSubscription().getChannel().get(i).toString().toLowerCase() + "'";
					   if (i < (rWrapper.getSubscription().getChannel().size()-2)){
						  sql += ","; 
					   }
				   }
				   sql += ")";
			   } 
			   
		List<MarketData> data = jtm.query(sql, new BeanPropertyRowMapper(MarketData.class));
		
		for(int i=0; i<data.size(); i++){
			data.get(i).add(linkTo(methodOn(CaravelloController.class).getMarketSurveysByMarketId(String.valueOf(data.get(i).getMarketId()))).withSelfRel());
		}
		
		
		logger.info("[getMarketSurveysList] sql::(" + sql + ") result::(" + data + ")");
		
		String uuid = StringUtils.getUUID();
		
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.getMarketData().addAll(data);
		responseWrapper.setIdResponse(uuid);
		responseWrapper.add(linkTo(methodOn(CaravelloController.class).getMarketSurveysList(responseWrapper.getIdResponse())).withSelfRel());
		
		return responseWrapper;
	}
	
	/**
	 * Query Market Survey register from Id
	 */
	public List<MarketData> getMarketSurvey(String id){
		String sql = "SELECT * FROM MarketData where marketid = " + id;
		List<MarketData> data = jtm.query(sql, new BeanPropertyRowMapper(MarketData.class));
		logger.info("[getMarketSurvey] sql::(" + sql + ") result::(" + data + ")");
		
		for(int i=0; i<data.size(); i++){
			data.get(i).add(linkTo(methodOn(CaravelloController.class).getMarketSurveysByMarketId(String.valueOf(data.get(i).getMarketId()))).withSelfRel());
		}
		
		return data;
	}
	
	

}
