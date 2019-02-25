package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.web.rest.vm.LoggerVM;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for view and managing Log Level at runtime.
 */
/**
 *
 * @ProjectName:
 * @Package:        com.mycompany.myapp.web.rest
 * @ClassName:      LogsResource
 * @Description:都是获取LoggerContext，然后进行相应的操作。
 * @Author:         chenkangli
 * @CreateDate:     15:41 2019/2/22
 * @UpdateUser:
 * @UpdateDate:     15:41 2019/2/22
 * @UpdateRemark:
 * @Version:        1.0
 */
@RestController
@RequestMapping("/management")
public class LogsResource {

    @GetMapping("/logs")
    @Timed
    public List<LoggerVM> getList() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        return context.getLoggerList()
            .stream()
            .map(LoggerVM::new)
            .collect(Collectors.toList());
    }

    @PutMapping("/logs")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Timed
    public void changeLevel(@RequestBody LoggerVM jsonLogger) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger(jsonLogger.getName()).setLevel(Level.valueOf(jsonLogger.getLevel()));
    }
}
