angular.module('gtUser')
  .factory('Auth', function ($http, $rootScope, $state, $timeout) {
    return {
      isLoggedIn: function (scope, fn, cancel) {
        var rand = (new Date()).getTime();
        $http.get('/api/auth?rand=' + rand, {ignoreLoadingBar: true }).success(function (data) {
          if (data.error) {
            if (fn) {
              fn();
            }
            if (cancel) {
              return;
            }
            $state.go('login');
            return;
          }
          if (fn) {
            fn(data.user);
          }
          scope.user = data.user;
          $rootScope.user = data.user;
          $timeout(function () {
            scope.$broadcast('logined', data.user);
          });
        }).error(function () {
          if (cancel) {
            return;
          }
          $state.go('login');
        });
      }
    };
  });