package zw.co.equals.customersupportservice.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import zw.co.equals.customersupportservice.enums.IssueStatus;
import zw.co.equals.customersupportservice.enums.IssueType;

@Data
@Builder
public class IssueLogDto {
    @Enumerated(EnumType.STRING)
    @NotNull(message = "IssueType should not be empty")
    private IssueType issueType;
    @NotEmpty(message = "Issue description should not be empty")
    private String description;
    @NotEmpty(message = "Account Number should not be empty")
    private String fromAccount;
}
