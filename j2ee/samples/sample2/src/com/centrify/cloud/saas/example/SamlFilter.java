package com.centrify.cloud.saas.example;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import com.centrify.cloud.saas.ISamlRequestGenerator;
import com.centrify.cloud.saas.filter.AbstractSamlFilter;

/**
 * Servlet Filter implementation class SamlFilter
 */
public class SamlFilter extends AbstractSamlFilter {

    @Override
    protected ISamlRequestGenerator getSamlRequestGenerator() {
        return CentrifySamlInitalizer.getFactory().getRequestGenerator();
    }

    @Override
    protected String getIdpHttpPostUrl() {
        return CentrifySamlInitalizer.getFactory().getIdpPostUrl();
    }
    
}
