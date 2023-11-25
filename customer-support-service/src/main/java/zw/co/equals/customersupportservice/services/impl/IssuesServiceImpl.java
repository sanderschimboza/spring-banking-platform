package zw.co.equals.customersupportservice.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import zw.co.equals.customersupportservice.data.IssueTrackResponse;
import zw.co.equals.customersupportservice.dto.IssueLogDto;
import zw.co.equals.customersupportservice.enums.IssueStatus;
import zw.co.equals.customersupportservice.exceptions.IssueNotFoundException;
import zw.co.equals.customersupportservice.models.IssueLog;
import zw.co.equals.customersupportservice.models.IssueTicket;
import zw.co.equals.customersupportservice.repositories.IssuesLogRepository;
import zw.co.equals.customersupportservice.repositories.IssuesTicketRepository;
import zw.co.equals.customersupportservice.services.IssuesService;
import zw.co.equals.customersupportservice.utils.CodeGenerator;
import zw.co.equals.customersupportservice.utils.DateFormatter;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssuesServiceImpl implements IssuesService {

    private final IssuesLogRepository issuesLogRepository;
    private final IssuesTicketRepository issuesTicketRepository;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public Long logIssue(IssueLogDto issueLogDto) {

        IssueLog issueLog = IssueLog.builder()
                .issueId(CodeGenerator.generateId(0))
                .issueDate(DateFormatter.formatCurrentDate(new Date()))
                .issueStatus(IssueStatus.PENDING)
                .issueType(issueLogDto.getIssueType())
                .description(issueLogDto.getDescription())
                .fromAccount(issueLogDto.getFromAccount())
                .build();

        log.info("After building issue log => {}", issueLog);

        IssueTicket issueTicket = IssueTicket.builder()
                .ticketId(CodeGenerator.generateId(3))
                .issueLog(issueLog)
                .narrative("AWAITING RESPONSE")
                .build();

        log.info("After building issue ticket => {}", issueLog);

        issuesLogRepository.save(issueLog);
        publishSupportTicketToRabbitQueue(issueTicket);

        log.info("Saved Issue to DB with ticket id => {}", issueTicket.getTicketId());
        return issuesTicketRepository.save(issueTicket).getTicketId();
    }

    private void publishSupportTicketToRabbitQueue(IssueTicket issueTicket) {
        rabbitTemplate.convertAndSend("", "q.support-issue",
                issueTicket);
        log.info("Published issue to q.support-issue Queue: {} ", issueTicket);
    }

    @Override
    public IssueTrackResponse trackIssue(Long ticketId) {

        log.info("Going to track issue with ticket id => {}", ticketId);

        IssueTicket issueTicket = issuesTicketRepository.findByTicketId(ticketId)
                .orElseThrow(() -> new IssueNotFoundException(
                                "Issue with that ticket Id was not found!"
                        )
                );

        log.info("tracked issue response => {}", issueTicket);

        return IssueTrackResponse.builder()
                .issueId(issueTicket.getIssueLog().getIssueId())
                .ticketId(issueTicket.getTicketId())
                .issueStatus(issueTicket.getIssueLog().getIssueStatus())
                .description(issueTicket.getNarrative())
                .dateLogged(issueTicket.getIssueLog().getIssueDate())
                .issueLog(issueTicket.getIssueLog())
                .build();
    }
}
