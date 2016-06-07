(function () {
    'use strict';

    angular
        .module('app')
        .controller('UserController', UserController);

    UserController.$inject = ['UserService', '$rootScope', 'FlashService'];
    function UserController(UserService, $rootScope, FlashService) {
        var vm = this;

        vm.user = null;
        vm.allUsers = [];
        
        vm.enableUser = enableUser;
        vm.disableUser = disableUser;

        initController();

        function initController() {
            loadAllUsers();
        }

        function loadCurrentUser() {
            UserService.GetByEmail($rootScope.globals.currentUser.email)
                .then(function (user) {
                    vm.user = user;
                });
        }

        function loadAllUsers() {
            UserService.GetAll()
                .then(function (users) {
                    vm.allUsers = users;
                });
        }
        
        function enableUser(user){
        	UserService.EnableUser(user.id)
        		.then(function (response){
        			if((typeof response) == "string"){
        				FlashService.Success("User is enabled!", true);
        				loadAllUsers();
					} else {
						FlashService.Error(response);
					}
        		});
        }
        
        function disableUser(user){
        	UserService.DisableUser(user.id)
    			.then(function (response){
    				if(response){
    					FlashService.Success("User is disabled!", true);
    					loadAllUsers();
    				} else {
    					FlashService.Error("User still has some borrowed books, cannot disable!");
    				}
    			});
        }
    }

})();