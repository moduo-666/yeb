package com.xxx.server.converter;
//继承这个转换类，不要导错包
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日期转换
 * @author Wu Zicong
 * @create 2021-05-09 14:43
 */
@Component
public class DateConverter implements Converter<String,LocalDate> {

    @Override
    public LocalDate convert(String s) {
        if("".equals(s)){
            return null;
        }
        try {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
