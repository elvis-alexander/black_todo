
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


function formatDate(d) {
    var today = new Date(d);
    var mm = today.getMonth() + 1;
    return today.getFullYear() + "-" + mm + "-" + today.getDate();
    /*
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
     */
}