package com.mycompany.myapp.web.rest.vm;

import ch.qos.logback.classic.Logger;

/**
 * View Model object for storing a Logback logger.
 */
/**
 *
 * @ProjectName:
 * @Package:        com.mycompany.myapp.web.rest.vm
 * @ClassName:      LoggerVM
 * @Description:    JHipster使用了VM作为前后端传递对象视图模型，LoggerVM只有2个属性，name和level，这样就简化了前后端的数据传递，毕竟我们的目标只是显示和修改日志的等级设置，传递整个Logger对象显然不适合。
 * @Author:         chenkangli
 * @CreateDate:     15:42 2019/2/22
 * @UpdateUser:
 * @UpdateDate:     15:42 2019/2/22
 * @UpdateRemark:
 * @Version:        1.0
 */
public class LoggerVM {

    private String name;

    private String level;

    public LoggerVM(Logger logger) {
        this.name = logger.getName();
        this.level = logger.getEffectiveLevel().toString();
    }

    public LoggerVM() {
        // Empty public constructor used by Jackson.
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "LoggerVM{" +
            "name='" + name + '\'' +
            ", level='" + level + '\'' +
            '}';
    }
}
