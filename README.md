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
### Decorators

#### PRE decorators
##### Request
An instance of class `PreDecoratorRequest` can be used as input to the decorator handler.

The utility functions exposed by the `PreDecoratorRequest` instance can be used to access the original request:

+ `getOriginalRequestMethod()` - returns the original request method
+ `getOriginalRequestPath()` - returns the path of the original request
+ `getOriginalRequestHeaders()` - returns the headers of the original request
+ `getOriginalRequestQuery()` - returns the querystring of the original request
+ `getOriginalRequestBody()` - returns the body of the original request

In addition to the methods described above, the `PreDecoratorRequest` instance exposes an interface to modify the original request,
 which will come forwarded to the target service. This interface is accessible using the instance method 
 `changeOriginalRequest` which returns a builder for `PreDecoratorRequestProxy` object with following methods:

+ `setMethod(String newMethod)` - modify the method of the original request
+ `setPath(String newPath)` - modify the path of the original request
+ `setHeaders(Map<String, String> newHeaders)` - modify the headers of the original request
+ `setQuery (Map<String, String> newQuery)` - modify the querystring of the original request
+ `setBody(Object newBody)` - change the body of the original request

To leave the original request unchanged, the `leaveOriginalRequestUnmodified` function can be used instead.

##### Response
Both the result of `changeOriginalRequest` building operation and the one of `leaveOriginalRequestUnmodified` call can be passed to static method
 `DecoratorResponseFactory.makePreDecoratorResponse(PreDecoratorRequest preDecoratorRequest)`.
This method returns an instance of `DecoratorResponse`, which represents the response that should be returned.

##### Abort chain
To abort the decorator chain, you can obtain the related `DecoratorResponse` instance by calling the method
 `DecoratorResponseFactory.abortChain(int finalStatusCode, Object finalBody, Map<String, String> finalHeaders)`.


#### POST decorators
##### Request
An instance of class `PostDecoratorRequest` can be used as input to the decorator handler.

The utility functions exposed by the `PostDecoratorRequest` instance can be used to access the original request:
As with the original request, the `PostDecoratorResponse` instance has useful methods for access both the original request and the original response:

+ `getOriginalRequestMethod()` - returns the original request method
+ `getOriginalRequestPath()` - returns the path of the original request
+ `getOriginalRequestHeaders()` - returns the headers of the original request
+ `getOriginalRequestQuery()` - returns the querystring of the original request
+ `getOriginalRequestBody()` - returns the body of the original request
+ `getOriginalResponseBody()` - returns the body of the original response
+ `getOriginalResponseHeaders()` - returns the headers of the original response
+ `getOriginalResponseStatusCode()` - returns the status code of the original response

In addition to the methods described above, the `PostDecoratorResponse` instance exposes an interface to modify the original response,
 which will come forwarded by microservice-gateway to the target service. This interface is accessible using the instance method 
 `changeOriginalResponse` which returns a builder for `PostDecoratorRequestProxy` object with following methods:

+ `setHeaders(Map<String, String> newHeaders)` - modify the headers of the original response
+ `setBody(Object newBody)` - change the body of the original response
+ `setStatusCode(int statusCode)` - change the status code of the original response

To leave the original response unchanged, the `leaveOriginalResponseUnmodified` function can be used instead.

##### Response
Both the result of `changeOriginalRequest` building operation and the one of `leaveOriginalRequestUnmodified` call can be passed to static method
 `DecoratorResponseFactory.makePostDecoratorResponse(PostDecoratorRequest postDecoratorRequest)`.
This method returns an instance of `DecoratorResponse`, which represents the response that should be returned.

##### Abort chain
To abort the decorator chain, you can obtain the related `DecoratorResponse` instance by calling the method
 `DecoratorResponseFactory.abortChain(int finalStatusCode, Object finalBody, Map<String, String> finalHeaders)`.
