'use strict';
//Declare a module
var addressModule = angular.module('addressModule', []);

addressModule.controller('addressController', function($scope, $http, $route) {
	$scope.addressAPI = '/users/' + $scope.userId + '/addresses/';
	//onload -> Get all addresses
	$http.get($scope.addressAPI).success(function(data) {
		$scope.addresses = data;
		$scope.selectedId = -1;//mark to know which actions: -1: new address, else: edit an address with selectedId
		
		//Create a new blank address
		$scope.address = {
			number : "",
			street : "",
			city : "",	
			postcode : "",
			country : ""
		};
	});
	
	//when edit button is clicked
	$scope.editAddress = function(id) {
		//find the address
		for(var i=0; i <$scope.addresses.length; i++){
			if($scope.addresses[i].id == id){
				$scope.address.number = $scope.addresses[i].number; 
				$scope.address.street = $scope.addresses[i].street;
				$scope.address.city = $scope.addresses[i].city; 
				$scope.address.postcode = $scope.addresses[i].postcode;
				$scope.address.country = $scope.addresses[i].country;
				$scope.selectedId = id;
				break;
			}		   
		}
	};

	//when delete button is clicked
	$scope.deleteAddress = function(id) {
		$http.delete($scope.addressAPI + id).success(function(data) {
			$scope.addresses = data;  					     
			$route.reload();
		});
	};
	
	//Save a new address or update an old address
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
			$http.put($scope.addressAPI+ $scope.selectedId, addressObj).success(function(data) {
				$scope.addresses = data;
				$route.reload();
			});
		}
		else
		{
			$http.post($scope.addressAPI, addressObj).success(function(data) {
				$scope.addresses = data;
				$route.reload();
			});
		}	
		
	};
});

