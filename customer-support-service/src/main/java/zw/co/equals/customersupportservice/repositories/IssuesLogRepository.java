package zw.co.equals.customersupportservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import zw.co.equals.customersupportservice.models.IssueLog;

@Repository
public interface IssuesLogRepository extends MongoRepository<IssueLog, Long> {
}
