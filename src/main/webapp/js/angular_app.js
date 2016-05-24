/**
 * Created by Jan Duda on 5/24/2016.
 */
var library = angular.module('library', ['ngRoute']);

library.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/index', {templateUrl: 'index.html'}).
        when('/add_book', {templateUrl: '/add_book.html'}).
        when('/add_user', {templateUrl: '/add_user.html'}).
        when('/borrowings', {templateUrl: '/borrowings.html'}).
        when('/login', {templateUrl: '/login.html'}).
        when('/reservations', {templateUrl: '/reservations.html'}).
        when('/signup', {templateUrl: '/signup.html'}).
        when('/users', {templateUrl: '/users.html'}).
        otherwise({redirectTo: '/index'});
    }]);

library.run(function($rootScope) {
    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideWarningAlert = function () {
        $rootScope.warningAlert = undefined;
    };
    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };
});