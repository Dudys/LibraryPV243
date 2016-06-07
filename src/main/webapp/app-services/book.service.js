(function () {
    'use strict';

    angular
        .module('app')
        .factory('BookService', BookService);

    BookService.$inject = ['$http'];
    function BookService($http) {
        var service = {};

        service.GetAllBooks = GetAllBooks;
        service.GetBookById = GetBookById;
        service.CreateBook = CreateBook;
        service.UpdateBook = UpdateBook;
        service.whenIsBookAvailable = whenIsBookAvailable;
        service.isBookAvailable = isBookAvailable;
        service.canBookBeReserved = canBookBeReserved;
        service.DeleteBook = DeleteBook;

        return service;

        function GetAllBooks() {
            return $http.get('/library/rest/books').then(handleSuccess, handleError('Error getting all books'));
        }

        function GetBookById(id) {
            return $http.get('/library/rest/books/' + id).then(handleSuccess, handleError('Error getting book by id'));
        }

        function CreateBook(book) {
            return $http.post('/library/rest/books/add', book).then(handleSuccess, handleError('Error creating new book'));
        }

        function UpdateBook(book) {
            return $http.put('/library/rest/books/update', book).then(handleSuccess, handleError('Error updating book'));
        }
        
        function whenIsBookAvailable(id) {
            return $http.get('/library/rest/books/' + id + '/available').then(handleSuccess, handleError('Error getting book availability'));
        }
        
        function isBookAvailable(id) {
            return $http.get('/library/rest/books/' + id + '/isavailable').then(handleSuccess, handleError('Error getting book availability'));
        }
        
        function canBookBeReserved(id) {
            return $http.get('/library/rest/books/' + id + '/canbereserved').then(handleSuccess, handleError('Error getting book availability'));
        }

        function DeleteBook(id) {
            return $http.delete('/library/rest/books/' + id + '/delete').then(handleSuccess, handleError('Error deleting book'));
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