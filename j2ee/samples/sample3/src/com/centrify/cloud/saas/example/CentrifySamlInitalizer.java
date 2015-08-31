package com.centrify.cloud.saas.example;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.centrify.cloud.saas.CentrifySamlFactory;
import com.centrify.cloud.saas.InvalidSamlFormatException;
import com.centrify.cloud.saas.servlet.ServletConfigWrapper;

public class CentrifySamlInitalizer extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private static final String PARAM_AUDIENCE = "audience";
    
    
    private static volatile CentrifySamlFactory factory;
    
    public static CentrifySamlFactory getFactory() {
        if (factory == null) {
            throw new NullPointerException("factory is not initalized yet.");
        }
        return factory;
    }

    private String loadFile() throws ServletException {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = this.getServletContext().getResourceAsStream("/WEB-INF/idp.xml");
            try {
                int c;
                while ((c = is.read()) != -1 ) {
                    sb.append((char)c);
                }
            } finally {
                is.close();
            }
        } catch (IOException e) {
            throw new ServletException(e);
        }
        return sb.toString();
    }
    
    @Override
    public void init() throws ServletException {
        ServletConfigWrapper config = new ServletConfigWrapper(getServletConfig());
        String spIssuerName = "centrify.sample";
        String audience = config.getString(PARAM_AUDIENCE, "https://login.myapp.com");
        
        try {
            factory = new CentrifySamlFactory(loadFile(),
                    spIssuerName, audience);
        } catch (InvalidSamlFormatException e) {
            throw new ServletException(e);
        } catch (CertificateException e) {
            throw new ServletException(e);
        }
    }
    
}
