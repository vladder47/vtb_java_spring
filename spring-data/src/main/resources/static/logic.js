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
});

app.controller('booksController', function ($scope, $http, $routeParams) {
    $scope.form = {};
    fillTable = function (pageNumber, params) {
        if (pageNumber === undefined) {
            pageNumber = 1;
        }
        $http.get(contextPath + '/api/v1/books?page=' + pageNumber + getUrl(params))
            .then(function (response) {
                var range = [];
                for (var i=0; i < response.data.book.totalPages; i++) {
                    range.push(i + 1);
                }
                $scope.BooksList = response.data.book.content;
                $scope.genres = response.data.genres;
                $scope.page = response.data.book;
                $scope.range = range;
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

    // пока не использую эту функцию при формировании номеров страниц
    function GetPager(totalItems, currentPage, pageSize) {
        currentPage = currentPage || 1;

        pageSize = pageSize || 10;

        var totalPages = Math.ceil(totalItems / pageSize);

        var startPage, endPage;
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
        var startIndex = (currentPage - 1) * pageSize;
        var endIndex = Math.min(startIndex + pageSize - 1, totalItems - 1);

        var pages = _.range(startPage, endPage + 1);

        return {
            totalItems: totalItems,
            currentPage: currentPage,
            pageSize: pageSize,
            totalPages: totalPages,
            startPage: startPage,
            endPage: endPage,
            startIndex: startIndex,
            endIndex: endIndex,
            pages: pages
        };
    }

    fillTable($routeParams.page, $scope.form);
});

