package zw.co.equals.customersupportservice.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.equals.customersupportservice.enums.IssueStatus;
import zw.co.equals.customersupportservice.models.IssueLog;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueTrackResponse {
    private Long issueId;
    private Long ticketId;
    private IssueStatus issueStatus;
    private String description;
    private String dateLogged;
    private IssueLog issueLog;
}
