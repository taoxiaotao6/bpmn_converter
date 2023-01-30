package cn.lzgabel.camunda.converter.demo;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author jiangtao
 * @Desc
 * @Date 2023-01-30 17:26
 **/
public class Demo {

    private static final String OUT_PATH = "/Users/a58/jiangtao/work/word/test/";

    public static void main(String[] args) {
        BpmnModelInstance bpmnModelInstance = Bpmn.createExecutableProcess()
                .startEvent()
                .userTask("task-1")
                .boundaryEvent()
                .timerWithCycle("").cancelActivity(false)
                .userTask("task-2")
                .moveToNode("task-1")
                .done();

        Path path = Paths.get(OUT_PATH + "xxxx" + ".bpmn");
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
