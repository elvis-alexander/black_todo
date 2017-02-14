/**
 * Created by Chaeyoung on 2/14/17.
 */

/* current selected row */

var current_row = null;

$(document).ready(function() {
    on_selected_row(null);

    var arr = [];
    $('tbody > tr > td > input').each(function() {
        arr.push(this);
    });

    for(var i = 0; i < arr.length; i+=2) {
        var hiddenDate = arr[i];
        var dateField = arr[i+1];
        var formattedDate = formatDate(hiddenDate.value);
        dateField.value = formattedDate;
    }

});


function formatDate(d) {
    var today = new Date(d);
    today++;
    var dd = today.getDate() + 1;
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();
    if(dd<10){
        dd='0'+dd;
    }
    if(mm<10){
        mm='0'+mm;
    }
    var today = yyyy+'-'+mm+'-'+dd;
    return today;
}


// resets all unslected colors
function reset_colors() {
    $('tbody > tr').each(function() {
        $(this).css('background-color', '#333333');
        $(this).css('color', '#ffffff');
    });
}

/* handler for selected row */
function on_selected_row(row) {
    console.log('selected');
    current_row = row;
    reset_colors();
    $(current_row).css('background-color', '#616161');
    $(current_row).css('color', '#ffffff');
}