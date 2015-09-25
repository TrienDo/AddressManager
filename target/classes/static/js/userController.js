'use strict';
//Declare a module
var userModule = angular.module('userModule', []);

userModule.controller('navigation',['$rootScope', '$scope', '$http', '$location', '$route', 'settings', function($rootScope, $scope, $http, $location, $route, settings) {

	$scope.tab = function(route) {
		return $route.current && route === $route.current.controller;
	};

	var authenticate = function(credentials, callback) {
		//encode and create header
		var headers = credentials ? {
			authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)
		} : {};
		
		//Authenticate with server
		$http.get('/user', { headers : headers})
		.success(function(data) {
			if (data.id != -1) {
				$rootScope.authenticated = true;
				settings.userId = data.id;
				settings.restApiBase = '/users/' + data.id;
				settings.username = data.subject,
				settings.userEmail = data.text
			} 
			else {
				$rootScope.authenticated = false;
			}
			callback && callback($rootScope.authenticated);
		})
		.error(function() {
			$rootScope.authenticated = false;
			callback && callback(false);
		});
	}

	authenticate();

	$scope.credentials = {};
	
	$scope.login = function() {
		authenticate($scope.credentials, function(authenticated) {
			if (authenticated) {
				console.log("Login succeeded")
				$location.path("/");
				$scope.error = false;
				$rootScope.authenticated = true;
			} 
			else {
				console.log("Login failed")
				$location.path("/");
				$scope.error = true;
				$rootScope.authenticated = false;
			}
		})
	};

	$scope.logout = function() {
		$http.post('/logout', {}).success(function() {
			$rootScope.authenticated = false;
			$location.path("/");
		}).error(function(data) {
			console.log("Logout failed")
			$location.path("/");
			$rootScope.authenticated = false;
		});
	}

}]);

userModule.controller('home',['$scope', 'settings', function($scope, settings) {
	$scope.greeting = settings;
}]);

/*userModule.controller('home', function($scope, $http) {
	$http.get('/resource').success(function(data) {
		$scope.greeting = data;
	});
});*/

userModule.controller('register', function($scope, $http, $route) {
	$scope.register = function() {					
		var userObj = {
			username : $scope.user.username,
			email : $scope.user.email,
			password : $scope.user.password
		};
	
		$http.post('/user', userObj).success(function(data) {
			//$location.path("/login");
			if(data.id == -1)
				$scope.error = true;
			else 
				$scope.error = false;
			$scope.messageContent = data.text;			
		});
	};		
});
