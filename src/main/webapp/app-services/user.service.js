(function () {
    'use strict';

    angular
        .module('app')
        .factory('UserService', UserService);

    UserService.$inject = ['$http'];
    function UserService($http) {
        var service = {};

        service.GetAll = GetAll;
        service.GetById = GetById;
        service.GetByEmail = GetByEmail;
        service.Create = Create;
        service.Update = Update;
        service.EnableUser = EnableUser;
        service.DisableUser = DisableUser;
        service.GetReservationsOfUser = GetReservationsOfUser;
        service.GetBorrowingsOfUser = GetBorrowingsOfUser;
        service.AddReservationToUser = AddReservationToUser;
        service.AddBorrowingToUser = AddBorrowingToUser;
        service.RemoveReservationOfUser = RemoveReservationOfUser;
        service.RemoveBorrowingOfUser = RemoveBorrowingOfUser;

        return service;

        function GetAll() {
            return $http.get('/library/rest/users').then(handleSuccess, handleError('Error getting all users'));
        }

        function GetById(id) {
            return $http.get('/library/rest/users/' + id).then(handleSuccess, handleError('Error getting user by id'));
        }

        function GetByEmail(email) {
            return $http.get('/library/rest/users/findByEmail?email=' + email).then(handleSuccess, handleError('Error getting user by username'));
        }

        function Create(user) {
            return $http.post('/library/rest/users/add', user).then(handleSuccess, handleError('User with ' + user.email + ' already exist!'));
        }

        function Update(user) {
            return $http.put('/library/rest/users/' + user.id, user).then(handleSuccess, handleError('Error updating user'));
        }

        function EnableUser(id) {
            return $http.post('/library/rest/users/' + id + '/enable').then(handleSuccess, handleError('Error enabling user'));
        }
        
        function DisableUser(id) {
            return $http.post('/library/rest/users/' + id + '/disable').then(handleSuccess, handleError('Error disabling user'));
        }
        
        function GetReservationsOfUser(id){
        	return $http.get('/library/rest/users/' + id + '/reservations').then(handleSuccess, handleError('Error getting reservations of user'));
        }
        
        function GetBorrowingsOfUser(id){
        	return $http.get('/library/rest/users/' + id + '/borrowings').then(handleSuccess, handleError('Error getting borrowings of user'));
        }
        
        function AddReservationToUser(id, reservation){
        	return $http.post('/library/rest/users/' + id + '/add/reservation', reservation)
        		.then(handleSuccess, handleError('Error adding reservation to user'));
        }
        
        function AddBorrowingToUser(id, borrowing, bookId){
        	return $http.post('/library/rest/users/' + id + '/remove/borrowing/' + bookId, borrowing)
        		.then(handleSuccess, handleError('Error adding borrowing to user'));
        }
        
        function RemoveReservationOfUser(id, reservationId){
        	return $http.delete('/library/rest/users/' + id + '/remove/reservation/' + reservationId)
    			.then(handleSuccess, handleError('Error removing reservation of user'));
        }
        
        function RemoveBorrowingOfUser(id, borrowingId){
        	return $http.delete('/library/rest/users/' + id + '/remove/borrowing/' + borrowingId)
    			.then(handleSuccess, handleError('Error removing borrowing of user'));
        }


        function handleSuccess(res) {
            return res.data;
        }

        function handleError(error) {
            return function () {
                return error;
            };
        }
    }

})();
