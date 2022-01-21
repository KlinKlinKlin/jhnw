package com.sgcc.web.controller.webSocket;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.sgcc.sql.domain.ScanResults;
import com.sgcc.sql.domain.ScanResultsVO;
import springfox.documentation.service.ResponseMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/*
 * Text<ResponseMessage>里的ResponseMessage是我自己写的一个消息类
 * 如果你写了一个名叫Student的类，需要通过sendObject()方法发送，那么这里就是Text<Student>
 */
public class ServerEncoder implements Encoder.Text<ScanResults> {

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        // 这里不重要
    }

    @Override
    public void init(EndpointConfig arg0) {
        // TODO Auto-generated method stub
        // 这里也不重要

    }

    /*
     *  encode()方法里的参数和Text<T>里的T一致，如果你是Student，这里就是encode（Student student）
     */
    @Override
    public String encode(ScanResults scanResults) throws EncodeException {
        try {
            /*
             * 这里是重点，只需要返回Object序列化后的json字符串就行
             * 你也可以使用gosn，fastJson来序列化。
             */
            JsonMapper jsonMapper = new JsonMapper();
            return jsonMapper.writeValueAsString(scanResults);

        } catch ( JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}