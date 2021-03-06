/** 
* Copyright (c) 2004-2020 ZK-Vinson Technologies, Inc.
* address: 
* All rights reserved. 
* 
* This software is the confidential and proprietary information of 
* ZK-Vinson Technologies, Inc. ("Confidential Information"). You shall not 
* disclose such Confidential Information and shall use it only in 
* accordance with the terms of the license agreement you entered into 
* with ZK-Vinson. 
*
* @Title: ZKSysResFuncApiReqChannelService.java 
* @author Vinson 
* @Package com.zk.sys.res.service 
* @Description: TODO(simple description this file what to do. ) 
* @date Nov 30, 2021 10:54:28 AM 
* @version V1.0 
*/
package com.zk.sys.res.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.core.exception.ZKCodeException;
import com.zk.sys.res.dao.ZKSysResFuncApiReqChannelDao;
import com.zk.sys.res.entity.ZKSysResFuncApi;
import com.zk.sys.res.entity.ZKSysResFuncApiReqChannel;
import com.zk.sys.res.entity.ZKSysResRequestChannel;

/**
 * @ClassName: ZKSysResFuncApiReqChannelService
 * @Description: TODO(simple description this class what to do. )
 * @author Vinson
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class ZKSysResFuncApiReqChannelService
        extends ZKBaseService<String, ZKSysResFuncApiReqChannel, ZKSysResFuncApiReqChannelDao> {

    // public ZKPage<ZKSysResFuncApiReqChannel> findByChannelCode(ZKPage<ZKSysResFuncApiReqChannel> page,
    // String channelCode, String systemCode) {
    // ZKSysResFuncApiReqChannel e = new ZKSysResFuncApiReqChannel();
    // e.setChannelCode(channelCode);
    // e.setSystemCode(systemCode);
    // return super.findPage(page, e);
    // }
    
    // public ZKPage<ZKSysResFuncApiReqChannel> findByApiCode(ZKPage<ZKSysResFuncApiReqChannel> page, String apiCode) {
    // ZKSysResFuncApiReqChannel e = new ZKSysResFuncApiReqChannel();
    // e.setFuncApiCode(apiCode);
    // return super.findPage(page, e);
    // }

    /********************************************************************************************/
    /*** ??? ***********************************************************************************/
    /********************************************************************************************/

    /**
     * ????????????ID?????????ID??? ????????????
     *
     * @Title: getByRelationId
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Nov 30, 2021 11:03:47 AM
     * @param channelId
     *            ??????ID
     * @param funcApiId
     *            API????????????ID
     * @return
     * @return ZKSysResFuncApiReqChannel
     */
    public ZKSysResFuncApiReqChannel getByRelationId(String channelId, String funcApiId) {
        return this.dao.getByRelationId(ZKSysResFuncApiReqChannel.initSqlProvider().getTableName(),
                ZKSysResFuncApiReqChannel.initSqlProvider().getTableAlias(),
                ZKSysResFuncApiReqChannel.initSqlProvider().getSqlBlockSelCols(), channelId, funcApiId);
    }

    /**
     * ?????? ???????????? ????????? ????????????
     *
     * @Title: getByCode
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Nov 30, 2021 11:03:33 AM
     * @param channelCode
     *            ????????????
     * @param systemCode
     *            ??????API????????????????????????
     * @param funcApiCode
     *            API??????????????????
     * @return
     * @return ZKSysResFuncApiReqChannel
     */
    ZKSysResFuncApiReqChannel getByCode(String channelCode, String systemCode, String funcApiCode) {
        return this.dao.getByCode(ZKSysResFuncApiReqChannel.initSqlProvider().getTableName(),
                ZKSysResFuncApiReqChannel.initSqlProvider().getTableAlias(),
                ZKSysResFuncApiReqChannel.initSqlProvider().getSqlBlockSelCols(), channelCode, systemCode, funcApiCode);
    }

    /********************************************************************************************/
    /*** ??????????????? *******************************************************************************/
    /********************************************************************************************/

    /**
     * ????????????????????????????????????????????????????????????????????????
     *
     * @Title: save
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Nov 30, 2021 12:13:07 PM
     * @param channel
     *            ????????????
     * @param funcApi
     *            API????????????
     * @return int
     */
    @Transactional(readOnly = false)
    public ZKSysResFuncApiReqChannel save(ZKSysResRequestChannel channel, ZKSysResFuncApi funcApi) {
        ZKSysResFuncApiReqChannel funcApiReqChannel = new ZKSysResFuncApiReqChannel(channel.getPkId(),
                funcApi.getPkId());
        // ?????????????????????????????????
        ZKSysResFuncApiReqChannel old = this.getByRelationId(channel.getPkId(), funcApi.getPkId());
        if (old != null) {
            if (ZKSysResFuncApiReqChannel.DEL_FLAG.normal == old.getDelFlag().intValue()) {
                // ??????????????????
                return old;
            }
            else {
                funcApiReqChannel.setPkId(old.getPkId());
                funcApiReqChannel.setDelFlag(ZKSysResFuncApiReqChannel.DEL_FLAG.normal);
            }
        }
        // ????????????
        funcApiReqChannel.setChannelCode(channel.getCode());
        funcApiReqChannel.setSystemCode(funcApi.getSystemCode());
        funcApiReqChannel.setFuncApiCode(funcApi.getCode());
        super.save(funcApiReqChannel);
        return funcApiReqChannel;
    }

    /**
     * ????????????????????????????????? API???????????????
     *
     * @Title: addByChannel
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Nov 30, 2021 12:04:07 PM
     * @param channel
     *            ????????????
     * @param addFuncApis
     *            ??????????????? API????????????
     * @param delFuncApis
     *            ????????????????????? API????????????????????? null ?????????????????????????????? API????????????????????????????????? API???????????????
     * @return List<ZKSysResFuncApiReqChannel>
     */
    @Transactional(readOnly = false)
    public List<ZKSysResFuncApiReqChannel> addByChannel(ZKSysResRequestChannel channel,
            List<ZKSysResFuncApi> addFuncApis, List<ZKSysResFuncApi> delFuncApis) {
        if (channel == null) {
            log.error("[>_<:20220406-1916-002] ?????????????????????!");
            throw new ZKCodeException("zk.sys.000006", "?????????????????????");
        }
        // ????????? ???????????????????????????
        if(delFuncApis == null) {
            this.diskDelByChannelId(channel.getPkId());
        }else {
            delFuncApis.forEach(item -> {
                this.diskDelByChannelIdAndApiId(channel.getPkId(), item.getPkId());
            });
        }
        // ???????????????????????????
        if (addFuncApis != null && !addFuncApis.isEmpty()) {
            List<ZKSysResFuncApiReqChannel> res = new ArrayList<>();
            addFuncApis.forEach(item -> {
                res.add(this.save(channel, item));
            });
            return res;
        }
        return Collections.emptyList();
    }

    /**
     * ??? API???????????? ?????? ???????????? ??????
     *
     * @Title: addByFuncApi
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Nov 30, 2021 12:13:00 PM
     * @param funcApi
     *            ?????? API????????????
     * @param addChannels
     *            ???????????????????????????
     * @param delChannels
     *            ??????????????????????????????????????? null ?????????????????????????????? ????????????????????????????????? ???????????????
     * @return List<ZKSysResFuncApiReqChannel>
     */
    @Transactional(readOnly = false)
    public List<ZKSysResFuncApiReqChannel> addByFuncApi(ZKSysResFuncApi funcApi,
            List<ZKSysResRequestChannel> addChannels, List<ZKSysResRequestChannel> delChannels) {
        if (funcApi == null) {
            log.error("[>_<:20220406-1916-001] ????????????API?????????!");
            throw new ZKCodeException("zk.sys.000005", "????????????API?????????");
        }
        // ????????? ???????????????????????????
        if (delChannels == null) {
            this.diskDelByApiId(funcApi.getPkId());
        }
        else {
            delChannels.forEach(item -> {
                this.diskDelByChannelIdAndApiId(item.getPkId(), funcApi.getPkId());
            });
        }
        // ???????????????????????????
        if (addChannels != null && !addChannels.isEmpty()) {
            List<ZKSysResFuncApiReqChannel> res = new ArrayList<>();
            addChannels.forEach(item -> {
                res.add(this.save(item, funcApi));
            });
            return res;
        }
        return Collections.emptyList();
    }

    /********************************************************************************************/
    /*** ?????? ***********************************************************************************/
    /********************************************************************************************/

