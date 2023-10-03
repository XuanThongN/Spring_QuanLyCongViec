(function ($) {
    var _$modal = $('#add-new-task-modal'),
        _$modal_view = $('#task-detail-modal'),
        _$modal_update = $('#task-update-modal'),
        _$form_update = _$modal_update.find('#task-form'),
        _$form = _$modal.find('form')
    
//Hàm load ra bảng công việc
    $.renderBoard = ()=>{
        $.ajax({
            url: '/Task/LoadBoard',
            type: 'GET',
            dataType: 'html',
            success: function (content) {
                $('#load-board').html(content)
            },
            error: function (e) {
            }
        });
    }
    $.renderBoard()
    _$form.find('button[type="submit"]:not("#todo-btn-submit")').click(function (e) {
        e.preventDefault();

        // if (!_$form.valid()) {
        //     return;
        // }

        let task = _$form.serializeFormToObject();
        task.users = [...new Set(_$form.find('.select2[data-toggle="select2"]').val())]
        task.dueDate = _$form.find('#birthdatepicker').datepicker('getDate')
        task.subtasks = jQuery.TodoApp.$todoData || []

        $.ajax({
            url: '/Task/Create',
            type: 'POST',
            data: JSON.stringify(task),
            contentType: "application/json",
            async: true,
            success: function (content) {
                _$modal.modal('hide');
                _$form[0].reset();
                _$modal.find("select.select2").val('')
                $.renderBoard()
                $.NotificationApp.send("Thành công", "Thêm thành công", "Position", "green", "Hello")
            },
            error: function (e) {
                console.log(e)
            }
        });

    });

    //Click vào thẻ để hiển thị form cập nhật
    $(document).on('click', '.tasks .dropdown-menu-task-action a:first-child', function (e) {
        e.preventDefault()
        var taskId = $(this).closest("div[data-task-id]").data('task-id');

        $.ajax({
            url: '/Task/Update/' + taskId,
            type: 'GET',
            dataType: 'html',
            success: function (content) {
                _$modal_update.find('.modal-content').html(content)
                _$modal_update.modal('show')
            },
            error: function (e) {
            }
        });
    });

    $(document).on('click', ".task-list-items h5 a", function (e) {
        e.preventDefault()
        let id = $(this).closest("div[data-task-id]").data("task-id")
        $.ajax({
            url: '/Task/Details/' + id,
            type: 'GET',
            contentType: "application/json",
            dataType: "html",
            async: true,
            success: function (content) {
                _$modal_view.modal('show')
                _$modal_view.find(".modal-content").html(content)
            },
            error: function (e) {
                console.log(e)
            }
        });
    })

    _$modal.on('shown.bs.modal', () => {
        _$modal.find('input:not([type=hidden]):first').focus();
    }).on('hidden.bs.modal', () => {
        //Reset hết dữ liệu ở các trường
        _$form.trigger('reset');
        _$form.find('select.select2').trigger("reset").trigger("change")
        jQuery.TodoApp.$todoData = []
        jQuery.TodoApp.generate()
    });

    //Reset modal view khi tắt để tránh lưu lại dữ liệu cũ
    _$modal_view.on('shown.bs.modal', function () {

    }).on('hidden.bs.modal', () => {
        $(this).html('')
        $(this).find('#todo-list').empty()
    });

})(jQuery);