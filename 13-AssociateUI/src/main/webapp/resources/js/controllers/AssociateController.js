'use strict';

/**
 * AssociateController
 * 
 * @constructor
 */
var AssociateController = function($scope, $http) {
    $scope.associateList = {};
    $scope.editMode = false;

    $scope.fetchAssociatesList = function() {
        $http.get('associate/associatelist.json').success(function(associateList){
            $scope.associateList = associateList;
        });
    };

    $scope.addNewAssociate = function(associate) {
        $scope.resetError();

        $http.post('associate/addAssociate', associate).success(function() {
        	$scope.fetchAssociatesList();
        }).error(function() {
        	 
            $scope.setError('Could not add a new associate');
        });
    };

    $scope.updateAssociate = function(associate) {
        $scope.resetError();
       $http.put('associate/updateAssociate', associate).success(function() {
            $scope.fetchAssociatesList();
        }).error(function() {
            $scope.setError('Could not update the associate');
        });
    };

    $scope.editAssociate = function(associate) {
        $scope.resetError();
        $scope.associate = associate;
        $scope.editMode = true;
    };

    $scope.removeAssociate = function(associate) {
        $scope.resetError();

        $http.delete('associate/removeAssociate/' + associate.associateId).success(function() {
            $scope.fetchAssociatesList();
        }).error(function() {
            $scope.setError('Could not remove associate');
        });
        $scope.associate.associateName = '';
        $scope.associate.associateId = '';
    };

    $scope.removeAllAssociates = function() {
        $scope.resetError();

        $http.delete('associate/removeAllAssociate').success(function() {
            $scope.fetchAssociatesList();
        }).error(function() {
            $scope.setError('Could not remove all associate');
        });

    };

    $scope.resetAssociateForm = function() {
        $scope.resetError();
        $scope.associate = {};
        $scope.editMode = false;
    };

    $scope.resetError = function() {
        $scope.error = false;
        $scope.errorMessage = '';
    };

    $scope.setError = function(message) {
        $scope.error = true;
        $scope.errorMessage = message;
    };

    $scope.fetchAssociatesList();

    $scope.predicate = 'associateId';
};