package com.centrify.cloud.saas.example;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import com.centrify.cloud.saas.AbstractServletConfigWrapper;
import com.centrify.cloud.saas.ISamlRequestGenerator;
import com.centrify.cloud.saas.SamlRequestGenerator;
import com.centrify.cloud.saas.filter.AbstractSamlFilter;
import com.centrify.cloud.saas.filter.FilterConfigWrapper;

/**
 * Servlet Filter implementation class SamlFilter
 */
public class SamlFilter extends AbstractSamlFilter {

    private static final String PARAM_ISSUER_NAME = "spIssuerName";
    
    private static final String PARAM_IDP_HTTP_POST_URL = "idpPostUrl";
    
    private ISamlRequestGenerator requestGeneartor;
    private String idpHttpPostUrl;
    
    @Override
    protected ISamlRequestGenerator getSamlRequestGenerator() {
        return requestGeneartor;
    }

    @Override
    protected String getIdpHttpPostUrl() {
        return idpHttpPostUrl;
    }
    
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        super.init(fConfig);
        AbstractServletConfigWrapper filterConfig = new FilterConfigWrapper(
              fConfig);
        
        String spIssuerName = filterConfig.getString(PARAM_ISSUER_NAME);
        requestGeneartor = new SamlRequestGenerator(spIssuerName);
        
        idpHttpPostUrl = filterConfig.getString(PARAM_IDP_HTTP_POST_URL);
    }
    
}