//    /**
//     * ?????? ??????ID ??? API????????????ID ???????????? ???????????????
//     *
//     * @Title: delByChannelIdAndApiId
//     * @Description: TODO(simple description this method what to do.)
//     * @author Vinson
//     * @date Apr 1, 2022 10:52:19 AM
//     * @param channelPkId
//     * @param apiPkId
//     * @return
//     * @return int
//     */
//    @Transactional(readOnly = false)
//    public int delByChannelIdAndApiId(String channelPkId, String apiPkId) {
//        return this.dao.delByChannelIdAndApiId(ZKSysResFuncApiReqChannel.initSqlProvider().getTableName(), channelPkId,
//                apiPkId, ZKSysResFuncApiReqChannel.DEL_FLAG.delete);
//    }
//
//    /**
//     * ?????? ??????ID?????????????????????????????? API??????????????????
//     *
//     * @Title: delByChannelId
//     * @Description: TODO(simple description this method what to do.)
//     * @author Vinson
//     * @date Nov 30, 2021 11:32:27 AM
//     * @param channelPkId
//     * @return
//     * @return int
//     */
//    @Transactional(readOnly = false)
//    public int delByChannelId(String channelPkId) {
//        return this.dao.delByChannelId(ZKSysResFuncApiReqChannel.initSqlProvider().getTableName(), channelPkId,
//                ZKSysResFuncApiReqChannel.DEL_FLAG.delete);
//    }
//
//    /**
//     * ?????? API????????????ID??????????????????????????????????????????
//     *
//     * @Title: delByApiId
//     * @Description: TODO(simple description this method what to do.)
//     * @author Vinson
//     * @date Nov 30, 2021 11:32:34 AM
//     * @param apiPkId
//     * @return
//     * @return int
//     */
//    @Transactional(readOnly = false)
//    public int delByApiId(String apiPkId) {
//        return this.dao.delByApiId(ZKSysResFuncApiReqChannel.initSqlProvider().getTableName(), apiPkId,
//                ZKSysResFuncApiReqChannel.DEL_FLAG.delete);
//    }

    /**
     * ?????? ??????ID??? API????????????ID ????????????????????????
     *
     * @Title: diskDelByChannelIdAndApiId
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 1, 2022 10:52:27 AM
     * @param channelPkId
     * @param apiPkId
     * @return
     * @return int
     */
    public int diskDelByChannelIdAndApiId(String channelPkId, String apiPkId) {
        return this.dao.diskDelByChannelIdAndApiId(ZKSysResFuncApiReqChannel.initSqlProvider().getTableName(),
                channelPkId, apiPkId);
    }

    /**
     * ?????? ??????ID?????????????????????????????? API??????????????????
     *
     * @Title: diskDelByChannelId
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Nov 30, 2021 11:32:30 AM
     * @param channelPkId
     * @return
     * @return int
     */
    @Transactional(readOnly = false)
    public int diskDelByChannelId(String channelPkId) {
        return this.dao.diskDelByChannelId(ZKSysResFuncApiReqChannel.initSqlProvider().getTableName(), channelPkId);
    }

    /**
     * ?????? API????????????ID??????????????????????????????????????????
     *
     * @Title: diskDelByApiId
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Nov 30, 2021 11:32:39 AM
     * @param apiPkId
     * @return
     * @return int
     */
    @Transactional(readOnly = false)
    public int diskDelByApiId(String apiPkId) {
        return this.dao.diskDelByApiId(ZKSysResFuncApiReqChannel.initSqlProvider().getTableName(), apiPkId);
    }

}

