/**
 * Created by Jan Duda on 5/24/2016.
 */
//use strict;

var library = angular.module('library', ['ngRoute', 'libraryControllers']);
var libraryControllers = angular.module('libraryControllers', []);

// Configures URL fragment routing
library.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/books', {templateUrl: 'html/books.html', controller: "BooksCtrl"}).
        when('/borrowings', {templateUrl: 'html/borrowings.html', controller: "BorrowingsCtrl"}).
        when('/login', {templateUrl: 'html/login.html', controller: "NewBookCtrl"}).
        when('/reservations', {templateUrl: 'html/reservations.html', controller: "ReservationsCtrl"}).
        when('/signup', {templateUrl: 'html/signup.html', controller: "SignUpCtrl"}).
        when('/users', {templateUrl: 'html/users.html', controller: "UsersCtrl"}).
        when('/books/add', {templateUrl: 'html/add_book.html', controller: "NewBookCtrl"}).
        when('/users/add', {templateUrl: 'html/add_user.html', controller: "NewUserCtrl"}).
        otherwise({redirectTo: '/books'});
    }]);

// filter for boolean values
library.filter('yesNo', function() {
    return function(input) {
        return input ? 'yes' : 'no';
    }
});

//Password strength 
library.factory('Password', function() {
	function getStrength(pass) {
		var score = 0;
		if (!pass)
			return score;

		// award every unique letter until 5 repetitions
		var letters = new Object();
		for (var i=0; i<pass.length; i++) {
			letters[pass[i]] = (letters[pass[i]] || 0) + 1;
			score += 5.0 / letters[pass[i]];
		}

		// bonus points for mixing it up
		var variations = {
				digits: /\d/.test(pass),
				lower: /[a-z]/.test(pass),
				upper: /[A-Z]/.test(pass),
				nonWords: /\W/.test(pass)
		};

		var variationCount = 0;
		for (var check in variations) {
			variationCount += (variations[check] == true) ? 1 : 0;
		}
		score += (variationCount - 1) * 10;

		if(score > 100) score = 100;

		return parseInt(score);
	}

	return {
		getStrength: function(pass) {
			return getStrength(pass);
		}
	}
});

library.controller('PasswordCtrl', function($scope, Password) {
	$scope.$watch('user.password', function(pass) {
		$scope.passwordStrength = Password.getStrength(pass);
		if($scope.isPasswordWeak()) {
			$scope.form.password.$setValidity('strength', false);
		} else {
			$scope.form.password.$setValidity('strength', true);
		}
	});

	$scope.isPasswordWeak = function() {
		return $scope.passwordStrength < 40;
	};

	$scope.isPasswordOk = function() {
		return $scope.passwordStrength >= 40 && $scope.passwordStrength <= 70;
	};

	$scope.isPasswordStrong = function() {
		return $scope.passwordStrength > 70;
	};
	
	$scope.isInputValid = function(input){
		return input.$dirty && input.$valid;
	};
	
	$scope.isInputInvalid = function(input){
		return input.$dirty && input.$invalid;
	}
});

// controller to handle user's roles checkbox
library.controller('RoleCheckboxCtrl', function($scope) {
	$scope.roles = [
	    {id: 0, text: 'Customer'},
		{id: 1, text: 'Librarian'},
		{id: 2, text: 'Admin'}
	];
});

// Controllers

// Controller for creating new book
libraryControllers.controller('NewBookCtrl',
		function ($scope, $routeParams, $http, $location, $rootScope) {
			$scope.book = {
					'name': '',
					'isbn': '',
					'totalItems': 0
			};
	        $scope.createBook = function (book) {
	        	$http({
	                method: 'POST',
	                url: '/library/rest/books/add',
	                data: book
	            }).then(function success(response) {
	                console.log('created book');
	                var createdBook = response.data;
	                $rootScope.successAlert = 'A new book "' + createdBook.name + '" was created';
	                $location.path("/books");
	            }, function error(response) {
	                $scope.errorAlert = 'Cannot create book !';
	            });
	        };
	    });

// Controller for creating new user
libraryControllers.controller('NewUserCtrl',
		function ($scope, $routeParams, $http, $location, $rootScope) {
			$scope.user = {
					'name': '',
					'surname': '',
					'email': '',
					'roles': [],
					'age': 0,
					'enabled': true,
					'password': ''
			};
	        $scope.createUser = function (user) {
	        	$http({
	                method: 'POST',
	                url: '/library/rest/users/add',
	                data: user
	            }).then(function success(response) {
	                console.log('created user');
	                var createdUser = response.data;
	                $rootScope.successAlert = 'A new user "' + createdUser.name + '" "' + createdUser.surname  + '" was created';
	                $location.path("/users");
	            }, function error(response) {
	                $scope.errorAlert = 'Cannot create user !';
	            });
	        };
	    });

// Controller for sign up a customer
libraryControllers.controller('SignUpCtrl',
		function ($scope, $routeParams, $http, $location, $rootScope) {
			$scope.user = {
					'name': '',
					'surname': '',
					'email': '',
					'roles': [0],
					'age': 0,
					'enabled': true,
					'password': ''
			};
	        $scope.createUser = function (user) {
	        	$http({
	                method: 'POST',
	                url: '/library/rest/users/add',
	                data: user
	            }).then(function success(response) {
	                console.log('created user');
	                var createdUser = response.data;
	                $rootScope.successAlert = 'User with name "' + createdUser.name + '" "' + createdUser.surname  + '" has signed up';
	                $location.path("/books");
	            }, function error(response) {
	                $scope.errorAlert = 'Cannot create user !';
	            });
	        };
	    });

// Load all books 
function loadBooks($http, $scope) {
    $http({
    	method: 'GET',
    	url: '/library/rest/books'
    }).then(function success(response){
    	console.log('loaded all books');
    	$scope.books = response.data;
    }), function error(response) {
    	$scope.errorAlert = 'Cannot load all book !';
    };
}

libraryControllers.controller('BooksCtrl',
		function ($scope, $rootScope, $routeParams, $http) {
    		loadBooks($http, $scope);
	    });

// Load all reservations
function loadReservations($http, $scope) {
    $http({
    	method: 'GET',
    	url: '/library/rest/reservations'
    }).then(function success(response){
    	console.log('loaded all reservations');
    	$scope.reservations = response.data;
    }), function error(response) {
    	$scope.errorAlert = 'Cannot load all reservations !';
    };
}

libraryControllers.controller('ReservationsCtrl',
		function ($scope, $rootScope, $routeParams, $http) {
    		loadReservations($http, $scope);
	    });

// Load all borrowings
function loadBorrowings($http, $scope) {
    $http({
    	method: 'GET',
    	url: '/library/rest/borrowings'
    }).then(function success(response){
    	console.log('loaded all borrowings');
    	$scope.borrowings = response.data;
    }), function error(response) {
    	$scope.errorAlert = 'Cannot load all borrowings !';
    };
}

libraryControllers.controller('BorrowingsCtrl',
		function ($scope, $rootScope, $routeParams, $http) {
			loadBorrowings($http, $scope);
	    });

//Load all users
function loadUsers($http, $scope) {
    $http({
    	method: 'GET',
    	url: '/library/rest/users'
    }).then(function success(response){
    	console.log('loaded all users');
    	$scope.users = response.data;
    }), function error(response) {
    	$scope.errorAlert = 'Cannot load all users!';
    };
}

libraryControllers.controller('UsersCtrl',
		function ($scope, $rootScope, $routeParams, $http) {
			loadUsers($http, $scope);
	    });