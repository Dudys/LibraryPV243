﻿(function () {
    'use strict';

    angular
        .module('app')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$location', 'AuthenticationService', 'FlashService'];
    function LoginController($location, AuthenticationService, FlashService) {
        var vm = this;
        
    	vm.user = {
                'email': '',
                'password': ''
            };

        vm.login = login;

        (function initController() {
            // reset login status
            AuthenticationService.ClearCredentials();
        })();

        function login() {
            vm.dataLoading = true;
            AuthenticationService.Login(vm.user)
            	.then(function (response) {
            		if ((typeof response) != "string") {
            			AuthenticationService.SetCredentials(vm.user.password, response);
            			FlashService.Success('You are logged in', true);
            			$location.path('/books');
            		} else {
            			FlashService.Error("Wrong email or password!");
            			vm.dataLoading = false;
            		}
            	});
        };
    }

})();
