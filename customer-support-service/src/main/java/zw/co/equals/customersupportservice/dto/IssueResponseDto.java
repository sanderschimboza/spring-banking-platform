package zw.co.equals.customersupportservice.dto;

import lombok.Data;
import zw.co.equals.customersupportservice.enums.IssueStatus;

@Data
public class IssueResponseDto {
    private IssueStatus issueStatus;
    private String narrative;
}
