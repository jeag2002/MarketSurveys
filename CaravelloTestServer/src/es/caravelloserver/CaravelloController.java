package es.caravelloserver;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import es.caravelloserver.bean.request.RequestWrapper;
import es.caravelloserver.model.MarketData;
import es.caravelloserver.response.ResponseWrapper;
import es.caravelloserver.response.ResponseWrapperAsync;
import es.caravelloserver.service.IMarketService;
import es.caravelloserver.service.MarketService;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class CaravelloController {
	
	private final Logger logger = LoggerFactory.getLogger(CaravelloController.class);
	
	@Autowired
	private IMarketService marketService;
	
	private static final ExecutorService ex = Executors.newFixedThreadPool(10);
	
	/**
	 * Get Market Survey List from RequestWrapper query (POST)
	 * @param requestWrapper
	 * @return
	 */
	@ApiOperation(value="getmarketsurveyslist",nickname="getmarketsurveyslist")
	@RequestMapping(value = "/getmarketsurveyslist", method = RequestMethod.POST)
    public ResponseEntity<ResponseWrapper> getMarketSurveysList(@RequestBody RequestWrapper requestWrapper) {
		logger.info("[CaravelloControler] -- getmarketsurveyslist POST");
		ResponseWrapper responseWrapper = marketService.getMarketSurveysList(requestWrapper);
		return new ResponseEntity<ResponseWrapper>(responseWrapper, HttpStatus.OK);
    }
	
	/**
	 * Get Market Survey List from RequestWrapper query (async response POST)
	 * @param requestWrapper
	 * @return
	 */
	@ApiOperation(value="asyncgetmarketsurveyslist",nickname="asyncgetmarketsurveyslist")
	@RequestMapping(value = "/asyncgetmarketsurveyslist", method = RequestMethod.POST)
	public DeferredResult<ResponseWrapperAsync> getAsyncMarketSurveysList(@RequestBody RequestWrapper requestWrapper){
		
		logger.info("[CaravelloControler] -- asyncgetmarketsurveyslist POST");
		
		
		DeferredResult<ResponseWrapperAsync> dr = new DeferredResult<ResponseWrapperAsync>();
		
		final RequestWrapper _reqWrap = requestWrapper;
		new Thread(()->{
			dr.setResult(marketService.getMarketSurveysListAsync(_reqWrap));
		},"").start();
		
		/*
		CompletableFuture.supplyAsync(
		()->{return marketService.getMarketSurveysList(requestWrapper);},ex).
		thenAccept((ResponseWrapper responseWrapper)->{dr.setResult(responseWrapper);});
		*/
		
		return dr;
	}
	
	/**
	 * Get Market Survey List query cached from Id (response GET)
	 * @param uuid: Id of previous query response
	 * @return
	 */
	@ApiOperation(value="getmarketsurveyslistcache",nickname="getmarketsurveyslistcache")
	@RequestMapping(value = "/getmarketsurveyslistcache", method = RequestMethod.GET)
    public HttpEntity<ResponseWrapper> getMarketSurveysList(@RequestParam(value = "uuid") String uuid) {
		logger.info("[CaravelloControler] -- getmarketsurveyslistcache GET");
		ResponseWrapper responseWrapper = marketService.getMarketSurveyCache(uuid);
		return new ResponseEntity<ResponseWrapper>(responseWrapper, HttpStatus.OK);
    }
	
	
	/**
	 * Get single Market Survey data from Id (HATEOAS href response GET)
	 * @param uuid: Id of Market Survey element
	 * @return
	 */
	@ApiOperation(value="getmarketsurvey",nickname="getmarketsurvey")
	@RequestMapping(value = "/getmarketsurvey", method = RequestMethod.GET)
	public HttpEntity<List<MarketData>> getMarketSurveysByMarketId( 
			@RequestParam(value = "marketid") String marketid){
		
		logger.info("[CaravelloControler] -- getmarketsurvey GET");
		List<MarketData> dataList = marketService.getMarketSurvey(marketid);
		return new ResponseEntity<List<MarketData>>(dataList, HttpStatus.OK);
	}
	
	
}
