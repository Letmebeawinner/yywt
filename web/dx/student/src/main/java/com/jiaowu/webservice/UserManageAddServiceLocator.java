/**
 * UserManageAddServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jiaowu.webservice;

public class UserManageAddServiceLocator extends org.apache.axis.client.Service implements UserManageAddService {

    // Use to get a proxy class for UserManageAddServiceHttpPort
    private String UserManageAddServiceHttpPort_address = "http://10.100.101.35/UMC/service/UserManageAddService";
    // The WSDD service name defaults to the port name.
    private String UserManageAddServiceHttpPortWSDDServiceName = "UserManageAddServiceHttpPort";
    private java.util.HashSet ports = null;

    public UserManageAddServiceLocator() {
    }

    public UserManageAddServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UserManageAddServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    public String getUserManageAddServiceHttpPortAddress() {
        return UserManageAddServiceHttpPort_address;
    }

    public String getUserManageAddServiceHttpPortWSDDServiceName() {
        return UserManageAddServiceHttpPortWSDDServiceName;
    }

    public void setUserManageAddServiceHttpPortWSDDServiceName(String name) {
        UserManageAddServiceHttpPortWSDDServiceName = name;
    }

    public UserManageAddServicePortType getUserManageAddServiceHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(UserManageAddServiceHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUserManageAddServiceHttpPort(endpoint);
    }

    public UserManageAddServicePortType getUserManageAddServiceHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            UserManageAddServiceHttpBindingStub _stub = new UserManageAddServiceHttpBindingStub(portAddress, this);
            _stub.setPortName(getUserManageAddServiceHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUserManageAddServiceHttpPortEndpointAddress(String address) {
        UserManageAddServiceHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (UserManageAddServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                UserManageAddServiceHttpBindingStub _stub = new UserManageAddServiceHttpBindingStub(new java.net.URL(UserManageAddServiceHttpPort_address), this);
                _stub.setPortName(getUserManageAddServiceHttpPortWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("UserManageAddServiceHttpPort".equals(inputPortName)) {
            return getUserManageAddServiceHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.resm.dp.com", "UserManageAddService");
    }

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.resm.dp.com", "UserManageAddServiceHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

if ("UserManageAddServiceHttpPort".equals(portName)) {
            setUserManageAddServiceHttpPortEndpointAddress(address);
        }
        else
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
