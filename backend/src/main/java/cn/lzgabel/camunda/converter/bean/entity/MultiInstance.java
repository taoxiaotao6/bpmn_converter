package cn.lzgabel.camunda.converter.bean.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @Author jiangtao
 * @Desc 多实例变量
 * @Date 2023-01-04 17:56
 **/
@Data
@SuperBuilder
@NoArgsConstructor
public class MultiInstance {

    private String collection;

    private String elementVariable = "user";

    private String completionCondition = "${nrOfCompletedInstances == 1}";

}
