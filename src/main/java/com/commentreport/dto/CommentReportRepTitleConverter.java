package com.commentreport.dto;//package com.example.commentreport.dto;
//
//import com.example.commentreport.constant.CommentReportRepTitle;
//
//import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;
////轉換enum值到其描述字符串、資料庫讀取時，自動將字符串轉換回枚舉值
//@Converter(autoApply = true)
//public class CommentReportRepTitleConverter implements AttributeConverter<CommentReportRepTitle, String> {
//    @Override
//    public String convertToDatabaseColumn(CommentReportRepTitle attribute) {
//        if (attribute == null) {
//            return null;
//        }
//        return attribute.getTitle();
//    }
//
//    @Override
//    public CommentReportRepTitle convertToEntityAttribute(String dbData) {
//        if (dbData == null) {
//            return null;
//        }
//        for (CommentReportRepTitle title : CommentReportRepTitle.values()) {
//            if (title.getTitle().equals(dbData)) {
//                return title;
//            }
//        }
//        throw new IllegalArgumentException("未知的描述: " + dbData);
//    }
//}
