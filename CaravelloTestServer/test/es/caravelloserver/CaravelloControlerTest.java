package es.caravelloserver;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import es.caravelloserver.bean.request.Income;
import es.caravelloserver.bean.request.Provider;
import es.caravelloserver.bean.request.RequestWrapper;
import es.caravelloserver.bean.request.Requester;
import es.caravelloserver.bean.request.Subscription;
import es.caravelloserver.bean.request.Survey;
import es.caravelloserver.bean.request.Target;
import es.caravelloserver.bean.request.params.Channel;
import es.caravelloserver.bean.request.params.Country;
import es.caravelloserver.bean.request.params.Currency;
import es.caravelloserver.bean.request.params.Frecuency;
import es.caravelloserver.bean.request.params.Gender;
import es.caravelloserver.dao.IMarketDao;
import es.caravelloserver.model.MarketData;
import es.caravelloserver.response.ResponseWrapper;
import es.caravelloserver.service.IMarketService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CaravelloController.class, secure = false)
public class CaravelloControlerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IMarketDao marketDao;
	
	@MockBean
	private IMarketService marketService;
	
	
	@Test
	//-->GET /getmarketsurvey?marketId=2
	public void marketSurveyInfoTest() throws Exception {
		
		MarketData mData = new MarketData();
		mData.setMarketId(2);
		mData.setProviderId("tgi");
		mData.setGender("f");
		mData.setCountry("es");
		mData.setCurrency("eur");
		mData.setIncome(30000);
		mData.setAge(60);
		mData.setChannel("postal");
		mData.setFrecuency("weekly");
		
		List<MarketData> mockData = new ArrayList<MarketData>();
		mockData.add(mData);
		
		Mockito.when(marketDao.getMarketSurvey(Mockito.anyString())).thenReturn(mockData);
		Mockito.when(marketService.getMarketSurvey(Mockito.anyString())).thenReturn(mockData);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/getmarketsurvey?marketid=2").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{\"marketId\":2,\"providerId\":\"tgi\",\"gender\":\"f\",\"age\":60,\"currency\":\"eur\",\"income\":30000,\"country\":\"es\",\"frecuency\":\"weekly\",\"channel\":\"postal\",\"links\":[]}]";
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	
	
	
	@Test
	//-->POST getmarketsurveyslist
	
	public void marketSurveyListTest() throws Exception{
		
		
		//-->REQUEST
		//////////////////////////////////////////////////
		RequestWrapper rWrapper = new RequestWrapper();
		
		Requester req = new Requester();
		req.setId("cvo");
		req.setName("caravelo");
		rWrapper.setRequester(req);
		
		
		Provider prov = new Provider();
		prov.setId("tgi");
		prov.setName("kantar");		
		rWrapper.setProvider(prov);
		
		Survey surv = new Survey();
		surv.setSubject("81111600");
		surv.setCountry(Country.ES);
		
		Target targ = new Target();
		targ.setGender(Gender.F);
		targ.getAge().add(30);
		targ.getAge().add(60);
		
		Income inc = new Income();
		inc.setCurrency(Currency.EUR);
		inc.getRange().add(20000);
		inc.getRange().add(40000);		
		targ.setIncome(inc);
		surv.setTarget(targ);
		rWrapper.setSurvey(surv);
		
		Subscription subs = new Subscription();
		subs.setFrecuency(Frecuency.WEEKLY);
		subs.getChannel().add(Channel.POSTAL);
		rWrapper.setSubscription(subs);
		//////////////////////////////////////////////////
		
		//-->RESPONSE
		//////////////////////////////////////////////////
		
		ResponseWrapper resp = new ResponseWrapper();
		
		resp.setIdResponse("AAAAAAAAAAAA");
		
		MarketData mData = new MarketData();
		mData.setMarketId(2);
		mData.setProviderId("tgi");
		mData.setGender("f");
		mData.setCountry("es");
		mData.setCurrency("eur");
		mData.setIncome(30000);
		mData.setAge(60);
		mData.setChannel("postal");
		mData.setFrecuency("weekly");
		
		List<MarketData> mockData = new ArrayList<MarketData>();
		mockData.add(mData);
		resp.getMarketData().addAll(mockData);
		/////////////////////////////////////////////////
		
		Mockito.when(marketDao.getMarketSurveysList(Mockito.any(RequestWrapper.class))).thenReturn(resp);
		Mockito.when(marketService.getMarketSurveysList(Mockito.any(RequestWrapper.class))).thenReturn(resp);
		
		String rWrapperStr = "{ \"requester\": {\"id\": \"CVO\",\"name\": \"Caravelo\"},\"provider\": {\"id\": \"TGI\",\"name\": \"Kantar\"},\"survey\": {\"subject\": 81111600,";         
		rWrapperStr +=	"\"target\": {\"gender\": \"F\",\"age\": [30, 60],\"income\": {\"currency\": \"EUR\",\"range\": [20000, 40000]}},\"country\": \"ES\"},";
		rWrapperStr += "\"subscription\": {\"frequency\": \"weekly\",\"channel\": [\"postal\", \"mail\", \"api\", \"ftp\" ]}}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/getmarketsurveyslist")
				.accept(MediaType.APPLICATION_JSON).content(rWrapperStr.getBytes())
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String response = "{\"idResponse\":\"AAAAAAAAAAAA\",\"marketData\":[{\"marketId\":2,\"providerId\":\"tgi\",\"gender\":\"f\",\"age\":60,\"currency\":\"eur\",\"income\":30000,\"country\":\"es\",\"frecuency\":\"weekly\",\"channel\":\"postal\"}]}";

		JSONAssert.assertEquals(response, result.getResponse().getContentAsString(), false);
	}
	

}
