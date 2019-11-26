/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




$(document).ready(function () {


    $('.form').find('input, textarea, password, email').on('keyup blur focus', function (e) {

        var $this = $(this),
                label = $this.prev('label');

        if (e.type === 'keyup') {
            if ($this.val() === '') {
                label.removeClass('active highlight');
                $(this).val(fullCheck($(this).val(), $(this).attr("name")));
            } else {
                label.addClass('active highlight');
                $(this).val(fullCheck($(this).val(), $(this).attr("name")));
            }
        } else if (e.type === 'blur') {
            if ($this.val() === '') {
                label.removeClass('active highlight');
                $(this).val(fullCheck($(this).val(), $(this).attr("name")));
            } else {
                label.removeClass('highlight');
                $(this).val(fullCheck($(this).val(), $(this).attr("name")));
            }
        } else if (e.type === 'focus') {

            if ($this.val() === '') {
                label.removeClass('highlight');
                $(this).val(fullCheck($(this).val(), $(this).attr("name")));
            } else if ($this.val() !== '') {
                label.addClass('highlight');
                $(this).val(fullCheck($(this).val(), $(this).attr("name")));
            }
        }

    });


    $('.tab a').on('click', function (e) {

        e.preventDefault();

        $(this).parent().addClass('active');
        $(this).parent().siblings().removeClass('active');

        target = $(this).attr('href');

        $('.tab-content > div').not(target).hide();

        $(target).fadeIn(1000);

    });


    var fullCheck = function (value, name) {
        if (name === "fn" || name === "ln") {
            value = removeSpecials(removeNumbers(value));
        } else if (name === "un" || name === "em" || name === "us") {
            value = removeSpaces(value);
        } else if (name === "ph") {
            value = removeLower(removeSpecials(removeDashUnderscore(removeSpaces(removeUpper(value)))));
        } else if (name === "sh") {
            value = removeNumbers(removeSpecials(removeDashUnderscore(value)));
        }
        return value;
    };

    var removeNumbers = function (value) {
        var numberArray = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"];
        numberArray.forEach(el => {
            while (value.indexOf(el) !== -1) {
                value = value.replace(el, "");
            }
        });

        return value;
    };
    var removeUpper = function (value) {
        var upperCaseArray = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"];
        upperCaseArray.forEach(el => {
            while (value.indexOf(el) !== -1) {
                value = value.replace(el, "");
            }
        });

        return value;
    };
    var removeLower = function (value) {
        var lowerCaseArray = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"];
        lowerCaseArray.forEach(el => {
            while (value.indexOf(el) !== -1) {
                value = value.replace(el, "");
            }
        });

        return value;
    };
    var removeSpecials = function (value) {
        var specialArray = ["~", "`", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")",
            "+", "=", "{", "}", "[", "]", ":", ";", "'", "<", ">", ",", ".", "?", "/", "/\/", "|"];
        specialArray.forEach(el => {
            while (value.indexOf(el) !== -1) {
                value = value.replace(el, "");
            }
        });

        return value;
    };
    var removeDashUnderscore = function (value) {
        var dashUnderScoreArray = ["-", "_"];
        dashUnderScoreArray.forEach(el => {
            while (value.indexOf(el) !== -1) {
                value = value.replace(el, "");
            }
        });

        return value;
    };
    var removeSpaces = function (value) {
        var space = ' ';

        while (value.indexOf(space) !== -1) {
            value = value.replace(space, "");
        }

        return value;
    };
});