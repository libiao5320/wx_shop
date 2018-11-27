angular.module('gtUser')
  .controller('BaseCtrl', function ($scope, $rootScope, $http, $state, Auth) {
    Auth.isLoggedIn($rootScope, function (user) {
      if (!user) {
        $state.go('simple.login');
        return;
      }
      $rootScope.globalLoaded = true;
    }, true);

    $scope.is_active = function(url) {
      return $state.$current.name.indexOf(url) >= 0;
    };

    $scope.logout = function() {
      $http.post('/api/logout').success(function(e) {
        if(e.success) {
          $state.go('simple.login');
        }
      })
    }
  });