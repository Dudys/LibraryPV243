(function () {
    'use strict';
    
    angular
    	.module("app")
    	.filter('yesNo', function() {
    		return function(input) {
    			return input ? 'yes' : 'no';
    		}
    	})
})();