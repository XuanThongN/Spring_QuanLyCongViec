$.fn.serializeObject = function () {
    var i, len, item, object = {}, array = $(this).serializeArray() || [];
    len = array.length;
    for (i = 0; i < len; i += 1) {
        item = array[i];
        object[item.name] = item.value;
    }

    return object;
}
$(document).ready(function () {
    // Sử dụng AJAX để tải dữ liệu từ API khi trang đã tải hoàn toàn
    $.ajax({
        url: "/meeting/list",// Đường dẫn đến API của bạn
        type: "GET",
        dataType: "json", // Đặt kiểu dữ liệu trả về
        success: function (data) {
            // Chuyển đổi dữ liệu từ JSON thành mảng sự kiện
            var events = [];
            $.each(data, function (index, meeting) {
                events.push({
                    id: meeting.id,
                    content: meeting.content,
                    start: meeting.startTime, // Đảm bảo định dạng đúng
                    end: meeting.endTime, // Đảm bảo định dạng đúng
                    title: meeting.title,
                    userIds: meeting.userIds,
                });
            });
            // Khởi tạo FullCalendar với các tùy chỉnh
            //------------- Hiện Thị giờ và tiêu đề trên calendar-----------------//
            var calendar = new FullCalendar.Calendar(document.getElementById('calendar'), {
                initialView: 'dayGridMonth', // Chọn chế độ xem mặc định
                events: events, // Sự kiện đã chuyển đổi
                slotDuration: '00:15:00', // Khoảng thời gian mỗi khe thời gian
                slotLabelFormat: {
                    hour: '2-digit',
                    minute: '2-digit',
                    omitZeroMinute: false,
                    meridiem: 'short'
                },
                backgroundColor: '#fd0707',
                color: 'yellow',   // an option!
                eventColor: '#378006',
                eventBackgroundColor: '#A780A8FF',
                eventContent: function (info) {
                    // Tạo một phần tử DOM chứa nội dung sự kiện
                    var eventContainer = document.createElement('div');

                    // Tạo tiêu đề sự kiện
                    var titleElement = document.createElement('div');
                    titleElement.innerText = info.event.title;

                    // Tạo thời gian bắt đầu và kết thúc
                    var timeElement = document.createElement('div');
                    timeElement.innerText = formatTime(info.event.start) + ' - ' + formatTime(info.event.end);

                    // Tạo id input
                    jQuery('', {
                        class: 'form-control',
                        title: '',
                        "data-meeting-id": info.event.id
                    }).appendTo(eventContainer);
                    // Thêm tiêu đề và thời gian vào phần tử DOM chứa sự kiện
                    eventContainer.appendChild(titleElement);
                    eventContainer.appendChild(timeElement);


                    return {domNodes: [eventContainer]};
                },
                eventTimeFormat: { // Định dạng thời gian sự kiện
                    hour: '2-digit',
                    minute: '2-digit',
                    second: '2-digit'
                },


                // ----------------- Hiện thị Meeting Deltai ---------------//
                eventClick: function (info) {
                    let form = $("#meetingModal")
                    let id = form.find("")
                    $.ajax({
                        url: "/meeting/Details/" + info.event.id,// Đường dẫn đến API của bạn
                        type: "GET",
                        dataType: "html", // Đặt kiểu dữ liệu trả về
                        success: function (data) {
                            form.find('.modal-content').html(data)
                        }
                    })
                    $('#meetingModal').modal('show'); // Hiển thị modal

                },

                // ----------Sự Kiện Click lên lịch Hiện thị Modal Thêm Meeting---------------//

                dateClick: function (info) {
                    // Khi ngày trên lịch được click, hiển thị modal
                    $('#event-modal').modal('show');

                    // Lấy ngày được click
                    var selectedDate = moment(info.date).format('YYYY-MM-DD HH:mm');

                    // Đặt giá trị của input thời gian bắt đầu và kết thúc
                    $('#event-start-time').val(selectedDate);
                    $('#event-end-time').val(selectedDate);

                    // Các xử lý khác bạn muốn thực hiện khi mở modal
                }

            });

            // ----------     Chức năng Thêm ---------------  //


            $("#btn-save-event").click(function (e) {
                e.preventDefault();

                // Kiểm tra xem form có hợp lệ không
                if ($("#form-event")[0].checkValidity()) {
                    var formData = $("#form-event").serializeObject();; // Lấy dữ liệu của form
                    formData.endTime = new Date(formData.endTime)
                    formData.startTime = new Date(formData.startTime)
                    formData.users = [...new Set($("#form-event").find('select[data-toggle="select2"]').val())];
                    $.ajax({
                        type: "POST",
                        url: "/meeting/create",
                        contentType: "application/json",
                        data: JSON.stringify(formData),
                        success: function (response) {
                            var eventData = $("#form-event").serializeObject();
                            eventData.id = response.id
                            eventData.start = new Date(eventData.startTime);
                            eventData.end = new Date(eventData.endTime);
                            calendar.addEvent(eventData);

                            // Đóng modal
                            $('#event-modal').modal('hide');

                            // Hiển thị thông báo thành công bằng SweetAlert2
                            Swal.fire({
                                icon: 'success',
                                title: 'Thành công!',
                                text: 'Cuộc họp đã được tạo thành công',
                            })
                                .then((result) => {
                                // if (result.isConfirmed) {
                                //     location.reload();
                                // }
                            });
                        },
                        error: function (xhr, status, error) {
                            // Xử lý lỗi nếu có
                            Swal.fire({
                                icon: 'error',
                                title: 'Lỗi!',
                                text: 'Thêm cuộc họp thất bại',
                            });
                        }
                    });
                } else {
                    // Nếu form không hợp lệ, hiển thị thông báo lỗi cho người dùng
                    $("#form-event")[0].classList.add('was-validated');
                }
            });


            // ----------Show Modal chỉnh Sửa Meeting---------------//


            $('#meetingModal').on('click', '#editMeetingButton', function (info) {
                var meetingId = $(this).data('meetingid');
                let form = $("#editMeeting")
                $.ajax({
                    url: "/meeting/Update/" + meetingId,// Đường dẫn đến API của bạn
                    type: "GET",
                    dataType: "html", // Đặt kiểu dữ liệu trả về
                    success: function (data) {
                        form.find('.modal-content').html(data)
                    }
                })
                $('#meetingModal').modal('hide');
                $('#editMeeting').modal('show'); // Hiển thị modal
            });

            // ----------  Chức Năng Sửa---------------//

            $('#editMeeting').on('click', '#saveEditMeetingButton', function (e) {
                e.preventDefault();
                let form = $("#editMeeting form");

                // Kiểm tra xem form có hợp lệ không
                if (form[0].checkValidity()) {
                    let meeting = form.serializeObject();
                    meeting.title = form.find("#editMeetingTitle").val()
                    meeting.startTime = form.find("#editMeetingStartTime").val()
                    meeting.endTime = form.find("#editMeetingEndTime").val()
                    meeting.content = form.find("#editMeetingContent").val()
                    meeting.users = [...new Set(form.find('select[data-toggle="select2"]').val())];

                    // Gửi yêu cầu cập nhật thông tin cuộc họp bằng Ajax
                    $.ajax({
                        type: "POST",
                        url: "/meeting/update",
                        contentType: "application/json",
                        data: JSON.stringify(meeting),
                        success: function (response) {
                            // Xử lý kết quả thành công
                            Swal.fire({
                                icon: 'success',
                                title: 'Thành công!',
                                text: 'Cập nhật cuộc họp thành công',
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    location.reload();
                                    $('#editMeeting').modal('hide');
                                }
                            });
                        },
                        error: function (xhr, status, error) {
                            // Xử lý lỗi
                            Swal.fire({
                                icon: 'error',
                                title: 'Lỗi!',
                                text: 'Cập nhật Không Thành Công',
                            });
                        }
                    });
                } else {
                    // Nếu form không hợp lệ, hiển thị thông báo lỗi cho người dùng
                    form[0].classList.add('was-validated');
                }
            });



            // ----------  Chức năng xóa Meeting --------------//

            $('#meetingModal').on('click', '#deleteMeetingButton', function (info) {
                Swal.fire({
                    title: 'Xác nhận xóa',
                    text: 'Bạn muốn xóa cuộc họp này không?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonText: 'Xóa',
                    cancelButtonText: 'Hủy',
                }).then((result) => {
                    if (result.isConfirmed) {
                        var meetingId = $(this).data('meetingid'); // Lấy meetingId từ data-meetingId
                        // Gửi yêu cầu xóa cuộc họp thông qua Ajax
                        $.ajax({
                            url: '/meeting/delete/' + meetingId,
                            type: 'POST',
                            success: function (data) {
                                // Kiểm tra xem phản hồi từ máy chủ có chứa thông báo thành công không
                                if (data === "Xóa cuộc họp thành công") {
                                    // Xóa cuộc họp thành công
                                    Swal.fire({
                                        icon: 'success',
                                        title: 'Thành công!',
                                        text: 'Cuộc họp đã được xóa thành công',
                                    });
                                    // Đóng modal xóa
                                    $('#meetingModal').modal('hide');

                                    // Tìm và xóa sự kiện có ID tương ứng trên lịch
                                    var eventToRemove = calendar.getEventById(meetingId);
                                    if (eventToRemove) {
                                        eventToRemove.remove();
                                    }
                                    location.reload();
                                } else {
                                    // Xóa cuộc họp thất bại (có lỗi từ máy chủ)
                                    Swal.fire({
                                        icon: 'error',
                                        title: 'Lỗi!',
                                        text: 'Không thể xóa cuộc họp này',
                                    });
                                }
                            },
                            error: function (error) {
                                // Xóa cuộc họp thất bại (lỗi AJAX)
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Lỗi!',
                                    text: 'Không thể xóa cuộc họp này',
                                });
                            }
                        });
                    }
                });
            });

            // ----------Render ra CALENDAR--------------

            calendar.render();

        },
        error: function (error) {
            console.error("Lỗi khi tải dữ liệu từ API: " + error);
        }
    });
});

function formatTime(date) {
    if (date) {
        var hours = date.getHours();
        var minutes = date.getMinutes();
        var seconds = date.getSeconds();
        return hours.toString().padStart(2, '0') + ":" + minutes.toString().padStart(2, '0') + ":" + seconds.toString().padStart(2, '0');
    } else {
        return ""; // Trả về một giá trị mặc định hoặc rỗng nếu biến date không tồn tại
    }
}



