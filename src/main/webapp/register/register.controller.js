(function () {
    'use strict';

    angular
        .module('app')
        .controller('RegisterController', RegisterController);

    RegisterController.$inject = ['UserService', '$location', '$rootScope', 'FlashService'];
    function RegisterController(UserService, $location, $rootScope, FlashService) {
        var vm = this;
        vm.user = {
                'name': '',
                'surname': '',
                'email': '',
                'roles': [0],
                'age': '',
                'enabled': 'true',
                'password': ''
            };

        vm.register = register;

        function register() {
            vm.dataLoading = true;
            UserService.Create(vm.user)
                .then(function (response) {
                    if ((typeof response) != "string") {
                        FlashService.Success('Registration successful', true);
                        $location.path('/login');
                    } else {
                        FlashService.Error(response);
                        vm.dataLoading = false;
                    }
                });
        }
    }

})();
