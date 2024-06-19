package edu.miu.attendifypro.dto.response.common;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageErrorNode {

    private List<ErrorField> fields;

    private List<String> messages;

}