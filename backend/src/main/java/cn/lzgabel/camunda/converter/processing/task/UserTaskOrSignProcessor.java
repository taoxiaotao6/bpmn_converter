package cn.lzgabel.camunda.converter.processing.task;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.entity.Extension;
import cn.lzgabel.camunda.converter.bean.task.UserTaskOrSignDefinition;
import cn.lzgabel.camunda.converter.constant.ConverterConstant;
import cn.lzgabel.camunda.converter.processing.BpmnElementProcessor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.MultiInstanceLoopCharacteristicsBuilder;
import org.camunda.bpm.model.bpmn.builder.UserTaskBuilder;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

/**
 * @Author jiangtao
 * @Desc 或签类型的用户任务
 * @Date 2023-01-04 17:52
 **/
public class UserTaskOrSignProcessor implements BpmnElementProcessor<UserTaskOrSignDefinition, AbstractFlowNodeBuilder> {

    @Override
    public String onComplete(AbstractFlowNodeBuilder flowNodeBuilder, UserTaskOrSignDefinition flowNode) throws InvocationTargetException, IllegalAccessException {
        String nodeType = flowNode.getNodeType();
        String nodeName = flowNode.getNodeName();
        String assignee = flowNode.getAssignee();
        String collection = flowNode.getMultiInstance().getCollection();
        String elementVariable = flowNode.getMultiInstance().getElementVariable();
        String completionCondition = flowNode.getMultiInstance().getCompletionCondition();
        List<Extension> extensionList = flowNode.getExtensionList();

        UserTask userTask = (UserTask) createInstance(flowNodeBuilder, nodeType);
        userTask.setName(nodeName);
        String id = userTask.getId();
        collection = id + ConverterConstant._ASSIGNEE;
        // set assignee and MultiInstance
        UserTaskBuilder userTaskBuilder = userTask.builder();
        if (StringUtils.isNotBlank(assignee)) {
            userTaskBuilder.camundaAssignee(assignee);
        }
        MultiInstanceLoopCharacteristicsBuilder multiInstanceBuilder = userTaskBuilder.multiInstance();
        if (StringUtils.isNotBlank(collection)) {
            multiInstanceBuilder.camundaCollection(collection);
        }
        if (StringUtils.isNotBlank(elementVariable)) {
            multiInstanceBuilder.camundaElementVariable(elementVariable);
        }
        if (StringUtils.isNotBlank(completionCondition)) {
            multiInstanceBuilder.completionCondition(completionCondition);
        }
        if (CollectionUtils.isNotEmpty(extensionList)) {
            CamundaProperties camundaProperties = userTaskBuilder.getElement().getModelInstance().newInstance(CamundaProperties.class);
            for (Extension extension : extensionList) {
                String extensionName = extension.getName();
                if (extension.getName().contains(ConverterConstant._ASSIGNEE)) {
                    extensionName = collection;
                }
                // 这里创建CamundaProperty
                CamundaProperty camundaProperty = userTaskBuilder.getElement().getModelInstance().newInstance(CamundaProperty.class);
                camundaProperty.setCamundaName(extensionName);
                camundaProperty.setCamundaValue(extension.getValue());
                camundaProperties.getCamundaProperties().add(camundaProperty);
            }
            userTaskBuilder.addExtensionElement(camundaProperties);
        }

        // create execution listener
        createExecutionListener(flowNodeBuilder, flowNode);
        // 如果当前任务还有后续任务，则遍历创建后续任务
        BaseDefinition nextNode = flowNode.getNextNode();
        if (Objects.nonNull(nextNode)) {
            return onCreate(moveToNode(flowNodeBuilder, id), nextNode);
        } else {
            return id;
        }
    }

}
