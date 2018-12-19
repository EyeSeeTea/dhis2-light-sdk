"use strict";

window.onload=function() {
    document.getElementById('login-form').onsubmit=function() {

        document.getElementById("result").innerText = "";

        var library = require('library');

        var lightsdk = library.org.eyeseetea.dhis2.lightsdk

        var credentials = new lightsdk.D2Credentials(
                document.getElementById("usernameInput").value,
                document.getElementById("passwordInput").value)

        var d2Api = new lightsdk.D2Api(
                document.getElementById("urlInput").value,
                credentials,
                document.getElementById("apiVersion").value);

        d2Api.optionSets().getAll().execute()
            .then((response) => {
                document.getElementById("result").innerText =response.toString();
            });

        // You must return false to prevent the default form behavior
        return false;
  }
}
