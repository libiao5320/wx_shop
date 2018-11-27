angular.module('gtUser')
  .controller('ProfileCtrl', function ($scope, $http, $location) {
    var location = $location.search();
    $scope.companyPic = "";
    $scope.Porfile = 0;
    $scope.company = {
      name: "",
      tel: "",
      address: "",
      admin: "",
      disable: false,
      location: "",
      url: ""
    };
    $scope.reset = {
      password: "",
      newpassword: "",
      repassword: ""
    };
    var init = function () {
      $http.get('api/profile').success(function (data) {
        if (data.success) {
          $scope.company = data.company;
          $scope.companyPic = data.company.img;
        }
      })
    };
    init();
    $scope.profileUpdate = function () {
      var data = $scope.company;
      $scope.profileMessage = "";
      $scope.profileSuccess = false;
      $scope.profileFail = false;
      if (location.location) {
        data.location = location.location;
        data.url = location.url;
      }
      if (!$scope.companyPic) {
        $scope.profileFail = true;
        $scope.profileMessage = "请上传营业执照";
        return;
      }
      data.img = $scope.companyPic;
      $http.post('api/profile', data).success(function (data) {
        console.log(data);
        if (data.success) {
          $scope.profileSuccess = true;
          if (data.url) {
            window.location.href = data.url;
          }
        } else {
          $scope.profileFail = true;
          $scope.profileMessage = data.error;
        }
      })
    };
    init();
    $scope.passwordUpdate = function () {
      var data = {
        password: $scope.reset.password,
        newpassword: $scope.reset.newpassword,
        repassword: $scope.reset.repassword
      };
      $http.post('api/profile/newreset', data).success(function (data) {
        $scope.success = false;
        $scope.error = false;
        if (data.success) {
          $scope.success = true;
        } else {
          $scope.error = true;
        }
        ;
      })
    };
    $scope.uploadFile = function (files) {
      var fd = new FormData();
      //Take the first selected file
      $scope.typeError = false;
      $scope.showImage = false;
      var file = files[0];
      var type = file.type;

      if (type.indexOf('png') === -1
        && type.indexOf('jpeg') === -1) {
        $scope.typeError = true;
        $scope.showImage = false;
        $scope.$apply();
        return;
      }
      if (file.size > 3000000) {
        $scope.sizeError = true;
        $scope.showImage = false;
        $scope.$apply();
        return;
      }

      fd.append("file", files[0]);
      $http.post("/api/upload", fd, {
        withCredentials: true,
        headers: {'Content-Type': undefined},
        transformRequest: angular.identity
      }).success(function (data) {
        if (data.success) {
          $scope.companyPic = data.url;
        }
      });
    };
  });
