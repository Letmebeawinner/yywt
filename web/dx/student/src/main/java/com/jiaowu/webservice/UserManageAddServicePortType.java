/**
 * UserManageAddServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jiaowu.webservice;

public interface UserManageAddServicePortType extends java.rmi.Remote {
    public String editPassword(String account, String password, long overdueTime, String phone) throws java.rmi.RemoteException;
    public String addUser(String account, String username, String password, int onlineMax, String part, long overdueTime, String phone) throws java.rmi.RemoteException;
    public String delUser(String account) throws java.rmi.RemoteException;
}
