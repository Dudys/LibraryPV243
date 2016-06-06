(function () {
    'use strict';

    angular
        .module('app')
        .controller('AddBookController', AddBookController);

    AddBookController.$inject = ['BookService', '$rootScope','FlashService', '$location'];
    function AddBookController(BookService, $rootScope, FlashService, $location) {
        var vm = this;

        vm.user = null;
        vm.book = {
				'name': '',
				'isbn': '',
				'totalItems': 0
		};
        
        vm.addBook = addBook;

        function loadCurrentUser() {
            UserService.GetByEmail($rootScope.globals.currentUser.email)
                .then(function (user) {
                    vm.user = user;
                });
        }

        function addBook() {
            vm.dataLoading = true;
            BookService.CreateBook(vm.book)
                .then(function (response) {
            		if ((typeof response) != "string") {
            			FlashService.Success('Book was created', true);
            			$location.path('/books');
            		} else {
            			FlashService.Error(response);
            			vm.dataLoading = false;
            		}
                });
        }
    }

})();