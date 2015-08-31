package com.centrify.cloud.saas.example;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.centrify.cloud.saas.AbstractServletConfigWrapper;
import com.centrify.cloud.saas.ISamlResponseValidator;
import com.centrify.cloud.saas.servlet.AbstractLoginServlet;
import com.centrify.cloud.saas.servlet.ServletConfigWrapper;

public class LoginServlet extends AbstractLoginServlet {
    
    private static final long serialVersionUID = 1L;
    
    private static final String PARAM_DEFAULT_HOME_PAGE = "defaultHomepage";

    final Logger log = LoggerFactory.getLogger(LoginServlet.class);
    
    ISamlResponseValidator samlValidator;
    String defaultHomepage;
    
    @Override
    public void init() throws ServletException {
        AbstractServletConfigWrapper servletConfig = new ServletConfigWrapper(this);
        
        samlValidator = CentrifySamlInitalizer.getFactory().getResponseValidator();
        
        defaultHomepage = servletConfig.getString(
                PARAM_DEFAULT_HOME_PAGE, null);
        if(defaultHomepage == null) {
            defaultHomepage = "/" + getServletContext().getServletContextName();
            log.debug("The parameter '" + PARAM_DEFAULT_HOME_PAGE + "' is not set. The default will be '" + defaultHomepage + "'");
        }
    }

    @Override
    protected ISamlResponseValidator getValidator() {
        return samlValidator;
    }

    @Override
    protected String getDefaultHomepage() {
        return defaultHomepage;
    }
    
}
