package com.centrify.cloud.saas.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.centrify.cloud.saas.AbstractServletConfigWrapper;
import com.centrify.cloud.saas.ISamlResponseValidator;
import com.centrify.cloud.saas.SamlResponseValidator;
import com.centrify.cloud.saas.servlet.AbstractLoginServlet;
import com.centrify.cloud.saas.servlet.ServletConfigWrapper;

public class LoginServlet extends AbstractLoginServlet {
    
    private static final long serialVersionUID = 1L;
    
    private static final String PARAM_DEFAULT_HOME_PAGE = "defaultHomepage";
    private static final String PARAM_IDP_ISSUER_NAME = "idpIssuerName";
    private static final String PARAM_IDP_CERT_PATH = "idpCertFile";
    private static final String PARAM_AUDIENCE = "audience";

    final Logger log = LoggerFactory.getLogger(LoginServlet.class);
    
    ISamlResponseValidator samlValidator;
    String defaultHomepage;
    
    @Override
    public void init() throws ServletException {
        AbstractServletConfigWrapper servletConfig = new ServletConfigWrapper(this);
        
        String audience = servletConfig.getString(PARAM_AUDIENCE);
        String idpIssuerName = servletConfig.getString(PARAM_IDP_ISSUER_NAME);
        String certFile = servletConfig.getString(PARAM_IDP_CERT_PATH, null);
        
        X509Certificate cert;
        try {
            InputStream is;
            if (certFile != null) {
                is = new FileInputStream(certFile);
            } else {
                is = getServletContext().getResourceAsStream("/WEB-INF/idpCert.cer");
            }
            
            CertificateFactory certFact = CertificateFactory
                    .getInstance("X.509");
            cert = (X509Certificate)certFact.generateCertificate(is);
        } catch (FileNotFoundException e) {
            throw new ServletException(e);
        } catch (CertificateException e) {
            throw new ServletException(e);
        }
        log.debug("setting cert, subjectDn='" + cert.getSubjectDN() +"'");
        
        samlValidator = new SamlResponseValidator(cert.getPublicKey(),
                idpIssuerName, audience);
        
        defaultHomepage = servletConfig.getString(
                PARAM_DEFAULT_HOME_PAGE, null);
        if(defaultHomepage == null) {
            defaultHomepage = "/" + getServletContext().getServletContextName();
            log.info("The parameter '" + PARAM_DEFAULT_HOME_PAGE + "' is not set. The default will be '" + defaultHomepage + "'");
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
