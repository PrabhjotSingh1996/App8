package packageMain;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller_Class.class)
public class Controller_ClassTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private Service_Class serviceClass;
	
	@Test
	public void getTest() throws Exception {
		when(serviceClass.select(anyInt())).thenReturn(new Account(1,"a",123.456));
		String url="/account/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals("{name:a}",result.getResponse().getContentAsString() , false);
		System.err.println(result.getResponse().getContentAsString());
	}

	@Test
	public void selectAllTest() throws Exception{
		ArrayList<Account> list = new ArrayList<>();
		list.add(new Account(1,"PrabhjotSingh",6025.0));
		when(serviceClass.selectAll()).thenReturn(list);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/account").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals("[{name:PrabhjotSingh}]", result.getResponse().getContentAsString(), false);
		System.err.println(result.getResponse().getContentAsString());
	}

	@Test
	public void postTest() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account").accept(MediaType.APPLICATION_JSON).content("{\"name\":\"PrabhjotSingh\",\"accountBalance\":\"6025.0\",\"accountNumber\":\"1\"}").contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals("Account Created", result.getResponse().getContentAsString());
	}
}
