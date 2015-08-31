Samples On Tomcat

This package contains a pre-configured Tomcat (apache-tomcat-7.0.41) instance which hosts three samples. Before running Tomcat, please install Java Development Kit (JDK) 6 or higher.
By default, this Tomcat uses port 8080 for HTTP and 8443 for HTTPS.

The three samples are:

1. sample1
   This samples creates the SamlFilter and LoginServlet by specifying each SAML configuration parameter.
   http://localhost:8080/sample1/
   It corresponds to the Cloud App "sample1" on https://station.centrify.com/
   
2. sample2
   This sample creates the SamlFilter and LoginServlet base on the IdP metadata.
   http://localhost:8080/sample2/
   It corresponds to the Cloud App "sample2" on https://station.centrify.com/
   
3. sample3
   This sample creates the SamlFilter and SDKDemoServlet base on the IdP metadata. This sample does not come with any web pages. It only provides a REST call named "GetCustomers" that returns a JSON.
   A valid SAML Response must be provided in the HTTP header "SAMLResponse". 
   http://localhost:8080/sample3/GetCustomers
   It corresponds to the Cloud App "sample3" on https://station.centrify.com/
