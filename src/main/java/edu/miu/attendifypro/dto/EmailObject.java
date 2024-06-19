package edu.miu.attendifypro.dto;

/**
 * @author kush
 */

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class EmailObject {
    private String from;
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private String subject;
    private String body;
    private String contentType;

    public EmailObject() {
        this.to = new ArrayList<>();
        this.cc = new ArrayList<>();
        this.bcc = new ArrayList<>();
        this.contentType = "text/plain";
    }

    public void addTo(String to) {
        this.to.add(to);
    }
    public void addCc(String cc) {
        this.cc.add(cc);
    }
    public void addBcc(String bcc) {
        this.bcc.add(bcc);
    }

}
