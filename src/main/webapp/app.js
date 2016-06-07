(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngCookies'])
        .config(config)
        .run(run);

    config.$inject = ['$routeProvider', '$locationProvider'];
    function config($routeProvider, $locationProvider) {
        $routeProvider
            .when('/books', {
                controller: 'BookController',
                templateUrl: 'book/book.view.html',
                controllerAs: 'vm'
            })
            
            .when('/books/add', {
            	resolve: {
            		"check" : function($location, AuthenticationService){
            					if(!AuthenticationService.IsUserAdmin() && !AuthenticationService.IsUserLibrarian()){
            						$location.path('/books');
            					}
            				}
            	},
            	controller: "AddBookController",
            	templateUrl: 'add_book/addbook.view.html',
            	controllerAs: 'vm'
            })
            
            .when('/admin/users', {
            	resolve: {
            		"check" : function($location, AuthenticationService){
            					if(!AuthenticationService.IsUserAdmin() && !AuthenticationService.IsUserLibrarian()){
            						$location.path('/books');
            					}
            				}
            	},
            	controller: "UserController",
            	templateUrl: 'user/user.view.html',
            	controllerAs: 'vm'
            })
            
            .when('/admin/users/add', {
            	resolve: {
            		"check" : function($location, AuthenticationService){
            					if(!AuthenticationService.IsUserAdmin() && !AuthenticationService.IsUserLibrarian()){
            						$location.path('/books');
            					}
            				}
            	},
            	controller: "AddUserController",
            	templateUrl: 'add_user/add_user.view.html',
            	controllerAs: 'vm'
            })
            
            .when('/reservations', {
            	controller: "UsersReservationsController",
            	templateUrl: 'reservations/reservations.view.html',
            	controllerAs: 'vm'
            })
            
            .when('/borrowings', {
            	controller: "UsersBorrowingsController",
            	templateUrl: 'borrowings/borrowings.view.html',
            	controllerAs: 'vm'
            })
            
            .when('/librarian/reservations', {
            	resolve: {
            		"check" : function($location, AuthenticationService){
            					if(!AuthenticationService.IsUserLibrarian()){
            						$location.path('/books');
            					}
            				}
            	},
            	controller: "AllReservationsController",
            	templateUrl: 'reservations/reservations.view.html',
            	controllerAs: 'vm'
            })
            
            .when('/librarian/borrowings', {
            	resolve: {
            		"check" : function($location, AuthenticationService){
            					if(!AuthenticationService.IsUserLibrarian()){
            						$location.path('/books');
            					}
            				}
            	},
            	controller: "AllBorrowingsController",
            	templateUrl: 'borrowings/borrowings.view.html',
            	controllerAs: 'vm'
            })

            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'login/login.view.html',
                controllerAs: 'vm'
            })

            .when('/register', {
                controller: 'RegisterController',
                templateUrl: 'register/register.view.html',
                controllerAs: 'vm'
            })

            .otherwise({ redirectTo: '/login' });
    }

    run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
    function run($rootScope, $location, $cookieStore, $http) {
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; 
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
            var loggedIn = $rootScope.globals.currentUser;
            if (restrictedPage && !loggedIn) {
                $location.path('/login');
            }
        });
    }

})();