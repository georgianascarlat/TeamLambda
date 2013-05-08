/**
 * LambdaWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webclient;

public interface LambdaWebService_PortType extends java.rmi.Remote {
    public java.lang.String[] relevantUsers(java.lang.String type, java.lang.String serviceName) throws java.rmi.RemoteException;
    public boolean loginUser(java.lang.String username, java.lang.String userType, java.lang.String password, java.lang.String[] services) throws java.rmi.RemoteException;
    public java.lang.String[] logoutUser(java.lang.String user) throws java.rmi.RemoteException;
}
