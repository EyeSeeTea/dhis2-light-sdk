"use strict";

var library = require('library');

var lightsdk = library.org.eyeseetea.dhis2.lightsdk

var d2Api =  (new lightsdk.D2Api.Builder()).build();

var endpoints = loadEndpoints()

window.onload=function() {

    populateEndpointsSelect();

    document.getElementById('login-form').onsubmit = function() {

        document.getElementById("result").innerText = "";

        var credentials = new lightsdk.D2Credentials(
                document.getElementById("usernameInput").value,
                document.getElementById("passwordInput").value)

        d2Api = new lightsdk.D2Api(
                document.getElementById("urlInput").value,
                credentials,
                document.getElementById("apiVersion").value);

        var endpointSelect = document.getElementById("endpoint-select");
        var key =  endpointSelect.options[endpointSelect.selectedIndex].value;

        var endpoint = endpoints[key]

        endpoint().execute()
            .then((response) => {
                document.getElementById("result").innerText =response.toString();
            });

        // You must return false to prevent the default form behavior
        return false;
    }
}

function populateEndpointsSelect(){
    var endpointSelect = document.getElementById("endpoint-select");

    Object.keys(endpoints).forEach(function(key,index) {
       var opt = document.createElement("option");

       opt.innerHTML = key;
       opt.value = key

       // then append it to the select element
       endpointSelect.appendChild(opt);
    });
}

function loadEndpoints(){
    var endpoints = {};

    endpoints["SystemInfo"] = function () {
        return d2Api.systemInfo().get();
        };

    endpoints["OptionSets"] = function () {
        return d2Api.optionSets().getAll();
        };

    return endpoints;
}