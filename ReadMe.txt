Centrify(R) SAML SDK Read Me

(C) 2004-2013 Centrify Corporation.
This software is protected by international copyright laws.
All Rights Reserved.

Table of Contents

1.   About This Release
2.   SDK Contents
3.   Installation and use
4.   Known Issues
5.   Additional Information and Support



1.  About This Release

  Centrify Cloud Service allows you to integrate your mobile
  devices with your organization’s internal network using
  Active Directory (AD), and enables you to authenticate,
  monitor, and manage those devices. It also provides access
  to internal network resources such as mail servers and VPNs
  to device owners.

  Centrify Cloud SDKs allow developers of mobile and SaaS
  applications to build applications that take advantage of
  the Centrify Cloud Service to provide single or zero sign-on
  using Active Directory.



2.  SDK Contents

  - ReadMe.txt - This file

  - centrify-cloud-quickstart.pdf - SDK Quick Start Guide

  - centrify-cloud-progguide.pdf - SDK Implementation Guide

  - HTML_Doc/*.* - SDK Quick Start and Implementation Guide in html format

  - java/ directory
         javadoc/*.*         - The Javadoc for the SAML Java API
         lib/*.*             - The centrify-saas-java-sdk.jar file and its
                               dependencies
         lib/endorsed/*.*    - Jar files for the JAXP environment
	 samples/*.*         - Sample with source code

  - j2ee/ directory
         javadoc/*.*         - The Javadoc for the SAML J2EE API
         lib/*.*             - The centrify-saas-j2ee-sdk.jar file
         samples/*.*         - Samples with source code



3.  Installation and use

  a. The Java and J2EE SDKs require Java 1.6 or higher.

  b. The J2EE SDK requires the Java SDK to be installed.

  c. Each sample contains a ReadMe.txt file that describes the source, how
     to build and use.



4. Known Issues
  
  The following sections describe common known issues or 
  limitations associated with this release of the Centrify
  SAML SDK.

  - If you receive the following error:
    
    "Cannot resolve 'xt:DEFAULT' as a QName: the prefix 'xt' is not declared."

    You will need to endorse the JAXP jar files in this directory. 
    See http://docs.oracle.com/javase/6/docs/technotes/guides/standards 
    for more information.None known.



5. Additional Information and Support

  In addition to the documentation provided with this package, you 
  can find the answers to common questions, ask new questions or
  get best practice guidance by visiting the Centrify Mobile
  community site at http://community.centrify.com where Centrify's
  technical support staff and other users are ready to help. If you
  have purchased enterprise support, login to the Centrify Support
  Portal to gain access to the Centrify Knowledge Base or open a
  support case.

  For information about purchasing or evaluating Centrify products,
  send email to info@centrify.com.
