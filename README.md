# ![eyeseetea logo][eyeseetealogo] DHIS2 Api Client multi platform written in Kotlin [![Build Status](https://travis-ci.org/EyeSeeTea/dhis2-light-sdk.svg?branch=master)](https://travis-ci.org/EyeSeeTea/dhis2-light-sdk)

This is implementation written in Kotlin for the [Dhis2 Api][dhis2Api]. 

This a is a multi platform library with javascript and jvm as targets.

This library does not use database, it's a stateless light implementation for the [Dhis2 Api][dhis2Api]. 

This library is implemented using Kotlin and it is fully compatible with Android and Javascript.

**IMPORTANT**: This library is under development.

## Usage

Example of usage written in Kotlin

```kotlin
val d2Api = D2Api.Builder()
        .url("some url")
        .credentials("some username","some password")
        .build()

val optionSetsResponse = d2Api.optionSets().getAll().execute()

when(optionSetsResponse){
    is D2Response.Success -> handleSuccess(success.getValue())
    is D2Response.Error -> handleError(errorResponse)
}
```


Example of usage written in Java

```java
class ExampleClass{
    void example(){
        D2Api d2Api = new D2Api.Builder()
                .url("some url")
                .credentials("some username","some password")
                .build();
        
        List<OptionSet> optionSetsResponse = d2Api.optionSets().getAll().execute();
        
        if (response.isSuccess()) {
            D2Response.Success<List<OptionSet>> success =
                    (D2Response.Success<List<OptionSet>>) optionSetsResponse;
            
            handleSuccess(success.getValue());        
        } else {
            D2Response.Error errorResponse = (D2Response.Error) response;
            
            handleError(errorResponse);
        }        
    }
}
```

Example of usage written in Javascript

```js
    var library = require('library');

    var lightsdk = library.org.eyeseetea.dhis2.lightsdk

    var credentials = new lightsdk.D2Credentials(
            "some username", "some password")

    var d2Api = new lightsdk.D2Api("some url", credentials);

    d2Api.optionSets().getAll().execute()
        .then((response) => {
            console.log(response.toString());
        });
```
## Android

There are a android sample app.

To compile to jvm execute app from Android Studio. 

To run test in jvm:

```
  > ./gradlew build
```


## Javascript

There are a android sample app.

To compile to js:

```
  > ./gradlew buildJsToWeb
``` 

To run test in js:

```
  > ./gradlew jsTest
```

## Static code analysis 

This repository use [ktlint] as static code analysis.

To run analysis:
```
  > ./gradlew ktlint
```


## Add it to your project

Pending publish

## Do you want to contribute?

Feel free to add any useful feature to the library, we will be glad to improve it with your help.

Keep in mind that your PRs **must** be validated by Travis-CI. Please, run a local build with ``./gradlew checkstyle assemble test`` before submiting your code.


## Libraries used in this project

* [kotlin] [kotlin]
* [ktor] [ktor]
* [kotlinx erialization] [kotlinx_serialization]
* [kotlin test] [kotlin_test]
* [ktlint] [ktlint]

[dhis2Api]: https://docs.dhis2.org/master/en/developer/html/webapi.html
[kotlin]: https://github.com/JetBrains/kotlin
[ktor]: https://github.com/ktorio/ktor
[kotlinx_serialization]: https://github.com/Kotlin/kotlinx.serialization
[kotlin_test]: https://github.com/kotlintest/kotlintest
[ktlint]: https://github.com/shyiko/ktlint
[eyeseetealogo]: https://user-images.githubusercontent.com/5593590/47744878-ac565b80-dc82-11e8-9daa-fe51d1a5a241.png
