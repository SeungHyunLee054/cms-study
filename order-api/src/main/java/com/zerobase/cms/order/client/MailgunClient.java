package com.zerobase.cms.order.client;

import com.zerobase.cms.order.client.mailgun.SendMailForm;
import feign.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailgun", url = "https://api.mailgun.net/v3/")
@Qualifier("mailgun")
public interface MailgunClient {
    @PostMapping("sandbox5a3da1d6859741de99fe9289e41590ff.mailgun.org/messages")
    Response sendEmail(@SpringQueryMap SendMailForm form);
}