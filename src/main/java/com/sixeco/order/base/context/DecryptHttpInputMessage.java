package com.sixeco.order.base.context;

import com.google.common.io.CharStreams;
import com.sixeco.order.base.utils.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 解密Http消息对象
 *
 * @author: Zhanghe
 * @date: 2019-04-04
 */
@Slf4j
public class DecryptHttpInputMessage implements HttpInputMessage {

    private HttpInputMessage inputMessage;

    private String charset;

    private String privateKey;

    public DecryptHttpInputMessage(HttpInputMessage inputMessage, String charset, String privateKey) {
        this.inputMessage = inputMessage;
        this.charset = charset;
        this.privateKey = privateKey;
    }

    @Override
    public InputStream getBody() throws IOException {
        String content = CharStreams.toString(new InputStreamReader(inputMessage.getBody() , charset));
        String decryptBody = "";
        try {
            decryptBody = RsaUtil.decrypt(content, RsaUtil.getPrivateKey(privateKey));
        } catch (Exception e) {
            log.error("解密失败");
            return null;
        }
        return new ByteArrayInputStream(decryptBody.getBytes(charset));
    }

    @Override
    public HttpHeaders getHeaders() {
        return inputMessage.getHeaders();
    }
}
