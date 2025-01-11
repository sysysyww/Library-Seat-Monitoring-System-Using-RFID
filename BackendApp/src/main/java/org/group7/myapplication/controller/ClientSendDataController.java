package org.group7.myapplication.controller;

import org.group7.myapplication.entity.LibraryInfo;
import org.group7.myapplication.service.LibraryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ClientSendDataController {
    @Autowired
    private LibraryInfoService libraryInfoService;

    @PostMapping("/data")
    public ResponseEntity<String> receiveData(@RequestBody LibraryInfo info) {
        System.out.println(info);
        LibraryInfo savedInfo = libraryInfoService.saveLibraryInfo(info);
        return ResponseEntity.ok("Data saved: " + savedInfo.toString());
    }
}
