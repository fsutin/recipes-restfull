function Recipes($scope, $http) {
    $http.get('http://localhost:8080/recipe/all').
        success(function(data) {
            $scope.recipes = data;
        });
}