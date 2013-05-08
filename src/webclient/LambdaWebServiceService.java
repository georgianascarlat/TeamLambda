/**
 * LambdaWebServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webclient;

public interface LambdaWebServiceService extends javax.xml.rpc.Service {
    public java.lang.String getLambdaWebServiceAddress();

    public webclient.LambdaWebService_PortType getLambdaWebService() throws javax.xml.rpc.ServiceException;

    public webclient.LambdaWebService_PortType getLambdaWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
