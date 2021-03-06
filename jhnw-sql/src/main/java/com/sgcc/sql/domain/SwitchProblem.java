package com.sgcc.sql.domain;

import com.sgcc.common.annotation.Excel;
import com.sgcc.common.core.domain.BaseEntity;

/**
 * 交换机问题对象 switch_problem
 * 
 * @author ruoyi
 * @date 2021-12-28
 */
public class SwitchProblem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 交换机ip */
    @Excel(name = "交换机ip")
    private String switchIp;

    /** 交换机姓名 */
    @Excel(name = "交换机姓名")
    private String switchName;

    /** 交换机密码 */
    @Excel(name = "交换机密码")
    private String switchPassword;

    /** 问题索引 */
    @Excel(name = "问题索引")
    private String problemId;

    /** 是否有问题 */
    @Excel(name = "是否有问题")
    private String ifQuestion;

    /** 命令索引 */
    @Excel(name = "命令索引")
    private String comId;

    /** 参数索引 */
    @Excel(name = "参数索引")
    private Long valueId;

    /** 是否解决 */
    @Excel(name = "是否解决")
    private String resolved;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSwitchIp() {
        return switchIp;
    }

    public void setSwitchIp(String switchIp) {
        this.switchIp = switchIp;
    }

    public String getSwitchName() {
        return switchName;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    public String getSwitchPassword() {
        return switchPassword;
    }

    public void setSwitchPassword(String switchPassword) {
        this.switchPassword = switchPassword;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getIfQuestion() {
        return ifQuestion;
    }

    public void setIfQuestion(String ifQuestion) {
        this.ifQuestion = ifQuestion;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    @Override
    public String toString() {
        return "SwitchProblem{" +
                "id=" + id +
                ", switchIp='" + switchIp + '\'' +
                ", switchName='" + switchName + '\'' +
                ", switchPassword='" + switchPassword + '\'' +
                ", problemId='" + problemId + '\'' +
                ", ifQuestion='" + ifQuestion + '\'' +
                ", comId='" + comId + '\'' +
                ", valueId=" + valueId +
                ", resolved='" + resolved + '\'' +
                '}';
    }
}
