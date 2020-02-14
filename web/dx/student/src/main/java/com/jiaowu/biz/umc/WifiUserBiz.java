package com.jiaowu.biz.umc;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.jiaowu.dao.umc.WifiUserDao;
import com.jiaowu.entity.umc.WifiUser;
import com.jiaowu.entity.umc.WifiUserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * wifi用户
 *
 * @author YaoZhen
 * @date 03-29, 09:43, 2018.
 */
@Service
public class WifiUserBiz extends BaseBiz<WifiUser, WifiUserDao> {

    public boolean saveWifiUser(String phone, String realName, Date registerDeadline, Long oldId) {
        boolean flag = false;
        try {
            // 手机号已存在 视为添加成功
            if (alreadyExist(phone)) {
                return true;
            }

            WifiUser wifiUser = new WifiUser();
            wifiUser.setAccount(phone);
            wifiUser.setUsername(realName);
            wifiUser.setPassword("111111");
            wifiUser.setOnlineMax(1);
            wifiUser.setPart("/贵阳市委党校/学员/计划内班次/");
            wifiUser.setOverdueTime(new Timestamp(registerDeadline.getTime()));
            wifiUser.setPhone(phone);
            wifiUser.setStudentLinkId(oldId);
            super.save(wifiUser);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询该用户是否已存在
     */
    public boolean alreadyExist(String phone) {
        List<WifiUser> wifiUsers = super.find(null, " phone ='" + phone + "'");
        return !CollectionUtils.isEmpty(wifiUsers);
    }

    /**
     * 手机号一样的全删掉
     *
     * @param phone 手机号
     * @return 是否删除
     */
    public boolean deleteWifiUser(String phone) {
        try {
            WifiUser wifiUser = new WifiUser();
            wifiUser.setPhone(phone);

            List<WifiUser> wifiUserList = super.find(null, "phone = '" + phone + "'");
            List<Long> ids = new ArrayList<>();
            wifiUserList.forEach(w -> ids.add(w.getId()));
            super.deleteByIds(ids);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<WifiUserVO> findWifiUserVO(Pagination pagination, String whereSql) {
        List<WifiUser> wifiUserList = this.find(pagination, whereSql + " ORDER BY id DESC");

        return convert(wifiUserList);
    }

    private List<WifiUserVO> convert(List<WifiUser> wifiUserList) {
        List<WifiUserVO> wifiUserVOS = new ArrayList<>();
        wifiUserList.forEach(w ->
                {
                    WifiUserVO vo = new WifiUserVO();
                    wifiUserVOS.add(vo);
                    BeanUtils.copyProperties(w, vo);
                    vo.setOverTime(new SimpleDateFormat("yyyy-MM-dd").format(w.getOverdueTime()));
                }
        );
        return wifiUserVOS;
    }
}
