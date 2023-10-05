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
    let name = prompt("Hello")
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: name, type: 'JOIN'})
    )
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function thongbao(message) {
    if (message && stompClient) {
        var chatMessage = {
            sender: username,
            content: message,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.thongbao", {}, JSON.stringify(chatMessage));
    }
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    $.NotificationApp.send("Thông báo", message.content, "Position", "green", "Hello")
}