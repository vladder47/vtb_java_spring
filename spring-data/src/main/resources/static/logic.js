var app = angular.module('app', ['ngRoute']);
var contextPath = 'http://localhost:8189/app'

app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'about_page.html',
        })
        .when('/books', {
            templateUrl: 'all_books.html',
            controller: 'booksController'
        })
        .when('/stat', {
            templateUrl: 'stat_page.html',
            controller: 'statController'
        })

});

app.controller('booksController', function ($scope, $http, $routeParams) {
    $scope.form = {};
    fillTable = function (pageNumber, params) {
        if (pageNumber === undefined) {
            pageNumber = 1;
        }
        $http.get(contextPath + '/api/v1/books?page=' + pageNumber + getUrl(params))
            .then(function (response) {
                $scope.BooksList = response.data.book.content;
                $scope.genres = response.data.genres;
                $scope.page = response.data.book;
                $scope.pages = getPager(response.data.book.totalPages, pageNumber);
            });
    };

    $scope.findBooks = function (pageNumber) {
        fillTable(pageNumber, $scope.form);
    };

    function getUrl(params) {
        var result = "";
        if (params['minPrice']) {
            result += '&minPrice=' + params['minPrice'];
        }if (params['maxPrice']) {
            result  += '&maxPrice=' + params['maxPrice'];
        }if (params['minPublishYear']) {
            result  += '&minPublishYear=' +  params['minPublishYear'];
        }if (params['maxPublishYear']) {
            result  += '&maxPublishYear=' +  params['maxPublishYear'];
        }if (params['titlePart']) {
            result  += '&titlePart=' +  params['titlePart'];
        }
        $scope.genres?.forEach(genre => {
            if (params[genre[0]]) {
                result += `&genre=${genre[0]}`;
            }
        })
        return result;
    }

    // google's pagination logic (в строке навигации одновременно отображаются не более 10 страниц)
    function getPager(totalPages, currentPage) {
        currentPage = currentPage || 1;

        let startPage, endPage;
        if (totalPages <= 10) {
            startPage = 1;
            endPage = totalPages;
        } else {
            if (currentPage <= 6) {
                startPage = 1;
                endPage = 10;
            } else if (currentPage + 4 >= totalPages) {
                startPage = totalPages - 9;
                endPage = totalPages;
            } else {
                startPage = currentPage - 5;
                endPage = currentPage + 4;
            }
        }

        let pages = [];
        for (let i = startPage; i < endPage + 1; i++) {
            pages.push(i);
        }

        return pages;
    }

    fillTable($routeParams.page, $scope.form);
});

app.controller('statController', function ($scope, $http) {
    fillTable = function () {
        $http.get(contextPath + '/api/v1/stat')
            .then(function (response) {
                $scope.stat = response.data;
            });
    };

    $scope.equals = function(object, value) {
        return angular.equals(object, value);
    }

    fillTable();
});

