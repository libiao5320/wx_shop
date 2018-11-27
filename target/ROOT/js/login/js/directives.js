angular.module('gtUser')
  .directive('popup', function ($window, $timeout) {
    return {
      templateUrl: '/static/partials/popup.html',
      transclude: true,
      scope: {
        title: '@popupTitle',
        show: '='
      },
      link: function (scope, elem, attr, ctrl, transclude) {
        scope.hide = function (e) {
          if (e.target != e.currentTarget) {
            return;
          }
          scope.show = false;
        };
        var setHeight = function () {
          elem.find('.popup-content').css('max-height', $window.innerHeight - 146 + 'px');
        };
        scope.$watch('show', function (newValue, oldValue) {
          if (newValue && newValue != oldValue) {
            setHeight()
          }
        });
        angular.element($window).bind('resize', setHeight);
        transclude(scope.$parent, function (content) {
          elem.find(".popup-content").append(content);
        });
      }
    }
  })
  .directive('loadScript', ['$http', '$timeout', '$rootScope', function ($http, $timeout, $rootScope) {
    return {
      link: function (scope, elem, attr) {
        var callback = function () {
          scope.captchaLoaded = true;
        };
        scope.$watch(function () {
          return elem[0].getAttribute('src')
        }, function (newValue, oldValue) {
          if (newValue) {
            $http.jsonp(elem[0].getAttribute('src')).success(callback).error(callback);
          }
        });
        scope.$on('$destroy', function () {
          angular.element('.gt_widget').remove();
        });
      }
    };
  }])
  .directive('isWebsite', function () {
    return {
      require: 'ngModel',
      link: function (scope, elem, attr, ngModel) {
        var httpReg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?/;
        ngModel.$parsers.unshift(function (value) {
          ngModel.$setValidity('isWebsite', value.length ? httpReg.test(value) : true);
          return value;
        });
      }
    };
  })
  .directive('equals', function ($http) {
    return {
      require: 'ngModel',
      link: function (scope, elem, attrs, ngModel) {
        if (!ngModel) return; // do nothing if no ng-model

        // watch own value and re-validate on change
        scope.$watch(attrs.ngModel, function () {
          validate();
        });

        // observe the other value and re-validate on change
        attrs.$observe('equals', function (val) {
          validate();
        });

        var validate = function () {
          // values
          var val1 = ngModel.$viewValue;
          var val2 = attrs.equals;

          // set validity
          ngModel.$setValidity('equals', val1 === val2);
        };
      }
    }
  })
  .directive('formAutofillFix', function ($timeout) {
    return function (scope, element, attrs) {
      element.prop('method', 'post');
      if (attrs.ngSubmit) {
        $timeout(function () {
          element
            .unbind('submit')
            .bind('submit', function (event) {
              event.preventDefault();
              element
                .find('input, textarea, select')
                .trigger('input')
                .trigger('change')
                .trigger('keydown');
              scope.$apply(attrs.ngSubmit);
            });
        });
      }
    };
  }).directive('positive', function () {
    return {
      require: 'ngModel',
      link: function (scope, element, attrs, modelCtrl) {
        modelCtrl.$parsers.push(function (inputValue) {
          // this next if is necessary for when using ng-required on your input.
          // In such cases, when a letter is typed first, this parser will be called
          // again, and the 2nd time, the value will be undefined
          if (inputValue == undefined) return '';

          if (isNaN(inputValue - 0)) return '';
          //should be number first

          if (inputValue < 0) {
            modelCtrl.$setViewValue(-inputValue);
            modelCtrl.$render();
            return -inputValue;
          }

          return inputValue;
        });
      }
    };
  }).directive('tip', function () {
    return {
      restrict: "E",
      templateUrl: '/static/partials/tip.html',
      scope: {
        text: '@',
        url: '@',
        action: '='
      }
    }
  });