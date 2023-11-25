package zw.co.equals.customersupportservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zw.co.equals.customersupportservice.dto.IssueResponseDto;
import zw.co.equals.customersupportservice.models.IssueLog;
import zw.co.equals.customersupportservice.models.IssueTicket;
import zw.co.equals.customersupportservice.repositories.IssuesLogRepository;
import zw.co.equals.customersupportservice.repositories.IssuesTicketRepository;
import zw.co.equals.customersupportservice.services.AdminIssuesService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminIssuesServiceImpl implements AdminIssuesService {

    private final IssuesLogRepository issuesLogRepository;
    private final IssuesTicketRepository issuesTicketRepository;

    @Override
    public IssueLog findIssue(Long issueId) {
        return issuesLogRepository.findById(issueId)
                .orElseThrow();
    }

    @Override
    public List<IssueLog> findAllIssues() {
        return issuesLogRepository.findAll();
    }

    @Override
    public List<IssueTicket> findAllTickets() {
        return issuesTicketRepository.findAll();
    }

    @Override
    public void resolveIssue(Long ticketId, IssueResponseDto issueResponseDto) {

        IssueTicket issueTicket = issuesTicketRepository.findById(ticketId)
                .orElseThrow();

        IssueLog issueLog = issuesLogRepository.findById(issueTicket.getIssueLog()
                        .getIssueId()).orElseThrow();

        issueLog.setIssueStatus(issueResponseDto.getIssueStatus());
        issueTicket.setNarrative(issueResponseDto.getNarrative());
        issuesLogRepository.save(issueLog);
        issuesTicketRepository.save(issueTicket);
    }
}
