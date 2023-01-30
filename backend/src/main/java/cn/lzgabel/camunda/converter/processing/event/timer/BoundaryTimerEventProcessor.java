package cn.lzgabel.camunda.converter.processing.event.timer;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.event.timer.BoundaryTimerEventDefinition;
import cn.lzgabel.camunda.converter.processing.BpmnElementProcessor;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.BoundaryEventBuilder;
import org.camunda.bpm.model.bpmn.instance.BoundaryEvent;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @Author jiangtao
 * @Desc 定时器边界事件Processor
 * @Date 2023-01-29 19:39
 **/
public class BoundaryTimerEventProcessor implements BpmnElementProcessor<BoundaryTimerEventDefinition, AbstractFlowNodeBuilder> {

    @Override
    public String onComplete(AbstractFlowNodeBuilder flowNodeBuilder, BoundaryTimerEventDefinition flowNode) throws InvocationTargetException, IllegalAccessException {

        BoundaryEvent boundaryEvent = createInstance(flowNodeBuilder, BoundaryEvent.class);
        boundaryEvent.setName("超时自动提醒");
        BoundaryEventBuilder boundaryEventBuilder = boundaryEvent.builder();
        boundaryEventBuilder.timerWithDuration("${time_duration}");

        // create execution listener
        createExecutionListener(boundaryEventBuilder, flowNode);

        String id = boundaryEvent.getId();
        // 如果当前任务还有后续任务，则遍历创建后续任务
        BaseDefinition nextNode = flowNode.getNextNode();
        if (Objects.nonNull(nextNode)) {
            return onCreate(moveToNode(flowNodeBuilder, id), nextNode);
        } else {
            return id;
        }
    }

    private <T extends ModelElementInstance> T createInstance(
            AbstractFlowNodeBuilder<?, ?> abstractFlowNodeBuilder, Class<T> clazz) {
        return abstractFlowNodeBuilder.getElement().getModelInstance().newInstance(clazz);
    }
}
