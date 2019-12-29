package cn.sau.sauoh.config;

import java.text.*;
import java.util.Date;

/**
 * @author nullptr
 * @date 2019/12/29 16:45
 */
public class MyDateFormat extends DateFormat {

    private DateFormat dateFormat;

    private SimpleDateFormat format1 = new SimpleDateFormat("yyy-MM-dd HH:mm");

    public MyDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return dateFormat.format(date, toAppendTo, fieldPosition);
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        Date date = null;
        try {
            //使用自定义规则
            date = format1.parse(source, pos);
        } catch (Exception e) {
            //失败了再调用默认规则
            date = dateFormat.parse(source, pos);
        }
        return date;
    }

    @Override
    public Date parse(String source) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        return this.parse(source, parsePosition);
    }

    @Override
    public Object clone() {
        Object format = dateFormat.clone();
        return new MyDateFormat((DateFormat) format);
    }
}
