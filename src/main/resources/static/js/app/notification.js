$(document).ready(function () {
    'use strict';
    var stompClient = null;
    var username = null;
    $(document).ready(function () {
        connect()
    })

    function connect() {
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }

    function onConnected() {
        // let name = prompt("Hello")
        // Subscribe to the Public Topic
        stompClient.subscribe('/topic/notification', onMessageReceived);

        // Tell your username to the server
        stompClient.send("/app/notification.addUser",
            {},
            JSON.stringify({type: 'JOIN'})
        )
    }


    function onError(error) {
        $.NotificationApp.send("Thông báo", "Lỗi kết nối với Websocket", "Position", "red", "Hello")
    }

    function onMessageReceived(payload) {
        var message = JSON.parse(payload.body);
        $.getAllNotification()
        $.NotificationApp.send("Thông báo", message.content, "Position", "green", "Hello")
    }


});
