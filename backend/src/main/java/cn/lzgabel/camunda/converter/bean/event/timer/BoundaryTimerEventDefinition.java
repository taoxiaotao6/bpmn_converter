package cn.lzgabel.camunda.converter.bean.event.timer;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.BpmnElementType;

/**
 * @Author jiangtao
 * @Desc 定时器边界事件
 * @Date 2023-01-29 19:30
 **/
public class BoundaryTimerEventDefinition extends BaseDefinition {

    @Override
    public String getNodeType() {
        return BpmnElementType.BOUNDARY_EVENT.getElementTypeName().get();
    }
}
