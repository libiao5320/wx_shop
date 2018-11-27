angular.module('gtUser')
    .controller('MoneyCtrl', function ($scope, $http, $state) {
        $scope.mainMoney = true;
        $scope.money = 0;
        var init = function () {
            $http.get('/api/money').success(function (data) {
                $scope.money = data.money;
            });
            $http.get('/api/money/detail').success(function (data) {
                $scope.detail = data.data;
                console.log($scope.detail);
            });
        };
        init();
        $scope.getDetailData = function (s) {
            console.log(s);
            $http.post('/api/money/detail', {type: s}).success(function (data) {
                $scope.detail = data.data;
            });
        };
    }).controller('ChargeCtrl', function ($scope, $http, $state) {
        console.log("charge");
        $scope.banks = [
            'gsyh', 'jsyh', 'nyyh', 'zgyz', 'jtyh', 'zsyh', 'zgyh', 'zggd', 'zxyh', 'zgms', 'gfyh', 'payh', 'xyyh', 'pfyh'
        ];
        console.log($scope.banks);
        $scope.chargeMoney = 0;
        $scope.chargeBank = 'alipay';
        $scope.bindBank = function(bank){
            console.log(bank);
            $scope.chargeBank = bank;
        };
        $scope.submitCharge = function(){
            var data = {
                money: $scope.chargeMoney,
                bank: $scope.chargeBank
            };
            $http.post('/api/charge',data).success(function(data){
                if(data.success){
                    window.location.href = data.url;
                };
            });
        };
    }).controller('InvoiceCtrl', function ($scope, $http, $state) {
        $scope.invoice = {};
        $scope.subInvoice = function (form) {
            $scope.error = false;
            $scope.message = false;
            var title = $scope.invoice.title;
            var name = $scope.invoice.name;
            var tel = $scope.invoice.tel;
            var address = $scope.invoice.address;

            $http.post('/api/inovice', {
                title: title,
                name: name,
                tel: tel,
                address: address
            }).success(function (data) {
                if (data.success) {
                    $scope.message = true;
                } else {
                    $scope.error = true;
                }
                ;
            }).error(function (data) {
                console.log("err")
            });
        };
    })
;