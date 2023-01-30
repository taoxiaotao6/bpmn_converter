package cn.lzgabel.camunda.converter.bean.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @Author jiangtao
 * @Desc 扩展变量
 * @Date 2023-01-04 18:18
 **/
@Data
@SuperBuilder
@NoArgsConstructor
public class Extension {

    private String name;

    private String value;

}
