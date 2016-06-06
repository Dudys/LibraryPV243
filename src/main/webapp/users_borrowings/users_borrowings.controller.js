(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersBorrowingsController', UsersBorrowingsController);

    UsersBorrowingsController.$inject = ['UserService', '$rootScope'];
    function UsersBorrowingsController(UserService, $rootScope) {
        var vm = this;

        vm.user = null;

        function loadCurrentUser() {
            UserService.GetByEmail($rootScope.globals.currentUser.email)
                .then(function (user) {
                    vm.user = user;
                });
        }

        function loadAllUsersBorrowings() {
            UserService.GetBorrowingsOfUser(vm.user.id)
                .then(function (borrowings) {
                    vm.usersBorrowings = borrowings;
                });
        }
    }

})();