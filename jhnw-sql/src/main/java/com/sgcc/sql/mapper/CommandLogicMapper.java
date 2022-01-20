package com.sgcc.sql.mapper;

import com.sgcc.sql.domain.CommandLogic;

import java.util.List;

/**
 * 命令逻辑Mapper接口
 * 
 * @author 韦亚宁
 * @date 2021-12-14
 */
public interface CommandLogicMapper 
{
    /**
     * 查询命令逻辑
     * 
     * @param id 命令逻辑主键
     * @return 命令逻辑
     */
    public CommandLogic selectCommandLogicById(Long id);

    /**
     * 查询命令逻辑列表
     * 
     * @param commandLogic 命令逻辑
     * @return 命令逻辑集合
     */
    public List<CommandLogic> selectCommandLogicList(CommandLogic commandLogic);

    /**
     * 新增命令逻辑
     * 
     * @param commandLogic 命令逻辑
     * @return 结果
     */
    public int insertCommandLogic(CommandLogic commandLogic);

    /**
     * 修改命令逻辑
     * 
     * @param commandLogic 命令逻辑
     * @return 结果
     */
    public int updateCommandLogic(CommandLogic commandLogic);

    /**
     * 删除命令逻辑
     * 
     * @param id 命令逻辑主键
     * @return 结果
     */
    public int deleteCommandLogicById(Long id);

    /**
     * 批量删除命令逻辑
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCommandLogicByIds(Long[] ids);
}
