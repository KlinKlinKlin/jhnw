package com.sgcc.web.controller.sql;

import com.sgcc.common.annotation.Log;
import com.sgcc.common.core.controller.BaseController;
import com.sgcc.common.core.domain.AjaxResult;
import com.sgcc.common.core.page.TableDataInfo;
import com.sgcc.common.enums.BusinessType;
import com.sgcc.common.utils.poi.ExcelUtil;
import com.sgcc.sql.domain.*;
import com.sgcc.sql.service.ISwitchProblemService;
import com.sgcc.sql.service.IValueInformationService;
import com.sgcc.web.controller.webSocket.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 交换机问题Controller
 * 
 * @author ruoyi
 * @date 2021-12-28
 */
@RestController
@RequestMapping("/sql/switch_problem")
public class SwitchProblemController extends BaseController
{
    @Autowired
    private ISwitchProblemService switchProblemService;
    @Autowired
    private IValueInformationService valueInformationService;

    /**
     * 查询交换机问题列表
     */
    @PreAuthorize("@ss.hasPermi('sql:switch_problem:list')")
    @GetMapping("/list")
    public TableDataInfo list(SwitchProblem switchProblem)
    {
        startPage();
        List<SwitchProblem> list = switchProblemService.selectSwitchProblemList(switchProblem);
        return getDataTable(list);
    }

    /**
     * 导出交换机问题列表
     */
    @PreAuthorize("@ss.hasPermi('sql:switch_problem:export')")
    @Log(title = "交换机问题", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SwitchProblem switchProblem)
    {
        List<SwitchProblem> list = switchProblemService.selectSwitchProblemList(switchProblem);
        ExcelUtil<SwitchProblem> util = new ExcelUtil<SwitchProblem>(SwitchProblem.class);
        return util.exportExcel(list, "交换机问题数据");
    }

    /**
     * 获取交换机问题详细信息
     */
    @PreAuthorize("@ss.hasPermi('sql:switch_problem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(switchProblemService.selectSwitchProblemById(id));
    }

    /**
     * 新增交换机问题
     */
    @PreAuthorize("@ss.hasPermi('sql:switch_problem:add')")
    @Log(title = "交换机问题", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SwitchProblem switchProblem)
    {
        return toAjax(switchProblemService.insertSwitchProblem(switchProblem));
    }

    /**
     * 修改交换机问题
     */
    @PreAuthorize("@ss.hasPermi('sql:switch_problem:edit')")
    @Log(title = "交换机问题", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SwitchProblem switchProblem)
    {
        return toAjax(switchProblemService.updateSwitchProblem(switchProblem));
    }

    /**
     * 删除交换机问题
     */
    @PreAuthorize("@ss.hasPermi('sql:switch_problem:remove')")
    @Log(title = "交换机问题", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(switchProblemService.deleteSwitchProblemByIds(ids));
    }

    /**
    * @method: getUnresolvedProblemInformationByData
    * @Param: []
    * @return: java.util.List<com.sgcc.sql.domain.SwitchProblem>
    * @Author: 天幕顽主
    * @E-mail: WeiYaNing97@163.com
    */
    @RequestMapping("getUnresolvedProblemInformationByData")
    public ScanResults getUnresolvedProblemInformationByData(){

        ScanResults scanResults = new ScanResults();

        String currentTime = Utils.getCurrentTime();
        String[] data_day = currentTime.split(" ");
        List<SwitchProblemVO> switchProblemList = switchProblemService.selectUnresolvedProblemInformationByData(data_day[0]);
        for (SwitchProblemVO switchProblemVO:switchProblemList){
            List<SwitchProblemCO> switchProblemCOList = switchProblemVO.getSwitchProblemCOList();
            for (SwitchProblemCO switchProblemCO:switchProblemCOList){
                List<ValueInformationVO> valueInformationVOList = valueInformationService.selectValueInformationVOListByID(switchProblemCO.getValueId());
                switchProblemCO.setValueInformationVOList(valueInformationVOList);
            }
        }
        //将IP地址去重放入set集合中
        HashSet<String> ip_hashSet = new HashSet<>();
        for (SwitchProblemVO switchProblemVO:switchProblemList){
            ip_hashSet.add(switchProblemVO.getSwitchIp());
        }
        //将ip存入回显实体类
        List<ScanResultsVO> scanResultsVOList = new ArrayList<>();
        for (String ip_string:ip_hashSet){
            ScanResultsVO scanResultsVO = new ScanResultsVO();
            scanResultsVO.setSwitchIp(ip_string);
            scanResultsVOList.add(scanResultsVO);
        }


        for (ScanResultsVO scanResultsVO:scanResultsVOList){
            List<SwitchProblemVO> switchProblemVOList = new ArrayList<>();
            for (SwitchProblemVO switchProblemVO:switchProblemList){
                if (switchProblemVO.getSwitchIp().equals(scanResultsVO.getSwitchIp())){
                    switchProblemVOList.add(switchProblemVO);
                }
            }
            scanResultsVO.setSwitchProblemVOList(switchProblemVOList);
        }
        scanResults.setScanResultsVOS(scanResultsVOList);
        WebSocketService.sendMessage("badao",scanResults);
        return scanResults;
    }
}
