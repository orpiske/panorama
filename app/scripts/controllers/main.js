'use strict';

/**
 * @ngdoc function
 * @name panoramaGuiV2App.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the panoramaGuiV2App
 */
angular.module('panoramaGuiV2App')
  .controller('MainCtrl', function ($scope, $http) {
	  
	  $scope.domains = '';
	  
	  
			
	  	  $scope.search = function (domain) {
			  $http.get('/tcs/domain/')
			  	.success(function(data){
					$scope.domains = data;
				});
		  };
		  
	  	  $scope.listDomains = function (domain) {
			  $http.get('/tcs/domain/')
			  	.success(function(data){
					$scope.domains = data;
				});
		  };
	
			/*
	  $scope.search = function (domain) {
		  
		  $http.get('http://localhost:8080/tcs/domain/')
		  	.success(function(data){
				main.providers = data;
			});
		};
			*/
});

  
