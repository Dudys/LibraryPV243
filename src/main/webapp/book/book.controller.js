(function () {
    'use strict';

    angular
        .module('app')
        .controller('BookController', BookController);

    BookController.$inject = ['BookService', '$rootScope', 'AuthenticationService', 'UserService', 'FlashService', '$location'];
    function BookController(BookService, $rootScope, AuthenticationService, UserService, FlashService, $location) {
        var vm = this;

        vm.user = null;
        vm.allUsers = [];
        vm.allBooks = [];
        vm.bookForBorrowing = null;
        
        vm.deleteBook = deleteBook;
        vm.reserveBook = reserveBook;
        vm.borrowBook = borrowBook;
        vm.borrowBookToUser = borrowBookToUser;
        vm.loadUsers = loadUsers; 
        
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
        	
        	BookService.canBookBeReserved(book.id)
    			.then(function (canBeReserved){
    				if(canBeReserved){
    					BookService.whenIsBookAvailable(book.id)
    						.then(function (date){
    							if((typeof date) != "string"){
    								var reservation = {
    										'reservedBook': book,
    										'reservationDate': new Date(),
    										'borrowingDate': new Date(date.date) 
    								}
    								UserService.AddReservationToUser(vm.user.id, reservation)
    	    							.then(function (response){
    	    								if((typeof response) != "string"){
    	    									FlashService.Success("Book is reserved!", true);
    	    									$location.path('/reservations');
    	    								} else {
    	    									FlashService.Error(response);
    	    								}
    	    						});
    							} else {
    								FlashService.Error(response);
    							}
    						});
    				} else {
    					FlashService.Error("You do not have to reserve the book, you can borrow it or someone already reserved the book!");
    				}
    			});
        }
        
        function borrowBook(book) {
        	var today = new Date();
        	
        	BookService.isBookAvailable(book.id)
        		.then(function (isAvailable){
        			if(isAvailable){
                		var borrowing = {
                				'startDate': new Date(),
                				'endDate': today.setMonth(today.getMonth() + 1) 
                		}
                		UserService.AddBorrowingToUser(vm.user.id, borrowing, book.id);
                		FlashService.Success("Book is borrowed!", true);
                		$location.path('/borrowings');
                	} else {
                		FlashService.Error("The book is borrowed, you can reserve the book!");
                	}
        		});
        }
        
        function borrowBookToUser(book, u) {
        	var today = new Date();
        	
        	BookService.isBookAvailable(book.id)
        		.then(function (isAvailable){
        			if(isAvailable){
                		var borrowing = {
                				'startDate': new Date(),
                				'endDate': today.setMonth(today.getMonth() + 1) 
                		}
                		UserService.AddBorrowingToUser(u.id, borrowing, book.id);
                		FlashService.Success("Book is borrowed!", true);
                		loadAllBooks();
                	} else {
                		FlashService.Error("The book is borrowed, you can reserve the book!");
                	}
        		});
        }
        
        function loadUsers(book){
        	UserService.GetAll()
            	.then(function (users) {
            		vm.allUsers = users;
            		vm.bookForBorrowing = book;
            	});
        }

        function deleteBook(book) {
            BookService.DeleteBook(book.id)
            	.then(function (response) {
            		if (response){
            			FlashService.Success("Book was removed!", true);
                		loadAllBooks();
            		} else {
            			FlashService.Error("The book is still borrowed or reserved, cannot remove!");
            		}
            	});
        }
    }

})();