angular.module('gtUser')
  .controller('MobileRegCtrl', function (Auth, $rootScope, $scope, $http, $state, $sce) {
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
      var captchaUrl = 'http://api.geetest.com/get.php?gt=4a0eb5e6f780944308954bc602814a53&random=' + (new Date()).getTime();
      $scope.geetestCaptcha = $sce.trustAsResourceUrl(captchaUrl);
      //$scope.captchaLoaded = false;
    }, true);

    window.gt_custom_ajax = function (result) {
      $scope.captchaPass = result;
    };

    $scope.user = {};
    $scope.sendTel = function (tel) {
      $scope.error = false;
      $scope.telMessage = false;
      var tel = $scope.user.tel
      data = {
        geetest_challenge: angular.element('[name=geetest_challenge]').val(),
        geetest_validate: angular.element('[name=geetest_validate]').val(),
        geetest_seccode: angular.element('[name=geetest_seccode]').val(),
        tel: tel
      }
      if (!tel) {
        $scope.error = "请先填写手机号";
        return;
      }
      if (!$scope.captchaPass) {
        $scope.error = '请先验证滑动验证';
        return;
      }
      $http.post("/api/message", data).success(function (data) {
        console.log(data);
        if (data.res == 1) {
          $scope.telMessage = "验证短信已发送";
        }else if(data.res == -4){
          $scope.error = "发送失败";
        }else if(data.res == -6){
          $scope.error = "滑动验证未通过";
        }else{
          $scope.error = "短信发送失败";
        }
      });
    };

    $scope.reg = function (form) {
      if (form.tel.$invalid) {
        angular.element('#tel').focus();
        angular.element('#tel').blur();
        angular.element('#tel').focus();
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
      if (!$scope.user.telcaptcha) {
        $scope.error = '短信验证码未填写';
        return;
      }

      var tel = $scope.user.tel;
      var password = $scope.user.password;
      var qq = $scope.user.qq;
      var telcaptcha = $scope.user.telcaptcha

      $http.post('/api/phone/reg', {
        tel: tel,
        password: password,
        qq: qq,
        telcaptcha: telcaptcha,
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
        if (data == 'success') {
          console.log('success');
          $state.go("user.money");
          return;
        }
      }).error(function (data) {
        $scope.error = data.error || '登录失败，网络错误';
      });
    };
  });