# custom-plugin-java
Library that allows you to define Mia-Platform custom plugins in java easily

## Purpouse

This plugin aims to provide an easy way to integrate the Platform.

It is able to integrate either the CRUD and other REST services.
The underlying library which enables the possibility to operate with external service is Retrofit, although the user (developer) wouldn't use this in his code. 

## Internal Architecture

This plugin is framework-independent. Every functionality is built using plain Java code. 

## Usage
To get a service proxy you can use the following method from class `ServiceCilentFactory`:
### getServiceProxy
``` java
Service serviceClient = ServiceClientFactory.getServiceProxy('my/api/path', , cpRequest.getHeadersPropagator());
const myServiceProxy = getDirectServiceProxy(MY_SERVICE_NAME)
 ```
all the options accepted by the getDirectServiceProxy can be passed (es: `{ port: CUSTOM_PORT }`).

### getServiceProxy
It need the MICROSERVICE_GATEWAY_SERVICE_NAME so you need to pass it like this:
``` javascript
const { getServiceProxy } = require('@mia-platform/custom-plugin-lib')
const myServiceProxy = getServiceProxy(MICROSERVICE_GATEWAY_SERVICE_NAME)
