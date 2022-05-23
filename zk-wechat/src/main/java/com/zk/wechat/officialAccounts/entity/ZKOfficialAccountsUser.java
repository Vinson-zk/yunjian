/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.wechat.officialAccounts.entity;

import java.util.Date;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zk.base.entity.ZKBaseEntity;
import com.zk.core.commons.data.ZKJson;
import com.zk.core.commons.data.ZKJsonArray;
import com.zk.core.utils.ZKDateUtils;
import com.zk.core.utils.ZKIdUtils;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.mybatis.commons.ZKSqlProvider;
import com.zk.wechat.wx.officialAccounts.msgBean.ZKWXUserAuthAccessToken;

/**
 * 账号的用户
 * 
 * @author
 * @version
 */
@ZKTable(name = "t_wx_official_accounts_user", alias = "officialAccountsUser", orderBy = " c_create_date ASC ")
public class ZKOfficialAccountsUser extends ZKBaseEntity<String, ZKOfficialAccountsUser> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKOfficialAccountsUser());
        }
        return sqlProvider;
    }
    
    private static final long serialVersionUID = 1L;

    /**
     * 用户来源渠道；0-未知；1-见面授权；2-关注公众号；
     */
    public static interface KeyWxCannel {
        /**
         * 用户来源渠道；0-未知；
         */
        public static final int unknown = 0;

        /**
         * 用户来源渠道；1-网面授权；
         */
        public static final int webAuth = 1;

        /**
         * 用户来源渠道；2-关注公众号；
         */
        public static final int officialAccount = 2;

        /**
         * 用户来源渠道；3-小程序；
         */
        public static final int miniprogram = 3;

    }

    /**
     * 关注状态；0-取消关注；1-已关注；2-未知；
     */
    public static interface KeyWxSubscribeStatus {
        /**
         * 关注状态；0-取消关注；
         */
        public static final int unsubscribe = 0;

        /**
         * 关注状态；1-已关注
         */
        public static final int subscribe = 1;

        /**
         * 关注状态；2-未知；
         */
        public static final int unknown = 2;
    }
	
	/**
	 * 数据域平台标识；第三方数据绑定标识
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_data_space_platform", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String dataSpacePlatform;	
	/**
	 * 数据域分组标识；第三方数据绑定标识
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_data_space_group", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String dataSpaceGroup;	
	/**
	 * 数据域拥有标识；第三方数据绑定标识
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_data_space_owner", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String dataSpaceOwner;	

	/**
     * 集团代码
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_group_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
    String groupCode;

    /**
     * 公司ID
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_company_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
    String companyId;

    /**
     * 公司代码
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_company_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
    String companyCode;

    /**
     * 微信第三方平台 Appid
     */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_third_party_appid", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String wxThirdPartyAppid;	
	
    /**
     * 账号，公众号或小程序的 appid
     */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_wx_official_account_appid", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
    String wxOfficialAccountAppid;

    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     */
    @Range(min = 0, max = 9, message = "{zk.core.data.validation.rang.int}")
    @ZKColumn(name = "c_wx_subscribe", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
    String wxSubscribe;

	/**
	 * 用户的标识，对当前公众号唯一: openid
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_openid", isInsert = true, isUpdate = false, javaType = String.class, isQuery = false)
	String wxOpenid;	

    /**
     * 微信绑定的手机号
     */
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_wx_phone_num", isInsert = true, isUpdate = true, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
    String phoneNum;

	/**
	 * 用户的昵称: nickname
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_nickname", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String wxNickname;	
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知: sex
	 */
	@Range(min = 0, max = 999999999, message = "{zk.core.data.validation.rang.int}")
	@ZKColumn(name = "c_wx_sex", isInsert = true, isUpdate = true, javaType = Long.class, isQuery = false)
    Integer wxSex;

	/**
	 * 用户所在城市: city
	 */
	@Length(min = 0, max = 256, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_city", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String wxCity;	
	/**
	 * 用户所在国家: country
	 */
	@Length(min = 0, max = 128, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_country", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String wxCountry;	
	/**
	 * 用户所在省份: province
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_province", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String wxProvince;	
	/**
	 * 用户的语言，简体中文为zh_CN: language
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_language", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String wxLanguage;	
	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效:  headimgurl
	 */
    @Length(min = 0, max = 512, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_headimgurl", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String wxHeadimgurl;	
	/**
	 * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间：subscribe_time	
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_wx_subscribe_time_str", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
    String wxSubscribeTimeStr;

    /**
     * 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
     */
    @ZKColumn(name = "c_wx_privilege", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
    ZKJsonArray wxPrivilege;

	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段：unionid
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_unionid", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String wxUnionid;	
	/**
	 * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注：remark
	 */
	@Length(min = 0, max = 128, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_remark", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String wxRemark;	
	/**
	 * 用户所在的分组ID（兼容旧的用户分组接口）：groupid
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_groupid", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String wxGroupid;	
	/**
	 * 用户被打上的标签ID列表：tagid_list
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_tagid_list", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String wxTagidList;	
	/**
	 * 返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，ADD_SCENE_OTHERS 其他：subscribe_scene
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_subscribe_scene", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String wxSubscribeScene;	
	/**
	 * 二维码扫码场景（开发者自定义）：qr_scene
	 */
	@Length(min = 0, max = 128, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_qr_scene", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String wxQrScene;	
	/**
	 * 二维码扫码场景描述（开发者自定义）：qr_scene_str
	 */
	@Length(min = 0, max = 128, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_wx_qr_scene_str", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String wxQrSceneStr;	
	
    /**
     * 小程序会话密钥
     */
    @Length(min = 0, max = 128, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_wx_session_key", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
    String wxSessionKey;

    /**
     * 网页授权时，存放的 zk 平台的 用户授权 access_token 对象
     */
	@ZKColumn(name = "c_wx_zk_access_token", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = false)
    ZKJson wxZKAccessToken;
	
    /**
     * 用户来源渠道；0-未知；1-网面授权；2-关注公众号；
     */
    @Range(min = 0, max = 9, message = "{zk.core.data.validation.rang.int}")
    @ZKColumn(name = "c_wx_channel", isInsert = true, isUpdate = true, javaType = Integer.class, isQuery = false)
    Integer wxChannel;

    /**
     * 关注状态；0-取消关注；1-已关注；2-未知；
     */
    @Range(min = 0, max = 9, message = "{zk.core.data.validation.rang.int}")
    @ZKColumn(name = "c_wx_subscribe_status", isInsert = true, isUpdate = true, javaType = Integer.class, isQuery = false)
    Integer wxSubscribeStatus;

    /**
     * 关注时间
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    @JsonFormat(pattern = ZKDateUtils.DF_yyyy_MM_dd_HH_mm_ss, timezone = timezone)
    @ZKColumn(name = "c_wx_subscribe_date", isInsert = true, isUpdate = true, javaType = Date.class, isQuery = false)
    Date wxSubscribeDate;

	public ZKOfficialAccountsUser() {
		super();
	}

	public ZKOfficialAccountsUser(String pkId){
		super(pkId);
	}
	
	/**
	 * 数据域平台标识；第三方数据绑定标识	
	 */	
	public String getDataSpacePlatform() {
		return dataSpacePlatform;
	}
	
	/**
	 * 数据域平台标识；第三方数据绑定标识
	 */	
	public void setDataSpacePlatform(String dataSpacePlatform) {
		this.dataSpacePlatform = dataSpacePlatform;
	}
	/**
	 * 数据域分组标识；第三方数据绑定标识	
	 */	
	public String getDataSpaceGroup() {
		return dataSpaceGroup;
	}
	
	/**
	 * 数据域分组标识；第三方数据绑定标识
	 */	
	public void setDataSpaceGroup(String dataSpaceGroup) {
		this.dataSpaceGroup = dataSpaceGroup;
	}
	/**
	 * 数据域拥有标识；第三方数据绑定标识	
	 */	
	public String getDataSpaceOwner() {
		return dataSpaceOwner;
	}
	
	/**
	 * 数据域拥有标识；第三方数据绑定标识
	 */	
	public void setDataSpaceOwner(String dataSpaceOwner) {
		this.dataSpaceOwner = dataSpaceOwner;
	}
	/**
	 * 微信第三方平台 Appid	
	 */	
	public String getWxThirdPartyAppid() {
		return wxThirdPartyAppid;
	}
	
	/**
	 * 微信第三方平台 Appid
	 */	
	public void setWxThirdPartyAppid(String wxThirdPartyAppid) {
		this.wxThirdPartyAppid = wxThirdPartyAppid;
	}
	
    /**
     * @return wxOfficialAccountAppid
     */
    public String getWxOfficialAccountAppid() {
        return wxOfficialAccountAppid;
    }

    /**
     * @param wxOfficialAccountAppid
     */
    public void setWxOfficialAccountAppid(String wxOfficialAccountAppid) {
        this.wxOfficialAccountAppid = wxOfficialAccountAppid;
    }

    /**
     * @return 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     */
    public String getWxSubscribe() {
        return wxSubscribe;
    }

    /**
     * @param 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     */
    public void setWxSubscribe(String wxSubscribe) {
        this.wxSubscribe = wxSubscribe;
    }

    /**
     * 用户的标识，对当前公众号唯一: openid
     */	
	public String getWxOpenid() {
		return wxOpenid;
	}
	
	/**
	 * 用户的标识，对当前公众号唯一: openid
	 */	
	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}

    /**
     * 微信绑定的手机号
     * 
     * @return phoneNum sa
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * 微信绑定的手机号
     * 
     * @param phoneNum
     *            the phoneNum to set
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

	/**
	 * 用户的昵称: nickname	
	 */	
	public String getWxNickname() {
		return wxNickname;
	}
	
	/**
	 * 用户的昵称: nickname
	 */	
	public void setWxNickname(String wxNickname) {
		this.wxNickname = wxNickname;
	}
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知: sex	
	 */	
    public Integer getWxSex() {
		return wxSex;
	}
	
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知: sex
	 */	
    public void setWxSex(Integer wxSex) {
		this.wxSex = wxSex;
	}
	/**
	 * 用户所在城市: city	
	 */	
	public String getWxCity() {
		return wxCity;
	}
	
	/**
	 * 用户所在城市: city
	 */	
	public void setWxCity(String wxCity) {
		this.wxCity = wxCity;
	}
	/**
	 * 用户所在国家: country	
	 */	
	public String getWxCountry() {
		return wxCountry;
	}
	
	/**
	 * 用户所在国家: country
	 */	
	public void setWxCountry(String wxCountry) {
		this.wxCountry = wxCountry;
	}
	/**
	 * 用户所在省份: province	
	 */	
	public String getWxProvince() {
		return wxProvince;
	}
	
	/**
	 * 用户所在省份: province
	 */	
	public void setWxProvince(String wxProvince) {
		this.wxProvince = wxProvince;
	}
	/**
	 * 用户的语言，简体中文为zh_CN: language	
	 */	
	public String getWxLanguage() {
		return wxLanguage;
	}
	
	/**
	 * 用户的语言，简体中文为zh_CN: language
	 */	
	public void setWxLanguage(String wxLanguage) {
		this.wxLanguage = wxLanguage;
	}
	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效:  headimgurl	
	 */	
	public String getWxHeadimgurl() {
		return wxHeadimgurl;
	}
	
	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效:  headimgurl
	 */	
	public void setWxHeadimgurl(String wxHeadimgurl) {
		this.wxHeadimgurl = wxHeadimgurl;
	}
	/**
	 * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间：subscribe_time		
	 */	
    public String getWxSubscribeTimeStr() {
        return wxSubscribeTimeStr;
	}
	
	/**
	 * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间：subscribe_time	
	 */	
    public void setWxSubscribeTimeStr(String wxSubscribeTimeStr) {
        this.wxSubscribeTimeStr = wxSubscribeTimeStr;
	}

    /**
     * @return wxPrivilege sa
     */
    public ZKJsonArray getWxPrivilege() {
        return wxPrivilege;
    }

    /**
     * @param wxPrivilege
     *            the wxPrivilege to set
     */
    public void setWxPrivilege(ZKJsonArray wxPrivilege) {
        this.wxPrivilege = wxPrivilege;
    }

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段：unionid
     */	
	public String getWxUnionid() {
		return wxUnionid;
	}
	
	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段：unionid
	 */	
	public void setWxUnionid(String wxUnionid) {
		this.wxUnionid = wxUnionid;
	}
	/**
	 * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注：remark	
	 */	
	public String getWxRemark() {
		return wxRemark;
	}
	
	/**
	 * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注：remark
	 */	
	public void setWxRemark(String wxRemark) {
		this.wxRemark = wxRemark;
	}
	/**
	 * 用户所在的分组ID（兼容旧的用户分组接口）：groupid	
	 */	
	public String getWxGroupid() {
		return wxGroupid;
	}
	
	/**
	 * 用户所在的分组ID（兼容旧的用户分组接口）：groupid
	 */	
	public void setWxGroupid(String wxGroupid) {
		this.wxGroupid = wxGroupid;
	}
	/**
	 * 用户被打上的标签ID列表：tagid_list	
	 */	
	public String getWxTagidList() {
		return wxTagidList;
	}
	
	/**
	 * 用户被打上的标签ID列表：tagid_list
	 */	
	public void setWxTagidList(String wxTagidList) {
		this.wxTagidList = wxTagidList;
	}
	/**
	 * 返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，ADD_SCENE_OTHERS 其他：subscribe_scene	
	 */	
	public String getWxSubscribeScene() {
		return wxSubscribeScene;
	}
	
	/**
	 * 返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，ADD_SCENE_OTHERS 其他：subscribe_scene
	 */	
	public void setWxSubscribeScene(String wxSubscribeScene) {
		this.wxSubscribeScene = wxSubscribeScene;
	}
	/**
	 * 二维码扫码场景（开发者自定义）：qr_scene	
	 */	
	public String getWxQrScene() {
		return wxQrScene;
	}
	
	/**
	 * 二维码扫码场景（开发者自定义）：qr_scene
	 */	
	public void setWxQrScene(String wxQrScene) {
		this.wxQrScene = wxQrScene;
	}
	/**
	 * 二维码扫码场景描述（开发者自定义）：qr_scene_str	
	 */	
	public String getWxQrSceneStr() {
		return wxQrSceneStr;
	}
	
	/**
	 * 二维码扫码场景描述（开发者自定义）：qr_scene_str
	 */	
	public void setWxQrSceneStr(String wxQrSceneStr) {
		this.wxQrSceneStr = wxQrSceneStr;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }
	
    /**
     * 集团代码
     * 
     * @return groupCode sa
     */
    public String getGroupCode() {
        return groupCode;
    }

    /**
     * 集团代码
     * 
     * @param groupCode
     *            the groupCode to set
     */
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    /**
     * 公司ID
     * 
     * @return companyId sa
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 公司ID
     * 
     * @param companyId
     *            the companyId to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * 公司代码
     * 
     * @return companyCode sa
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * 公司代码
     * 
     * @param companyCode
     *            the companyCode to set
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * @return wxSessionKey sa
     */
    public String getWxSessionKey() {
        return wxSessionKey;
    }

    /**
     * @param wxSessionKey
     *            the wxSessionKey to set
     */
    public void setWxSessionKey(String wxSessionKey) {
        this.wxSessionKey = wxSessionKey;
    }

    /**
     * @return wxZKAccessToken sa
     */
    public ZKJson getWxZKAccessToken() {
        return wxZKAccessToken;
    }

    /**
     * @param wxZKAccessToken
     *            the wxZKAccessToken to set
     */
    public void setWxZKAccessToken(ZKJson wxZKAccessToken) {
        this.wxZKAccessToken = wxZKAccessToken;
    }

    @Transient
    @XmlTransient
    @JsonIgnore
    public ZKWXUserAuthAccessToken getAccessToken() {
        return this.getWxZKAccessToken() == null ? null
            : this.getWxZKAccessToken().format(ZKWXUserAuthAccessToken.class);
    }

    /**
     * @return wxCannel sa
     */
    public Integer getWxChannel() {
        return wxChannel;
    }

    /**
     * @return wxSubscribeStatus sa
     */
    public Integer getWxSubscribeStatus() {
        return wxSubscribeStatus;
    }

    /**
     * @param wxCannel
     *            the wxCannel to set
     */
    public void setWxChannel(Integer wxChannel) {
        this.wxChannel = wxChannel;
    }

    /**
     * @param wxSubscribeStatus
     *            the wxSubscribeStatus to set
     */
    public void setWxSubscribeStatus(Integer wxSubscribeStatus) {
        this.wxSubscribeStatus = wxSubscribeStatus;
    }

    /**
     * @return wxSubscribeDate sa
     */
    public Date getWxSubscribeDate() {
        return wxSubscribeDate;
    }

    /**
     * @param wxSubscribeDate
     *            the wxSubscribeDate to set
     */
    public void setWxSubscribeDate(Date wxSubscribeDate) {
        this.wxSubscribeDate = wxSubscribeDate;
    }

}
