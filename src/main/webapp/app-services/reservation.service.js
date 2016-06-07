(function () {
    'use strict';

    angular
        .module('app')
        .factory('ReservationService', ReservationService);

    ReservationService.$inject = ['$http'];
    function ReservationService($http) {
        var service = {};

        service.GetAllReservations = GetAllReservations;
        service.DeleteReservation = DeleteReservation;
        service.DeleteReservation = DeleteReservation;
        
        return service;
        
        function GetAllReservations(){
        	return $http.get('/library/rest/reservations').then(handleSuccess, handleError('Error getting all reservations'));
        }
        
        function DeleteReservation(id){
        	return $http.delete('/library/rest/reservations/' + id + "/delete").then(handleSuccess, handleError('Error deleting reservation'));
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