
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="indexModule">
<head>
    <title>NOAA</title>
</head>
<body>
    <div ng-controller="refreshCtrl">
        <div>
            <div>
                {{DownLoadInfo}}
            </div>
            <input type="text" ng-model="ftpAddress" placeholder="Enter Ftp Address Without FTP:// in front" style="width: 300px;">
            <button ng-click="downloadFile()">DownLoad</button>

            {{downloadStatus}}
        </div>
        <div>
            <input type="text" ng-model="localFileDirectory" style="width: 300px;">
            <button ng-click="refreshValue()">Refresh</button>
        </div>
        <div>
            <input type="text" ng-model="searchValue">
            <button ng-click="searchByStationId()">Search</button>
            {{searchErrorMessage}}
        </div>


        <table  border="1">
        <tr class="table">
            <tr>
                <th>StationId</th>.
                <th>Date</th>
                <th>Weather</th>
            </tr>

            <tr ng-repeat = "x in res">
                <td>{{ x.stationId  }}</td>
                <td>{{ x.date }}</td>
                <td>{{ x.weather }}</td>
            </tr>
        </tr>
        <tr>

        </tr>
        </table>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.2/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.2/angular-route.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.2/angular-resource.min.js"></script>



    <script>
        var indModule = angular.module("indexModule", []);

        indModule.controller('refreshCtrl', function($scope, $http){
            $scope.columnValue = 123;
            $scope.DownLoadInfo = "Please click download button first to download file to your local machine, then click refresh button to load the data";

            $scope.refreshValue = function () {
                $http.post("/FindAll", $scope.localFileDirectory).then(function (response) {
                    $scope.res = response.data;
                })
            }

            $scope.searchByStationId = function () {
                $http.post("/SearchById", $scope.searchValue).then(function (response) {
                    if(response.data === ""){
                        $scope.searchErrorMessage = "StationId was not found, please enter a valid StationId";
                        return;
                    }
                    $scope.searchErrorMessage = "";
                    $scope.res = response.data;
                })
            }

            $scope.downloadFile = function () {
                $http.put("/DownLoadFile", $scope.ftpAddress).then(function (response) {
                    if(response.data !== ""){
                        $scope.downloadStatus = alert("Download finished");
                        $scope.localFileDirectory = response.data;
                        return;
                    }
                    $scope.downloadStatus = alert("Download failed");
                })
            }
        });
    </script>


</body>
</html>
