package edu.miu.attendifypro.dto;

import edu.miu.attendifypro.domain.AppStatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.Assert;

import java.util.*;

@Data
@AllArgsConstructor
@Builder
public final class ServiceResponse<T> {

    private final Optional<T> data;
    private final AppStatusCode statusCode;
    private final HashMap<String, List<String>> messages;

    private ServiceResponse(T data, AppStatusCode statusCode, HashMap<String, List<String>> messages) {

        Assert.notNull(statusCode, "StatusCode  must not be null");
        this.data = Objects.nonNull(data) ? Optional.of(data) : Optional.empty();
        this.statusCode = statusCode;
        this.messages = Objects.nonNull(messages) ? messages : new HashMap<>();

    }

    public static <T> ServiceResponse<T> of(T first, AppStatusCode second) {
        return new ServiceResponse<>(first, second, new HashMap<String, List<String>>());
    }

    public static <T> ServiceResponse<T> of(AppStatusCode second) {
        return new ServiceResponse<>(null, second, new HashMap<String, List<String>>());
    }

    public static <T> ServiceResponse<T> of(T first, AppStatusCode second, List<String> third) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        third.forEach(str -> hashMap.put(str, new ArrayList<>()));
        return new ServiceResponse<>(first, second, hashMap);
    }


}
