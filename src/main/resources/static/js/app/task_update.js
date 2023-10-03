(function ($) {
    var _$modal = $('#add-new-task-modal'),
        _$modal_update = $('#task-update-modal'),
        _$form_update = _$modal_update.find('#task-update-form')

    //Khởi tạo todo và select2
    jQuery.TodoAppUpdate.generate()
    _$modal_update.find('select.select2').select2()

    //Submit form cập nhật
    // _$form_update.on('click','button[type="submit"][form="task-form"]',function (e) {
    $(document).on('submit', _$form_update, function (e) {
        e.preventDefault();
        // if (!_$form.valid()) {
        //     return;
        // }

        let task = _$form_update.serializeFormToObject();
        task.users = [...new Set(_$form_update.find('.select2[data-toggle="select2"]').val())]
        task.dueDate = _$form_update.find('#birthdatepicker').datepicker('getDate')
        task.subtasks = jQuery.TodoAppUpdate.$todoData || []

        $.ajax({
            url: '/Task/Update',
            type: 'POST',
            data: JSON.stringify(task),
            contentType: "application/json",
            async: true,
            success: function (content) {
                _$modal_update.modal('hide');
                _$form_update[0].reset();
                _$form_update.find(".statesSelect2").val('')
                $.renderBoard()
                $.NotificationApp.send("Thành công", "Cập nhật thành công", "Position", "green", "Hello")
            },
            error: function (e) {
                console.log(e)
            }
        });
    })

    _$modal_update.on('shown.bs.modal', () => {
        _$modal_update.find('input:not([type=hidden]):first').focus();
    }).on('hidden.bs.modal', () => {
        //Reset hết dữ liệu ở các trường
        _$modal_update.trigger('reset');
        _$modal_update.find('select.select2').trigger("reset").trigger("change")
        jQuery.TodoAppUpdate.$todoData = []
        jQuery.TodoAppUpdate.generate()
    });

    //Reset modal view khi tắt để tránh lưu lại dữ liệu cũ
    _$modal_update.on('shown.bs.modal', function () {

    }).on('hidden.bs.modal', () => {
        $(this).html('')
        $(this).find('#todo-list').empty()
    });
})(jQuery);