'use strict';

var AssociateApp = {};

var App = angular.module('AssociateApp', [ "ngRoute" ]);

// Declare app level module which depends on filters, and services
App.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/associate', {
		templateUrl : 'associate/layout',
		controller : AssociateController
	}).otherwise({
		redirectTo : '/associate'
	});
} ]);
