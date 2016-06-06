(function () {
    'use strict';

    angular
        .module('app')
        .controller('AddUserController', AddUserController);

    AddUserController.$inject = ['UserService', '$location', '$rootScope', 'FlashService'];
    function AddUserController(UserService, $location, $rootScope, FlashService) {
        var vm = this;
        vm.user = {
                'name': '',
                'surname': '',
                'email': '',
                'roles': [],
                'age': '',
                'enabled': '',
                'password': ''
            };

        vm.addUser = addUser;

        function addUser() {
            vm.dataLoading = true;
            UserService.Create(vm.user)
                .then(function (response) {
                    if ((typeof response) != "string") {
                        FlashService.Success('User was created', true);
                        $location.path('/admin/users');
                    } else {
                        FlashService.Error(response);
                    }
                });
        }
    }

})();