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
            	controller: "AddBookController",
            	templateUrl: 'add_book/addbook.view.html',
            	controllerAs: 'vm'
            })
            
            .when('/admin/users', {
            	controller: "UserController",
            	templateUrl: 'user/user.view.html',
            	controllerAs: 'vm'
            })
            
            .when('/admin/users/add', {
            	controller: "AddUserController",
            	templateUrl: 'add_user/add_user.view.html',
            	controllerAs: 'vm'
            })
            
            .when('/reservations', {
            	controller: "UsersReservationsController",
            	templateUrl: 'users_reservations/users_reservations.view.html',
            	controllerAs: 'vm'
            })
            
            .when('/borrowings', {
            	controller: "UsersBorrowingsController",
            	templateUrl: 'users_borrowings/users_borrowings.view.html',
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