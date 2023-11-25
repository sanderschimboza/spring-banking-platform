package zw.co.equals.customersupportservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "issue_ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueTicket {
    @Id
    private Long ticketId;
    @OneToOne
    @JoinColumn(name = "issue_id",referencedColumnName = "issue_id")
    private IssueLog issueLog;
    private String narrative;
}
