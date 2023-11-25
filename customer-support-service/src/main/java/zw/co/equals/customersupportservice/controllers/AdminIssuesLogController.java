package zw.co.equals.customersupportservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.equals.customersupportservice.dto.IssueResponseDto;
import zw.co.equals.customersupportservice.models.IssueLog;
import zw.co.equals.customersupportservice.models.IssueTicket;
import zw.co.equals.customersupportservice.services.AdminIssuesService;

import java.util.List;

@RestController
@RequestMapping("/supportAdmin")
@RequiredArgsConstructor
public class AdminIssuesLogController {

    private final AdminIssuesService adminIssuesService;

    @PostMapping("/resolve/{ticketId}")
    public ResponseEntity<String> resolveIssue(@PathVariable Long ticketId,
                                               @RequestBody IssueResponseDto issueResponseDto) {
        adminIssuesService.resolveIssue(ticketId, issueResponseDto);
        return new ResponseEntity<>("Issue resolved => " + ticketId, HttpStatus.OK);
    }

    @GetMapping("/findIssues")
    public ResponseEntity<List<IssueLog>> findAll() {
        return new ResponseEntity<>(adminIssuesService.findAllIssues(), HttpStatus.OK);
    }

    @GetMapping("/findTickets")
    public ResponseEntity<List<IssueTicket>> findAllTickets() {
        return new ResponseEntity<>(adminIssuesService.findAllTickets(), HttpStatus.OK);
    }
}
