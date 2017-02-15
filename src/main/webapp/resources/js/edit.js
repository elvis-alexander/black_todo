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
    today.setDate(today.getDate() + 1);
    var mm = today.getMonth() + 1;
    return today.getFullYear() + "-" + mm + "-" + today.getDate();
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

function filled_input_fields() {
    var allFilledIn = true;
    $('table > tbody  > tr').each(function() {
        var cols = this.cells;
        var start_date = cols[2];
        var end_date = cols[3];


        $(start_date).find('input').each(function() {
            if(this.type == 'date' && this.value == '')
                allFilledIn = false;
        });
        $(end_date).find('input').each(function() {
            if(this.type == 'date' && this.value == '')
                allFilledIn = false;
        });
    });

    return allFilledIn;

}

function save_todolist(id) {

    if(!filled_input_fields()) {
        $('#err_log').empty();
        $('#err_log').append('Please fill in all date fields');
        return;
    }

    console.log('saving todo list');
    var todoList = {};
    $('#private_input').find('input').each(function() {
        todoList["privateTodo"] = this.checked ? true : false;
    });

    $('#name_field').find('input').each(function() {
        todoList['name'] = this.value;
    });
    todoList['id'] = id;
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

    var request = $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: '/edit/',
        data: JSON.stringify(todoList),
        success: function (msg) {
            console.log('success edited');
            window.location.href = '/successedit'
        },
        error: function (errormessage) {
            console.log('ajax failure' + errormessage);
        }
    });
    console.log('todoList ' + JSON.stringify(todoList));
}

function sort_table(column){
    console.log("sort_table");
}