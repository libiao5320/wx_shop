angular.module('gtUser')
  .controller('SimpleCtrl', function ($scope, $http, $state) {

  })
  .controller('LoginCtrl', function (Auth, $rootScope, $scope, $http, $state, $sce, $location, $interval) {
    var location = $location.search();
    $scope.error = '';
    $scope.mailError = false;

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

    $scope.user = {
      remember: false
    };

    $scope.login = function (form) {
      if (form.$invalid) {
        form.email.$dirty = true;
        form.password.$dirty = true;
        return;
      }

      if (!$scope.captchaPass) {
        $scope.error = '验证码未填写';
        return;
      }

      var email = $scope.user.email;
      var password = $scope.user.password;
      var remember = $scope.user.remember;
      var url = location.url;

      $http.post('/api/login', {
        email: email,
        password: password,
        remember: remember,
        url: url,
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
        if (data.url){
          window.location.href = url;
        }
        $rootScope.user = data.user;
        //$state.go('user.money');
        window.location.href = "http://account.geetest.com";
      }).error(function (data) {
        $scope.error = data.error || '登录失败，网络错误';
      });
    };
      //var times = 0;
      //var iid = $interval(function () {
      //  if (times >= 5) {
      //    $interval.cancel(iid);
      //    return;
      //  }
      //  $scope.user.email = angular.element('#email').value;
      //  $scope.user.password = angular.element('#password').value;
      //  times = times + 1;
      //}, 1000);

  }).controller('ForgetCtrl', function (Auth, $rootScope, $scope, $http, $state, $sce) {
    $scope.mailError = false;
    $scope.forgetSuccess = false;

    var captchaUrl = 'http://api.geetest.com/get.php?gt=a40fd3b0d712165c5d13e6f747e948d4&random=' + (new Date()).getTime();
    $scope.geetestCaptcha = $sce.trustAsResourceUrl(captchaUrl);

    window.gt_custom_ajax = function (result) {
      $scope.captchaPass = result;
    };

    $scope.user = {};

    $scope.forget = function (form) {
      if (form.$invalid) {
        form.email.$dirty = true;
        return;
      }

      if (!$scope.captchaPass) {
        $scope.error = '验证码未填写';
        return;
      }

      var email = $scope.user.email;

      $http.post('/api/forget', {
        email: email,
        geetest_challenge: angular.element('[name=geetest_challenge]').val(),
        geetest_validate: angular.element('[name=geetest_validate]').val(),
        geetest_seccode: angular.element('[name=geetest_seccode]').val()
      }).success(function (data) {
        if (data.error) {
          $scope.error = data.error;
          return;
        } else {
          $scope.forgetSuccess = true;
        }
      });
    };
  }).controller('ResetCtrl', function ($stateParams, $scope, $sce, $http, $state) {
    var hash = $stateParams.hash;
    var email = $stateParams.email;
    var captchaUrl = 'http://api.geetest.com/get.php?gt=a40fd3b0d712165c5d13e6f747e948d4&random=' + (new Date()).getTime();
    $scope.geetestCaptcha = $sce.trustAsResourceUrl(captchaUrl);

    window.gt_custom_ajax = function (result) {
      $scope.captchaPass = result;
    };

    $scope.user = {};
    $scope.reset = function (form) {
      $scope.error = "";
      $scope.success = "";
      if ($scope.user.newpassword != $scope.user.password) {
        $scope.error = '两次输入的密码不一致';
        return;
      }
      ;
      if (!$scope.captchaPass) {
        $scope.error = '验证码未填写';
        return;
      }
      $http.post('/api/password/reset', {
        email: email,
        hash: hash,
        newpassword: $scope.user.newpassword,
        password: $scope.user.password,
        geetest_challenge: angular.element('[name=geetest_challenge]').val(),
        geetest_validate: angular.element('[name=geetest_validate]').val(),
        geetest_seccode: angular.element('[name=geetest_seccode]').val()
      }).success(function (data) {
        if (data.success) {
          $scope.success = true;
          return;
        } else {
          $scope.error = "密码重设失败";
        }
      });
    };
  });
