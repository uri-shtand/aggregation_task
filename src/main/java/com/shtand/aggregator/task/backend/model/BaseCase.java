package com.shtand.aggregator.task.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *    "Case ID": 1,
 *    "Customer_ID": 818591,
 *    "Provider": 6111,
 *    "CREATED_ERROR_CODE": 324,
 *    "STATUS": "Open",
 *    "TICKET_CREATION_DATE": "3/14/2019 16:30",
 *    "LAST_MODIFIED_DATE": "3/17/2019 3:41",
 *    "PRODUCT_NAME": "BLUE"
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseCase {


    @JsonProperty("Case ID")
    private Long caseId;

    @JsonProperty("Customer_ID")
    private Long customerId;

    @JsonProperty("Provider")
    private Long providerId;

    @JsonProperty("CREATED_ERROR_CODE")
    private Long createdErrorCode;

    @JsonProperty("STATUS")
    private String status;

    @JsonProperty("TICKET_CREATION_DATE")
    private String ticketCreationDate;

    @JsonProperty("LAST_MODIFIED_DATE")
    private String lastModifiedDate;

    @JsonProperty("PRODUCT_NAME")
    private String productName;

}
