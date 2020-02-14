
/**
 * SubmitServiceImplServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4  Built on : Apr 26, 2008 (06:24:30 EDT)
 */
        package com.sms.common;

        

        /*
        *  SubmitServiceImplServiceStub java implementation
        */

        
        public class SubmitServiceImplServiceStub extends org.apache.axis2.client.Stub
        {
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();

        private static int counter = 0;

        private static synchronized String getUniqueSuffix(){
            // reset the counter if it is greater than 99999
            if (counter > 99999){
                counter = 0;
            }
            counter = counter + 1; 
            return Long.toString(System.currentTimeMillis()) + "_" + counter;
        }

    
    private void populateAxisService() throws org.apache.axis2.AxisFault {

     //creating the Service with a unique name
     _service = new org.apache.axis2.description.AxisService("SubmitServiceImplService" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[2];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://service.msg.mas.com/", "smsSubmit"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://service.msg.mas.com/", "mmsSubmit"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[1]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         


    }

    /**
      *Constructor that takes in a configContext
      */

    public SubmitServiceImplServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
                                        String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public SubmitServiceImplServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
                                       String targetEndpoint, boolean useSeparateListener)
        throws org.apache.axis2.AxisFault {
         //To populate AxisService
         populateAxisService();
         populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,_service);


        configurationContext = _serviceClient.getServiceContext().getConfigurationContext();

        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);


    }

    /**
     * Default Constructor
     */
    public SubmitServiceImplServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {

                    this(configurationContext,"http://127.0.0.1/msgService/services/submitService" );

    }

    /**
     * Default Constructor
     */
    public SubmitServiceImplServiceStub() throws org.apache.axis2.AxisFault {

                    this("http://127.0.0.1/msgService/services/submitService" );

    }

    /**
     * Constructor taking the target endpoint
     */
    public SubmitServiceImplServiceStub(String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }




                    /**
                     * Auto generated method signature
                     *
                     * @param smsSubmit0

                     */



                            public  com.sms.common.SubmitServiceImplServiceStub.SmsSubmitResponseE smsSubmit(

                            com.sms.common.SubmitServiceImplServiceStub.SmsSubmit3 smsSubmit0)


                    throws java.rmi.RemoteException

                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();



              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;


                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    smsSubmit0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                    "smsSubmit")));

        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);


               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();


                                Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.sms.common.SubmitServiceImplServiceStub.SmsSubmitResponseE.class,
                                              getEnvelopeNamespaces(_returnEnv));


                                        return (com.sms.common.SubmitServiceImplServiceStub.SmsSubmitResponseE)object;

         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        String exceptionClassName = (String)faultExceptionClassNameMap.get(faultElt.getQName());
                        Class exceptionClass = Class.forName(exceptionClassName);
                        Exception ex=
                                (Exception) exceptionClass.newInstance();
                        //message class
                        String messageClassName = (String)faultMessageMap.get(faultElt.getQName());
                        Class messageClass = Class.forName(messageClassName);
                        Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new Class[]{messageClass});
                        m.invoke(ex,new Object[]{messageObject});


                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }

                /**
                * Auto generated method signature for Asynchronous Invocations
                *
                    * @param smsSubmit0

                */
                public  void startsmsSubmit(

                 com.sms.common.SubmitServiceImplServiceStub.SmsSubmit3 smsSubmit0,

                  final com.sms.common.SubmitServiceImplServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
             _operationClient.getOptions().setAction("\"\"");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");



              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();


                                    //Style is Doc.


                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    smsSubmit0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                    "smsSubmit")));

        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);



                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();

                                        Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.sms.common.SubmitServiceImplServiceStub.SmsSubmitResponseE.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultsmsSubmit(
                                        (com.sms.common.SubmitServiceImplServiceStub.SmsSubmitResponseE)object);

                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorsmsSubmit(e);
                            }
                            }

                            public void onError(Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(faultElt.getQName())){
											//make the fault by reflection
											try{
													String exceptionClassName = (String)faultExceptionClassNameMap.get(faultElt.getQName());
													Class exceptionClass = Class.forName(exceptionClassName);
													Exception ex=
														(Exception) exceptionClass.newInstance();
													//message class
													String messageClassName = (String)faultMessageMap.get(faultElt.getQName());
														Class messageClass = Class.forName(messageClassName);
													Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new Class[]{messageClass});
													m.invoke(ex,new Object[]{messageObject});


										            callback.receiveErrorsmsSubmit(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsmsSubmit(f);
                                            } catch (ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsmsSubmit(f);
                                            } catch (NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsmsSubmit(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsmsSubmit(f);
                                            } catch (IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsmsSubmit(f);
                                            } catch (InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsmsSubmit(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsmsSubmit(f);
                                            }
									    } else {
										    callback.receiveErrorsmsSubmit(f);
									    }
									} else {
									    callback.receiveErrorsmsSubmit(f);
									}
								} else {
								    callback.receiveErrorsmsSubmit(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorsmsSubmit(axisFault);
                                }
                            }
                });


          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[0].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[0].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }

                    /**
                     * Auto generated method signature
                     *
                     * @param mmsSubmit2

                     */



                            public  com.sms.common.SubmitServiceImplServiceStub.MmsSubmitResponseE mmsSubmit(

                            com.sms.common.SubmitServiceImplServiceStub.MmsSubmit2 mmsSubmit2)


                    throws java.rmi.RemoteException

                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();



              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;

                  env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    mmsSubmit2,
                                                    optimizeContent(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                    "mmsSubmit")));

        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);


               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();


                                Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.sms.common.SubmitServiceImplServiceStub.MmsSubmitResponseE.class,
                                              getEnvelopeNamespaces(_returnEnv));


                                        return (com.sms.common.SubmitServiceImplServiceStub.MmsSubmitResponseE)object;

         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        String exceptionClassName = (String)faultExceptionClassNameMap.get(faultElt.getQName());
                        Class exceptionClass = Class.forName(exceptionClassName);
                        Exception ex=
                                (Exception) exceptionClass.newInstance();
                        //message class
                        String messageClassName = (String)faultMessageMap.get(faultElt.getQName());
                        Class messageClass = Class.forName(messageClassName);
                        Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new Class[]{messageClass});
                        m.invoke(ex,new Object[]{messageObject});


                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }

                /**
                * Auto generated method signature for Asynchronous Invocations
                *
                    * @param mmsSubmit2

                */
                public  void startmmsSubmit(

                 com.sms.common.SubmitServiceImplServiceStub.MmsSubmit2 mmsSubmit2,

                  final com.sms.common.SubmitServiceImplServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
             _operationClient.getOptions().setAction("\"\"");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");



              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();//Style is Doc.
                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    mmsSubmit2,
                                                    optimizeContent(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                    "mmsSubmit")));

        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);



                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();

                                        Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.sms.common.SubmitServiceImplServiceStub.MmsSubmitResponseE.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultmmsSubmit(
                                        (com.sms.common.SubmitServiceImplServiceStub.MmsSubmitResponseE)object);

                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrormmsSubmit(e);
                            }
                            }

                            public void onError(Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(faultElt.getQName())){
											//make the fault by reflection
											try{
													String exceptionClassName = (String)faultExceptionClassNameMap.get(faultElt.getQName());
													Class exceptionClass = Class.forName(exceptionClassName);
													Exception ex=
														(Exception) exceptionClass.newInstance();
													//message class
													String messageClassName = (String)faultMessageMap.get(faultElt.getQName());
														Class messageClass = Class.forName(messageClassName);
													Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new Class[]{messageClass});
													m.invoke(ex,new Object[]{messageObject});


										            callback.receiveErrormmsSubmit(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormmsSubmit(f);
                                            } catch (ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormmsSubmit(f);
                                            } catch (NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormmsSubmit(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormmsSubmit(f);
                                            } catch (IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormmsSubmit(f);
                                            } catch (InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormmsSubmit(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormmsSubmit(f);
                                            }
									    } else {
										    callback.receiveErrormmsSubmit(f);
									    }
									} else {
									    callback.receiveErrormmsSubmit(f);
									}
								} else {
								    callback.receiveErrormmsSubmit(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrormmsSubmit(axisFault);
                                }
                            }
                });


          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[1].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[1].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }



       /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
       private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
       return returnMap;
    }



    private javax.xml.namespace.QName[] opNameArray = null;
    private boolean optimizeContent(javax.xml.namespace.QName opName) {


        if (opNameArray == null) {
            return false;
        }
        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;
            }
        }
        return false;
    }
     //http://127.0.0.1/msgService/services/submitService
        public static class ReqHeader
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = ReqHeader
                Namespace URI = http://req.msg.mas.com
                Namespace Prefix = ns1
                */


        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://req.msg.mas.com")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for AuthCode
                        */


                                    protected String localAuthCode ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAuthCodeTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getAuthCode(){
                               return localAuthCode;
                           }



                            /**
                               * Auto generated setter method
                               * @param param AuthCode
                               */
                               public void setAuthCode(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localAuthCodeTracker = true;
                                       } else {
                                          localAuthCodeTracker = true;

                                       }

                                            this.localAuthCode=param;


                               }


                        /**
                        * field for Reqno
                        */


                                    protected String localReqno ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReqnoTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getReqno(){
                               return localReqno;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Reqno
                               */
                               public void setReqno(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localReqnoTracker = true;
                                       } else {
                                          localReqnoTracker = true;

                                       }

                                            this.localReqno=param;


                               }


                        /**
                        * field for Sysid
                        */


                                    protected String localSysid ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSysidTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getSysid(){
                               return localSysid;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Sysid
                               */
                               public void setSysid(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localSysidTracker = true;
                                       } else {
                                          localSysidTracker = true;

                                       }

                                            this.localSysid=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       ReqHeader.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{




                String prefix = null;
                String namespace = null;


                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }

                  if (serializeType){


                   String namespacePrefix = registerPrefix(xmlWriter,"http://req.msg.mas.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":ReqHeader",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "ReqHeader",
                           xmlWriter);
                   }


                   }
                if (localAuthCodeTracker){
                                    namespace = "http://req.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"authCode", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"authCode");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("authCode");
                                    }


                                          if (localAuthCode==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localAuthCode);

                                          }

                                   xmlWriter.writeEndElement();
                             } if (localReqnoTracker){
                                    namespace = "http://req.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"reqno", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"reqno");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("reqno");
                                    }


                                          if (localReqno==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localReqno);

                                          }

                                   xmlWriter.writeEndElement();
                             } if (localSysidTracker){
                                    namespace = "http://req.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"sysid", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"sysid");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("sysid");
                                    }


                                          if (localSysid==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localSysid);

                                          }

                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{



                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localAuthCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://req.msg.mas.com",
                                                                      "authCode"));

                                         elementList.add(localAuthCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthCode));
                                    } if (localReqnoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://req.msg.mas.com",
                                                                      "reqno"));

                                         elementList.add(localReqno==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReqno));
                                    } if (localSysidTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://req.msg.mas.com",
                                                                      "sysid"));

                                         elementList.add(localSysid==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSysid));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());



        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static ReqHeader parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            ReqHeader object =
                new ReqHeader();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();


                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);

                            if (!"ReqHeader".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (ReqHeader) ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }


                  }


                }




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();




                    reader.next();


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://req.msg.mas.com","authCode").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setAuthCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://req.msg.mas.com","reqno").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setReqno(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://req.msg.mas.com","sysid").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setSysid(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }

                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();

                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class CPMMS
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = CPMMS
                Namespace URI = http://data.msg.mas.com
                Namespace Prefix = ns2
                */


        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://data.msg.mas.com")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for Cpfrags
                        */


                                    protected ArrayOfCPFrag localCpfrags ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCpfragsTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return ArrayOfCPFrag
                           */
                           public  ArrayOfCPFrag getCpfrags(){
                               return localCpfrags;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Cpfrags
                               */
                               public void setCpfrags(ArrayOfCPFrag param){

                                       if (param != null){
                                          //update the setting tracker
                                          localCpfragsTracker = true;
                                       } else {
                                          localCpfragsTracker = true;

                                       }

                                            this.localCpfrags=param;


                               }


                        /**
                        * field for Description
                        */


                                    protected String localDescription ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDescriptionTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getDescription(){
                               return localDescription;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Description
                               */
                               public void setDescription(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localDescriptionTracker = true;
                                       } else {
                                          localDescriptionTracker = true;

                                       }

                                            this.localDescription=param;


                               }


                        /**
                        * field for Id
                        */


                                    protected String localId ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIdTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getId(){
                               return localId;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Id
                               */
                               public void setId(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localIdTracker = true;
                                       } else {
                                          localIdTracker = true;

                                       }

                                            this.localId=param;


                               }


                        /**
                        * field for Subject
                        */


                                    protected String localSubject ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSubjectTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getSubject(){
                               return localSubject;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Subject
                               */
                               public void setSubject(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localSubjectTracker = true;
                                       } else {
                                          localSubjectTracker = true;

                                       }

                                            this.localSubject=param;


                               }


                        /**
                        * field for Type
                        */


                                    protected int localType ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTypeTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getType(){
                               return localType;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Type
                               */
                               public void setType(int param){

                                       // setting primitive attribute tracker to true

                                               if (param== Integer.MIN_VALUE) {
                                           localTypeTracker = false;

                                       } else {
                                          localTypeTracker = true;
                                       }

                                            this.localType=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       CPMMS.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{




                String prefix = null;
                String namespace = null;


                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }

                  if (serializeType){


                   String namespacePrefix = registerPrefix(xmlWriter,"http://data.msg.mas.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":CPMMS",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "CPMMS",
                           xmlWriter);
                   }


                   }
                if (localCpfragsTracker){
                                    if (localCpfrags==null){

                                            String namespace2 = "http://data.msg.mas.com";

                                        if (! namespace2.equals("")) {
                                            String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"cpfrags", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"cpfrags");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("cpfrags");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localCpfrags.serialize(new javax.xml.namespace.QName("http://data.msg.mas.com","cpfrags"),
                                        factory,xmlWriter);
                                    }
                                } if (localDescriptionTracker){
                                    namespace = "http://data.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"description", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"description");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("description");
                                    }


                                          if (localDescription==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localDescription);

                                          }

                                   xmlWriter.writeEndElement();
                             } if (localIdTracker){
                                    namespace = "http://data.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"id", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"id");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("id");
                                    }


                                          if (localId==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localId);

                                          }

                                   xmlWriter.writeEndElement();
                             } if (localSubjectTracker){
                                    namespace = "http://data.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"subject", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"subject");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("subject");
                                    }


                                          if (localSubject==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localSubject);

                                          }

                                   xmlWriter.writeEndElement();
                             } if (localTypeTracker){
                                    namespace = "http://data.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"type", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"type");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("type");
                                    }

                                               if (localType== Integer.MIN_VALUE) {

                                                         throw new org.apache.axis2.databinding.ADBException("type cannot be null!!");

                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localType));
                                               }

                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{



                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localCpfragsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "cpfrags"));


                                    elementList.add(localCpfrags==null?null:
                                    localCpfrags);
                                } if (localDescriptionTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "description"));

                                         elementList.add(localDescription==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDescription));
                                    } if (localIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "id"));

                                         elementList.add(localId==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId));
                                    } if (localSubjectTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "subject"));

                                         elementList.add(localSubject==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSubject));
                                    } if (localTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "type"));

                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localType));
                            }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());



        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static CPMMS parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            CPMMS object =
                new CPMMS();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();


                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);

                            if (!"CPMMS".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (CPMMS) ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }


                  }


                }




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();




                    reader.next();


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","cpfrags").equals(reader.getName())){

                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setCpfrags(null);
                                          reader.next();

                                            reader.next();

                                      }else{

                                                object.setCpfrags(ArrayOfCPFrag.Factory.parse(reader));

                                        reader.next();
                                    }
                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","description").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setDescription(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","id").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","subject").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setSubject(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","type").equals(reader.getName())){

                                    String content = reader.getElementText();

                                              object.setType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                               object.setType(Integer.MIN_VALUE);

                                    }

                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();

                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class MmsSubmitResponseE
        implements org.apache.axis2.databinding.ADBBean{

                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://service.msg.mas.com/",
                "mmsSubmitResponse",
                "ns3");



        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://service.msg.mas.com/")){
                return "ns3";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for MmsSubmitResponse
                        */


                                    protected MmsSubmitResponse localMmsSubmitResponse ;


                           /**
                           * Auto generated getter method
                           * @return MmsSubmitResponse
                           */
                           public  MmsSubmitResponse getMmsSubmitResponse(){
                               return localMmsSubmitResponse;
                           }



                            /**
                               * Auto generated setter method
                               * @param param MmsSubmitResponse
                               */
                               public void setMmsSubmitResponse(MmsSubmitResponse param){

                                            this.localMmsSubmitResponse=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



                org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       MmsSubmitResponseE.this.serialize(MY_QNAME,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               MY_QNAME,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{


                //We can safely assume an element has only one type associated with it

                                 if (localMmsSubmitResponse==null){
                                   throw new org.apache.axis2.databinding.ADBException("Property cannot be null!");
                                 }
                                 localMmsSubmitResponse.serialize(MY_QNAME,factory,xmlWriter);


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{




                //We can safely assume an element has only one type associated with it
                return localMmsSubmitResponse.getPullParser(MY_QNAME);

        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static MmsSubmitResponseE parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            MmsSubmitResponseE object =
                new MmsSubmitResponseE();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();



                while(!reader.isEndElement()) {
                    if (reader.isStartElement() ){

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.msg.mas.com/","mmsSubmitResponse").equals(reader.getName())){

                                                object.setMmsSubmitResponse(MmsSubmitResponse.Factory.parse(reader));

                              }  // End of if for expected property start element

                             else{
                                        // A start element we are not expecting indicates an invalid parameter was passed
                                        throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                             }

                             } else {
                                reader.next();
                             }
                           }  // end of while loop




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class ArrayOfCPFrag
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = ArrayOfCPFrag
                Namespace URI = http://data.msg.mas.com
                Namespace Prefix = ns2
                */


        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://data.msg.mas.com")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for CPFrag
                        * This was an Array!
                        */


                                    protected CPFrag[] localCPFrag ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCPFragTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return CPFrag[]
                           */
                           public  CPFrag[] getCPFrag(){
                               return localCPFrag;
                           }






                              /**
                               * validate the array for CPFrag
                               */
                              protected void validateCPFrag(CPFrag[] param){

                              }


                             /**
                              * Auto generated setter method
                              * @param param CPFrag
                              */
                              public void setCPFrag(CPFrag[] param){

                                   validateCPFrag(param);


                                          if (param != null){
                                             //update the setting tracker
                                             localCPFragTracker = true;
                                          } else {
                                             localCPFragTracker = true;

                                          }

                                      this.localCPFrag=param;
                              }



                             /**
                             * Auto generated add method for the array for convenience
                             * @param param CPFrag
                             */
                             public void addCPFrag(CPFrag param){
                                   if (localCPFrag == null){
                                   localCPFrag = new CPFrag[]{};
                                   }


                                 //update the setting tracker
                                localCPFragTracker = true;


                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localCPFrag);
                               list.add(param);
                               this.localCPFrag =
                             (CPFrag[])list.toArray(
                            new CPFrag[list.size()]);

                             }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       ArrayOfCPFrag.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{




                String prefix = null;
                String namespace = null;


                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }

                  if (serializeType){


                   String namespacePrefix = registerPrefix(xmlWriter,"http://data.msg.mas.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":ArrayOfCPFrag",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "ArrayOfCPFrag",
                           xmlWriter);
                   }


                   }
                if (localCPFragTracker){
                                       if (localCPFrag!=null){
                                            for (int i = 0;i < localCPFrag.length;i++){
                                                if (localCPFrag[i] != null){
                                                 localCPFrag[i].serialize(new javax.xml.namespace.QName("http://data.msg.mas.com","CPFrag"),
                                                           factory,xmlWriter);
                                                } else {

                                                            // write null attribute
                                                            String namespace2 = "http://data.msg.mas.com";
                                                            if (! namespace2.equals("")) {
                                                                String prefix2 = xmlWriter.getPrefix(namespace2);

                                                                if (prefix2 == null) {
                                                                    prefix2 = generatePrefix(namespace2);

                                                                    xmlWriter.writeStartElement(prefix2,"CPFrag", namespace2);
                                                                    xmlWriter.writeNamespace(prefix2, namespace2);
                                                                    xmlWriter.setPrefix(prefix2, namespace2);

                                                                } else {
                                                                    xmlWriter.writeStartElement(namespace2,"CPFrag");
                                                                }

                                                            } else {
                                                                xmlWriter.writeStartElement("CPFrag");
                                                            }

                                                           // write the nil attribute
                                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                           xmlWriter.writeEndElement();

                                                }

                                            }
                                     } else {

                                                // write null attribute
                                                String namespace2 = "http://data.msg.mas.com";
                                                if (! namespace2.equals("")) {
                                                    String prefix2 = xmlWriter.getPrefix(namespace2);

                                                    if (prefix2 == null) {
                                                        prefix2 = generatePrefix(namespace2);

                                                        xmlWriter.writeStartElement(prefix2,"CPFrag", namespace2);
                                                        xmlWriter.writeNamespace(prefix2, namespace2);
                                                        xmlWriter.setPrefix(prefix2, namespace2);

                                                    } else {
                                                        xmlWriter.writeStartElement(namespace2,"CPFrag");
                                                    }

                                                } else {
                                                    xmlWriter.writeStartElement("CPFrag");
                                                }

                                               // write the nil attribute
                                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                               xmlWriter.writeEndElement();

                                    }
                                 }
                    xmlWriter.writeEndElement();


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{



                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localCPFragTracker){
                             if (localCPFrag!=null) {
                                 for (int i = 0;i < localCPFrag.length;i++){

                                    if (localCPFrag[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                          "CPFrag"));
                                         elementList.add(localCPFrag[i]);
                                    } else {

                                                elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                          "CPFrag"));
                                                elementList.add(null);

                                    }

                                 }
                             } else {

                                        elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                          "CPFrag"));
                                        elementList.add(localCPFrag);

                             }

                        }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());



        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static ArrayOfCPFrag parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            ArrayOfCPFrag object =
                new ArrayOfCPFrag();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();


                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);

                            if (!"ArrayOfCPFrag".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (ArrayOfCPFrag) ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }


                  }


                }




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();




                    reader.next();

                        java.util.ArrayList list1 = new java.util.ArrayList();


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","CPFrag").equals(reader.getName())){



                                    // Process the array and step past its final element's end.

                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list1.add(null);
                                                              reader.next();
                                                          } else {
                                                        list1.add(CPFrag.Factory.parse(reader));
                                                                }
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone1 = false;
                                                        while(!loopDone1){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone1 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://data.msg.mas.com","CPFrag").equals(reader.getName())){

                                                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                                          list1.add(null);
                                                                          reader.next();
                                                                      } else {
                                                                    list1.add(CPFrag.Factory.parse(reader));
                                                                        }
                                                                }else{
                                                                    loopDone1 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array

                                                        object.setCPFrag((CPFrag[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                CPFrag.class,
                                                                list1));

                              }  // End of if for expected property start element

                                    else {

                                    }

                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();

                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class Param
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = Param
                Namespace URI = http://data.msg.mas.com
                Namespace Prefix = ns2
                */


        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://data.msg.mas.com")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for Name
                        */


                                    protected String localName ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNameTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getName(){
                               return localName;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Name
                               */
                               public void setName(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localNameTracker = true;
                                       } else {
                                          localNameTracker = true;

                                       }

                                            this.localName=param;


                               }


                        /**
                        * field for Value
                        */


                                    protected String localValue ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localValueTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getValue(){
                               return localValue;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Value
                               */
                               public void setValue(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localValueTracker = true;
                                       } else {
                                          localValueTracker = true;

                                       }

                                            this.localValue=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       Param.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{




                String prefix = null;
                String namespace = null;


                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }

                  if (serializeType){


                   String namespacePrefix = registerPrefix(xmlWriter,"http://data.msg.mas.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":Param",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "Param",
                           xmlWriter);
                   }


                   }
                if (localNameTracker){
                                    namespace = "http://data.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"name", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"name");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("name");
                                    }


                                          if (localName==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localName);

                                          }

                                   xmlWriter.writeEndElement();
                             } if (localValueTracker){
                                    namespace = "http://data.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"value", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"value");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("value");
                                    }


                                          if (localValue==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localValue);

                                          }

                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{



                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "name"));

                                         elementList.add(localName==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localName));
                                    } if (localValueTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "value"));

                                         elementList.add(localValue==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localValue));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());



        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static Param parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            Param object =
                new Param();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();


                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);

                            if (!"Param".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (Param) ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }


                  }


                }




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();




                    reader.next();


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","name").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","value").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setValue(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }

                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();

                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class ArrayOfString
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = ArrayOfString
                Namespace URI = http://service.msg.mas.com/
                Namespace Prefix = ns3
                */


        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://service.msg.mas.com/")){
                return "ns3";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for String
                        * This was an Array!
                        */


                                    protected String[] localString ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStringTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String[]
                           */
                           public  String[] getString(){
                               return localString;
                           }






                              /**
                               * validate the array for String
                               */
                              protected void validateString(String[] param){

                              }


                             /**
                              * Auto generated setter method
                              * @param param String
                              */
                              public void setString(String[] param){

                                   validateString(param);


                                          if (param != null){
                                             //update the setting tracker
                                             localStringTracker = true;
                                          } else {
                                             localStringTracker = true;

                                          }

                                      this.localString=param;
                              }



                             /**
                             * Auto generated add method for the array for convenience
                             * @param param java.lang.String
                             */
                             public void addString(String param){
                                   if (localString == null){
                                   localString = new String[]{};
                                   }


                                 //update the setting tracker
                                localStringTracker = true;


                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localString);
                               list.add(param);
                               this.localString =
                             (String[])list.toArray(
                            new String[list.size()]);

                             }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       ArrayOfString.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{




                String prefix = null;
                String namespace = null;


                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }

                  if (serializeType){


                   String namespacePrefix = registerPrefix(xmlWriter,"http://service.msg.mas.com/");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":ArrayOfString",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "ArrayOfString",
                           xmlWriter);
                   }


                   }
                if (localStringTracker){
                             if (localString!=null) {
                                   namespace = "http://service.msg.mas.com/";
                                   boolean emptyNamespace = namespace == null || namespace.length() == 0;
                                   prefix =  emptyNamespace ? null : xmlWriter.getPrefix(namespace);
                                   for (int i = 0;i < localString.length;i++){

                                            if (localString[i] != null){

                                                if (!emptyNamespace) {
                                                    if (prefix == null) {
                                                        String prefix2 = generatePrefix(namespace);

                                                        xmlWriter.writeStartElement(prefix2,"string", namespace);
                                                        xmlWriter.writeNamespace(prefix2, namespace);
                                                        xmlWriter.setPrefix(prefix2, namespace);

                                                    } else {
                                                        xmlWriter.writeStartElement(namespace,"string");
                                                    }

                                                } else {
                                                    xmlWriter.writeStartElement("string");
                                                }


                                                        xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localString[i]));

                                                xmlWriter.writeEndElement();

                                                } else {

                                                           // write null attribute
                                                            namespace = "http://service.msg.mas.com/";
                                                            if (! namespace.equals("")) {
                                                                prefix = xmlWriter.getPrefix(namespace);

                                                                if (prefix == null) {
                                                                    prefix = generatePrefix(namespace);

                                                                    xmlWriter.writeStartElement(prefix,"string", namespace);
                                                                    xmlWriter.writeNamespace(prefix, namespace);
                                                                    xmlWriter.setPrefix(prefix, namespace);

                                                                } else {
                                                                    xmlWriter.writeStartElement(namespace,"string");
                                                                }

                                                            } else {
                                                                xmlWriter.writeStartElement("string");
                                                            }
                                                            writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                            xmlWriter.writeEndElement();

                                                }

                                   }
                             } else {

                                         // write the null attribute
                                        // write null attribute
                                            String namespace2 = "http://service.msg.mas.com/";
                                            if (! namespace2.equals("")) {
                                                String prefix2 = xmlWriter.getPrefix(namespace2);

                                                if (prefix2 == null) {
                                                    prefix2 = generatePrefix(namespace2);

                                                    xmlWriter.writeStartElement(prefix2,"string", namespace2);
                                                    xmlWriter.writeNamespace(prefix2, namespace2);
                                                    xmlWriter.setPrefix(prefix2, namespace2);

                                                } else {
                                                    xmlWriter.writeStartElement(namespace2,"string");
                                                }

                                            } else {
                                                xmlWriter.writeStartElement("string");
                                            }

                                           // write the nil attribute
                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                           xmlWriter.writeEndElement();

                             }

                        }
                    xmlWriter.writeEndElement();


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{



                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localStringTracker){
                            if (localString!=null){
                                  for (int i = 0;i < localString.length;i++){

                                         if (localString[i] != null){
                                          elementList.add(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                                              "string"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localString[i]));
                                          } else {

                                                    elementList.add(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                                              "string"));
                                                    elementList.add(null);

                                          }


                                  }
                            } else {

                                    elementList.add(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                                              "string"));
                                    elementList.add(null);

                            }

                        }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());



        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static ArrayOfString parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            ArrayOfString object =
                new ArrayOfString();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();


                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);

                            if (!"ArrayOfString".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (ArrayOfString) ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }


                  }


                }




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();




                    reader.next();

                        java.util.ArrayList list1 = new java.util.ArrayList();


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.msg.mas.com/","string").equals(reader.getName())){



                                    // Process the array and step past its final element's end.

                                              nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                              if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                  list1.add(null);

                                                  reader.next();
                                              } else {
                                            list1.add(reader.getElementText());
                                            }
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone1 = false;
                                            while(!loopDone1){
                                                // Ensure we are at the EndElement
                                                while (!reader.isEndElement()){
                                                    reader.next();
                                                }
                                                // Step out of this element
                                                reader.next();
                                                // Step to next element event.
                                                while (!reader.isStartElement() && !reader.isEndElement())
                                                    reader.next();
                                                if (reader.isEndElement()){
                                                    //two continuous end elements means we are exiting the xml structure
                                                    loopDone1 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://service.msg.mas.com/","string").equals(reader.getName())){

                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list1.add(null);

                                                              reader.next();
                                                          } else {
                                                        list1.add(reader.getElementText());
                                                        }
                                                    }else{
                                                        loopDone1 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array

                                                    object.setString((String[])
                                                        list1.toArray(new String[list1.size()]));

                              }  // End of if for expected property start element

                                    else {

                                    }

                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();

                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class MmsSubmit2
        implements org.apache.axis2.databinding.ADBBean{

                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://service.msg.mas.com/",
                "mmsSubmit",
                "ns3");



        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://service.msg.mas.com/")){
                return "ns3";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for MmsSubmit
                        */


                                    protected MmsSubmit localMmsSubmit ;


                           /**
                           * Auto generated getter method
                           * @return MmsSubmit
                           */
                           public  MmsSubmit getMmsSubmit(){
                               return localMmsSubmit;
                           }



                            /**
                               * Auto generated setter method
                               * @param param MmsSubmit
                               */
                               public void setMmsSubmit(MmsSubmit param){

                                            this.localMmsSubmit=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



                org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       MmsSubmit2.this.serialize(MY_QNAME,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               MY_QNAME,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{


                //We can safely assume an element has only one type associated with it

                                 if (localMmsSubmit==null){
                                   throw new org.apache.axis2.databinding.ADBException("Property cannot be null!");
                                 }
                                 localMmsSubmit.serialize(MY_QNAME,factory,xmlWriter);


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{




                //We can safely assume an element has only one type associated with it
                return localMmsSubmit.getPullParser(MY_QNAME);

        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static MmsSubmit2 parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            MmsSubmit2 object =
                new MmsSubmit2();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();



                while(!reader.isEndElement()) {
                    if (reader.isStartElement() ){

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.msg.mas.com/","mmsSubmit").equals(reader.getName())){

                                                object.setMmsSubmit(MmsSubmit.Factory.parse(reader));

                              }  // End of if for expected property start element

                             else{
                                        // A start element we are not expecting indicates an invalid parameter was passed
                                        throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                             }

                             } else {
                                reader.next();
                             }
                           }  // end of while loop




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class RespHeader
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = RespHeader
                Namespace URI = http://resp.msg.mas.com
                Namespace Prefix = ns4
                */


        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://resp.msg.mas.com")){
                return "ns4";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for Reqno
                        */


                                    protected String localReqno ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReqnoTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getReqno(){
                               return localReqno;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Reqno
                               */
                               public void setReqno(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localReqnoTracker = true;
                                       } else {
                                          localReqnoTracker = true;

                                       }

                                            this.localReqno=param;


                               }


                        /**
                        * field for Respno
                        */


                                    protected String localRespno ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRespnoTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getRespno(){
                               return localRespno;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Respno
                               */
                               public void setRespno(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localRespnoTracker = true;
                                       } else {
                                          localRespnoTracker = true;

                                       }

                                            this.localRespno=param;


                               }


                        /**
                        * field for ResultCode
                        */


                                    protected String localResultCode ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResultCodeTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getResultCode(){
                               return localResultCode;
                           }



                            /**
                               * Auto generated setter method
                               * @param param ResultCode
                               */
                               public void setResultCode(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localResultCodeTracker = true;
                                       } else {
                                          localResultCodeTracker = true;

                                       }

                                            this.localResultCode=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       RespHeader.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{




                String prefix = null;
                String namespace = null;


                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }

                  if (serializeType){


                   String namespacePrefix = registerPrefix(xmlWriter,"http://resp.msg.mas.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":RespHeader",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "RespHeader",
                           xmlWriter);
                   }


                   }
                if (localReqnoTracker){
                                    namespace = "http://resp.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"reqno", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"reqno");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("reqno");
                                    }


                                          if (localReqno==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localReqno);

                                          }

                                   xmlWriter.writeEndElement();
                             } if (localRespnoTracker){
                                    namespace = "http://resp.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"respno", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"respno");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("respno");
                                    }


                                          if (localRespno==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localRespno);

                                          }

                                   xmlWriter.writeEndElement();
                             } if (localResultCodeTracker){
                                    namespace = "http://resp.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"resultCode", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"resultCode");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("resultCode");
                                    }


                                          if (localResultCode==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localResultCode);

                                          }

                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{



                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localReqnoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://resp.msg.mas.com",
                                                                      "reqno"));

                                         elementList.add(localReqno==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReqno));
                                    } if (localRespnoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://resp.msg.mas.com",
                                                                      "respno"));

                                         elementList.add(localRespno==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRespno));
                                    } if (localResultCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://resp.msg.mas.com",
                                                                      "resultCode"));

                                         elementList.add(localResultCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResultCode));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());



        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static RespHeader parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            RespHeader object =
                new RespHeader();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();


                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);

                            if (!"RespHeader".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (RespHeader) ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }


                  }


                }




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();




                    reader.next();


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://resp.msg.mas.com","reqno").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setReqno(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://resp.msg.mas.com","respno").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setRespno(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://resp.msg.mas.com","resultCode").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setResultCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }

                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();

                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class CPFrag
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = CPFrag
                Namespace URI = http://data.msg.mas.com
                Namespace Prefix = ns2
                */


        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://data.msg.mas.com")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for Content
                        */


                                    protected javax.activation.DataHandler localContent ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localContentTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return javax.activation.DataHandler
                           */
                           public  javax.activation.DataHandler getContent(){
                               return localContent;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Content
                               */
                               public void setContent(javax.activation.DataHandler param){

                                       if (param != null){
                                          //update the setting tracker
                                          localContentTracker = true;
                                       } else {
                                          localContentTracker = true;

                                       }

                                            this.localContent=param;


                               }


                        /**
                        * field for Id
                        */


                                    protected String localId ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIdTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getId(){
                               return localId;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Id
                               */
                               public void setId(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localIdTracker = true;
                                       } else {
                                          localIdTracker = true;

                                       }

                                            this.localId=param;


                               }


                        /**
                        * field for Length
                        */


                                    protected int localLength ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLengthTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getLength(){
                               return localLength;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Length
                               */
                               public void setLength(int param){

                                       // setting primitive attribute tracker to true

                                               if (param== Integer.MIN_VALUE) {
                                           localLengthTracker = false;

                                       } else {
                                          localLengthTracker = true;
                                       }

                                            this.localLength=param;


                               }


                        /**
                        * field for Name
                        */


                                    protected String localName ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNameTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getName(){
                               return localName;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Name
                               */
                               public void setName(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localNameTracker = true;
                                       } else {
                                          localNameTracker = true;

                                       }

                                            this.localName=param;


                               }


                        /**
                        * field for Type
                        */


                                    protected int localType ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTypeTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getType(){
                               return localType;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Type
                               */
                               public void setType(int param){

                                       // setting primitive attribute tracker to true

                                               if (param== Integer.MIN_VALUE) {
                                           localTypeTracker = false;

                                       } else {
                                          localTypeTracker = true;
                                       }

                                            this.localType=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       CPFrag.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{




                String prefix = null;
                String namespace = null;


                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }

                  if (serializeType){


                   String namespacePrefix = registerPrefix(xmlWriter,"http://data.msg.mas.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":CPFrag",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "CPFrag",
                           xmlWriter);
                   }


                   }
                if (localContentTracker){
                                    namespace = "http://data.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"content", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"content");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("content");
                                    }


                                    if (localContent!=null)
                                    {
                                       xmlWriter.writeDataHandler(localContent);
                                    }

                                   xmlWriter.writeEndElement();
                             } if (localIdTracker){
                                    namespace = "http://data.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"id", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"id");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("id");
                                    }


                                          if (localId==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localId);

                                          }

                                   xmlWriter.writeEndElement();
                             } if (localLengthTracker){
                                    namespace = "http://data.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"length", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"length");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("length");
                                    }

                                               if (localLength== Integer.MIN_VALUE) {

                                                         throw new org.apache.axis2.databinding.ADBException("length cannot be null!!");

                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLength));
                                               }

                                   xmlWriter.writeEndElement();
                             } if (localNameTracker){
                                    namespace = "http://data.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"name", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"name");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("name");
                                    }


                                          if (localName==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localName);

                                          }

                                   xmlWriter.writeEndElement();
                             } if (localTypeTracker){
                                    namespace = "http://data.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"type", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"type");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("type");
                                    }

                                               if (localType== Integer.MIN_VALUE) {

                                                         throw new org.apache.axis2.databinding.ADBException("type cannot be null!!");

                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localType));
                                               }

                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{



                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localContentTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                        "content"));

                            elementList.add(localContent);
                        } if (localIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "id"));

                                         elementList.add(localId==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId));
                                    } if (localLengthTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "length"));

                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLength));
                            } if (localNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "name"));

                                         elementList.add(localName==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localName));
                                    } if (localTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "type"));

                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localType));
                            }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());



        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static CPFrag parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            CPFrag object =
                new CPFrag();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();


                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);

                            if (!"CPFrag".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (CPFrag) ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }


                  }


                }




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();




                    reader.next();


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","content").equals(reader.getName())){
                                reader.next();
                                    if (isReaderMTOMAware(reader)
                                            &&
                                            Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_BINARY)))
                                    {
                                        //MTOM aware reader - get the datahandler directly and put it in the object
                                        object.setContent(
                                                (javax.activation.DataHandler) reader.getProperty(org.apache.axiom.om.OMConstants.DATA_HANDLER));
                                    } else {
                                        if (reader.getEventType() == javax.xml.stream.XMLStreamConstants.START_ELEMENT && reader.getName().equals(new javax.xml.namespace.QName(org.apache.axiom.om.impl.MTOMConstants.XOP_NAMESPACE_URI, org.apache.axiom.om.impl.MTOMConstants.XOP_INCLUDE)))
                                        {
                                            String id = org.apache.axiom.om.util.ElementHelper.getContentID(reader, "UTF-8");
                                            object.setContent(((org.apache.axiom.soap.impl.builder.MTOMStAXSOAPModelBuilder) ((org.apache.axiom.om.impl.llom.OMStAXWrapper) reader).getBuilder()).getDataHandler(id));
                                            reader.next();

                                                reader.next();

                                        } else if(reader.hasText()) {
                                            //Do the usual conversion
                                            String content = reader.getText();
                                            object.setContent(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBase64Binary(content));

                                                reader.next();

                                        }
                                    }


                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","id").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","length").equals(reader.getName())){

                                    String content = reader.getElementText();

                                              object.setLength(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                               object.setLength(Integer.MIN_VALUE);

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","name").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","type").equals(reader.getName())){

                                    String content = reader.getElementText();

                                              object.setType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                               object.setType(Integer.MIN_VALUE);

                                    }

                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();

                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class SmsSubmitE
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = SmsSubmit
                Namespace URI = http://data.msg.mas.com
                Namespace Prefix = ns2
                */


        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://data.msg.mas.com")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for Content
                        */


                                    protected String localContent ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localContentTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getContent(){
                               return localContent;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Content
                               */
                               public void setContent(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localContentTracker = true;
                                       } else {
                                          localContentTracker = true;

                                       }

                                            this.localContent=param;


                               }


                        /**
                        * field for Dest
                        */


                                    protected ArrayOfString localDest ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDestTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return ArrayOfString
                           */
                           public  ArrayOfString getDest(){
                               return localDest;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Dest
                               */
                               public void setDest(ArrayOfString param){

                                       if (param != null){
                                          //update the setting tracker
                                          localDestTracker = true;
                                       } else {
                                          localDestTracker = true;

                                       }

                                            this.localDest=param;


                               }


                        /**
                        * field for Param
                        */


                                    protected ArrayOfParam localParam ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localParamTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return ArrayOfParam
                           */
                           public  ArrayOfParam getParam(){
                               return localParam;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Param
                               */
                               public void setParam(ArrayOfParam param){

                                       if (param != null){
                                          //update the setting tracker
                                          localParamTracker = true;
                                       } else {
                                          localParamTracker = true;

                                       }

                                            this.localParam=param;


                               }


                        /**
                        * field for SourceAddr
                        */


                                    protected String localSourceAddr ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSourceAddrTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getSourceAddr(){
                               return localSourceAddr;
                           }



                            /**
                               * Auto generated setter method
                               * @param param SourceAddr
                               */
                               public void setSourceAddr(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localSourceAddrTracker = true;
                                       } else {
                                          localSourceAddrTracker = true;

                                       }

                                            this.localSourceAddr=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       SmsSubmitE.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{




                String prefix = null;
                String namespace = null;


                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }

                  if (serializeType){


                   String namespacePrefix = registerPrefix(xmlWriter,"http://data.msg.mas.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":SmsSubmit",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "SmsSubmit",
                           xmlWriter);
                   }


                   }
                if (localContentTracker){
                                    namespace = "http://data.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"content", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"content");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("content");
                                    }


                                          if (localContent==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localContent);

                                          }

                                   xmlWriter.writeEndElement();
                             } if (localDestTracker){
                                    if (localDest==null){

                                            String namespace2 = "http://data.msg.mas.com";

                                        if (! namespace2.equals("")) {
                                            String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"dest", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"dest");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("dest");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localDest.serialize(new javax.xml.namespace.QName("http://data.msg.mas.com","dest"),
                                        factory,xmlWriter);
                                    }
                                } if (localParamTracker){
                                    if (localParam==null){

                                            String namespace2 = "http://data.msg.mas.com";

                                        if (! namespace2.equals("")) {
                                            String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"param", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"param");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("param");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localParam.serialize(new javax.xml.namespace.QName("http://data.msg.mas.com","param"),
                                        factory,xmlWriter);
                                    }
                                } if (localSourceAddrTracker){
                                    namespace = "http://data.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"sourceAddr", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"sourceAddr");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("sourceAddr");
                                    }


                                          if (localSourceAddr==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localSourceAddr);

                                          }

                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{



                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localContentTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "content"));

                                         elementList.add(localContent==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localContent));
                                    } if (localDestTracker){
                            elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "dest"));


                                    elementList.add(localDest==null?null:
                                    localDest);
                                } if (localParamTracker){
                            elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "param"));


                                    elementList.add(localParam==null?null:
                                    localParam);
                                } if (localSourceAddrTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "sourceAddr"));

                                         elementList.add(localSourceAddr==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSourceAddr));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());



        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static SmsSubmitE parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            SmsSubmitE object =
                new SmsSubmitE();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();


                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);

                            if (!"SmsSubmit".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (SmsSubmitE) ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }


                  }


                }




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();




                    reader.next();


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","content").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setContent(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","dest").equals(reader.getName())){

                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setDest(null);
                                          reader.next();

                                            reader.next();

                                      }else{

                                                object.setDest(ArrayOfString.Factory.parse(reader));

                                        reader.next();
                                    }
                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","param").equals(reader.getName())){

                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setParam(null);
                                          reader.next();

                                            reader.next();

                                      }else{

                                                object.setParam(ArrayOfParam.Factory.parse(reader));

                                        reader.next();
                                    }
                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","sourceAddr").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setSourceAddr(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }

                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();

                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class ArrayOfParam
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = ArrayOfParam
                Namespace URI = http://data.msg.mas.com
                Namespace Prefix = ns2
                */


        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://data.msg.mas.com")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for Param
                        * This was an Array!
                        */


                                    protected Param[] localParam ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localParamTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return Param[]
                           */
                           public  Param[] getParam(){
                               return localParam;
                           }






                              /**
                               * validate the array for Param
                               */
                              protected void validateParam(Param[] param){

                              }


                             /**
                              * Auto generated setter method
                              * @param param Param
                              */
                              public void setParam(Param[] param){

                                   validateParam(param);


                                          if (param != null){
                                             //update the setting tracker
                                             localParamTracker = true;
                                          } else {
                                             localParamTracker = true;

                                          }

                                      this.localParam=param;
                              }



                             /**
                             * Auto generated add method for the array for convenience
                             * @param param Param
                             */
                             public void addParam(Param param){
                                   if (localParam == null){
                                   localParam = new Param[]{};
                                   }


                                 //update the setting tracker
                                localParamTracker = true;


                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localParam);
                               list.add(param);
                               this.localParam =
                             (Param[])list.toArray(
                            new Param[list.size()]);

                             }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       ArrayOfParam.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{




                String prefix = null;
                String namespace = null;


                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }

                  if (serializeType){


                   String namespacePrefix = registerPrefix(xmlWriter,"http://data.msg.mas.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":ArrayOfParam",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "ArrayOfParam",
                           xmlWriter);
                   }


                   }
                if (localParamTracker){
                                       if (localParam!=null){
                                            for (int i = 0;i < localParam.length;i++){
                                                if (localParam[i] != null){
                                                 localParam[i].serialize(new javax.xml.namespace.QName("http://data.msg.mas.com","Param"),
                                                           factory,xmlWriter);
                                                } else {

                                                            // write null attribute
                                                            String namespace2 = "http://data.msg.mas.com";
                                                            if (! namespace2.equals("")) {
                                                                String prefix2 = xmlWriter.getPrefix(namespace2);

                                                                if (prefix2 == null) {
                                                                    prefix2 = generatePrefix(namespace2);

                                                                    xmlWriter.writeStartElement(prefix2,"Param", namespace2);
                                                                    xmlWriter.writeNamespace(prefix2, namespace2);
                                                                    xmlWriter.setPrefix(prefix2, namespace2);

                                                                } else {
                                                                    xmlWriter.writeStartElement(namespace2,"Param");
                                                                }

                                                            } else {
                                                                xmlWriter.writeStartElement("Param");
                                                            }

                                                           // write the nil attribute
                                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                           xmlWriter.writeEndElement();

                                                }

                                            }
                                     } else {

                                                // write null attribute
                                                String namespace2 = "http://data.msg.mas.com";
                                                if (! namespace2.equals("")) {
                                                    String prefix2 = xmlWriter.getPrefix(namespace2);

                                                    if (prefix2 == null) {
                                                        prefix2 = generatePrefix(namespace2);

                                                        xmlWriter.writeStartElement(prefix2,"Param", namespace2);
                                                        xmlWriter.writeNamespace(prefix2, namespace2);
                                                        xmlWriter.setPrefix(prefix2, namespace2);

                                                    } else {
                                                        xmlWriter.writeStartElement(namespace2,"Param");
                                                    }

                                                } else {
                                                    xmlWriter.writeStartElement("Param");
                                                }

                                               // write the nil attribute
                                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                               xmlWriter.writeEndElement();

                                    }
                                 }
                    xmlWriter.writeEndElement();


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{



                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localParamTracker){
                             if (localParam!=null) {
                                 for (int i = 0;i < localParam.length;i++){

                                    if (localParam[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                          "Param"));
                                         elementList.add(localParam[i]);
                                    } else {

                                                elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                          "Param"));
                                                elementList.add(null);

                                    }

                                 }
                             } else {

                                        elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                          "Param"));
                                        elementList.add(localParam);

                             }

                        }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());



        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static ArrayOfParam parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            ArrayOfParam object =
                new ArrayOfParam();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();


                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);

                            if (!"ArrayOfParam".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (ArrayOfParam) ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }


                  }


                }




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();




                    reader.next();

                        java.util.ArrayList list1 = new java.util.ArrayList();


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","Param").equals(reader.getName())){



                                    // Process the array and step past its final element's end.

                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list1.add(null);
                                                              reader.next();
                                                          } else {
                                                        list1.add(Param.Factory.parse(reader));
                                                                }
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone1 = false;
                                                        while(!loopDone1){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone1 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://data.msg.mas.com","Param").equals(reader.getName())){

                                                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                                          list1.add(null);
                                                                          reader.next();
                                                                      } else {
                                                                    list1.add(Param.Factory.parse(reader));
                                                                        }
                                                                }else{
                                                                    loopDone1 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array

                                                        object.setParam((Param[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                Param.class,
                                                                list1));

                              }  // End of if for expected property start element

                                    else {

                                    }

                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();

                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class MmsSubmit
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = mmsSubmit
                Namespace URI = http://service.msg.mas.com/
                Namespace Prefix = ns3
                */


        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://service.msg.mas.com/")){
                return "ns3";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for Arg0
                        */


                                    protected ReqHeader localArg0 ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localArg0Tracker = false ;


                           /**
                           * Auto generated getter method
                           * @return ReqHeader
                           */
                           public  ReqHeader getArg0(){
                               return localArg0;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Arg0
                               */
                               public void setArg0(ReqHeader param){

                                       if (param != null){
                                          //update the setting tracker
                                          localArg0Tracker = true;
                                       } else {
                                          localArg0Tracker = false;

                                       }

                                            this.localArg0=param;


                               }


                        /**
                        * field for Arg1
                        */


                                    protected MmsSubmitE localArg1 ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localArg1Tracker = false ;


                           /**
                           * Auto generated getter method
                           * @return MmsSubmitE
                           */
                           public  MmsSubmitE getArg1(){
                               return localArg1;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Arg1
                               */
                               public void setArg1(MmsSubmitE param){

                                       if (param != null){
                                          //update the setting tracker
                                          localArg1Tracker = true;
                                       } else {
                                          localArg1Tracker = false;

                                       }

                                            this.localArg1=param;


                               }


                        /**
                        * field for Arg2
                        */


                                    protected ArrayOfParam localArg2 ;


                           /**
                           * Auto generated getter method
                           * @return ArrayOfParam
                           */
                           public  ArrayOfParam getArg2(){
                               return localArg2;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Arg2
                               */
                               public void setArg2(ArrayOfParam param){

                                            this.localArg2=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       MmsSubmit.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{




                String prefix = null;
                String namespace = null;


                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }

                  if (serializeType){


                   String namespacePrefix = registerPrefix(xmlWriter,"http://service.msg.mas.com/");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":mmsSubmit",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "mmsSubmit",
                           xmlWriter);
                   }


                   }
                if (localArg0Tracker){
                                            if (localArg0==null){
                                                 throw new org.apache.axis2.databinding.ADBException("arg0 cannot be null!!");
                                            }
                                           localArg0.serialize(new javax.xml.namespace.QName("http://service.msg.mas.com/","arg0"),
                                               factory,xmlWriter);
                                        } if (localArg1Tracker){
                                            if (localArg1==null){
                                                 throw new org.apache.axis2.databinding.ADBException("arg1 cannot be null!!");
                                            }
                                           localArg1.serialize(new javax.xml.namespace.QName("http://service.msg.mas.com/","arg1"),
                                               factory,xmlWriter);
                                        }
                                    if (localArg2==null){

                                            String namespace2 = "http://service.msg.mas.com/";

                                        if (! namespace2.equals("")) {
                                            String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"arg2", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"arg2");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("arg2");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localArg2.serialize(new javax.xml.namespace.QName("http://service.msg.mas.com/","arg2"),
                                        factory,xmlWriter);
                                    }

                    xmlWriter.writeEndElement();


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{



                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localArg0Tracker){
                            elementList.add(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                                      "arg0"));


                                    if (localArg0==null){
                                         throw new org.apache.axis2.databinding.ADBException("arg0 cannot be null!!");
                                    }
                                    elementList.add(localArg0);
                                } if (localArg1Tracker){
                            elementList.add(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                                      "arg1"));


                                    if (localArg1==null){
                                         throw new org.apache.axis2.databinding.ADBException("arg1 cannot be null!!");
                                    }
                                    elementList.add(localArg1);
                                }
                            elementList.add(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                                      "arg2"));


                                    elementList.add(localArg2==null?null:
                                    localArg2);


                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());



        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static MmsSubmit parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            MmsSubmit object =
                new MmsSubmit();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();


                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);

                            if (!"mmsSubmit".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (MmsSubmit) ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }


                  }


                }




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();




                    reader.next();


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.msg.mas.com/","arg0").equals(reader.getName())){

                                                object.setArg0(ReqHeader.Factory.parse(reader));

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.msg.mas.com/","arg1").equals(reader.getName())){

                                                object.setArg1(MmsSubmitE.Factory.parse(reader));

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.msg.mas.com/","arg2").equals(reader.getName())){

                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setArg2(null);
                                          reader.next();

                                            reader.next();

                                      }else{

                                                object.setArg2(ArrayOfParam.Factory.parse(reader));

                                        reader.next();
                                    }
                              }  // End of if for expected property start element

                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }

                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();

                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class SmsSubmitResponse
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = smsSubmitResponse
                Namespace URI = http://service.msg.mas.com/
                Namespace Prefix = ns3
                */


        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://service.msg.mas.com/")){
                return "ns3";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for _return
                        */


                                    protected RespHeader local_return ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean local_returnTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return RespHeader
                           */
                           public  RespHeader get_return(){
                               return local_return;
                           }



                            /**
                               * Auto generated setter method
                               * @param param _return
                               */
                               public void set_return(RespHeader param){

                                       if (param != null){
                                          //update the setting tracker
                                          local_returnTracker = true;
                                       } else {
                                          local_returnTracker = false;

                                       }

                                            this.local_return=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       SmsSubmitResponse.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{




                String prefix = null;
                String namespace = null;


                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }

                  if (serializeType){


                   String namespacePrefix = registerPrefix(xmlWriter,"http://service.msg.mas.com/");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":smsSubmitResponse",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "smsSubmitResponse",
                           xmlWriter);
                   }


                   }
                if (local_returnTracker){
                                            if (local_return==null){
                                                 throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
                                            }
                                           local_return.serialize(new javax.xml.namespace.QName("http://service.msg.mas.com/","return"),
                                               factory,xmlWriter);
                                        }
                    xmlWriter.writeEndElement();


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{



                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (local_returnTracker){
                            elementList.add(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                                      "return"));


                                    if (local_return==null){
                                         throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
                                    }
                                    elementList.add(local_return);
                                }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());



        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static SmsSubmitResponse parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            SmsSubmitResponse object =
                new SmsSubmitResponse();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();


                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);

                            if (!"smsSubmitResponse".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (SmsSubmitResponse) ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }


                  }


                }




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();




                    reader.next();


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.msg.mas.com/","return").equals(reader.getName())){

                                                object.set_return(RespHeader.Factory.parse(reader));

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }

                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();

                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class SmsSubmitResponseE
        implements org.apache.axis2.databinding.ADBBean{

                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://service.msg.mas.com/",
                "smsSubmitResponse",
                "ns3");



        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://service.msg.mas.com/")){
                return "ns3";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for SmsSubmitResponse
                        */


                                    protected SmsSubmitResponse localSmsSubmitResponse ;


                           /**
                           * Auto generated getter method
                           * @return SmsSubmitResponse
                           */
                           public  SmsSubmitResponse getSmsSubmitResponse(){
                               return localSmsSubmitResponse;
                           }



                            /**
                               * Auto generated setter method
                               * @param param SmsSubmitResponse
                               */
                               public void setSmsSubmitResponse(SmsSubmitResponse param){

                                            this.localSmsSubmitResponse=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



                org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       SmsSubmitResponseE.this.serialize(MY_QNAME,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               MY_QNAME,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{


                //We can safely assume an element has only one type associated with it

                                 if (localSmsSubmitResponse==null){
                                   throw new org.apache.axis2.databinding.ADBException("Property cannot be null!");
                                 }
                                 localSmsSubmitResponse.serialize(MY_QNAME,factory,xmlWriter);


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{




                //We can safely assume an element has only one type associated with it
                return localSmsSubmitResponse.getPullParser(MY_QNAME);

        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static SmsSubmitResponseE parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            SmsSubmitResponseE object =
                new SmsSubmitResponseE();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();



                while(!reader.isEndElement()) {
                    if (reader.isStartElement() ){

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.msg.mas.com/","smsSubmitResponse").equals(reader.getName())){

                                                object.setSmsSubmitResponse(SmsSubmitResponse.Factory.parse(reader));

                              }  // End of if for expected property start element

                             else{
                                        // A start element we are not expecting indicates an invalid parameter was passed
                                        throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                             }

                             } else {
                                reader.next();
                             }
                           }  // end of while loop




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class ExtensionMapper{

          public static Object getTypeObject(String namespaceURI,
                                                       String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws Exception{


                  if (
                  "http://req.msg.mas.com".equals(namespaceURI) &&
                  "ReqHeader".equals(typeName)){

                            return  ReqHeader.Factory.parse(reader);


                  }


                  if (
                  "http://data.msg.mas.com".equals(namespaceURI) &&
                  "CPMMS".equals(typeName)){

                            return  CPMMS.Factory.parse(reader);


                  }


                  if (
                  "http://data.msg.mas.com".equals(namespaceURI) &&
                  "ArrayOfCPFrag".equals(typeName)){

                            return  ArrayOfCPFrag.Factory.parse(reader);


                  }


                  if (
                  "http://data.msg.mas.com".equals(namespaceURI) &&
                  "Param".equals(typeName)){

                            return  Param.Factory.parse(reader);


                  }


                  if (
                  "http://service.msg.mas.com/".equals(namespaceURI) &&
                  "ArrayOfString".equals(typeName)){

                            return  ArrayOfString.Factory.parse(reader);


                  }


                  if (
                  "http://resp.msg.mas.com".equals(namespaceURI) &&
                  "RespHeader".equals(typeName)){

                            return  RespHeader.Factory.parse(reader);


                  }


                  if (
                  "http://service.msg.mas.com/".equals(namespaceURI) &&
                  "mmsSubmitResponse".equals(typeName)){

                            return  MmsSubmitResponse.Factory.parse(reader);


                  }


                  if (
                  "http://data.msg.mas.com".equals(namespaceURI) &&
                  "CPFrag".equals(typeName)){

                            return  CPFrag.Factory.parse(reader);


                  }


                  if (
                  "http://data.msg.mas.com".equals(namespaceURI) &&
                  "ArrayOfParam".equals(typeName)){

                            return  ArrayOfParam.Factory.parse(reader);


                  }


                  if (
                  "http://data.msg.mas.com".equals(namespaceURI) &&
                  "SmsSubmit".equals(typeName)){

                            return  SmsSubmitE.Factory.parse(reader);


                  }


                  if (
                  "http://service.msg.mas.com/".equals(namespaceURI) &&
                  "mmsSubmit".equals(typeName)){

                            return  MmsSubmit.Factory.parse(reader);


                  }


                  if (
                  "http://service.msg.mas.com/".equals(namespaceURI) &&
                  "smsSubmitResponse".equals(typeName)){

                            return  SmsSubmitResponse.Factory.parse(reader);


                  }


                  if (
                  "http://service.msg.mas.com/".equals(namespaceURI) &&
                  "smsSubmit".equals(typeName)){

                            return  SmsSubmit.Factory.parse(reader);


                  }


                  if (
                  "http://data.msg.mas.com".equals(namespaceURI) &&
                  "MmsSubmit".equals(typeName)){

                            return  MmsSubmitE.Factory.parse(reader);


                  }


             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }

        public static class SmsSubmit3
        implements org.apache.axis2.databinding.ADBBean{

                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://service.msg.mas.com/",
                "smsSubmit",
                "ns3");



        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://service.msg.mas.com/")){
                return "ns3";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for SmsSubmit
                        */


                                    protected SmsSubmit localSmsSubmit ;


                           /**
                           * Auto generated getter method
                           * @return SmsSubmit
                           */
                           public  SmsSubmit getSmsSubmit(){
                               return localSmsSubmit;
                           }



                            /**
                               * Auto generated setter method
                               * @param param SmsSubmit
                               */
                               public void setSmsSubmit(SmsSubmit param){

                                            this.localSmsSubmit=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



                org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       SmsSubmit3.this.serialize(MY_QNAME,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               MY_QNAME,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{


                //We can safely assume an element has only one type associated with it

                                 if (localSmsSubmit==null){
                                   throw new org.apache.axis2.databinding.ADBException("Property cannot be null!");
                                 }
                                 localSmsSubmit.serialize(MY_QNAME,factory,xmlWriter);


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{




                //We can safely assume an element has only one type associated with it
                return localSmsSubmit.getPullParser(MY_QNAME);

        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static SmsSubmit3 parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            SmsSubmit3 object =
                new SmsSubmit3();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();



                while(!reader.isEndElement()) {
                    if (reader.isStartElement() ){

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.msg.mas.com/","smsSubmit").equals(reader.getName())){

                                                object.setSmsSubmit(SmsSubmit.Factory.parse(reader));

                              }  // End of if for expected property start element

                             else{
                                        // A start element we are not expecting indicates an invalid parameter was passed
                                        throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                             }

                             } else {
                                reader.next();
                             }
                           }  // end of while loop




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class MmsSubmitE
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = MmsSubmit
                Namespace URI = http://data.msg.mas.com
                Namespace Prefix = ns2
                */


        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://data.msg.mas.com")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for Cpmms
                        */


                                    protected CPMMS localCpmms ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCpmmsTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return CPMMS
                           */
                           public  CPMMS getCpmms(){
                               return localCpmms;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Cpmms
                               */
                               public void setCpmms(CPMMS param){

                                       if (param != null){
                                          //update the setting tracker
                                          localCpmmsTracker = true;
                                       } else {
                                          localCpmmsTracker = true;

                                       }

                                            this.localCpmms=param;


                               }


                        /**
                        * field for Dest
                        */


                                    protected ArrayOfString localDest ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDestTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return ArrayOfString
                           */
                           public  ArrayOfString getDest(){
                               return localDest;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Dest
                               */
                               public void setDest(ArrayOfString param){

                                       if (param != null){
                                          //update the setting tracker
                                          localDestTracker = true;
                                       } else {
                                          localDestTracker = true;

                                       }

                                            this.localDest=param;


                               }


                        /**
                        * field for Param
                        */


                                    protected ArrayOfParam localParam ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localParamTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return ArrayOfParam
                           */
                           public  ArrayOfParam getParam(){
                               return localParam;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Param
                               */
                               public void setParam(ArrayOfParam param){

                                       if (param != null){
                                          //update the setting tracker
                                          localParamTracker = true;
                                       } else {
                                          localParamTracker = true;

                                       }

                                            this.localParam=param;


                               }


                        /**
                        * field for SourceAddr
                        */


                                    protected String localSourceAddr ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSourceAddrTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getSourceAddr(){
                               return localSourceAddr;
                           }



                            /**
                               * Auto generated setter method
                               * @param param SourceAddr
                               */
                               public void setSourceAddr(String param){

                                       if (param != null){
                                          //update the setting tracker
                                          localSourceAddrTracker = true;
                                       } else {
                                          localSourceAddrTracker = true;

                                       }

                                            this.localSourceAddr=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       MmsSubmitE.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{




                String prefix = null;
                String namespace = null;


                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }

                  if (serializeType){


                   String namespacePrefix = registerPrefix(xmlWriter,"http://data.msg.mas.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":MmsSubmit",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "MmsSubmit",
                           xmlWriter);
                   }


                   }
                if (localCpmmsTracker){
                                    if (localCpmms==null){

                                            String namespace2 = "http://data.msg.mas.com";

                                        if (! namespace2.equals("")) {
                                            String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"cpmms", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"cpmms");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("cpmms");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localCpmms.serialize(new javax.xml.namespace.QName("http://data.msg.mas.com","cpmms"),
                                        factory,xmlWriter);
                                    }
                                } if (localDestTracker){
                                    if (localDest==null){

                                            String namespace2 = "http://data.msg.mas.com";

                                        if (! namespace2.equals("")) {
                                            String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"dest", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"dest");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("dest");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localDest.serialize(new javax.xml.namespace.QName("http://data.msg.mas.com","dest"),
                                        factory,xmlWriter);
                                    }
                                } if (localParamTracker){
                                    if (localParam==null){

                                            String namespace2 = "http://data.msg.mas.com";

                                        if (! namespace2.equals("")) {
                                            String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"param", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"param");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("param");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localParam.serialize(new javax.xml.namespace.QName("http://data.msg.mas.com","param"),
                                        factory,xmlWriter);
                                    }
                                } if (localSourceAddrTracker){
                                    namespace = "http://data.msg.mas.com";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"sourceAddr", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"sourceAddr");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("sourceAddr");
                                    }


                                          if (localSourceAddr==null){
                                              // write the nil attribute

                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);

                                          }else{


                                                   xmlWriter.writeCharacters(localSourceAddr);

                                          }

                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{



                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localCpmmsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "cpmms"));


                                    elementList.add(localCpmms==null?null:
                                    localCpmms);
                                } if (localDestTracker){
                            elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "dest"));


                                    elementList.add(localDest==null?null:
                                    localDest);
                                } if (localParamTracker){
                            elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "param"));


                                    elementList.add(localParam==null?null:
                                    localParam);
                                } if (localSourceAddrTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://data.msg.mas.com",
                                                                      "sourceAddr"));

                                         elementList.add(localSourceAddr==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSourceAddr));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());



        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static MmsSubmitE parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            MmsSubmitE object =
                new MmsSubmitE();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();


                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);

                            if (!"MmsSubmit".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (MmsSubmitE) ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }


                  }


                }




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();




                    reader.next();


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","cpmms").equals(reader.getName())){

                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setCpmms(null);
                                          reader.next();

                                            reader.next();

                                      }else{

                                                object.setCpmms(CPMMS.Factory.parse(reader));

                                        reader.next();
                                    }
                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","dest").equals(reader.getName())){

                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setDest(null);
                                          reader.next();

                                            reader.next();

                                      }else{

                                                object.setDest(ArrayOfString.Factory.parse(reader));

                                        reader.next();
                                    }
                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","param").equals(reader.getName())){

                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setParam(null);
                                          reader.next();

                                            reader.next();

                                      }else{

                                                object.setParam(ArrayOfParam.Factory.parse(reader));

                                        reader.next();
                                    }
                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://data.msg.mas.com","sourceAddr").equals(reader.getName())){

                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){

                                    String content = reader.getElementText();

                                              object.setSourceAddr(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                       } else {


                                           reader.getElementText(); // throw away text nodes if any.
                                       }

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }

                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();

                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class SmsSubmit
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = smsSubmit
                Namespace URI = http://service.msg.mas.com/
                Namespace Prefix = ns3
                */


        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://service.msg.mas.com/")){
                return "ns3";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for Arg0
                        */


                                    protected ReqHeader localArg0 ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localArg0Tracker = false ;


                           /**
                           * Auto generated getter method
                           * @return ReqHeader
                           */
                           public  ReqHeader getArg0(){
                               return localArg0;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Arg0
                               */
                               public void setArg0(ReqHeader param){

                                       if (param != null){
                                          //update the setting tracker
                                          localArg0Tracker = true;
                                       } else {
                                          localArg0Tracker = false;

                                       }

                                            this.localArg0=param;


                               }


                        /**
                        * field for Arg1
                        */


                                    protected SmsSubmitE localArg1 ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localArg1Tracker = false ;


                           /**
                           * Auto generated getter method
                           * @return SmsSubmitE
                           */
                           public  SmsSubmitE getArg1(){
                               return localArg1;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Arg1
                               */
                               public void setArg1(SmsSubmitE param){

                                       if (param != null){
                                          //update the setting tracker
                                          localArg1Tracker = true;
                                       } else {
                                          localArg1Tracker = false;

                                       }

                                            this.localArg1=param;


                               }


                        /**
                        * field for Arg2
                        */


                                    protected ArrayOfParam localArg2 ;


                           /**
                           * Auto generated getter method
                           * @return ArrayOfParam
                           */
                           public  ArrayOfParam getArg2(){
                               return localArg2;
                           }



                            /**
                               * Auto generated setter method
                               * @param param Arg2
                               */
                               public void setArg2(ArrayOfParam param){

                                            this.localArg2=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       SmsSubmit.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{




                String prefix = null;
                String namespace = null;


                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }

                  if (serializeType){


                   String namespacePrefix = registerPrefix(xmlWriter,"http://service.msg.mas.com/");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":smsSubmit",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "smsSubmit",
                           xmlWriter);
                   }


                   }
                if (localArg0Tracker){
                                            if (localArg0==null){
                                                 throw new org.apache.axis2.databinding.ADBException("arg0 cannot be null!!");
                                            }
                                           localArg0.serialize(new javax.xml.namespace.QName("http://service.msg.mas.com/","arg0"),
                                               factory,xmlWriter);
                                        } if (localArg1Tracker){
                                            if (localArg1==null){
                                                 throw new org.apache.axis2.databinding.ADBException("arg1 cannot be null!!");
                                            }
                                           localArg1.serialize(new javax.xml.namespace.QName("http://service.msg.mas.com/","arg1"),
                                               factory,xmlWriter);
                                        }
                                    if (localArg2==null){

                                            String namespace2 = "http://service.msg.mas.com/";

                                        if (! namespace2.equals("")) {
                                            String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"arg2", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"arg2");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("arg2");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localArg2.serialize(new javax.xml.namespace.QName("http://service.msg.mas.com/","arg2"),
                                        factory,xmlWriter);
                                    }

                    xmlWriter.writeEndElement();


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{



                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localArg0Tracker){
                            elementList.add(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                                      "arg0"));


                                    if (localArg0==null){
                                         throw new org.apache.axis2.databinding.ADBException("arg0 cannot be null!!");
                                    }
                                    elementList.add(localArg0);
                                } if (localArg1Tracker){
                            elementList.add(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                                      "arg1"));


                                    if (localArg1==null){
                                         throw new org.apache.axis2.databinding.ADBException("arg1 cannot be null!!");
                                    }
                                    elementList.add(localArg1);
                                }
                            elementList.add(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                                      "arg2"));


                                    elementList.add(localArg2==null?null:
                                    localArg2);


                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());



        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static SmsSubmit parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            SmsSubmit object =
                new SmsSubmit();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();


                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);

                            if (!"smsSubmit".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (SmsSubmit) ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }


                  }


                }




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();




                    reader.next();


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.msg.mas.com/","arg0").equals(reader.getName())){

                                                object.setArg0(ReqHeader.Factory.parse(reader));

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.msg.mas.com/","arg1").equals(reader.getName())){

                                                object.setArg1(SmsSubmitE.Factory.parse(reader));

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.msg.mas.com/","arg2").equals(reader.getName())){

                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setArg2(null);
                                          reader.next();

                                            reader.next();

                                      }else{

                                                object.setArg2(ArrayOfParam.Factory.parse(reader));

                                        reader.next();
                                    }
                              }  // End of if for expected property start element

                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }

                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();

                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


        public static class MmsSubmitResponse
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = mmsSubmitResponse
                Namespace URI = http://service.msg.mas.com/
                Namespace Prefix = ns3
                */


        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://service.msg.mas.com/")){
                return "ns3";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }



                        /**
                        * field for _return
                        */


                                    protected RespHeader local_return ;

                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean local_returnTracker = false ;


                           /**
                           * Auto generated getter method
                           * @return RespHeader
                           */
                           public  RespHeader get_return(){
                               return local_return;
                           }



                            /**
                               * Auto generated setter method
                               * @param param _return
                               */
                               public void set_return(RespHeader param){

                                       if (param != null){
                                          //update the setting tracker
                                          local_returnTracker = true;
                                       } else {
                                          local_returnTracker = false;

                                       }

                                            this.local_return=param;


                               }


     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try{
          isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }


        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{



               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       MmsSubmitResponse.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);

       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{




                String prefix = null;
                String namespace = null;


                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }

                  if (serializeType){


                   String namespacePrefix = registerPrefix(xmlWriter,"http://service.msg.mas.com/");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":mmsSubmitResponse",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "mmsSubmitResponse",
                           xmlWriter);
                   }


                   }
                if (local_returnTracker){
                                            if (local_return==null){
                                                 throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
                                            }
                                           local_return.serialize(new javax.xml.namespace.QName("http://service.msg.mas.com/","return"),
                                               factory,xmlWriter);
                                        }
                    xmlWriter.writeEndElement();


        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(String prefix,String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(String namespace,String attName,
                                      String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
                String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }



        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{



                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (local_returnTracker){
                            elementList.add(new javax.xml.namespace.QName("http://service.msg.mas.com/",
                                                                      "return"));


                                    if (local_return==null){
                                         throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
                                    }
                                    elementList.add(local_return);
                                }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());



        }



     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{




        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static MmsSubmitResponse parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            MmsSubmitResponse object =
                new MmsSubmitResponse();

            int event;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();


                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);

                            if (!"mmsSubmitResponse".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (MmsSubmitResponse) ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }


                  }


                }




                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();




                    reader.next();


                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.msg.mas.com/","return").equals(reader.getName())){

                                                object.set_return(RespHeader.Factory.parse(reader));

                                        reader.next();

                              }  // End of if for expected property start element

                                    else {

                                    }

                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();

                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());




            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class



        }


            private  org.apache.axiom.om.OMElement  toOM(com.sms.common.SubmitServiceImplServiceStub.SmsSubmit3 param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {


                        try{
                             return param.getOMElement(com.sms.common.SubmitServiceImplServiceStub.SmsSubmit3.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }


            }

            private  org.apache.axiom.om.OMElement  toOM(com.sms.common.SubmitServiceImplServiceStub.SmsSubmitResponseE param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {


                        try{
                             return param.getOMElement(com.sms.common.SubmitServiceImplServiceStub.SmsSubmitResponseE.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }


            }

            private  org.apache.axiom.om.OMElement  toOM(com.sms.common.SubmitServiceImplServiceStub.MmsSubmit2 param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {


                        try{
                             return param.getOMElement(com.sms.common.SubmitServiceImplServiceStub.MmsSubmit2.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }


            }

            private  org.apache.axiom.om.OMElement  toOM(com.sms.common.SubmitServiceImplServiceStub.MmsSubmitResponseE param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {


                        try{
                             return param.getOMElement(com.sms.common.SubmitServiceImplServiceStub.MmsSubmitResponseE.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }


            }


                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.sms.common.SubmitServiceImplServiceStub.SmsSubmit3 param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{


                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.sms.common.SubmitServiceImplServiceStub.SmsSubmit3.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }


                                        }


                             /* methods to provide back word compatibility */



                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.sms.common.SubmitServiceImplServiceStub.MmsSubmit2 param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{


                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.sms.common.SubmitServiceImplServiceStub.MmsSubmit2.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }


                                        }


                             /* methods to provide back word compatibility */




        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  Object fromOM(
        org.apache.axiom.om.OMElement param,
        Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {

                if (com.sms.common.SubmitServiceImplServiceStub.SmsSubmit3.class.equals(type)){

                           return com.sms.common.SubmitServiceImplServiceStub.SmsSubmit3.Factory.parse(param.getXMLStreamReaderWithoutCaching());


                }

                if (com.sms.common.SubmitServiceImplServiceStub.SmsSubmitResponseE.class.equals(type)){

                           return com.sms.common.SubmitServiceImplServiceStub.SmsSubmitResponseE.Factory.parse(param.getXMLStreamReaderWithoutCaching());


                }

                if (com.sms.common.SubmitServiceImplServiceStub.MmsSubmit2.class.equals(type)){

                           return com.sms.common.SubmitServiceImplServiceStub.MmsSubmit2.Factory.parse(param.getXMLStreamReaderWithoutCaching());


                }

                if (com.sms.common.SubmitServiceImplServiceStub.MmsSubmitResponseE.class.equals(type)){

                           return com.sms.common.SubmitServiceImplServiceStub.MmsSubmitResponseE.Factory.parse(param.getXMLStreamReaderWithoutCaching());


                }

        } catch (Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    
   }
   