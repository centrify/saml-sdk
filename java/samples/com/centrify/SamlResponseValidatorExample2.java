package com.centrify;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.List;

import com.centrify.cloud.saas.CentrifySamlFactory;
import com.centrify.cloud.saas.ISamlResponseValidator;
import com.centrify.cloud.saas.SamlResponse;

/**
 * This sample shows how to get ISamlResponseValidator from idp metadata;
 */
public class SamlResponseValidatorExample2 {

    /**
     * Read a base64 encoded SAML response from a file.
     * Validate the response and show a parsed result.
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args == null || args.length != 1) {
            System.out.println("Usage: <file path to base64 saml response>");
            return;
        }
        
        URL idpMetadataUrl = new File("idp.xml").toURI().toURL();
        
        // case-sensitive. This value must match the Issuer element
        String idpIssuerName = "Centrify";
        
        // case-sensitive. This value must match the Audience element
        String spAudience = "https://sp.centrify.com/login";
        
        // This is the Service Provider Issuer name.
        // This is not used to create ISamlResponseValidator.
        String spIssuerName = "spIssuerName";

        //create a factory object
        // The factory can create ISamlResponseValidator and ISamlRequestGenerator.
        CentrifySamlFactory factory = new CentrifySamlFactory(
                idpMetadataUrl, 
                idpIssuerName,
                spIssuerName, 
                spAudience 
        );
        
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
        
        // get the ISamlResponseValidator from the factory
        ISamlResponseValidator validator = factory.getResponseValidator();
     
        // load the base64 SAML Response from a file.
        String base64input = getBase64SamlResponse(args[0]);
     
        // validate and parse the sample response.
        // if the input is in XML format, please call processSamlResponseXML(String) instead
        SamlResponse response = validator.processSamlResponse(base64input);
        
        //If no exception is thrown, the validation is success.
        System.out.println("The SAML response is valid.");
        System.out.println(response);
        
        displaySamlResponse(response);
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
    
    /**
     * load a <code>file</code> into String 
     * @param file
     * @return
     * @throws IOException
     */
    private static String getBase64SamlResponse(String file) throws IOException {
        StringBuilder sb = new StringBuilder();
        FileReader reader = new FileReader(file);
        try {
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        } finally {
            reader.close();
        }
        return sb.toString();
    }

}
