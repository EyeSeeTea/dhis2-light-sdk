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

<img width="200" alt="android-sample-app" src="https://user-images.githubusercontent.com/5593590/49922975-737ce980-feb2-11e8-9967-2da35537413c.png">

To run test in jvm:

```
  > ./gradlew build
```

To run Android Sample execute AndroidSample from Android Studio


## Javascript

There are a web sample app.


<img width="400" alt="web-sample-app" src="https://user-images.githubusercontent.com/5593590/49922982-78da3400-feb2-11e8-8bd4-fc621e588eca.png">


To run test in js:

```
  > ./gradlew jsTest
```

To execute web sample

1. Open chrome disabling web security:

For OSX:
```
$ open -a Google\ Chrome --args --disable-web-security --user-data-dir
```
--user-data-dir required on Chrome 49+ on OSX

For Linux run:
```
$ google-chrome --disable-web-security
```

For Windows go into the command prompt and go into the folder where Chrome.exe is and type:
```
chrome.exe --disable-web-security
```

2. Compile code to js and create bundle.js

execute:
```
./gradlew buildJsToWeb
```
3. Open web sample index.html under webSample directory
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

* [kotlin][kotlin]
* [ktor][ktor]
* [kotlinx erialization][kotlinx_serialization]
* [kotlin test][kotlin_test]
* [ktlint][ktlint]

[dhis2Api]: https://docs.dhis2.org/master/en/developer/html/webapi.html
[kotlin]: https://github.com/JetBrains/kotlin
[ktor]: https://github.com/ktorio/ktor
[kotlinx_serialization]: https://github.com/Kotlin/kotlinx.serialization
[kotlin_test]: https://github.com/kotlintest/kotlintest
[ktlint]: https://github.com/shyiko/ktlint
[eyeseetealogo]: https://user-images.githubusercontent.com/5593590/47744878-ac565b80-dc82-11e8-9daa-fe51d1a5a241.png
