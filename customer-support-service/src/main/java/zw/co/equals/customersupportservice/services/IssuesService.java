package zw.co.equals.customersupportservice.services;

import zw.co.equals.customersupportservice.data.IssueTrackResponse;
import zw.co.equals.customersupportservice.dto.IssueLogDto;
import zw.co.equals.customersupportservice.models.IssueLog;
import zw.co.equals.customersupportservice.models.IssueTicket;

public interface IssuesService {
    Long logIssue(IssueLogDto issueLogDto);
    IssueTrackResponse trackIssue(Long ticketId);
}
