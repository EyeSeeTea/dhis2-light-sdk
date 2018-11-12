# ![eyeseetea logo][eyeseetealogo] DHIS2 Api Client written in Kotlin [![Build Status](https://travis-ci.org/EyeSeeTea/dhis2-light-sdk.svg?branch=master)](https://travis-ci.org/EyeSeeTea/dhis2-light-sdk)

This is implementation written in Kotlin for the [Dhis2 Api][dhis2Api]. 

This library does not use database, it's a stateless light implementation for the [Dhis2 Api][dhis2Api]. 

This library is implemented using Kotlin and it is fully compatible with Android.

**IMPORTANT**: This library is under development.

## Usage

Example of usage written in kotlin

```kotlin
val d2Api = D2Api.Builder()
        .url("some url")
        .credentials("some username","some password")
        .build();

val optionSetsResponse = d2Api.optionSets().getAll()

when(optionSetsResponse){
    is D2Response.Success -> handleSuccess(success.getValue())
    is D2Response.Error -> handleError(errorResponse)
}
```


Example of usage written in java

```java
class ExampleClass{
    void example(){
        D2Api d2Api = new D2Api.Builder()
                .url("some url")
                .credentials("some username","some password")
                .build();
        
        List<OptionSet> optionSetsResponse = d2Api.optionSets().getAll();
        
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


## Add it to your project

Include the library in your ``build.gradle``

## Do you want to contribute?

Feel free to add any useful feature to the library, we will be glad to improve it with your help.

Keep in mind that your PRs **must** be validated by Travis-CI. Please, run a local build with ``./gradlew checkstyle assemble test`` before submiting your code.


## Libraries used in this project

* [OkHttp] [okHttp]
* [Retrofit] [retrofit]
* [JUnit] [junit]
* [Kluent] [kluent]
* [Mockito] [mockito]
* [mockwebserver] [mockwebserver]

[dhis2Api]: https://docs.dhis2.org/master/en/developer/html/webapi.html
[okHttp]: https://github.com/square/okhttp
[retrofit]: https://github.com/square/retrofit
[junit]: https://github.com/junit-team/junit
[kluent]: https://github.com/MarkusAmshove/Kluent
[mockito]: https://github.com/mockito/mockito
[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[eyeseetealogo]: https://user-images.githubusercontent.com/5593590/47744878-ac565b80-dc82-11e8-9daa-fe51d1a5a241.png
