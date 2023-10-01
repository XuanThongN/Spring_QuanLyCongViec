(function ($) {
    var _$modal = $('#add-new-task-modal'),
     _$modal_view = $('#task-detail-modal'),
        _$form = _$modal.find('form')
    //init
    // $(".statesSelect2").select2({
    //     dropdownParent: $('#CuocHopCreateModal'),
    //     closeOnSelect: false,
    //     minimumResultsForSearch: 20,
    //     placeholder: {
    //         text: 'Chọn thành viên tham gia'
    //     }
    // });
    // _$modal.find('.select2 ').addClass("w-100")
    // // Lắng nghe sự kiện select2:select của Select2
    // $('.statesSelect2').on('select2:opening', function (e) {
    //     // Lấy giá trị của lựa chọn được chọn
    //     var selectedValue = e.currentTarget.value;
    //
    //     // Nếu không có lựa chọn nào được chọn, thiết lập giá trị mặc định cho trường select
    //     if (!selectedValue) {
    //         $('.statesSelect2').val(userId);
    //         $('.statesSelect2').trigger('change');
    //     }
    // });

    _$form.find('button[type="submit"]').click(function (e) {
        e.preventDefault();

        // if (!_$form.valid()) {
        //     return;
        // }

        var task = _$form.serializeFormToObject();
        task.users = [...new Set(_$form.find('.select2[data-toggle="select2"]').val())]
        task.dueDate = _$form.find('#birthdatepicker').datepicker('getDate')

        $.ajax({
            url: '/Task/Create',
            type: 'POST',
            data: JSON.stringify(task),
            contentType: "application/json",
            async: true,
            success: function (content) {
                _$modal.modal('hide');
                _$form[0].reset();
                _$modal.find(".statesSelect2").val('')
                $.NotificationApp.send("Thành công", "Thêm thành công", "Position", "green", "Hello")
            },
            error: function (e) {
                console.log(e)
            }
        });

    });

    $(document).on('click', '.edit-cuocHop', function (e) {
        var cuocHopId = $(this).attr('data-cuocHop-id');

        abp.ajax({
            url: abp.appPath + 'CuocHop/EditModal?Id=' + cuocHopId,
            type: 'POST',
            dataType: 'html',
            success: function (content) {
                $('#CuocHopEditModal div.modal-content').html(content);
            },
            error: function (e) {
            }
        });
    });
    $(document).on('click', '.view-cuocHop', function (e) {
        var cuocHopId = $(this).attr('data-cuocHop-id');

        abp.ajax({
            url: abp.appPath + 'CuocHop/ViewModal?Id=' + cuocHopId,
            type: 'POST',
            dataType: 'html',
            success: function (content) {
                $('#CuocHopViewModal div.modal-content').html(content);
            },
            error: function (e) {
            }
        });
    });

    $(document).on('click',".task-list-items a[data-bs-target='#task-detail-modal']",function (e){
        e.preventDefault()
        let id = $(this).closest("div[data-task-id]").data("task-id")
        $.ajax({
            url: '/Task/Details/'+id,
            type: 'GET',
            contentType: "application/json",
            dataType: "html",
            async: true,
            success: function (content) {
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
        _$form.clearForm();
    });
})(jQuery);