/**
 * Created by elvis on 1/29/17.
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
    var dd = today.getDate();
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
        $(this).css('background-color', 'white');
    });
}

/* handler for selected row */
function on_selected_row(row) {
    console.log('selected');
    if(row == null) {
        current_row = $('table > tbody > tr');
        $(current_row).click(function() {
            on_selected_row(this)
        });
    } else {
        current_row = row;
    }
    reset_colors();
    $(current_row).css('background-color', '#616161 ');
    // var cols = row.cells;s
    // var first_name = cols[0];
}

/* new row added */
function on_add_row(fn, ln) {

    console.log('on_add_row' + fn + ln);
    $("tbody")
        .append($('<tr>')
            .attr('onclick', 'on_selected_row(this)')
            .append($('<td>')
                .text(fn)
                .attr('align', 'center')
                .attr('contenteditable', 'true')
            )
            .append($('<td>')
                .text(ln)
                .attr('align', 'center')
                .attr('contenteditable', 'true')
            )
            .append($('<td>')
                .append($('<input>')
                    .attr('type', 'date')
                )
            )
            .append($('<td>')
                .append($('<input>')
                    .attr('type', 'date')
                )
            )
            .append($('<td>')
                .append($('<div>')
                    .attr('class', 'switch')
                    .append($('<label>')
                        .append("Off")
                        .append($('<input>')
                            .attr('type', 'checkbox')
                        )
                        .append($('<span>')
                            .attr('class', 'lever')
                        )
                        .append('On')
                    )
                )
            )
        );
}

/* handler for deleted row */
function on_del_row() {
    current_row.remove();
}

/* handler to move row up */
function move_up() {
    console.log('move_up');
    jQuery(current_row).prev().before(jQuery(current_row));
}

/* handler to move row down */
function move_down() {
    console.log('move_down')
    jQuery(current_row).next().after(jQuery(current_row));
}



function save_todolist() {
    console.log('saving todo list');
    var todoList = {};
    $('#private_input').find('input').each(function() {
        todoList["privateTodo"] = this.checked ? true : false;
    });

    $('#name_field').find('input').each(function() {
        todoList['name'] = this.value;
    });

    todoList['rows'] = [];
    $('table > tbody  > tr').each(function() {
        var current_row = {};
        var cols = this.cells;
        current_row['category'] = cols[0].innerHTML;
        current_row['description'] = cols[1].innerHTML;
        var start_date = cols[2];
        var end_date = cols[3];
        var checkbox = cols[4];
        $(start_date).find('input').each(function() {
            current_row['start'] = this.value;
        });

        $(end_date).find('input').each(function() {
            current_row['end'] = this.value;
        });

        $(checkbox).find('input').each(function() {
            current_row['completed'] = this.checked ? true : false;
        });

        todoList['rows'].push(current_row);
    });

    $('#id_field').each(function () {
        todoList['id'] = this.value;
    });

    var request = $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: '/edit',
        data: JSON.stringify(todoList),
        success: function (msg) {
            console.log('success edited');
            // window.location.href = '/success'
        },
        error: function (errormessage) {
            console.log('ajax failure' + errormessage);
        }
    });
    console.log('todoList ' + JSON.stringify(todoList));
}









