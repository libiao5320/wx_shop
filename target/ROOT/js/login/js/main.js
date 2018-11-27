'use strict';

angular
    .module('gtUser', [
        'ngAnimate',
        'ngSanitize',
        'ngMessages',
        'ui.router',
        //'angularFileUpload',
        'gtDatePicker',
        'ngTouch'
        //'paginateRepeat',
        //'angular-loading-bar'
        //'highcharts-ng'
    ]).config(function ($locationProvider, $stateProvider, $urlRouterProvider, $sceDelegateProvider) {
        $sceDelegateProvider.resourceUrlWhitelist([
            'self',
            'http://api.geetest.com/**'
        ]);

        $stateProvider.state('simple', {
            url: '',
            templateUrl: "/static/templates/simple.html",
            controller: 'SimpleCtrl',
            abstract: true
        }).state("simple.login", {
            url: "/login",
            templateUrl: "/static/templates/login.html",
            controller: "LoginCtrl",
            displayName: "登陆"
        }).state("simple.reg", {
            url: "/reg",
            templateUrl: "/static/templates/reg.html",
            controller: "RegCtrl",
            displayName: "注册"
        }).state("simple.phonereg", {
            url: "/phone/reg",
            templateUrl: "/static/templates/mobile_reg.html",
            controller: "MobileRegCtrl",
            displayName: "注册"
        }).state("simple.regsuccess", {
            url: "/reg/success",
            templateUrl: "/static/templates/reg_success.html",
            controller: "RegSuccessCtrl",
            displayName: "注册成功"
        }).state("simple.forget", {
            url: "/forget",
            templateUrl: "/static/templates/forget.html",
            controller: "ForgetCtrl",
            displayName: "忘记密码"
        }).state("simple.reset", {
            url: "/setPassword/:hash/:email",
            templateUrl: "/static/templates/reset.html",
            controller: "ResetCtrl",
            displayName: "重设密码"
        });

        $stateProvider.state('user', {
            url: '',
            templateUrl: "/static/templates/base.html",
            controller: 'BaseCtrl',
            abstract: true
        }).state("user.money", {
            url: "/money",
            templateUrl: "/static/templates/money.html",
            controller: "MoneyCtrl",
            displayName: "账户金额"
        }).state("user.charge", {
            url: "/charge",
            templateUrl: "/static/templates/charge.html",
            controller: "ChargeCtrl"
        }).state("user.invoice", {
            url: "/invoice",
            templateUrl: "/static/templates/invoice.html",
            controller: "InvoiceCtrl"
        }).state("user.profile", {
            url: "/profile",
            templateUrl: "/static/templates/profile.html",
            controller: "ProfileCtrl",
            displayName: "账户资料"
        });

        $urlRouterProvider.otherwise('/login');
        $locationProvider.html5Mode(true);
    }).run(function ($rootScope) {
        $rootScope.$on('$stateChangeSuccess',
            function (event, toState, toParams, fromState, fromParams) {
                $rootScope.title = toState.displayName;
                event.preventDefault();
            });
    });
