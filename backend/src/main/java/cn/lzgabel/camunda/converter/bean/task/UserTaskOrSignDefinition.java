package cn.lzgabel.camunda.converter.bean.task;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.BpmnElementType;
import cn.lzgabel.camunda.converter.bean.entity.Extension;
import cn.lzgabel.camunda.converter.bean.entity.MultiInstance;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @Author jiangtao
 * @Desc 或签类型的用户任务
 * @Date 2023-01-04 17:53
 **/
@Data
@SuperBuilder
@NoArgsConstructor
public class UserTaskOrSignDefinition extends BaseDefinition {

    /**
     * 审批人
     */
    private String assignee = "${user}";

    /**
     * Multi Instance变量
     */
    private MultiInstance multiInstance;

    /**
     * 额外变量
     */
    private List<Extension> extensionList;

    @Override
    public String getNodeType() {
        return BpmnElementType.USER_TASK_OR_SIGN.getElementTypeName().get();
    }
}
