
// document.addEventListener('DOMContentLoaded', function () {
//     var btn = document.querySelector('.button'),
//         loader = document.querySelector('.loader'),
//         check = document.querySelector('.check');
//
//     btn.addEventListener('click', function () {
//         loader.classList.add('active');
//     });
//
//     loader.addEventListener('animationend', function() {
//         check.classList.add('active');
//     });
// });
// const chatbox = jQuery.noConflict();
//
// chatbox(() => {
//     chatbox(".chatbox-open").click(() =>
//         chatbox(".chatbox-popup, .chatbox-close").fadeIn()
//     );
//
//     chatbox(".chatbox-close").click(() =>
//         chatbox(".chatbox-popup, .chatbox-close").fadeOut()
//     );
//
//     chatbox(".chatbox-maximize").click(() => {
//         chatbox(".chatbox-popup, .chatbox-open, .chatbox-close").fadeOut();
//         chatbox(".chatbox-panel").fadeIn();
//         chatbox(".chatbox-panel").css({display: "flex"});
//     });
//
//     chatbox(".chatbox-minimize").click(() => {
//         chatbox(".chatbox-panel").fadeOut();
//         chatbox(".chatbox-popup, .chatbox-open, .chatbox-close").fadeIn();
//     });
//
//     chatbox(".chatbox-panel-close").click(() => {
//         chatbox(".chatbox-panel").fadeOut();
//         chatbox(".chatbox-open").fadeIn();
//     });
// });
$(document).ready(function() {
    var btn = $('.button');
    var loader = $('.loader');
    var check = $('.check');

    btn.on('click', function() {
        loader.addClass('active');
    });

    loader.on('animationend', function() {
        check.addClass('active');
    });
});

$(function() {
    $(".chatbox-open").click(function() {
        $(".chatbox-popup, .chatbox-close").fadeIn();
    });

    $(".chatbox-close").click(function() {
        $(".chatbox-popup, .chatbox-close").fadeOut();
    });

    $(".chatbox-maximize").click(function() {
        $(".chatbox-popup, .chatbox-open, .chatbox-close").fadeOut();
        $(".chatbox-panel").fadeIn();
        $(".chatbox-panel").css({ display: "flex" });
    });

    $(".chatbox-minimize").click(function() {
        $(".chatbox-panel").fadeOut();
        $(".chatbox-popup, .chatbox-open, .chatbox-close").fadeIn();
    });

    $(".chatbox-panel-close").click(function() {
        $(".chatbox-panel").fadeOut();
        $(".chatbox-open").fadeIn();
    });
});

    function hideAndShowForms() {
    var form1 = document.getElementById("usernameForm");
    form1.style.display = "none";
    var form2 = document.getElementById("messageForm");
    form2.style.display = "block";
}