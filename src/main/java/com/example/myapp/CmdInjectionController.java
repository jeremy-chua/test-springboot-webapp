package com.example.myapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CmdInjectionController {

    @GetMapping("/cmdi")
    public String index(@RequestParam(defaultValue = "ls") String cmd) {
        try {
            String[] command = cmd.split(" ");
            ProcessBuilder builder = new ProcessBuilder(command);

            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("<br>");
            }

            return output.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

}
