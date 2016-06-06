(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersReservationsController', UsersReservationsController);

    UsersReservationsController.$inject = ['UserService', '$rootScope'];
    function UsersReservationsController(UserService, $rootScope) {
        var vm = this;

        vm.user = null;

        function loadCurrentUser() {
            UserService.GetByEmail($rootScope.globals.currentUser.email)
                .then(function (user) {
                    vm.user = user;
                });
        }

        function loadAllUsersReservations() {
            UserService.GetReservationsOfUser(vm.user.id)
                .then(function (reservations) {
                    vm.usersReservations = reservations;
                });
        }
    }

})();