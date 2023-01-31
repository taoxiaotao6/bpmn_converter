package cn.lzgabel.camunda.converter.demo;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.MessageEventDefinitionBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;

/**
 * @Author jiangtao
 * @Desc
 * @Date 2023-01-30 17:26
 **/
public class Demo01 {

    private static final String OUT_PATH = "/Users/a58/jiangtao/work/word/test/";

    public static void main(String[] args) {

        MessageEventDefinitionBuilder builder = Bpmn.createExecutableProcess()
                .startEvent()
                .userTask("user-task1")
                .boundaryEvent()
                .timerWithCycle("").cancelActivity(false)
                .endEvent()
                .messageEventDefinition();
        builder.getElement().setAttributeValueNs(CAMUNDA_NS, CAMUNDA_ATTRIBUTE_DELEGATE_EXPRESSION, "${timeoutRemindDelegate}");

        BpmnModelInstance bpmnModelInstance = builder.done();

        Path path = Paths.get(OUT_PATH + "xxxxf" + ".bpmn");
        if (path.toFile().exists()) {
            path.toFile().delete();
        }
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
