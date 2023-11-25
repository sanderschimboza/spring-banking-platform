package zw.co.equals.customersupportservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import zw.co.equals.customersupportservice.models.IssueTicket;

import java.util.Optional;

@Repository
public interface IssuesTicketRepository extends MongoRepository<IssueTicket, Long> {
    Optional<IssueTicket> findByTicketId(Long ticketId);
}
