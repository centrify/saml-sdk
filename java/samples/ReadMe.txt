Centrify Java Sample ReadMe.txt

1. Requirements

  - Java 1.6 or higher
  - Ant 1.8 or higher



2. Contents

  - build.xml
    Ant file to build and run the samples. The samples directory is assumed 
    to be in the same level as the SDK. If not, set the sdk-dir parameter 
    to the SDK directory when running ant. For example:

      ant -Dsdk-dir="c:\centrify-saas-java-sdk"

    The default target for build.xml builds all samples and runs the 
    SamlResponseValidatorExample1.java and SamlResponseValidator2.java 
    samples.

    The target "run-CentrifySaasServiceExample" runs the 
    CentrifySaasServiceExample sample.

  - valid-response
    A sample Base64 encoded SAML Response sent by the mobile device for 
    authentication.

  - valid-response2
    A sample Base64 encoded SAML Response with SAML attributes sent by the 
    mobile device for authentication.

  - sample.cer
    The certificate used to sign the SAML response in valid-response.
    The certificate for a SAML application in the MyCentrify user portal 
    can be downloaded from:

    https://mas.centrify.com/saasmanage/downloadCertificateforApp?appkey=MyAppName

    You must be logged into the Centrify Cloud Manager first and be 
    in the sysadmin role to use the URL.

  - idp.xml
    The IDP SAML Metadata containing the certificate used to sign the 
    SAML response in valid-response. The IDP Saml Metadata for a SAML 
    application in the MyCentrify user portal can be downloaded from:

    https://mas.centrify.com/saasmanage/downloadSAMLMetadataforApp?appkey=MyAppName

    You must be logged into the Centrify Cloud Manager before and be in 
    the sysadmin role to use the URL.

  - com/centrify/com.centrify.SamlResponseValidatorExample1.java
  - com/centrify/com.centrify.SamlResponseValidatorExample2.java
    SDK samples. Both samples validate the SAML response in the
    valid-response file and return the user name in the response. The
    first sample validates the SAML response using the certificate in
    sample.cer. The second sample validates the SAML response using the
    certificate in the SAML meta data file idp.xml.

  - com/centrify/com.centrify.CentrifySaasServiceExample.java
    SDK sample using the CentrifySaasService class. The main program takes 
    the following command line arguments:

    - The URL of the Centrify Cloud Manager, for example
      https://mas.centrify.com
    - The customer ID
    - User name
    - Password
    - An application name

    It uses the CentrifySaasService class to get the SAML metadata for
    the application from the Centrify Cloud Manager, gets a SAML response
    for the application, and verifies the SAML response using the
    information from the SAML metadata.
    
