package com.xuanthongn.spring_quanlycongviec.controllers;

import com.xuanthongn.spring_quanlycongviec.dto.meeting.MeetingDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.TaskDto;
import com.xuanthongn.spring_quanlycongviec.entities.Meeting;
import com.xuanthongn.spring_quanlycongviec.entities.Task;
import com.xuanthongn.spring_quanlycongviec.entities.User;
import com.xuanthongn.spring_quanlycongviec.repository.MeetingRepository;
import com.xuanthongn.spring_quanlycongviec.repository.UserRepository;
import com.xuanthongn.spring_quanlycongviec.services.MeetingService;
import com.xuanthongn.spring_quanlycongviec.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/meeting")
public class MeetingController {


    @Autowired
    private MeetingService meetingService;
    @Autowired
    private UserService userService;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @RequestMapping("")
    public String Index(Model model) {
        List<Meeting> meetings = meetingService.findAll();
        List<User> users = userService.findAll();
        model.addAttribute("meeting", meetings);
        model.addAttribute("users", users);
        return "meeting/index";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Meeting> findAll() {
        List<Meeting> meetings = meetingService.findAll();
        return meetings;
    }

    @GetMapping("/Details/{id}")
    @ResponseBody
    public ModelAndView Details(@PathVariable Long id) {
        try {
            Meeting meeting = meetingService.findById(id);
            MeetingDto meetingDto = mapper.map(meeting, MeetingDto.class);
            meetingDto.setUserNames(meeting.getAttendees().stream().map(e -> e.getName()).collect(Collectors.toList()));
            ModelAndView modelAndView = new ModelAndView("meeting/meeting_detail");
            modelAndView.addObject("meeting", meetingDto);
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/Update/{id}")
    @ResponseBody
    public ModelAndView Update(@PathVariable Long id) {
        try {
            Meeting meeting = meetingService.findById(id);
            ModelAndView modelAndView = new ModelAndView("meeting/meeting_update");
            modelAndView.addObject("meeting", meeting);
            modelAndView.addObject("users", userService.findAll());
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteMeeting(@PathVariable Long id) {
        try {
            meetingService.deleteById(id);
            return ResponseEntity.ok("Xóa cuộc họp thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Xóa cuộc họp thất bại: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<MeetingDto> createMeeting(@RequestBody @Valid MeetingDto input) {
        try {
            // Định dạng thời gian từ chuỗi và đặt thời gian bắt đầu và kết thúc
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
//            LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
//            LocalDateTime endDateTime = LocalDateTime.parse(endTime, formatter);

            // Kiểm tra xem thời gian kết thúc có nhỏ hơn thời gian bắt đầu không
            if (input.getEndTime().isBefore(input.getStartTime())) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            // Tạo một đối tượng Meeting từ dữ liệu đầu vào
//            Meeting meeting = new Meeting();
//            meeting.setTitle(title);
//            meeting.setContent(content);
//            meeting.setStartTime(startDateTime);
//            meeting.setEndTime(endDateTime);
            Meeting newMeeting = mapper.map(input, Meeting.class);
            // Lấy danh sách người dùng từ các userIds và đặt cho cuộc họp
            Set<User> attendees = new HashSet<>();
            for (Integer userId : input.getUsers()) {
                User user = userService.findById(userId);
                if (user != null) {
                    attendees.add(user);
                }
            }
            newMeeting.setAttendees(attendees);
            // Lưu cuộc họp mới vào cơ sở dữ liệu thông qua service
            Meeting savedEntity = meetingService.save(newMeeting);
            return new ResponseEntity<MeetingDto>(mapper.map(savedEntity,MeetingDto.class), HttpStatus.OK);
        } catch (DateTimeParseException e) {
            // Xử lý lỗi định dạng ngày giờ không hợp lệ
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<String> updateMeeting(@RequestBody MeetingDto input) {
        try {
            // Tìm cuộc họp theo ID
            Meeting meeting = meetingService.findById(input.getId());
            meeting = mapper.map(input, Meeting.class);
            Collection<User> users = userRepository.findAllById(input.getUsers().stream().map(Long::valueOf).collect(Collectors.toList()));
            meeting.setAttendees(users);
            Meeting savedEntity = meetingRepository.save(meeting);
            return ResponseEntity.ok("Cập nhật cuộc họp thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cập nhật cuộc họp thất bại: " + e.getMessage());
        }
    }


}
