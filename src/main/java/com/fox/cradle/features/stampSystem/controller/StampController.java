package com.fox.cradle.features.stampSystem.controller;

import com.fox.cradle.features.stampSystem.model.stamp.StampFieldResponse;
import com.fox.cradle.features.stampSystem.model.stampcard.StampCardResponse;
import com.fox.cradle.features.stampSystem.service.stamp.StampService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stamp")
@RequiredArgsConstructor
public class StampController
{
    private final StampService stampService;

    @PostMapping("/stampThisCard")
    public ResponseEntity<Boolean> attemptToStamp(@RequestBody StampCardResponse stampCardResponse)
    {
        boolean isStampAble = stampService.stampThisCard(stampCardResponse);
        return ResponseEntity.ok(isStampAble);
    }
}
