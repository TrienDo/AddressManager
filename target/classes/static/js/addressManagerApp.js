'use strict';

var addressManagerApp = angular.module('addressManager', [
	'ngRoute',
	'userModule',
	'addressModule'                                                          
]);

addressManagerApp.config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'home'
	}).when('/login', {
		templateUrl : 'login.html',
		controller : 'navigation'
	}).when('/register', {
		templateUrl : 'register.html',
		controller : 'register'
	}).when('/addresses', {
		templateUrl : 'address.html',
		controller : 'addressController'
	}).otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

});