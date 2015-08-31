
This sample will create the SamlFilter and LoginServlet by specifying each saml configuration parameter.

    The parameters are located in web.xml. 
    You must replace the following parameters.
      * idpPostUrl
      * spIssuerName
      * idpIssueName
      * idpCertFile
      * audience

    You can use Apache Ant to bulid sample. Before you do, you need to modify build.properties.

How to build
    You can use Apache Ant to bulid sample. Before you do, you need to modify build.properties.
    You can build the war file when default target, simply run "ant".
    The war file will be under "dist" folder.