package com.xxx.server.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 消息
 * @author Wu Zicong
 * @create 2021-05-16 16:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)  //chain = true 表示set方法返回当前对象
public class ChatMsg {

    private String from; //发起人
    private String to; //发给谁
    private String content; //
    private LocalDateTime date;
    private String fromNickName;
}
