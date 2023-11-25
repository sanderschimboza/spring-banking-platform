package zw.co.equals.customersupportservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import zw.co.equals.customersupportservice.enums.IssueStatus;
import zw.co.equals.customersupportservice.enums.IssueType;

@Document(collection = "issues_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueLog {
    @Id
    @Column(name = "issue_id")
    private Long issueId;
    @Enumerated(EnumType.STRING)
    private IssueType issueType;
    private String description;
    private String fromAccount;
    @Enumerated(EnumType.STRING)
    private IssueStatus issueStatus;
    private String issueDate;
}
