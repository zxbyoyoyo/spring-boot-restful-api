package com.ailk.devinfo.serv;

import com.ailk.devinfo.domain.DevInfoPO;
import com.ailk.devinfo.mapper.DevInfoMapper;
//import com.linkage.module.liposs.system.cao.snmpgather.SnmpGatherCorba;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxb3(74637)
 * @version 1.0
 * @category com.ailk.devinfo.serv
 * @copyright Ailk NBS-Network Mgt. RD Dept.
 * @since 2017/3/30 16:22
 */
@Service
public class DevInfoServ
{
    public static final Logger logger = Logger.getLogger(DevInfoServ.class);
    @Autowired
    private DevInfoMapper devInfoMapper;
    //@Resource(name="liposs_corba_SnmpGatherCorbaImp")
    //private SnmpGatherCorba snmpGatherCorba;

    public DevInfoPO qryDevInfo(DevInfoPO devInfoPO)
    {
        logger.info("qryDevInfo enter...");
        List<Map<String, Object>> lst = null;
        int pageNo = 0;
        int pageSize = 0;
        int pageTotalCount = 0;
        int lstSize = 0;
        pageNo = !isEmpty(devInfoPO.getPageNo()) ? Integer.parseInt(devInfoPO.getPageNo()) : 1;
        pageSize = !isEmpty(devInfoPO.getPageSize()) ? Integer.parseInt(devInfoPO.getPageSize()) : 10;
        devInfoPO.setTop(pageNo * pageSize + "");
        lst = devInfoMapper.queryDevInfo(devInfoPO);
        logger.info("qryDevInfo lst.size" + lst.size());
        if (!CollectionUtils.isEmpty(lst))
        {
            lstSize = lst.size();
            pageTotalCount = lstSize / pageSize + 1;
            devInfoPO.setPageTotalCount(pageTotalCount + "");
            lst = lst.subList((pageNo - 1) * pageSize, pageNo * pageSize > lstSize ? lstSize : pageNo * pageSize);
            devInfoPO.setDevInfoList(lst);
        }
        return devInfoPO;
    }

    public List<DevInfoPO> selectDevInfo(List<DevInfoPO> devInfoPOs)
    {
        logger.info("DevInfoServ enter...");
        List<Map<String, Object>> lst = null;
        int pageNo = 0;
        int pageSize = 0;
        int pageTotalCount = 0;
        int lstSize = 0;
        for (DevInfoPO dev : devInfoPOs)
        {
            pageNo = !isEmpty(dev.getPageNo()) ? Integer.parseInt(dev.getPageNo()) : 1;
            pageSize = !isEmpty(dev.getPageSize()) ? Integer.parseInt(dev.getPageSize()) : 10;
            dev.setTop(pageNo * pageSize + "");
            lst = devInfoMapper.queryDevInfo(dev);
            logger.info("DevInfoServ lst.size" + lst.size());
            if (!CollectionUtils.isEmpty(lst))
            {
                lstSize = lst.size();
                pageTotalCount = lstSize / pageSize + 1;
                dev.setPageTotalCount(pageTotalCount + "");
                lst = lst.subList((pageNo - 1) * pageSize, pageNo * pageSize > lstSize ? lstSize : pageNo * pageSize);
                dev.setDevInfoList(lst);//不为null
            }
        }
        return devInfoPOs;
    }


    /**
     * 设备厂商列表
     *
     * @return
     */
    public List<Map<String, Object>> qryVendors()
    {
        logger.info("qryVendorList enter...");
        List<Map<String, Object>> lst = null;
        lst = devInfoMapper.qryVendors();

        return lst;
    }

    /**
     * 根据vendor_id 获取设备型号列表
     *
     * @return
     */
    public List<Map<String, Object>> qryDeviceModels(String vendor_id)
    {
        logger.info("qryDeviceModels enter...");
        List<Map<String, Object>> lst = null;
        lst = devInfoMapper.qryDeviceModels(vendor_id);

        return lst;
    }

    /**
     * 属地列表
     *
     * @return
     */
    public List<Map<String, Object>> qryCitys()
    {
        logger.info("qryCitys enter...");
        List<Map<String, Object>> lst = null;
        lst = devInfoMapper.qryCitys();
        return lst;
    }

    /**
     * 局向列表
     *
     * @return
     */
    public List<Map<String, Object>> qryOffices(String city_id)
    {
        logger.info("qryOffices enter...");
        List<Map<String, Object>> lst = null;
        lst = devInfoMapper.qryOffices(city_id);
        return lst;
    }


    /**
     * 小区列表
     *
     * @return
     */
    public List<Map<String, Object>> qryZones()
    {
        logger.info("qryZones enter...");
        List<Map<String, Object>> lst = null;
        lst = devInfoMapper.qryZones();
        return lst;
    }

    /**
     * 设备层次列表
     *
     * @return
     */
    public List<Map<String, Object>> qryResourceTypes()
    {
        logger.info("qryResourceTypes enter...");
        List<Map<String, Object>> lst = null;
        lst = devInfoMapper.qryResourceTypes();
        return lst;
    }

    /**
     * MIB 版本列表
     *
     * @return
     */
    public List<Map<String, Object>> qryOsVersions()
    {
        logger.info("qryOsVersions enter...");
        List<Map<String, Object>> lst = null;
        lst = devInfoMapper.qryOsVersions();
        return lst;
    }

    /**
     * 采集机列表
     *
     * @return
     */
    public List<Map<String, Object>> qryGathers()
    {
        logger.info("qryGathers enter...");
        List<Map<String, Object>> lst = null;
        lst = devInfoMapper.qryGathers();
        return lst;
    }

    /**
     * 更新设备
     *
     * @param devInfoPO
     * @return
     */

    public String updOneDev(DevInfoPO devInfoPO)
    {
        logger.info("updOneDev enter..." + devInfoPO);
        int i = 0;
        i = devInfoMapper.updDevInfo(devInfoPO);
        logger.info("updOneDev end..." + i);
        return i > 0 ? "UPDATE_SUCCESS" : "UPDATE_FAIL";
    }

    /**
     * 删除设备
     *
     * @return
     */
    public String delOneDev(String device_id)
    {
        logger.info("delDevInfo enter...device_id：" + device_id);
        int i = 0;
        i = devInfoMapper.delDevInfo(device_id);
        logger.info("delDevInfo end..." + i);
        return i > 0 ? "DELETE_SUCCESS" : "DELETE_FAIL";
    }


    public String verifyReadCommunity()
    {
        //logger.info("snmpGatherCorba:"+snmpGatherCorba);
        //boolean falg=snmpGatherCorba.VerifyReadCommunity("szx","10.245.2.222","private");
        //logger.info(falg);
        return "----";

    }

    /**
     * @param str
     * @return
     */
    public static boolean isEmpty(String str)
    {
        return str == null || str.length() == 0;
    }

}
