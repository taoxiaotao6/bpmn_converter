package cn.lzgabel.camunda.converter.controller;

import cn.lzgabel.camunda.converter.BpmnBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2021/8/21
 * @since 1.0.0
 */
@RestController
@RequestMapping
public class WorkflowController {

  private static final String OUT_PATH = "/Users/a58/jiangtao/work/word/test/";

  @GetMapping("/ok")
  public String ok() {
    return "ok";
  }

  @PostMapping(value = "/deploy", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  @CrossOrigin
  public String deploy(@RequestBody DeployRequest request) {

    BpmnModelInstance modelInstance = BpmnBuilder.build(request.toString());

      Path path = Paths.get(OUT_PATH + "yyyy" + ".bpmn");
      if (path.toFile().exists()) {
          path.toFile().delete();
      }
      try {
          Files.createDirectories(path.getParent());
      } catch (IOException e) {
          e.printStackTrace();
      }
      try {
          Bpmn.writeModelToFile(Files.createFile(path).toFile(), modelInstance);
      } catch (IOException e) {
          e.printStackTrace();
      }

//    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//    Bpmn.writeModelToStream(outputStream, modelInstance);
    return "ok";
  }

    @PostMapping(value = "/deploy_outputstream", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @CrossOrigin
    public String deployOutPutStream(@RequestBody DeployRequest request) {

        BpmnModelInstance modelInstance = BpmnBuilder.build(request.toString());

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Bpmn.writeModelToStream(outputStream, modelInstance);
    String result = new String(outputStream.toByteArray());

    String ss = "{\n" +
            "        \"process\": {\n" +
            "            \"processId\": \"process-id1\",\n" +
            "            \"name\": \"process-name1\"\n" +
            "        },\n" +
            "        \"processNode\": {\n" +
            "            \"nodeName\": \"总部审批1\",\n" +
            "            \"nodeType\": \"userTaskOrSign\",\n" +
            "            \"multiInstance\": {\n" +
            "                \"collection\": \"_assignee\"\n" +
            "            },\n" +
            "            \"extensionList\": [\n" +
            "                {\n" +
            "                    \"name\": \"_assignee\",\n" +
            "                    \"value\": \"shop.NC_OfflineContract_Approval.(role:NC_OfflineContract_Approval)\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"nextNode\": null\n" +
            "        }\n" +
            "    }";
        return result;
    }
}
