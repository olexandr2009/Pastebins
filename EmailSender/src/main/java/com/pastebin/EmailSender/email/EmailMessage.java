package com.pastebin.EmailSender.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {
    private List<String> recipients;

    private String subject;

    private String text;
}
