(function () {
    'use strict';

    angular
        .module('app')
        .controller('AllReservationsController', AllReservationsController);

    AllReservationsController.$inject = ['ReservationService', '$rootScope', 'FlashService', 'UserService', 'BorrowingService', 'BookService', '$location'];
    function AllReservationsController(ReservationService, $rootScope, FlashService, UserService, BorrowingService, BookService, $location) {
    	var vm = this;
        
        vm.user = null;
        vm.title = "All Reservations";
        vm.reservations = [];
        
        vm.cancelReservation = cancelReservation;
        vm.borrowBook = borrowBook;
        
        initController();

        function initController() {
            loadCurrentUser();
        	loadAllReservations();
        }

        function loadCurrentUser() {
            UserService.GetByEmail($rootScope.globals.currentUser.user.email)
                .then(function (user) {
                    vm.user = user;
                });
        }

        function loadAllReservations() {
        	ReservationService.GetAllReservations()
                .then(function (reservations) {
                    vm.reservations  = reservations;
                });
        }
        
        function borrowBook(reservation){
        	var today = new Date();
        	
        	BookService.isBookAvailable(reservation.reservedBook.id)
        		.then(function (isAvailable){
        			if(isAvailable){
                		var borrowing = {
                				'startDate': new Date(),
                				'endDate': today.setMonth(today.getMonth() + 1) 
                		}
                		BorrowingService.CreateBorrowingFromReservation(reservation.id, borrowing);
                		ReservationService.DeleteReservation(reservation.id);
                		FlashService.Success("Book is borrowed!", true);
                		$location.path('/librarian/borrowings');
                	} else {
                		FlashService.Error("The book is still borrowed!");
                	}
        		});
        }
        
        function cancelReservation(reservation) {
        	ReservationService.DeleteReservation(reservation.id)
        		.then(function (reservation){
        			FlashService.Success("Reservation was canceled!", true);
        			loadAllReservations();
        		});
        }
    }

})();