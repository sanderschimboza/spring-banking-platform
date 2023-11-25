package zw.co.equals.customersupportservice.services;

import zw.co.equals.customersupportservice.dto.IssueResponseDto;
import zw.co.equals.customersupportservice.models.IssueLog;
import zw.co.equals.customersupportservice.models.IssueTicket;

import java.util.List;

public interface AdminIssuesService {
    IssueLog findIssue(Long issueId);
    List<IssueLog> findAllIssues();

    List<IssueTicket> findAllTickets();
    void resolveIssue(Long ticketId, IssueResponseDto issueResponseDto);
}
