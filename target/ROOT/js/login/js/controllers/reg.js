angular.module('gtUser')
  .controller('RegCtrl', function (Auth, $rootScope, $scope, $http, $state, $sce) {
    $scope.user = {};
    $scope.error = '';
    $scope.mailError = false;
    $scope.regSuccess = false;

    Auth.isLoggedIn($scope, function (user) {
      $rootScope.globalLoaded = true;
      if (user) {
        $state.go('user.money');
        return;
      }
      $rootScope.globalLoaded = true;
      var captchaUrl = 'http://api.geetest.com/get.php?gt=a40fd3b0d712165c5d13e6f747e948d4&random=' + (new Date()).getTime();
      $scope.geetestCaptcha = $sce.trustAsResourceUrl(captchaUrl);
      //$scope.captchaLoaded = false;
    }, true);

    window.gt_custom_ajax = function (result) {
      $scope.captchaPass = result;
    };

    $scope.user = {};

    $scope.reg = function (form) {
      if (form.email.$invalid) {
        angular.element('#email').focus();
        angular.element('#email').blur();
        angular.element('#email').focus();
        return;
      }
      if (form.password.$invalid) {
        angular.element('#password').focus();
        angular.element('#password').blur();
        angular.element('#password').focus();
        return;
      }
      if (form.qq.$invalid) {
        angular.element('#qq').focus();
        angular.element('#qq').blur();
        angular.element('#qq').focus();
        return;
      }

      if (!$scope.captchaPass) {
        $scope.error = '验证码未填写';
        return;
      }

      var email = $scope.user.email;
      var password = $scope.user.password;
      var qq = $scope.user.qq;
      if (!qq) {
        $scope.error = 'QQ未填写';
        return;
      }

      $http.post('/api/reg', {
        email: email,
        password: password,
        qq: qq,
        geetest_challenge: angular.element('[name=geetest_challenge]').val(),
        geetest_validate: angular.element('[name=geetest_validate]').val(),
        geetest_seccode: angular.element('[name=geetest_seccode]').val()
      }).success(function (data) {
        if (data.mailerror) {
          $scope.mailUrl = data.url;
          $scope.mailError = true;
        }
        if (data.error) {
          $scope.error = data.error;
          return;
        }
        $state.go("simple.regsuccess");
        //$scope.regSuccess = true;
      }).error(function (data) {
        $scope.error = data.error || '登录失败，网络错误';
      });
    };
  }).controller('RegSuccessCtrl', function ($scope, $http, $state) {

    })
;