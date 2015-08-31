package com.centrify.cloud.saas.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.centrify.cloud.saas.CentrifySaasException;
import com.centrify.cloud.saas.ISamlResponseValidator;
import com.centrify.cloud.saas.SamlPrincipal;
import com.centrify.cloud.saas.SamlResponse;
import com.centrify.cloud.saas.StatusException;
import com.centrify.cloud.saas.ValidationException;

/**
 * Servlet implementation class SdkdemoServlet
 */
public class SdkdemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SdkdemoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String samlRespB64 = request.getHeader("SAMLResponse");
		
		String json = "";
		if (samlRespB64 == null)
			json = "{ \"success\":false, \"message\" = \"Not authenticated. SAMLResponse not found in HTTP Header.\" }";
		else
		{
			// Validate SAML Response against Engine.
			ISamlResponseValidator samlValidator = CentrifySamlInitalizer.getFactory().getResponseValidator();
			
			try {
				SamlResponse result = samlValidator.processSamlResponse(samlRespB64);
				json = "{ " +
						"\"success\":true," +
						"\"message\":\"Welcome, " + result.getNameid() + "!\"," +
						"\"payload\": [" +
							"{ \"Name\" : \"AcmeWidgets\", \"Sales\" : 20 }," +
							"{ \"Name\" : \"BigMines\", \"Sales\" : 40 }, " +
							"{ \"Name\" : \"FastPlanes\", \"Sales\" : 100 }" +
							"]" +
						" }";
			} catch (CentrifySaasException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				json = "{ \"success\":false, \"message\" = \"Not authenticated. " + e.getMessage() + "\" }";
			}
		}
		
		// Output JSON.
		ServletOutputStream sos = response.getOutputStream();
		sos.write(json.getBytes());
		sos.close();
	}//end function
}//end class
