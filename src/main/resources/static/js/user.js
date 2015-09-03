angular
		.module('hello', ['ngRoute'])
		.config(
				function($routeProvider, $httpProvider) {

					$routeProvider.when('/', {
						templateUrl : 'home.html',
						controller : 'home'
					}).when('/login', {
						templateUrl : 'login.html',
						controller : 'navigation'
					}).when('/addresses', {
						templateUrl : 'address.html',
						controller : 'addresses'
					}).otherwise('/');

					$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

				}).controller(
				'navigation',

				function($rootScope, $scope, $http, $location, $route) {

					$scope.tab = function(route) {
						return $route.current
								&& route === $route.current.controller;
					};

					var authenticate = function(credentials, callback) {

						var headers = credentials ? {
							authorization : "Basic "
									+ btoa(credentials.username + ":"
											+ credentials.password)
						} : {};

						$http.get('user', {
							headers : headers
						}).success(function(data) {
							if (data.name) {
								$rootScope.authenticated = true;
							} else {
								$rootScope.authenticated = false;
							}
							callback && callback($rootScope.authenticated);
						}).error(function() {
							$rootScope.authenticated = false;
							callback && callback(false);
						});

					}

					authenticate();

					$scope.credentials = {};
					$scope.login = function() {
						authenticate($scope.credentials,
								function(authenticated) {
									if (authenticated) {
										console.log("Login succeeded")
										$location.path("/");
										$scope.error = false;
										$rootScope.authenticated = true;
									} else {
										console.log("Login failed")
										$location.path("/login");
										$scope.error = true;
										$rootScope.authenticated = false;
									}
								})
					};

					$scope.logout = function() {
						$http.post('logout', {}).success(function() {
							$rootScope.authenticated = false;
							$location.path("/");
						}).error(function(data) {
							console.log("Logout failed")
							$rootScope.authenticated = false;
						});
					}

				}).controller('home', function($scope, $http) {
					$http.get('/resource/').success(function(data) {
					$scope.greeting = data;
				})
			}).controller('addNewAddressController', function($scope, $http, $route) {
					$scope.submit = function() {					
						var addressObj = {
							number : $scope.address.number,
							street : $scope.address.street,
							city : $scope.address.city,	
							postcode : $scope.address.postcode,
							country : $scope.address.country
						};
						if($scope.selectedId!=-1)
						{
							$http.put('/addresses/'+ $scope.selectedId, addressObj).success(function(data) {
								$scope.addresses = data;
								$route.reload();
							});
						}
						else
						{
							$http.post('/addresses/', addressObj).success(function(data) {
								$scope.addresses = data;
								$route.reload();
							});
						}	
						
					};		

			}).controller('addresses', function($scope, $http, $route) {
				
					$http.get('/addresses/').success(function(data) {
					$scope.addresses = data;
					$scope.selectedId = -1;
					$scope.address = {
							number : "",
							street : "",
							city : "",	
							postcode : "",
							country : ""
						};
					$scope.deleteAddress = function(id) {
						$http.delete('/addresses/' + id).success(function(data) {
							$scope.addresses = data;  					     
							$route.reload();
						});
					  
					};
					$scope.editAddress = function(id) {
						//find the address
						for(i=0; i <$scope.addresses.length; i++)
							if($scope.addresses[i].id == id)
							{
							    $scope.address.number = $scope.addresses[i].number; 
							    $scope.address.street = $scope.addresses[i].street;
							    $scope.address.city = $scope.addresses[i].city; 
							    $scope.address.postcode = $scope.addresses[i].postcode;
							    $scope.address.country = $scope.addresses[i].country;
							    $scope.selectedId = id;
							    break;
							}
					   
					};
				})
			});
