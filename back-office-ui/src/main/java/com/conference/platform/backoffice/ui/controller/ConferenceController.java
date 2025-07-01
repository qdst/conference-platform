package com.conference.platform.backoffice.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/conferences")
public class ConferenceController {

//
//
//  @GetMapping("/new")
//  public String newRoomForm(Model model) {
//    model.addAttribute("room", new RoomViewModel());
//    return "room";
//  }
//
//  @GetMapping("/{roomCode}")
//  public String editRoom(
//      @PathVariable String roomCode,
//      @ModelAttribute("room") RoomViewModel room,
//      Model model) {
//
//    if(!model.containsAttribute("room")) {
//      model.addAttribute("room", room);
//    } else {
//      var roomResponseDto = roomService.getRoom(roomCode);
//      var foundRoomViewModel = RoomMapper.toViewModel(roomResponseDto);
//      model.addAttribute("room", foundRoomViewModel);
//    }
//    return "edit-room";
//  }
//
//  @PutMapping("/{roomCode}")
//  public String updateRoom(@ModelAttribute("room") RoomViewModel room, Model model) {
//    model.addAttribute("room", room);
//    return "edit-room";
//  }
//
//  @PostMapping
//  public String createRoom(@ModelAttribute RoomViewModel room, RedirectAttributes redirectAttrs) {
//    CreateRoomRequestDto roomRequestDto = RoomMapper.toDto(room);
//    var createdRoomDto = roomService.createRoom(roomRequestDto);
//    var createdRoom = RoomMapper.toViewModel(createdRoomDto);
//    redirectAttrs.addFlashAttribute("room", createdRoom);
//    return "redirect:/rooms/" + createdRoomDto.getRoomCode();
//  }



}
