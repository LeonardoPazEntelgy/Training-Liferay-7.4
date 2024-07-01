package user.rest.web.portlet;

import user.rest.web.constants.UserRestWebPortletKeys;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author leona
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=UserRestWeb",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + UserRestWebPortletKeys.USERRESTWEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class UserRestWebPortlet extends MVCPortlet {
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {		
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		try {

			HttpGet getRequest = new HttpGet("https://reqres.in/api/users");
			
			//Set the API media type in http accept header
			getRequest.addHeader("accept", "application/xml");
			
			//Set the API media type in http accept header
			HttpResponse response = httpClient.execute(getRequest);
			
			//verify the valid error code first
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new RuntimeException("Failed with HTTP error code : " + statusCode);
			}
			
			//Now pull back the response object
			HttpEntity httpEntity = response.getEntity();
			String apiOutput = EntityUtils.toString(httpEntity);
			
			JSONObject objJson = JSONFactoryUtil.createJSONObject(apiOutput);
			//System.out.println(objJson);
			
			//Lets see what we got from API
			//Serialización			
			renderRequest.setAttribute("Data",objJson.getJSONArray("data"));
			
			//System.out.println(objJson.getJSONArray("data"));
			
		}catch(Exception e){
			 e.printStackTrace();
		}finally {
			httpClient.getConnectionManager().shutdown();
		}
		
		super.doView(renderRequest, renderResponse);
		
	}
}