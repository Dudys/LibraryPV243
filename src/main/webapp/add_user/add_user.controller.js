(function () {
    'use strict';

    angular
        .module('app')
        .controller('AddUserController', AddUserController);

    AddUserController.$inject = ['UserService', '$location', '$rootScope', 'FlashService', 'AuthenticationService'];
    function AddUserController(UserService, $location, $rootScope, FlashService, AuthenticationService) {
        var vm = this;
        vm.title = "Add New User";
        vm.user = {
                'name': '',
                'surname': '',
                'email': '',
                'roles': [0],
                'age': '',
                'enabled': 'true',
                'password': ''
            };

        vm.isUserAdmin = AuthenticationService.IsUserAdmin();
        
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