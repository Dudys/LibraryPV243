(function () {
    'use strict';

    angular
        .module('app')
        .controller('NavController', NavController);

    NavController.$inject = ['AuthenticationService'];
    function NavController(AuthenticationService) {
        var nav = this;

        nav.isUserCustomer = isUserCustomer;
        nav.isUserLibrarian = isUserLibrarian;
        nav.isUserAdmin = isUserAdmin;
        nav.isUserLoggedIn = isUserLoggedIn;
        nav.getUsersName = getUsersName;

        function isUserCustomer(){
        	return AuthenticationService.IsUserCustomer();
        }
        
        function isUserLibrarian(){
        	return AuthenticationService.IsUserLibrarian();
        }
        
        function isUserAdmin(){
        	return AuthenticationService.IsUserAdmin();
        }
        
        function isUserLoggedIn(){
        	return AuthenticationService.IsUserLoggedIn();
        }
        
        function getUsersName(){
        	return AuthenticationService.GetUsersName();
        }
    }

})();