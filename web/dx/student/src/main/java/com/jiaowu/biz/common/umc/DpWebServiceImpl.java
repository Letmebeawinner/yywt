package com.jiaowu.biz.common.umc;

import com.a_268.base.util.CollectionUtils;
import com.jiaowu.biz.umc.WifiUserBiz;
import com.jiaowu.common.StringUtils;
import com.jiaowu.entity.umc.WifiUser;
import com.jiaowu.util.DateUtil;
import com.jiaowu.webservice.UserManageAddServiceHttpBindingStub;
import com.jiaowu.webservice.UserManageAddServiceLocator;
import com.jiaowu.webservice.UserManageAddServicePortType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UMC
 *
 * @author YaoZhen
 * @date 03-28, 16:23, 2018.
 */
@Slf4j
@Service
public class DpWebServiceImpl implements DpWebService {
    /**
     * UMC 操作成功的返回值
     */
    private final String SUCCESS = "success";
    @Autowired
    private WifiUserBiz wifiUserBiz;

    @Override
    public boolean saveWifiUser(String phone, String realName, Date classEndTIme, Long oldId) {
        boolean flag = false;
        try {
            if (StringUtils.isNotMobileNo(phone)) {
                return false;
            }

            // 往UMC添加,
            boolean flagUMC = saveUMCWifi(phone, realName, classEndTIme);
            if (!flagUMC) {
                return false;
            }

            // 本地的添加成功也要判断用户是否已存在
            boolean flagDB = wifiUserBiz.saveWifiUser(phone, realName, classEndTIme, oldId);
            if (!flagDB) {
                return false;
            }

            flag = true;
        } catch (ServiceException | RemoteException e) {
            log.error("DpWebServiceImpl.saveWifiUser", e);
        }
        return flag;
    }

    private boolean saveUMCWifi(String phone, String realName, Date classEndTIme) throws ServiceException, RemoteException {
        // UMC接口
        UserManageAddServicePortType portType = getUserManageAddServicePortType();
        String rs = portType.addUser(phone, realName, "111111", 1,
                "/贵阳市委党校/学员/计划内班次/", classEndTIme.getTime() / 1000, phone);

        // UMC 的添加成功和该用户已存在都视为添加成功
        log.info(rs);
        return SUCCESS.equals(rs);
    }

    @Override
    public boolean delWifi(String phone) {
        try {
            if (StringUtils.isNotMobileNo(phone)) {
                return false;
            }

            UserManageAddServicePortType portType = getUserManageAddServicePortType();
            String rs = portType.delUser(phone);
            log.debug(rs);

            // UMC 删除成功后 删除本库
            if (SUCCESS.equals(rs)) {
                return wifiUserBiz.deleteWifiUser(phone);
            } else {
                log.error("UMC删除wifi账号失败: " + phone + ", UMC原因: " + rs);
            }

            // 若UMC不存在此账号, 本库也没有存在的意义
            if ("账号不存在".equals(rs)) {
                boolean flag = wifiUserBiz.deleteWifiUser(phone);
                if (flag) {
                    log.error("本库删除wifi账号成功: " + phone + ", 已和UMC保持同步");
                }
            }

        } catch (ServiceException | RemoteException e) {
            log.error("DpWebServiceImpl.delWifi", e);
        }
        return false;
    }

    @Override
    public boolean editWifi(String account, String password, long overdueTime, String phone, Long id) {
        try {
            UserManageAddServicePortType portType = getUserManageAddServicePortType();
            String rs = portType.editPassword(account, password, overdueTime / 1000, phone);
            log.debug(rs);

            // UMC 更新成功后 更新本库
            if (SUCCESS.equals(rs)) {
                WifiUser wifiUser = new WifiUser();
                wifiUser.setId(id);
                wifiUser.setPassword(password);
                int rows = wifiUserBiz.update(wifiUser);
                return rows >= 1;
            }

        } catch (ServiceException | RemoteException e) {
            log.error("DpWebServiceImpl.editWifi", e);
        }
        return false;
    }

    @Override
    public boolean resetUserPassWord(Long wifiUserId) {
        WifiUser wifiUser = wifiUserBiz.findById(wifiUserId);
        return wifiUser != null &&
                this.editWifi(wifiUser.getAccount(),
                        "111111", wifiUser.getOverdueTime().getTime(), wifiUser.getPhone(), wifiUser.getId());
    }

    /**
     * 通过Apache.axis获取webservice
     *
     * @return UMC接口实例
     * @throws ServiceException UMC宕机
     */
    private UserManageAddServicePortType getUserManageAddServicePortType() throws ServiceException {
        UserManageAddServiceLocator locator = new UserManageAddServiceLocator();
        UserManageAddServicePortType portType = locator.getUserManageAddServiceHttpPort();
        // If authorization is required
        ((UserManageAddServiceHttpBindingStub) portType).setUsername("admin");
        ((UserManageAddServiceHttpBindingStub) portType).setPassword("UMCAdministrator");
        ((UserManageAddServiceHttpBindingStub) portType).setTimeout(3 * 1000);
        return portType;
    }

    /**
     * 删除到期的学员
     */
    public void removeWifiUser() {
        List<WifiUser> wifiUsers = wifiUserBiz.findAll();

        // 过滤所有未到期的学员
        wifiUsers = wifiUsers
                .stream().filter(w ->
                        DateUtil.getOneMoreDay(w.getOverdueTime()) < System.currentTimeMillis()
                ).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(wifiUsers)) {
            return;
        }

        // 删除所有到期的学员wifi和UMC
        wifiUsers.forEach(w -> this.delWifi(w.getPhone()));
    }
}
