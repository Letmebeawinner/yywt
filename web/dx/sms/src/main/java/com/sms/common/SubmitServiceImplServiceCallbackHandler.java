
/**
 * SubmitServiceImplServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4  Built on : Apr 26, 2008 (06:24:30 EDT)
 */

    package com.sms.common;

    /**
     *  SubmitServiceImplServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class SubmitServiceImplServiceCallbackHandler {



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public SubmitServiceImplServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public SubmitServiceImplServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for smsSubmit method
            * override this method for handling normal response from smsSubmit operation
            */
           public void receiveResultsmsSubmit(
                    com.sms.common.SubmitServiceImplServiceStub.SmsSubmitResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from smsSubmit operation
           */
            public void receiveErrorsmsSubmit(Exception e) {
            }

           /**
            * auto generated Axis2 call back method for mmsSubmit method
            * override this method for handling normal response from mmsSubmit operation
            */
           public void receiveResultmmsSubmit(
                   com.sms.common.SubmitServiceImplServiceStub.MmsSubmitResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from mmsSubmit operation
           */
            public void receiveErrormmsSubmit(Exception e) {
            }
                


    }
    