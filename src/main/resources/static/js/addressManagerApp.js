'use strict';

var addressManagerApp = angular.module('addressManager', [
	'ngRoute',
	'userModule',
	'addressModule',
	'accountModule'
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
	}).when('/accounts', {
		templateUrl : 'account.html',
		controller : 'accountController'
	}).otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

});

addressManagerApp.value('settings', {
    restApiBase: '',    
    userId: '',
    username: '',
    userEmail:''
});
