(function () {
    'use strict';

    angular
        .module('app')
        .factory('BorrowingService', BorrowingService);

    BorrowingService.$inject = ['$http'];
    function BorrowingService($http) {
        var service = {};

        service.GetAllBorrowings = GetAllBorrowings;
        service.DeleteBorrowing = DeleteBorrowing;
        service.CreateBorrowingFromReservation = CreateBorrowingFromReservation;
        
        return service;
        
        function GetAllBorrowings(){
        	return $http.get('/library/rest/borrowings').then(handleSuccess, handleError('Error getting all borrowings'));
        }
        
        function DeleteBorrowing(id){
        	return $http.delete('/library/rest/borrowings/' + id + "/delete").then(handleSuccess, handleError('Error deleting borrowing'));
        }
        
        function CreateBorrowingFromReservation(reservationId, borrowing){
        	return $http.post('/library/rest/borrowings/add/' + reservationId, borrowing).then(handleSuccess, handleError('Error creating borrowing'));
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