package com.jiale.ollama.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ollama/chatModel")
public class OllamaChatModelController {

    private final OllamaChatModel ollamaChatModel;

    /**
     * 直接调用，等待生成结束返回
     */
    @RequestMapping("/call")
    public String call() {
        ChatOptions chatOptions = ChatOptions.builder().model("llama3.2").build();
        Prompt prompt = new Prompt("你好啊", chatOptions);
        ChatResponse call = ollamaChatModel.call(prompt);
        call.getResults().forEach(generation -> {
            System.out.println(generation.getOutput());
        });
        return "hello world";
    }

    /**
     * 流式调用，边生成边返回
     */
    @RequestMapping("/stream")
    public String stream() {
        ChatOptions chatOptions = ChatOptions.builder().model("llama3.2").build();
        Prompt prompt = new Prompt("你好啊", chatOptions);
        Flux<ChatResponse> chatResponseFlux = ollamaChatModel.stream(prompt);
        chatResponseFlux.subscribe(chatResponse -> {
            System.out.println(chatResponse.getResults());
        });
        return "hello world";
    }
}
