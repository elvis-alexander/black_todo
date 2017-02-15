
$(document).ready(function() {


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


// Because of the time zone, the date is always a day later than the given input date.
// Therefore, just adding one more day from current for quick patch.
function formatDate(d) {
    var today = new Date(d);
    today.setDate(today.getDate() + 1);
    var mm = today.getMonth() + 1;
    return today.getFullYear() + "-" + mm + "-" + today.getDate();
}