package com.conference.platform.backoffice.ui.controller;

import com.conference.platform.backoffice.ui.httpclient.dto.CreateRoomRequestDto;
import com.conference.platform.backoffice.ui.mapper.RoomMapper;
import com.conference.platform.backoffice.ui.service.RoomService;
import com.conference.platform.backoffice.ui.view.RoomViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

  private final RoomService roomService;

  @GetMapping("/new")
  public String newRoomForm(Model model) {
    model.addAttribute("room", new RoomViewModel());
    return "new-room";
  }

  @GetMapping("/{roomCode}")
  public String editRoom(
      @PathVariable String roomCode,
      Model model) {

    if(!model.containsAttribute("room")) {
      var roomResponseDto = roomService.getRoom(roomCode);
      var foundRoomViewModel = RoomMapper.toViewModel(roomResponseDto);
      model.addAttribute("room", foundRoomViewModel);
    }
    return "edit-room";
  }

  @PostMapping("/{roomCode}")
  public String updateRoom(
      @PathVariable String roomCode,
      @ModelAttribute("room") RoomViewModel room,
      RedirectAttributes redirectAttributes) {

    var roomUpdateRequestDto = RoomMapper.toUpdateDto(room);
    var roomDtoForUpdate = roomService.updateRoom(roomUpdateRequestDto, roomCode);
    var updatedRoom = RoomMapper.toViewModel(roomDtoForUpdate);
    redirectAttributes.addFlashAttribute("room", updatedRoom);
    return "redirect:/rooms/" + room.getRoomCode();
  }

  @PostMapping
  public String createRoom(@ModelAttribute RoomViewModel room, RedirectAttributes redirectAttributes) {
    CreateRoomRequestDto roomRequestDto = RoomMapper.toCreateDto(room);
    var createdRoomDto = roomService.createRoom(roomRequestDto);
    var createdRoom = RoomMapper.toViewModel(createdRoomDto);
    redirectAttributes.addFlashAttribute("room", createdRoom);
    return "redirect:/rooms/" + createdRoomDto.getRoomCode();
  }

}
