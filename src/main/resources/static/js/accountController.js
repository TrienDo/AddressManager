'use strict';
//Declare a module
var accountModule = angular.module('accountModule', []);

accountModule.controller('accountController',['$scope', '$http', '$route','settings', function($scope, $http, $route, settings) {
	$scope.accountAPI = settings.restApiBase  + '/accounts/';
	$scope.addressAPI = settings.restApiBase  + '/addresses/';
	
	//onload -> Get all accounts
	$http.get($scope.accountAPI).success(function(data) {
		$scope.accounts = data;
		$scope.selectedId = -1;//mark to know which actions: -1: new address, else: edit an address with selectedId		
		//Create a new blank address
		$scope.account = {
			name : "",
			website : "",
			description : ""
		};		
	});
	//Get all addresses
	$http.get($scope.addressAPI).success(function(data) {
		$scope.addresses = data;			
	});
	
	//when edit button is clicked
	$scope.editAccount = function(id) {
		//find the address
		for(var i=0; i <$scope.accounts.length; i++){
			if($scope.accounts[i].id == id){
				$scope.account.name = $scope.accounts[i].name; 
				$scope.account.website = $scope.accounts[i].website;
				$scope.account.description = $scope.accounts[i].description;
				$scope.selectedId = id;
				break;
			}		   
		}
	};

	//when delete button is clicked
	$scope.deleteAccount = function(id) {
		$http.delete($scope.accountAPI + id).success(function(data) {
			$scope.accounts = data;  					     
			$route.reload();
		});
	};
	
	//Save a new address or update an old address
	$scope.submit = function() {					
		var accountObj = {
			name		 	: $scope.account.name,
			website			: $scope.account.website,
			description 	: $scope.account.description
		};
		if($scope.selectedId!=-1)
		{
			$http.put($scope.accountAPI+ $scope.selectedId, accountObj).success(function(data) {
				$scope.accounts = data;
				$route.reload();
			});
		}
		else
		{
			$http.post($scope.accountAPI, accountObj).success(function(data) {
				$scope.accounts = data;
				$route.reload();
			});
		}	
		
	};
}]);

