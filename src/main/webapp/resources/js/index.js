$(document).ready(function() {
    console.log('index.js');

});

function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    var id_token = googleUser.getAuthResponse().id_token;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/todolist/signin');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
        window.location.href = '/todolist/browse';
    };
    xhr.send('idtoken=' + id_token);
}
