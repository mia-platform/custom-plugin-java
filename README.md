# custom-plugin-java
Library that allows you to define Mia-Platform custom plugins in java easily

## Purpose

This plugin aims to provide an easy way to integrate the Platform.

It is able to integrate either the CRUD and other REST services.
The underlying library which enables the possibility to operate with external service is Retrofit, although the user (developer) wouldn't use this in his code. 

## Internal Architecture

This plugin is framework-independent. Every functionality is built using plain Java code. 

## Usage
### Service proxy
#### Service options
Before getting your service proxy, you need to inject the appropriate `InitServiceOptions` instance:
+ Default `InitServiceOptions` (port: 3000, protocol: HTTP)
    ``` java
    InitServiceOptions initOptions = new InitServiceOptions();
    ```
+ Custom `InitServiceOptions`: you can build an instance with `InitServiceOptions.builder()`. For example, you can set custom headers (including Mia Platform headers):
    ``` java
    Map<String, String> customHeaders = ... // headers
    InitServiceOptions initOptions = InitServiceOptions.builder().headers(customHeaders).build();
    ``` 
  
#### Getting a proxy
To get a service proxy, you can use the following methods from class `ServiceClientFactory`:

+ `getDirectServiceProxy`, to directly communicate with a specific microservice
    ``` java
    Service serviceClient = ServiceClientFactory.getDirectServiceProxy("my-microservice", initOptions);
    ``` 

+ `getServiceProxy`, to use microservice gateway:
     ``` java
    Service serviceClient = ServiceClientFactory.getServiceProxy(initOptions);
    ``` 
