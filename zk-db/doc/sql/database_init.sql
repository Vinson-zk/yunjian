CREATE TABLE `t_zk_db_test` (
  `c_id` bigint NOT NULL,
  `c_type` int DEFAULT NULL,
  `c_value` varchar(256) DEFAULT NULL,
  `c_remarks` varchar(256) DEFAULT NULL,
  `c_json` json DEFAULT NULL,
  `c_date` datetime DEFAULT NULL,
  `c_boolean` tinyint DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='zk-db 测试表';
