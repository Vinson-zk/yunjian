CREATE TABLE `t_sc_server_certificate` (
  `c_pk_id` bigint(20) NOT NULL COMMENT '主键 ',
  `c_create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `c_update_user_id` bigint(20) DEFAULT NULL COMMENT '修改用户',
  `c_create_date` timestamp NOT NULL COMMENT '创建时间',
  `c_update_date` timestamp NOT NULL COMMENT '修改时间',
  `c_del_flag` int(1) NOT NULL COMMENT '删除标识；0-正常，1-删除；',
  `c_version` int(9) NOT NULL COMMENT '数据版本',
  `c_remarks` varchar(400) DEFAULT NULL COMMENT '备注',
  `c_spare1` varchar(400) DEFAULT NULL COMMENT '备用字段 1',
  `c_spare2` varchar(400) DEFAULT NULL COMMENT '备用字段 2',
  `c_spare3` varchar(400) DEFAULT NULL COMMENT '备用字段 3',
  `c_spare_json` json DEFAULT NULL COMMENT '备用字段 json',
  `c_valid_start_date` timestamp NULL DEFAULT NULL COMMENT '证书有效期起始日期',
  `c_valid_end_date` timestamp NULL DEFAULT NULL COMMENT '证书有效期结束日期；',
  `c_status` int(1) NOT NULL COMMENT '证书状态；0-正常，1-禁用；',
  `c_server_name` varchar(400) NOT NULL COMMENT '证书对应服务名称',
  `c_private_key` text NOT NULL COMMENT '证书私钥',
  `c_public_key` text NOT NULL COMMENT '证书公钥',
  PRIMARY KEY (`c_pk_id`) COMMENT '服务证书表'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务中心证书';

