package com.centrify;

import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.List;

import com.centrify.cloud.saas.CentrifySaasService;
import com.centrify.cloud.saas.CentrifySamlFactory;
import com.centrify.cloud.saas.ISamlResponseValidator;
import com.centrify.cloud.saas.SamlResponse;

/**
 * This sample uses the CentrifySaasService class to get the SAML Metadata for 
 * the application from the Cloud Management Portal, gets a SAML Response for 
 * the application, and verifies the SAML Response using the information from 
 * the SAML Metadata.
 */
public class CentrifySaasServiceExample {

    public static void main(String[] args) throws Exception {
        if (args == null || args.length != 6) {
            System.out.println("url customerId username password appkey spAudience");
            return;
        }
        
        String url = args[0];
        System.out.println("url: " + url);
        
        String customerId = args[1];
        System.out.println("customerId: " + customerId);
        
        String username = args[2];
        System.out.println("username: " + username);
        
        String password = args[3];
        System.out.println("password: HIDDEN");
        
        String appKey = args[4];
        System.out.println("appKey: " + appKey);
        
        String spAudience = args[5];
        System.out.println("spAudience: " + spAudience);
        
        // The CentrifySaasService can get the IdP metadata in a real time.
        CentrifySaasService service = new CentrifySaasService(url, customerId, username, password.toCharArray());
        
        
        CentrifySamlFactory factory;
        String ssoToken;
        try {
            factory = getCentrifySamlFactory(service, appKey, spAudience);
            
            System.out.println("Getting SSO token");
            // this is same as https://cloud.centrify.com/uprest/getSSOToken?appkey=appKey
            ssoToken = service.getSSOToken(appKey);
            
        } finally {
            // after the service is closed, you can no longer use it.
            service.close();
        }
        
        X509Certificate certificate = factory.getCertificate();
        try {
            //check certificate's validity.
            certificate.checkValidity();
        } catch (CertificateExpiredException e) {
            // the certificate has expired.
            
            System.out.println("The certificate has been expired since " + certificate.getNotAfter());
            throw e;
        } catch (CertificateNotYetValidException e) {
            // the certificate is not yet valid.
            
            System.out.println("The certificate is not yet valid until " + certificate.getNotBefore());
            throw e;
        }

        ISamlResponseValidator validator = factory.getResponseValidator();
        SamlResponse response = validator.processSamlResponseXml(ssoToken);
        
        // If no exception is thrown, the validation is success.
        System.out.println("The SSO token is valid.");
        System.out.println(response);
        
        displaySamlResponse(response);
    }
    
    private static CentrifySamlFactory getCentrifySamlFactory(
            CentrifySaasService service, String appKey, String spAudience) throws Exception {
        System.out.println("Getting metadata");
        // The CentrifySaasService will login automatically
        String metadata = service.getSAMLMetadataForApp(appKey);
        System.out.println("Successfully got metadata");
        
        // if the appKey doesn't match any existing application. The result is empty.
        if(metadata.isEmpty()) {
            throw new Exception("The metadata for appKey \"" + appKey + "\" is empty.");
        }
        
        String spIssuerName = "not used";
        
        // now passing the metadata to CentrifySamlFactory 
        
        return new CentrifySamlFactory(metadata, spIssuerName, spAudience);
    }
    
    private static void displaySamlResponse(SamlResponse response) {
        System.out.println();
        
        System.out.println("The id is \"" + response.getNameid() + "\"");
        
        // the attribute name is case sensitive
        SamlResponse.Attribute emailAttr = response.getAttribute("Email");
        if (emailAttr != null) {
            System.out.println(" Display Email attributes");
            System.out.println(" name format = " + emailAttr.getNameFormat());
            System.out.println(" Display attributes with \"format\"");
            List<SamlResponse.AttributeValue> attrValues = emailAttr.getValues();
            for (SamlResponse.AttributeValue attrValue : attrValues) {
                System.out.println("  " + attrValue.getFormat() + ": "
                        + attrValue.getValue());
            }

            System.out.println(" Display attributes without \"format\"");
            // if don't care the format, 
            // you can call Attribute.getStringValues() instead
            List<String> attrStringValues = emailAttr.getStringValues();
            System.out.println("  " + attrStringValues);
        } else {
            System.out.println(" no Email attribute");
        }
        
        
        // if you only need to get the attribute values, 
        // you can call response.getAttributeString(attributeName)
        List<String> groupValues = response.getAttributeValues("Groups");
        if (groupValues != null) {
            System.out.println(" Display Groups attributes");
            System.out.println(" Groups =  " + groupValues);
        } else {
            System.out.println(" no Groups attribute");
        }
    }
}
