/**
 * LambdaWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webclient;

public interface LambdaWebService_PortType extends java.rmi.Remote {
    public java.lang.String[] logoutUser(java.lang.String username) throws java.rmi.RemoteException;
    public boolean loginUser(java.lang.String username, java.lang.String userType, java.lang.String password, java.lang.Object[] services) throws java.rmi.RemoteException;
    public webclient.SimpleUser[] relevantUsers(java.lang.String username) throws java.rmi.RemoteException;
}
