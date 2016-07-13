var exec = require("cordova/exec");

module.exports = {

    ENTER_OPTIONS: {
	partner_order_no: "",
        subject: "HIS_TEST_TV",
        price: "0.1", 
        partner_notify_url: "http://pay.youjoy.tv/static/new_sdk/hisense.php"
    },

    pay: function (options, successCallback, errorCallback) {
        options = this.merge(this.ENTER_OPTIONS, options);
        cordova.exec(successCallback, errorCallback, "hiOrder", "Pay", [options]);
    },
	
    change: function (mag, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "hiOrder", "Change", [mag]);
    },
	
    iandroid: function (mag, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "hiOrder", "Iandroid", [mag]);
    },
    
    packageinfo: function (mag, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "hiOrder", "Packageinfo", [mag]);
    },
    
    sign: function (mag, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "hiOrder", "Sign", [mag]);
    },
    
    echo: function (mag, duration, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "hiOrder", "Echo", [mag,duration]);
    },
    
    merge: function () {
        var obj = {};
        Array.prototype.slice.call(arguments).forEach(function(source) {
            for (var prop in source) {
                obj[prop] = source[prop];
            }
        });
        return obj;
    }
};
