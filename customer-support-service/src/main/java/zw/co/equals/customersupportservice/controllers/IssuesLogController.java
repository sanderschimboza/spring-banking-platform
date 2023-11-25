package zw.co.equals.customersupportservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.equals.customersupportservice.data.IssueTrackResponse;
import zw.co.equals.customersupportservice.dto.IssueLogDto;
import zw.co.equals.customersupportservice.services.IssuesService;

@RestController
@RequestMapping("/support")
@RequiredArgsConstructor
public class IssuesLogController {

    private final IssuesService issuesService;

    @PostMapping("/log")
    public ResponseEntity<String> logIssue(@RequestBody IssueLogDto issueLogDto) {
        Long ticketId = issuesService.logIssue(issueLogDto);
        return new ResponseEntity<>("Issue logged successfully. Ticket track number => " +
                ticketId, HttpStatus.CREATED);
    }

    @GetMapping("/track/{ticketId}")
    public ResponseEntity<IssueTrackResponse> trackIssue(@PathVariable Long ticketId) {
        IssueTrackResponse issueTrackResponse = issuesService.trackIssue(ticketId);
        return new ResponseEntity<>(issueTrackResponse, HttpStatus.OK);
    }
}
