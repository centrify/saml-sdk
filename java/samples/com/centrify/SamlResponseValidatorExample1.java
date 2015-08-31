package com.centrify;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import org.apache.xml.security.keys.content.x509.XMLX509Certificate;

import com.centrify.cloud.saas.ISamlResponseValidator;
import com.centrify.cloud.saas.SamlResponse;
import com.centrify.cloud.saas.SamlResponseValidator;

/**
 * This sample shows how to create the SamlResponseValidator directly.
 * 
 */
public class SamlResponseValidatorExample1 {

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
        
        // The public key which match the signature of the SAML Response
        PublicKey idpPublicKey = getCertificate().getPublicKey();
        
        // case-sensitive. This value must match the Issuer element
        String idpIssuerName = "Centrify";
        
        // case-sensitive. This value must match the Audience element
        String audience = "https://sp.centrify.com/login"; 
        
        // create the SamlResponseValidator
        ISamlResponseValidator validator = new SamlResponseValidator(
                idpPublicKey, idpIssuerName, audience);

        // load the base64 SAML Response from a file.
        String base64SamlResponse = getBase64SamlResponse(args[0]);
        
        // validate and parse the sample response.
        // if the input is in XML format, please call processSamlResponseXML(String) instead
        SamlResponse response = validator.processSamlResponse(base64SamlResponse);
        
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
     * load the x509 certificate from a file.
     * @return
     * @throws Exception
     */
    private static X509Certificate getCertificate() throws Exception {
        
        CertificateFactory certFact = CertificateFactory
                .getInstance(XMLX509Certificate.JCA_CERT_ID);

        FileInputStream fis = new FileInputStream("sample.cer");
        
        try {
            return (X509Certificate) certFact.generateCertificate(fis);
        } finally {
            fis.close();
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
