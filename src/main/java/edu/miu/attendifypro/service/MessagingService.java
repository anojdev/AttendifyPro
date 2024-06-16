package edu.miu.attendifypro.service;

import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessagingService {
    private final MessageSource messageSource;

    public String getResponseMessage(ServiceResponse serviceResponse, String[] args) {
        //this section corresponds to the main response message corresponding to status code with incoming args
        String[] arguments = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            arguments[i] = messageSource.getMessage(args[i],
                    new String[]{},
                    LocaleContextHolder.getLocale());
        }
        //The remaining service messages are loaded as one comma separated string
        String[] allArguments = Arrays.copyOf(arguments, arguments.length + 1);

        //for each service message reference string, its corresponding param is loaded and a response is found
        //which in turn is appended in the last of main response message
        String eachServiceMsgs[] = new String[serviceResponse.getMessages().size()];
        int i = 0;
        for (Object set : serviceResponse.getMessages().entrySet()) {
            Map.Entry<String, List<String>> entry = (Map.Entry<String, List<String>>) set;
            String[] params=new String[entry.getValue().size()];
            for (int j = 0; j < params.length; j++) {
                params[j]=entry.getValue().get(j);
            }
            eachServiceMsgs[i] = messageSource.getMessage(entry.getKey(),
                    params,
                    LocaleContextHolder.getLocale());
            i++;
        }

        allArguments[allArguments.length - 1] = String.join(", ", eachServiceMsgs);
        return messageSource.getMessage(serviceResponse.getStatusCode().getDescription(),
                allArguments,
                LocaleContextHolder.getLocale());
    }
}
