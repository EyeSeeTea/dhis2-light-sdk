"use strict";

var library = require('library');

var lightsdk = library.org.eyeseetea.dhis2.lightsdk

var credentials = new lightsdk.D2Credentials("","")

var d2Api = new lightsdk.D2Api("", credentials);

d2Api.optionSets().getAll().execute()
    .then((response) => {
       console.log(response);
    });