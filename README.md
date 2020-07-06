# custom-plugin-java
Library that allows you to define Mia-Platform custom plugins in java easily

## Purpose

This plugin aims to provide an easy way to integrate the Platform.

It is able to integrate either the CRUD and other REST services.
The underlying library which enables the possibility to operate with external service is Retrofit, although the user (developer) wouldn't use this in his code. 

## Internal Architecture

This plugin is framework-independent. Every functionality is built using plain Java code. 

## Usage
To get a service proxy you can use the following method from class `ServiceCilentFactory`:
### getServiceProxy
Before getting your service proxy, you need to inject the appropriate InitServiceOptions instance:

- Default InitServiceOptions (port: 3000, protocol: HTTP)
    ``` java
    InitServiceOptions initOptions = new InitServiceOptions();
    Service serviceClient = ServiceClientFactory.getServiceProxy("my-microservice", initOptions);
    ```
- Custom InitServiceOptions you can set your custom headers (including Mia Platform headers) through the constructor `headers` parameters:
    ``` java
    Map<String, String> headers = ... // headers
    InitServiceOptions initOptions = new InitServiceOptions(3000, Protocol.HTTPS, headers, "");
    Service serviceClient = ServiceClientFactory.getServiceProxy("my-microservice", initOptions);
    ``` 
- Use microservice gateway as service name (like getServiceProxy in custom-plugin-lib)
     ``` java
    InitServiceOptions initOptions = new InitServiceOptions();
    Service serviceClient = ServiceClientFactory.getServiceProxy(MICROSERVICE_GATEWAY_SERVICE_NAME, initOptions);
    ``` 
