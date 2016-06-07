(function () {
    'use strict';

    angular
        .module('app')
        .controller('AllBorrowingsController', AllBorrowingsController);

    AllBorrowingsController.$inject = ['BorrowingService', '$rootScope', 'FlashService', 'UserService'];
    function AllBorrowingsController(BorrowingService, $rootScope, FlashService, UserService) {
    	var vm = this;
        
        vm.user = null;
        vm.title = "All Borrowings";
        vm.borrowings = [];
        vm.isLibrarian = true;
        
        vm.returnBook = returnBook; 
        
        initController();

        function initController() {
            loadCurrentUser();
        	loadAllBorrowings();
        }

        function loadCurrentUser() {
            UserService.GetByEmail($rootScope.globals.currentUser.user.email)
                .then(function (user) {
                    vm.user = user;
                });
        }

        function loadAllBorrowings() {
        	BorrowingService.GetAllBorrowings()
                .then(function (borrowings) {
                    vm.borrowings = borrowings;
                });
        }
        
        function returnBook(id){
        	BorrowingService.DeleteBorrowing(id)
        		.then(function (response){
        			if((typeof response) == "string"){
        				FlashService.Success("Book was returned", true);
        				loadAllBorrowings();
        			} else {
        				FlashService.Error(response);
        			}
        		});
        }
    }

})();