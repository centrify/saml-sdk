
This sample will create the SamlFilter and LoginServlet base on the IdP metadata.

    The parameters are located in web.xml. 
    You must replace the following parameters.
      * idpMetadataUrl
      * spIssuerName
      * audience

    The idpMetadataUrl should be on a local file system or network drive.


How to build
    You can use Apache Ant to bulid sample. Before you do, you need to modify build.properties.
    You can build the war file when default target, simply run "ant".
    The war file will be under "dist" folder.
