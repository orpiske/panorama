'use strict';

/**
 * @ngdoc overview
 * @name panoramaGuiV2App
 * @description
 * # panoramaGuiV2App
 *
 * Main module of the application.
 */
angular
  .module('panoramaGuiV2App', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
