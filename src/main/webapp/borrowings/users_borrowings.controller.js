(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersBorrowingsController', UsersBorrowingsController);

    UsersBorrowingsController.$inject = ['UserService', '$rootScope'];
    function UsersBorrowingsController(UserService, $rootScope) {
        var vm = this;
        
        vm.user = null;
        vm.title = "Your Borrowings";
        vm.borrowings = [];
        vm.isLibrarian = false;
        
        initController();

        function initController() {
            loadCurrentUser();
        	loadAllUsersBorrowings();
        }

        function loadCurrentUser() {
            UserService.GetByEmail($rootScope.globals.currentUser.user.email)
                .then(function (user) {
                    vm.user = user;
                });
        }

        function loadAllUsersBorrowings() {
            UserService.GetBorrowingsOfUser($rootScope.globals.currentUser.user.id)
                .then(function (borrowings) {
                    vm.borrowings = borrowings;
                });
        }
    }

})();