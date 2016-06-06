(function () {
    'use strict';

    angular
        .module('app')
        .controller('BookController', BookController);

    BookController.$inject = ['BookService', '$rootScope', 'AuthenticationService', 'UserService', 'FlashService', '$location'];
    function BookController(BookService, $rootScope, AuthenticationService, UserService, FlashService, $location) {
        var vm = this;

        vm.user = null;
        vm.allBooks = [];
        vm.deleteBook = deleteBook;
        vm.reserveBook = reserveBook;
        vm.borrowBook = borrowBook;
        
        vm.isUserCustomer = AuthenticationService.IsUserCustomer();
        vm.isUserLibrarian = AuthenticationService.IsUserLibrarian();
        vm.isUserAdmin = AuthenticationService.IsUserAdmin();
        vm.isUserLoggedIn = AuthenticationService.IsUserLoggedIn();
        vm.getUsersName = AuthenticationService.GetUsersName();

        initController();

        function initController() {
            loadAllBooks();
            loadCurrentUser();
        }

        function loadCurrentUser() {
            UserService.GetByEmail($rootScope.globals.currentUser.user.email)
                .then(function (user) {
                    vm.user = user;
                });
        }

        function loadAllBooks() {
            BookService.GetAllBooks()
                .then(function (books) {
                    vm.allBooks = books;
                });
        }
        
        function reserveBook(book) {
        	if(!BookService.isBookAvailable(book.id)){
        		var date = BookService.whenIsBookAvailable(book.id);
        		var reservation = {
        				'reservedBook': book,
        				'reservationDate': new Date(),
        				'borrowingDate': date 
        		}
        		UserService.AddReservationToUser(vm.user.id, reservation);
        		FlashService.Success("Book was reserved!", true);
        		$location.path('/reservations');
        	} else {
        		FlashService.Error("You do not have to reserve the book, you can borrow it or someone already reserved the book!");
        	}
        }
        
        function borrowBook(book) {
        	var today = new Date();
        	if(BookService.isBookAvailable(book.id)){
        		var borrowing = {
        				'startDate': today,
        				'endDate': today.setMonth(today.getMonth() + 1) 
        		}
        		UserService.AddBorrowingToUser(vm.user.id, borrowing, book.id);
        		FlashService.Success("Book is borrowed till " + borrowing.endDate + "!", true);
        		$location.path('/borrowings');
        	} else {
        		FlashService.Error("The book is borrowed, you can reserve the book!");
        	}
        }

        function deleteBook(id) {
            BookService.DeleteBook(id)
            .then(function () {
                loadAllBooks();
            });
        }
    }

})();