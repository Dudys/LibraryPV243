(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersReservationsController', UsersReservationsController);

    UsersReservationsController.$inject = ['UserService', '$rootScope', 'FlashService', 'BookService', 'ReservationService', '$location'];
    function UsersReservationsController(UserService, $rootScope, FlashService, BookService, ReservationService, $location) {
    	var vm = this;
        
        vm.user = null;
        vm.title = "Your Reservations";
        vm.reservations = [];
        
        vm.borrowBook = borrowBook;
        vm.cancelReservation = cancelReservation;
        
        initController();

        function initController() {
            loadCurrentUser();
        	loadAllUsersReservations();
        }

        function loadCurrentUser() {
            UserService.GetByEmail($rootScope.globals.currentUser.user.email)
                .then(function (user) {
                    vm.user = user;
                });
        }

        function loadAllUsersReservations() {
            UserService.GetReservationsOfUser($rootScope.globals.currentUser.user.id)
                .then(function (reservations) {
                    vm.reservations = reservations;
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
                		UserService.AddBorrowingToUser(vm.user.id, borrowing, reservation.reservedBook.id);
                		ReservationService.DeleteReservation(reservation.id);
                		FlashService.Success("Book is borrowed!", true);
                		$location.path('/borrowings');
                	} else {
                		FlashService.Error("The book is still borrowed!");
                	}
        		});
        }
        

        function cancelReservation(reservation) {
        	UserService.RemoveReservationOfUser(vm.user.id, reservation.id)
        		.then(function (reservation){
        			FlashService.Success("Reservation was canceled!", true);
        			loadAllUsersReservations();
        		});
        }
    }

})();